
package en.hi.dtsapp.model.people;
import en.hi.dtsapp.model.DAO;
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
    private static final String LEYNDO = "erling";
    
    /**
     * 
     * Inserts a new Customer to Customer Table
     * @param cp
     * @return  true if cp was inserted into
     *                  false if cp was already in the Customer Table
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    protected static boolean insertCustomer(CustomerPerson cp) 
            throws ClassNotFoundException, SQLException, IllegalArgumentException {
        try (Connection conn = getConnection()) {
            switch(customerInTable(conn, cp)) {
                case -1:
                    throw new IllegalArgumentException("Customer Email already in use. Try another email.");
                case 1:
                    return false;
                default: // case 0
                    // break
            }
            insertCustomer(conn, cp);
            conn.close();
            return true;
        }
    }
    
    private static void insertCustomer(Connection conn, CustomerPerson cp)
            throws ClassNotFoundException, SQLException{
        // Make statement and insert customer
        String s = "insert into customer values(?, ?, ?);";
        PreparedStatement pstmt = conn.prepareStatement(s);
        pstmt.setString(1, cp.getName());
        pstmt.setString(2, cp.getEmail());
        pstmt.setString(3, cp.getPassword(LEYNDO));
        try {
               pstmt.executeUpdate();
         } catch(SQLException e){
                System.err.println("Failed to insert customer to Database Table in CustomerDAO.insertCustomer()");
                System.err.println(e.getMessage());
                throw e;
         }
    }
    
    /**
     * 
     * @param conn
     * @param cp
     * @return 1 if customer is in Table, 
     *                 0 if customer is not in Table, 
     *                 -1 if customer email is otherwise already in use
     * @throws SQLException 
     */
    private static int customerInTable(Connection conn, CustomerPerson cp) throws SQLException{
        ResultSet rs = selectCustomerByEmail(conn, cp.getEmail());
        while(rs.next()){
            CustomerPerson temp = new CustomerPerson(
                    rs.getString(1),
                    rs.getString(3),
                    rs.getString(2));
            if (temp.equals(cp)) return 1;
            if (temp.getEmail().equals(cp.getEmail())) return -1;
        }
        return 0;
    }
    
    private static ResultSet selectCustomerByEmail(Connection conn, String email) throws SQLException{
        String s = "select * from customer where CustomerEmail = ?;";
        PreparedStatement pstmt = conn.prepareStatement(s);
        pstmt.setString(1, email);
        return pstmt.executeQuery();
    }
    
    public static ResultSet selectCustomerByEmail(String email) throws ClassNotFoundException, SQLException{
        Connection conn = getConnection();
        return selectCustomerByEmail(conn, email);
    }

    /**
     * @return List of CustomerPerson objects representing each DB entry
     * @throws Exception various SQL, e.g. fails to connect or get driver.
     */
    protected static List<CustomerPerson> initiateCustomerCatalog() throws Exception {
        List<CustomerPerson> customerPersonList = new ArrayList<>();
        try (Connection conn = getConnection()) {
            System.out.println("Connected to database using CustomerDAO.java");

            // Select all customers from customer table
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
    
     /**
     * @return conn, Connection to the DayTourSearch database
     * @throws ClassNotFoundException did not find JDBC driver class
     * @throws SQLException failed to establish a connection with database
     */
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(DRIVER);
         }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to get class/driver in CustomerDAO.insertCustomer()");
            throw ex;
         }
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;
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
