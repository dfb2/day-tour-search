
package en.hi.dtsapp.model.DAOs;
import en.hi.dtsapp.model.CustomerPerson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Notes for anyone who may want to modify this class in the future:
 *   Person takes input (name, password, email) but 
 *      Customer in the current database table has column sequence (name, email, password)
 * 
 * create your own customer and insert it into the DB Table by using
 *      public static int insertCustomer(..)
 * retrieve a CustomerPerson List of all customers in DB using 
 *      public static List initiateCustomerCatalog()
 * @author Erling Oskar Kristjansson, eok4@hi.is
 */
public class CustomerDAO implements DAO {
    
    /**
     * 
     * Adds a new Customer to Customer Table
     * @param cp
     * @return  1 if value was inserted, 
     *          0 if value already in Customer table, 
     *         -1 if failed to connect to database, 
     *         -5 if email already in Customer table, 
     *         -404 if unforeseen SQLException, error printed
     * @throws SQLException 
     */
    public static int insertCustomer(CustomerPerson cp)  throws SQLException{
        System.out.println("Called insertCustomer()");
        try {
            Class.forName(DRIVER);
         }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to connect to driver in CustomerDAO.insertCustomer()");
            return -1;
         }
        String name = cp.getName();
        String email = cp.getEmail();
        String password = cp.getPassword("erling");
        
         Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         System.out.println("Connected to database using CustomerDAO.insertCustomer()");
         
         // Check if customer already in table, or perhaps only email
         String s = "select * from customer where CustomerEmail = ?;";
         PreparedStatement pstmt = conn.prepareStatement(s);
         pstmt.setString(1, email);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()){
             if(rs.getString(2).equals(email)){ // customer in table?
                 if(rs.getString(1).equals(name) && rs.getString(3).equals(password)) return 0;
             }
             return -5; // only email
         }
         // Make statement and insert customer
         s = "insert into customer values(?, ?, ?);";
         pstmt = conn.prepareStatement(s);
         pstmt.setString(1, name);
         pstmt.setString(2, email);
         pstmt.setString(3, password);
         try {
            pstmt.executeUpdate();
         } catch(SQLException e){
             System.err.println(e.getMessage());
             return -404;
         }
         return 1; 
    }

    /**
     * @return List of CustomerPerson objects representing each DB entry
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
                        rs.getString(3),
                        rs.getString(2));
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
        
        CustomerPerson cp = new CustomerPerson("DummyCustomer", "dummyPassword", "dumCustomer@hi.is");
        
        t0 = System.nanoTime();
        insertCustomer(cp);
        t1 = System.nanoTime();
        System.out.println("Inserting Dummy Customer took " + ((t1-t0)/Math.pow(10,9)) + " seconds");
    }
}
