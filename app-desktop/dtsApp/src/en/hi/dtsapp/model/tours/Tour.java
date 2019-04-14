package en.hi.dtsapp.model.tours;



import en.hi.dtsapp.model.DBO;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Class Objectives: A Java object that represents a line from the Tour SQL Table. 
 * The only none-final String which has a setter is travelers which
 * represents the number of travelers signed up for that Tour. 
 * setTravelers() and addTravelers() do not affect the values in the database 
 *  they do however return true if update was successful,
 *  suggesting that the user may continue to update the booking in the DB
 *  using the class BookingDAO.java
 * 
 * Implements equals()
 * 
 * @author Erling Oskar Kristjansson eok4@hi.is
 */
public class Tour implements DBO {
    
    private final String STANDARD_IMG = "C:\\Users\\Erling Oskar\\Documents\\hi\\v19\\hbv\\DayTourSearch\\app-desktop\\dtsApp\\src\\en\\hi\\dtsapp\\model\\img\\WOW_logo_RGB.jpg";
    private final String name, operator, location, info, keywords, img;
    private final int maxTravelers, price;
    private int travelers;
    private final LocalDate date;
    private final LocalTime startTime, endTime;

    // Needs more work before an operator can create a Tour from the UI
    // E.g. validation methods as exemplified by DBO.java
    public Tour(String name, String operator, String location, String startTime, String endTime, String date,
            String travelers, String maxTravelers, String price, String info, String keywords, String img) throws ParseException {
        if (info == null) info = "";  // Deal with potential issues immediately
        if (keywords == null) keywords = "";
        if (img == null) img = STANDARD_IMG;  // use a standard image that is stored locally+
        if (date == null || date.trim().equals("") || date.length() != 8) date = "01012019"; 
        if (startTime == null || startTime.trim().equals("") 
                || startTime.length() != 4) startTime = "0000"; 
        if (endTime == null 
                || endTime.trim().equals("") 
                || endTime.length() != 4 
                || Integer.parseInt(endTime) > 2359) endTime = "2359"; 
        
        this.date = LocalDate.parse(date, DATE_FORMATTER);
        this.name = name;
        this.operator = operator;
        this.location = location;
        this.startTime = LocalTime.parse(startTime.trim(), TIME_FORMATTER); 
        this.endTime = LocalTime.parse(endTime, TIME_FORMATTER);
        this.travelers = Integer.parseInt(travelers);
        this.maxTravelers = Integer.parseInt(maxTravelers);
        this.price = Integer.parseInt(price);
        this.img = img;
        this.keywords = keywords;
        this.info = info;
    }
    
    /**
     * Sets the amount of travelers signed up for this Tour to amount travelers
     *      as long as there is space
     * @param travelers the travelers to set
     * @return true if new amount of travelers is less than this.getMaxTravelers()
     *      return value can be used as a suggestion as to whether the user
     *      might want to call BookingDAO.insertBooking(..)
    */
    private boolean setTravelers(int travelers) {
        if(travelers <= this.getMaxTravelers()){
            this.travelers = travelers;
            return true;
        }
        System.out.println("Did not add Traveler in Tour.setTravelers because bookings exceed MaxTravelers");
        return false;
    }

    /**
     * Increases the amount of travelers signed up for this Tour by amount travelers
     *      as long as there is space
     * @param travelers the travelers to set
     * @return true if this.setTravelers(this.getTravelers()+travelers) returns true
     */
    public boolean addTravelers(int travelers) {
        return this.setTravelers(this.getTravelers()+travelers);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb = sb.append(this.getName());
        sb = sb.append(", operated by ");
        sb = sb.append(this.getOperator());
        sb = sb.append(", departs from ");
        sb = sb.append(this.getLocation());
        sb = sb.append(" at ");
        sb = sb.append(this.getStartTime().toString());
        sb = sb.append(" o'clock, on date: ");
        sb = sb.append(this.getDate().toString());
        sb = sb.append(". Passengers: ");
        sb = sb.append(String.valueOf(this.getTravelers()));
        sb = sb.append("/");
        sb = sb.append(String.valueOf(this.getMaxTravelers()));
        return sb.toString();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }
    
    /**
     * @return the endTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }
    
    /**
     * @return String representation of the the startTime
     */
    public String getStartTimeAsString() {
        return startTime.format(TIME_FORMATTER);
    }
    
    /**
     * @return String representation of the endTime
     */
    public String getEndTimeAsString() {
        return endTime.format(TIME_FORMATTER);
    }

    /**
     * @return String representation of the date 
     */
    public String getDateAsString() {
        return date.format(DATE_FORMATTER);
    }

    /**
     * @return the maxTravelers
     */
    public int getMaxTravelers() {
        return maxTravelers;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @return the keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * @return the img
     */
    public String getImg() {
        return img;
    }

    /**
     * @return the travelers
     */
    public int getTravelers() {
        return travelers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true; 
        if (!(o instanceof Tour)) 
            return false;
        Tour t = (Tour)o;
        return this.hashCode() == t.hashCode();
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.operator);
        hash = 29 * hash + Objects.hashCode(this.location);
        hash = 29 * hash + Objects.hashCode(this.startTime);
        hash = 29 * hash + Objects.hashCode(this.date);
        return hash;
    }
}
