What's new April 6 2019

Moved DAO interface and classes, and DTSMethods.java to model.DAOs folder

Made the following classes:
0 DTSMethods
-   Contains final static variables and methods that any class can use. 
    Used for synchronization e.g. of Time and Date Formatters.
1 CustomerDAO is a static class with static methods
-   insertCustomer() takes 3 String parameters and inserts it into the Customer Table in the database.
    See documentation for this method before using it. 
    These strings should be in the correct format to be inserted into the database
-   initiateCustomerDialog() returns an immutable list representing "select * from customer;"
-   The main method of this class demonstrates how to insert a dummy customerPerson.
2 BookingDAO 
-   insertBooking() takes 7 String parameters and inserts them as a new Booking in the Booking Table in DB.
    Also increases the value of TourTravellers in the Tour table accordingly for this specific Tour.
    See documentation for this method.
-   deleteBookings() takes 1 String parameters: customerEmail, and deletes all his bookings.
    For security purposes we might add password here later. But this is a convenience method for now,
    can be used to delete bookings by this dummy customerPerson, because a constraint on the database
    prohibits a Customer from booking the exact same Tour (exact by primary key in Tour table).
        insertBooking() does however have an option to deal with such failed updates.
        It returns a specific negative integer that can help the user of the method (programmer) decide
        what error message to display to the user of the program.
3 abstract Person to be extended by 4 and 5
-   Constructor and getter for name, email and password
4 CustomerPerson extends Person, implements equals, 
    so that two customerPersons are equal if they have the same email address.
5 OperatorPerson extends Person, implements equals, 
    so that two operatorPersons are equal if they have the same email address,
    but won't be equal to a customerPerson with same email. 
    Will have to add more parameters to this constructor later, if we want to use it.

User can now book objects, but only the TourCatalog and ListView are updated.
Ideally the TourCatalog should call the BookingDAO.insertBooking() method with the correct parameters
in the correct format. This way, we don't rely too much on the BrowseTourController, which will be
replaced by an external unit.
