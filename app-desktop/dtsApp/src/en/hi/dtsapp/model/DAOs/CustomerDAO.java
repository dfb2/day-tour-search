
package en.hi.dtsapp.model.DAOs;

import en.hi.dtsapp.model.CustomerPerson;
import static en.hi.dtsapp.model.DAOs.DAO.DB_URL;
import static en.hi.dtsapp.model.DAOs.DAO.DRIVER;
import static en.hi.dtsapp.model.DAOs.DAO.PASS;
import static en.hi.dtsapp.model.DAOs.DAO.USER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create your own customer and insert it into the DB Table by using
 *      public static int insertCustomer(..)
 * retrieve a CustomerPerson List of all customers in DB using 
 *      public static List initiateCustomerCatalog()
 * @author Erling Oskar Kristjansson, eok4@hi.is
 */
public class CustomerDAO implements DAO {
    
    /**
     * Adds a new Customer to Customer Table
     * @param name
     * @param password
     * @param email
     * @return  1 if value was inserted
     *          0 if value already in Customer table
     *         -1 if failed to connect to database
     *         -2 if bad name contains dubious characters
     *         -3 if bad password contains dubious characters
     *         -4 if bad email contains dubious characters
     *         -5 if email already in Customer table
     * @throws SQLException 
     */
    public static int insertCustomer(String name, String password, String email)  throws SQLException{
        if(DTSMethods.isBadInput(name)) { System.err.println("bad name: " + name); return -2; }
        if(DTSMethods.isBadInput(password)) { System.err.println("bad password: " + password); return -3; }
        if(DTSMethods.isBadInput(email)) { System.err.println("bad email: " + password); return -4; }
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
                // System.out.println(s);
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
    

    /**
     * Performs "select * from customer" on DayTourSearch Database
     * returns a list of CustomerPerson objects representing each DB entry
     * Can thus potentially be used to check if a Customer already exists,
     * rather than attempting to create one.
     * 
     * @return List
     * @throws Exception various SQL, e.g. fails to connect or get driver.
     */
    public static List<CustomerPerson> initiateCustomerCatalog() throws Exception {
        List<CustomerPerson> customerPersonList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to connect to driver in CustomerDAO.java");
        }
        try  {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database using CustomerDAO.java");
            
            // Select all customers from tour table
            Statement stmt = conn.createStatement();
            String s = "select * from customer";
            ResultSet rs = stmt.executeQuery(s);
            while (rs.next()) { // Make customers from each line.
                CustomerPerson cp = new CustomerPerson(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                customerPersonList.add(cp);
            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        List<CustomerPerson> immutableList = Collections.unmodifiableList(customerPersonList);
        return immutableList; 
    }
    
    
    public static void main(String[] args) throws Exception {
        long t0 = System.nanoTime();
        List<CustomerPerson> immutableList = initiateCustomerCatalog();
        long t1 = System.nanoTime();
        System.out.println("Length of immutable List is " + immutableList.size());
        System.out.println("Fetching customers and creating immutable list took " + ((t1-t0)/Math.pow(10,9)) + " seconds");
        
        
        t0 = System.nanoTime();
        insertCustomer("DummyCustomer", "dummyCustomer@hi.is", "dummyPassword");
        t1 = System.nanoTime();
        System.out.println("Inserting Dummy Customer took " + ((t1-t0)/Math.pow(10,9)) + " seconds");
        
    }
}
