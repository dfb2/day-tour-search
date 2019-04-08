You may need to configure path to JDBC Driver under Project Properties.

The tours do not contain any images, and the path to the local default image is probably wrong.


The main class is: TourCatalogController.java
/*********************************************************************************************************************
 * Class objective: 
 * Accepts input, optionally validates it, and converts it to commands for the model or view.
 *****************************************************************************************************************************
 * Here's what you need to know to use it:
 *      public TourCatalog() 
 *          constructor. Builds two private variables:
 *          TOUR_LIST contains every single Tour from the Tour database
 *          DISTINCT_NAME_TOUR_LIST contains only one of each (by name, i.e. no repetitions of the same tour on other dates)
 * 
 *       public ObservableList<Tour> getFullTourList() 
 *          returns an ObserveableList copy of TOUR_LIST . Every single tour from the database
 * 
 *      public ObservableList<Tour> getDistinctNameTourList()
 *          returns an ObserveableList copy of DISTINCT_NAME_TOUR_LIST (for easy viewing)
 * 
 ****************************************************************************************************************************
 *      There are two methods you will be using:
 *          getToursBySearchParameters(..)
 *          bookTour(..)
 *      see detailed documentation right below.
 ****************************************************************************************************************************
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
    
    /************************************************************************************************
     * Primary method for receiving an observable list filtered by some (User) Input
     * 
     * @param keyword String
     * @param dateFrom LocalDate 
     * @param dateTo LocalDate
     * @param keywordExceptions List of Strings 
     * @return ObservableList with all the tours in TOUR_LIST that are 
     *          within the specified dates [dateFrom, dateTo], both included,
     *          and have anything to do with the keyword, e.g. it's part of 
     *          TourName, TourOperator, TourLocation, TourInfo or TourKeywords.
     *        If the keyword is in keywordExceptions, it will not filter the list
     *          (e.g. put UI SearchField prompt text into this List)
     *      Returns an empty list if DateTo is before DateFrom,
     *      Returns an empty list if keyword is just some nonsense
     ************************************************************************************************/
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
    
    /************************************************************************************************
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
     * @return 
     ***********************************************************************************************/
    public int bookTour(CustomerPerson cp, Tour tour, int passengers){
        int result = -404;
        int i = this.TOUR_LIST.indexOf(tour);
        Tour iTour;
        boolean b = false;
        if(i!=-1){
            iTour = this.TOUR_LIST.get(i);
            b =  this.TOUR_LIST.get(i).addTravelers(passengers);
            if(b) {
                System.out.println(iTour.getTravelers());
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
            System.out.println("Tour not in TourCatalog.TOUR_LIST. Didn't update booking.");
            return -10;
        }
        if(b && result == 1){
            int j = this.DISTINCT_NAME_TOUR_LIST.indexOf(tour);
            if(j!=-1){
                this.DISTINCT_NAME_TOUR_LIST.get(j).addTravelers(passengers);
            }
            else System.out.println("Tour not in TourCatalog.DISTINCT_NAME_TOUR_LIST. Didn't update booking there.");
        }
        // Decrement the value of the TOUR_LIST again because we never should've incremented it in the first place
        if(b && result != 1 && i != -1 && iTour != null) b = iTour.addTravelers(-passengers);
        return result;
    }
    
    /*********************Private Methods here below***************************/
    /*******************Called by Public Methods above***********************/





In order to make a booking, you need to have the username of a customer in the customer table.
For now it's probably easiest for you to create a dummy customer and send that as a paremeter
to the bookTour(..) method. This will however cause trouble if the dummy has already booked that tour.

private final CustomerPerson customerPerson = new CustomerPerson(
            "DummyCustomer",
            "dummyCustomer@hi.is",
             "dummyPassword");

You can also create your own customer and insert it into the Table by using the following static method from
CustomerDAO.java, although it has not been tested or documented thoroughly:

/**
     * Adds a new Customer to Customer Table
     * @param name
     * @param password
     * @param email
     * @return  1 if value was inserted
     *          0 if value already in Customer table
     *         -1 if failed to connect to database
     *         -2 if bad name contains dubious characters, e.g. ' ; " null
     *         -3 if bad password contains dubious characters
     *         -4 if bad email contains dubious characters
     *         -5 if email already in Customer table
     * @throws SQLException 
     */
    public static int insertCustomer(String name, String password, String email)  throws SQLException{
        if(DTSMethods.isBadInput(name)) { System.out.println("bad name"); return -2; }
        if(DTSMethods.isBadInput(password)) { System.out.println("bad password"); return -3; }
        if(DTSMethods.isBadInput(email)) { System.out.println("bad email"); return -4; }
        name = name.trim();
        password = password.trim();
        email = email.trim();
        
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to connect to driver in CustomerDAO.insertCustomer()");
            return -1;
        }
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database using CustomerDAO.insertCustomer()");
            
            // Make statement and insert customer
            Statement stmt = conn.createStatement();
            StringBuilder sb = new StringBuilder();
            sb = sb.append("insert into customer values('");
            sb = sb.append(name);
            sb = sb.append("', '");
            sb = sb.append(password);
            sb = sb.append("', '");
            sb = sb.append(email);
            sb = sb.append("');");
            String s = sb.toString();
            try{
                stmt.executeUpdate(s);
            } catch (SQLIntegrityConstraintViolationException e){
                System.err.println(e.getMessage());
                System.err.println(
                    "Tried to insert duplicate customer value with email ".concat(
                    email).concat(", in CustomerDAO.insertCustomer()"));
                ResultSet rs = stmt.executeQuery("select * from customer;");
                while (rs.next()) { // Make customers from each line.
                    if(rs.getString(2) == email && rs.getString(1) == name
                            && rs.getString(3) == password) return 0;
                    }
                    return -5;
            }
         return 1;
    }
