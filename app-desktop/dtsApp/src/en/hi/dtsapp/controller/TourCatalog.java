package en.hi.dtsapp.controller;

import en.hi.dtsapp.model.CustomerPerson;
import en.hi.dtsapp.model.DAOs.BookingDAO;
import en.hi.dtsapp.model.DAOs.DTSMethods;
import en.hi.dtsapp.model.Tour;
import en.hi.dtsapp.model.DAOs.TourDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*****************************************************************************************************************************
 * Class objective: 
 * Accepts input, optionally validates it, and converts it to commands for the model or view.
 *****************************************************************************************************************************
 * Here's what you need to know to use it:
 *      public TourCatalog() 
 *          constructor. Builds two private variables:
 *          TOUR_LIST contains every single Tour from the Tour database
 *          DISTINCT_NAME_TOUR_LIST contains only one of each (by name, i.e. no repetitions of the same tour on other dates)
 * 
 *       public ObservableList getFullTourList() 
 *          returns an ObserveableList copy of TOUR_LIST which contains every single tour from the database
 * 
 *      public ObservableList getDistinctNameTourList()
 *          returns an ObserveableList copy of DISTINCT_NAME_TOUR_LIST (for easy browsing)
 * 
 ****************************************************************************************************************************
 *      There are two methods you will be using:
 *          getToursBySearchParameters(..)
 *          bookTour(..)
 *      see detailed documentation right below.
 ****************************************************************************************************************************
 * @author Erling Oskar Kristjansson eok4@hi.is
 *         David Freyr Bjornsson dfb2@hi.is
 *         Andrea Osk Sigurdardottir aos26@hi.is
 * Haskoli Islands
 */
public class TourCatalog {
    
    private final List<Tour> TOUR_LIST;
    private final List<Tour> DISTINCT_NAME_TOUR_LIST;
    
    public TourCatalog() throws Exception {
        this.TOUR_LIST = TourDAO.initiateTourCatalog();
        this.DISTINCT_NAME_TOUR_LIST = TourDAO.distinctTourCatalog();
    //    displaySomeTours();
    }
    
    // Returns an observable List with all the tours
    public ObservableList<Tour> getFullTourList() {
        return FXCollections.observableArrayList(TOUR_LIST);
    }
    
    // Returns an observable List with one tour of each name
    public ObservableList<Tour> getDistinctNameTourList() {
        return FXCollections.observableArrayList(DISTINCT_NAME_TOUR_LIST);
    }
    
    /**
     * Primary method for receiving an observable list filtered by some (User) Input
     * 
     * @param keyword String
     * @param dateFrom LocalDate 
     * @param dateTo LocalDate
     * @param keywordExceptions List of Strings 
     * @return ObservableList with all the tours in TOUR_LIST that are 
     *      within the specified dates [dateFrom, dateTo], both included,
     *      and have anything to do with any of the words in the String keyword,
     *              e.g. it's part of TourName, TourOperator, 
     *              TourLocation, TourInfo or TourKeywords.
     *        If the String keyword is in keywordExceptions,
     *          it will not filter the list
     *          (e.g. put UI SearchField promptText into this List)
     *      Returns an empty list if DateTo is before DateFrom,
     *      Returns an empty list if keyword is just some nonsense
     */
    public ObservableList<Tour> getToursBySearchParameters(
            String keyword, LocalDate dateFrom, LocalDate dateTo, List<String> keywordExceptions) {
        ObservableList <Tour> newTourList = this.getFullTourList();
        if(!(keyword == null || keyword.isEmpty() 
                || keywordExceptions.contains(keyword))){
            newTourList = getToursByKeyword(newTourList, keyword.toLowerCase());
        }
        if(dateFrom == null) dateFrom = LocalDate.now();
        if(dateTo == null) dateTo = LocalDate.MAX;
        newTourList = getToursByDate(newTourList, dateFrom, dateTo);
        return newTourList;
    }
    
    /**
     * Primary method for booking a tour
     * 
     * If successful in action, this class should 
     *  Increase the number of bookings of the specified tour by amount passengers
     *  Register a booking for that exact tour in the Booking Table
     *  If it returns a value, it has either done both of those things or neither.
     * @return  An integer that should help user of this method to choose 
     *     an (error) message to display in UI. 
     *      E.g. display "Booking Confirmed" if 1 is returned,
     *                   "You have already booked this tour" if 0 is returned,
     *                   "Failed to connect to database" if -1 is returned,
     *                    
     *          1 if value was inserted
     *          0 if customer already booked this exact tour
     *         -1 if failed to connect to database
     *         -2,...,-8 if bad input contains dubious characters
     *              (see BookingDAO.insertBooking(..) for details,
     *              basically potential errors caused by flaws in
     *              Tour.java or CustemerPerson.java)
     *         -9 if the tour does not have enough free space
     *        -10 if the tour is not in TOUR_LIST
     *       -404 if it tour is in TourCatalog but BookingDAO.insertBooking(..)
     *              never returned a value or was never called. 
     *              i.e. Unknown Error 404.
     * @param cp customerPerson who is making the booking.
     * @param tour tour that cp wants to book
     * @param passengers number of passengers/travelers included in booking 
     */
    public int bookTour(CustomerPerson cp, Tour tour, int passengers){
        int result = -404;
        int i = this.TOUR_LIST.indexOf(tour);
        Tour iTour;
        boolean b = false;
        if(i!=-1){
            iTour = this.TOUR_LIST.get(i);
            b =  this.TOUR_LIST.get(i).addTravelers(passengers);
            if(b) {
            //    System.out.println(iTour.getTravelers());
                try{
                    result = BookingDAO.insertBooking(
                        cp.getEmail(),
                        tour.getName(),
                        tour.getOperator(),
                        tour.getLocation(),
                        DTSMethods.TIME_FORMATTER.format(tour.getStartTime()),
                        DTSMethods.DATE_FORMATTER.format(tour.getDate()),
                        String.valueOf(passengers));
                } catch(SQLException e) {
                    System.err.println(e.getMessage());
                    System.err.println("Probably didn't update booking because "
                            + "SQLException caught in TourCatalog.bookTour()");
                }
            }
        }
        else {
            System.err.println("Tour not in TourCatalog.TOUR_LIST. Didn't update booking.");
            return -10;
        }
        if(b && result == 1){
            int j = this.DISTINCT_NAME_TOUR_LIST.indexOf(tour);
            if(j!=-1){
                this.DISTINCT_NAME_TOUR_LIST.get(j).addTravelers(passengers);
            }
        //    else System.out.println("Tour not in TourCatalog.DISTINCT_NAME_TOUR_LIST. Didn't update booking there.");
        }
        // Decrement the value of the TOUR_LIST again because we never should've incremented it in the first place
        if(b && result != 1 && i != -1 && iTour != null) b = iTour.addTravelers(-passengers);
        return result;
    }
    
    /*********************Private Methods here below***************************/
    /*******************Called by Public Methods above***********************/
    
    // returns the tours that are in fullTourList and have something to do with
    //          the keyword, e.g. it's part of TourName, TourOperator, TourLocation, TourInfo or TourKeywords
    private ObservableList<Tour> getToursByKeyword(ObservableList<Tour> fullTourList, String keyword) {
            
            ObservableList <Tour> newTourList = fullTourList; 
            for(String kw: keyword.split(" ")){
                newTourList = newTourList.filtered(
                       s -> s.getKeywords().toLowerCase().contains(kw) 
                    || s.getName().toLowerCase().contains(kw) 
                    || s.getInfo().toLowerCase().contains(kw) 
                    || s.getLocation().toLowerCase().contains(kw) 
                    || s.getOperator().toLowerCase().contains(kw) );
            }
            
            return newTourList;
    }

    // returns the tours that are in fullTourList and 
    //           are in the time period [dateFrom, dateTo], both included.
    // returns an empty list if dateFrom is after dateTo
     private ObservableList<Tour> getToursByDate(
             ObservableList<Tour> fullTourList, LocalDate dateFrom, LocalDate dateTo) {
         if(dateFrom == null) dateFrom = LocalDate.now(); // 3 unnecesary lines because this is a private function??
         if(dateTo == null) dateTo = LocalDate.MAX;
        // System.out.println(dateFrom.toString() + "\n"+ dateTo.toString());
         ObservableList  <Tour> newTourList = FXCollections.observableArrayList();
         for(Tour s: fullTourList) {
            LocalDate sDate = s.getDate();
            if((sDate.equals(dateFrom) || sDate.isAfter(dateFrom))
                && (sDate.equals(dateTo) || sDate.isBefore(dateTo))) newTourList.add(s);
         }
         return newTourList;
    }
   
    private void displaySomeTours() {     // Basically a test method.
        System.out.println("Displaying some Tours:");
        System.out.println(TOUR_LIST.get(10).getStartTime());
        System.out.println(TOUR_LIST.get(100).getStartTime());
        System.out.println(TOUR_LIST.get(700).getStartTime());
    }
}
