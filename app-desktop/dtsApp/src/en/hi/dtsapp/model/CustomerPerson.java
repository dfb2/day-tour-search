package en.hi.dtsapp.model;

import en.hi.dtsapp.model.DAOs.CustomerDAO;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author Erling Oskar Kristjansson eok4@hi.is
 *         David Freyr Bjornsson dfb2@hi.is
 *         Andrea Osk Sigurdardottir aos26@hi.is
 * Haskoli Islands
 */
public class CustomerPerson extends Person {

    /**
     * Creates a CustomerPerson object with these values.
     * Password is private
     * Does not sanitize input, but the email must be in the format:
     *      [two or more chars]@[two or more chars].[two or more chars]
     *    And none of them can contain  or be from the set { ; ' " null }
     * @param name
     * @param email 
     * @param password
     * @throws java.sql.SQLException 
     * @throws IllegalArgumentException Another customer in the database is registered with this email.
     */
    public CustomerPerson(String name, String password, String email) throws SQLException, IllegalArgumentException {
        super(name, password, email);
        int result = this.insertToDB();
        switch(result){
            case -1:
                throw new SQLException("Failed to get class/driver to database. Try logging in again.");
            case -2:
                throw new SQLException("Failed to connect to database. Try logging in again.");
            case -5:
                throw new IllegalArgumentException("Email already in use. Try another email.");
            case -404:
                throw new SQLException("Unforeseen SQLException thrown in CustomerPerson constructor. Try again.");
            default:
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true; 
        if (!(o instanceof CustomerPerson)) 
            return false;
        CustomerPerson cp = (CustomerPerson)o;
        return this.hashCode() == cp.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.getEmail());
        return hash;
    }
    
    @Override
    public String toString(){
        String s = this.getName().concat(" - ").concat(this.getEmail());
        return s;
    }

    private int insertToDB() {
        return CustomerDAO.insertCustomer(this);
    }

    
    public static void main(String[] args) {
    //    CustomerPerson cp = new CustomerPerson("John", "johnspw", "webmaster@müller.de");
   //     System.out.println(cp);
    }
}
