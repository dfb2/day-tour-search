package mysqlpractice;
/**
 * Hlutverk Klasans
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * @date
 * Háskóli Íslands
 */
public class OperatorPerson {

    private final String name, location , ceo, email, password;
    private String info;
    private int numCols;
    
    /**
     *  Constructor
     * @param name
     * @param location
     * @param info
     * @param ceo
     * @param email
     * @param password 
     */
    public OperatorPerson(String name, String location, String info, String ceo, String email, String password) {
        this.name = name;
        this.location = location;
        this.info = info;
        this.ceo = ceo;
        this.email = email;
        this.password = password;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the ceo
     */
    public String getCeo() {
        return ceo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }
}


