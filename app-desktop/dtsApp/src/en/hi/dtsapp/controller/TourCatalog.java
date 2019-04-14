package en.hi.dtsapp.controller;

import en.hi.dtsapp.model.tours.*;
import en.hi.dtsapp.model.bookings.*;
import en.hi.dtsapp.model.people.CustomerPerson;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private CustomerPerson CustomerPerson;
    
    /**
     * @throws ClassNotFoundException JDBC Driver class not found
     * @throws SQLException Failed to get connection to database,
     *                      or unexplained error from ResultSet
     * @throws ParseException Failed to parse LocalDate or LocalTime to String
     */
    public TourCatalog() 
            throws ClassNotFoundException, SQLException, ParseException {
        try (Connection conn = TourDAO.getConnection()) {
            this.TOUR_LIST = TourDAO.initiateTourCatalog(conn);
            this.DISTINCT_NAME_TOUR_LIST = TourDAO.distinctTourCatalog(conn);
        }
    }
    
    /**
     * Creates a CustomerPerson object with these values.
     * @param name
     * @param password
     * @param email
     * @throws IllegalArgumentException if some input is in the wrong format 
     *         or email is already in use. Catch it and print the error message.
     * @throws ClassNotFoundException JDBC Driver class not found
     * @throws SQLException Failed to get connection to database,
     *                      or unexplained error from ResultSet
     */
    public void setCustomerPerson(String name, String password, String email) 
            throws IllegalArgumentException, ClassNotFoundException, SQLException{
        this.CustomerPerson = new CustomerPerson(name, password, email);
        this.CustomerPerson.insertToDB();
    }
    
    /**
     * @return CustomerPerson connected to this TourCatalog
     */
    public CustomerPerson getCustomerPerson(){
        if(this.CustomerPerson == null) throw new NullPointerException("CustomerPerson has not been initialized.");
        return this.CustomerPerson;
    }
    
    /**
     * @return observable List with all the tours
     */
    public ObservableList<Tour> getFullTourList() {
        return FXCollections.observableArrayList(TOUR_LIST);
    }
    
    /**
     * @return observable List with tours, one with each name
     */    public ObservableList<Tour> getDistinctNameTourList() {
        return FXCollections.observableArrayList(DISTINCT_NAME_TOUR_LIST);
    }
    
    /**
     * Primary method for receiving an observable list filtered by some (User) Input
     * See overloaded method without List input parameter to ignore exceptions.
     * @param keyword String 
     * @param dateFrom LocalDate 
     * @param dateTo LocalDate 
     * @param keywordExceptions List of Strings that will be excluded from the search
     * @return ObservableList with all the tours in TOUR_LIST that are 
     *      within the specified dates [dateFrom, dateTo], both included,
     *      and have anything to do with any of the words in the String keyword,
     *              e.g. it's part of TourName, TourOperator, 
     *              TourLocation, TourInfo or TourKeywords.
     *        If the String keyword is in keywordExceptions,
     *          the method will not filter the list
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
     * Primary method for receiving an observable list filtered by some (User) Input
     * See overloaded method with additional List input parameter for exceptions.
     * @param keyword String 
     * @param dateFrom LocalDate 
     * @param dateTo LocalDate 
     * @return ObservableList with all the tours in TOUR_LIST that are 
     *      within the specified dates [dateFrom, dateTo], both included,
     *      and have anything to do with any of the words in the String keyword,
     *              e.g. it's part of TourName, TourOperator, 
     *              TourLocation, TourInfo or TourKeywords.
     *      Returns an empty list if DateTo is before DateFrom,
     *      Returns an empty list if keyword is just some nonsense
     */
    public ObservableList<Tour> getToursBySearchParameters(
            String keyword, LocalDate dateFrom, LocalDate dateTo) {
        return getToursBySearchParameters(
                keyword, dateFrom, dateTo, new ArrayList<String>());
    }
    
    /**
     * Primary method for booking a tour
     * 
     * If successful in action, this class should: 
     *  Increase the number of bookings of the specified tour by amount passengers, 
     *  Register a booking for that exact tour in the Booking Table, 
     *  If it returns a value, it has either done both of those things or neither.
     * @return true if the tour was inserted into the database,
     *          false if tour was already in the database.
     * @param tour tour that the CustomerPerson wants to book. 
     * @param passengers number of passengers/travelers included in booking. 
     * @throws NullPointerException if customer person has not been initialized.
     * @throws IllegalArgumentException if some input is in the wrong format.
     *         or email is already in use. Catch it and print the error message.
     * @throws ClassNotFoundException JDBC Driver class not found.
     * @throws SQLException Failed to get connection to database,
     *                      or unexplained error from ResultSet.
     */
    public boolean bookTour(Tour tour, int passengers)
            throws IllegalArgumentException, ClassNotFoundException, 
            SQLException, NullPointerException{
        if(passengers <=0) throw new IllegalArgumentException("Number of passengers (travelers) must be a positive integer");
        Booking booking = new Booking(
                this.getCustomerPerson(), tour, passengers);
        Boolean b = booking.insertToDB();
        int i = this.TOUR_LIST.indexOf(tour);
        if(i==-1) throw new NullPointerException("Tour is not in catalog.");
        Tour iTour = this.TOUR_LIST.get(i);
        iTour.addTravelers(passengers);
        if(b && this.DISTINCT_NAME_TOUR_LIST.contains(tour)){
            this.DISTINCT_NAME_TOUR_LIST.get( this.DISTINCT_NAME_TOUR_LIST.indexOf(tour) ).addTravelers(passengers);
        }
        return b;
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
