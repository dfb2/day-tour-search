package en.hi.dtsapp.model;

/**
 * Hlutverk Klasans
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * @date Spring 2019
 * Háskóli Íslands
 */
class Tour {
    
    private final String name, operator, location, startTime, endTime, date, maxTravellers, price, info, keywords, img;
    private String travellers;
    
    public Tour(String name, String operator, String location, String startTime, String endTime, String date,
            String travellers, String maxTravellers, String price, String info, String keywords, String img) {
        this.name = name;
        this.operator = operator;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.travellers = travellers;
        this.maxTravellers = maxTravellers;
        this.price = price;
        this.info = info;
        this.keywords = keywords;
        if (img == null) {
            // use a standard image that is stored locally
            this.img = img;
        } else this.img = img; 
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
    public String getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the maxTravellers
     */
    public String getMaxTravellers() {
        return maxTravellers;
    }

    /**
     * @return the price
     */
    public String getPrice() {
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
     * @return the travellers
     */
    public String getTravellers() {
        return travellers;
    }

    /**
     * @param travellers the travellers to set
     */
    public void setTravellers(String travellers) {
        this.travellers = travellers;
    }
}
