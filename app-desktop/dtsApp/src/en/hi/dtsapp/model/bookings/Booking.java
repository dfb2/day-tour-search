package en.hi.dtsapp.model.bookings;

import en.hi.dtsapp.model.DBO;
import en.hi.dtsapp.model.tours.Tour;
import en.hi.dtsapp.model.people.CustomerPerson;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class Objectives: A Java object that represents a line from the Tour SQL Table. 
 * 
 * Implements Equals
 * Implements DBO (Database Object)
 * @author Erling Oskar Kristjansson eok4@hi.is
 */
public class Booking implements DBO {
    private final String cpEmail, tourName, tourOperator, tourLocation;
    private final LocalTime tourStartTime;
    private final LocalDate tourDate;
    private final int travelers;
    
    /**
     * @param cp the CustomerPerson that wants to make a booking
     * @param tour that the CustomerPerson wants to book
     * @param travelers number of travelers/passengers/members included in the booking
     * @throws IllegalArgumentException tour doesn't have enough space for these additional travelers
     */
    public Booking(CustomerPerson cp, Tour tour, int travelers) 
            throws IllegalArgumentException{
        if(validateTravelers(tour, travelers)){
            this.cpEmail = cp.getEmail();
            this.tourName = tour.getName();
            this.tourOperator = tour.getOperator();
            this.tourLocation = tour.getLocation();
            this.tourStartTime = tour.getStartTime();
            this.tourDate = tour.getDate();
            this.travelers = travelers;
        } else {
            throw new IllegalArgumentException("Could not create booking"
                    + " because the tour has fewer than " + travelers + " passenger/traveler slots remaining" );
        }
    }

    public boolean insertToDB() 
            throws ClassNotFoundException, SQLException, IllegalArgumentException {
        return BookingDAO.insertBooking(this);
    }
    
    /**
     * @param tour
     * @param travelers
     * @return true if tour has space for new travelers
     */
    private boolean validateTravelers(Tour tour, int travelers){
        return ( tour.getTravelers()+travelers <= tour.getMaxTravelers());
    }
    
    /**
     * @return the cpEmail
     */
    public String getCpEmail() {
        return cpEmail;
    }

    /**
     * @return the tourName
     */
    public String getTourName() {
        return tourName;
    }

    /**
     * @return the tourOperator
     */
    public String getTourOperator() {
        return tourOperator;
    }

    /**
     * @return the tourLocation
     */
    public String getTourLocation() {
        return tourLocation;
    }

    /**
     * @return the tourStartTime
     */
    public LocalTime getTourStartTime() {
        return tourStartTime;
    }

    /**
     * @return the tourDate
     */
    public LocalDate getTourDate() {
        return tourDate;
    }

    /**
     * @return the travelers
     */
    public int getTravelers() {
        return travelers;
    }
    
        /**
     * @return String representation of the the startTime
     */
    public String getStartTimeAsString() {
        return tourStartTime.format(TIME_FORMATTER);
    }

    /**
     * @return String representation of the date 
     */
    public String getDateAsString() {
        return tourDate.format(DATE_FORMATTER);
    }
}





/*
CREATE TABLE BOOKING
    ( CustomerEmail varchar(20) references CUSTOMER(CustomerUserID)
    , TourName varchar(30) references Tour(TourName)
    , TourOperator varchar(30) references Tour(TourOperator)
    , TourLocation varchar(20) references Tour(TourLocation)
    , TourStartTime char(4) references Tour(TourStartTime)
    , TourDate char(8) references Tour(TourDate)
    , Travellers int not null
    , constraint BookingID primary key(CustomerEmail, TourName, 
      TourOperator, TourLocation, TourStartTime, TourDate)
    );

*/