
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ellio
 */
public class CustomerDAO implements DAO {
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
            
            // Select all tours from tour table
            Statement stmt = conn.createStatement();
            String s = "select * from customer";
            ResultSet rs = stmt.executeQuery(s);
            while (rs.next()) { // Make tours from each line. This is effectively the "createTour()" method
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
    }
}
