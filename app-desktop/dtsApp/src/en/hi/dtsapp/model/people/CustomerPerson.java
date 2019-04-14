package en.hi.dtsapp.model.people;

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
     *    email must be in the format:
     *      [two or more chars]@[two or more chars].[two or more chars]
     *    And none of the parameters can contain  or be from the set { ; ' " null }
     * @param name
     * @param email 
     * @param password
     * @throws IllegalArgumentException Another customer in the database is registered with this email.
     */
    public CustomerPerson(String name, String password, String email) 
            throws IllegalArgumentException {
        super(name, password, email);
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

    public boolean insertToDB()
            throws ClassNotFoundException, SQLException, IllegalArgumentException {
        return CustomerDAO.insertCustomer(this);
    }
    
    public static void main(String[] args) {
    //    CustomerPerson cp = new CustomerPerson("John", "johnspw", "webmaster@müller.de");
   //     System.out.println(cp);
    }
}