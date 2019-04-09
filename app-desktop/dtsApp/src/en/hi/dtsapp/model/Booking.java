package en.hi.dtsapp.model;

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
    public Booking(CustomerPerson cp, Tour tour, int travelers) throws IllegalArgumentException{
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
                    + "because the tour has fewer than " + travelers + " passenger/traveler slots remaining" );
        }
    }
    
    /**
     * @param tour
     * @param travelers
     * @return true if tour has space for new travelers
     */
    private boolean validateTravelers(Tour tour, int travelers){
        return ( tour.getTravelers()+travelers <= tour.getMaxTravelers());
    }
}