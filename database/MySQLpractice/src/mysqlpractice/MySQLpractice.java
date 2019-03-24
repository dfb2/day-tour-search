package mysqlpractice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;


/**
 * Virkar í Command Prompt:
 javac MySQLpractice.java
 java -cp .;mysql-connector-java-8.0.14.jar MySQLpractice
 * Bætti í system environmental variable paths 
 C:\Program Files (x86)\MySQL\Connector J 8.0\mysql-connector-java-8.0.14
 sem dugði ekki fyrir VSC en að hafa þetta jar file í project library í netbeans dugði
*/
public class MySQLpractice
{
   
    // Kallar bara á dbAdminDAO sem getur verið aðal controller'inn okkar
    public static void main( String[] args )
        throws Exception {
    }

    

 
    /** Gamalt fall
     * Fyrstu drög að einhvers konar falli sem setur inn Tour í SQL töflu.
     * Byrjar á að eyða öllum gildum með sama nafn
     * @param tour
     * @param op
     * @param loc
     * @param c
     * @throws Exception 
     */
    public static void InsertTours(String tour, String op, String loc, Connection c) throws Exception
    {
        long t0 = System.nanoTime();
        try
        {
            String s = "delete from tour where TourName = '" + tour + "'";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(s);
            
            String ps = "insert into TOUR values ('" + tour + "', '" + op + "', '" + loc + "', '1400', '1700', ?, 10, 100, 'Ride by the coast.', ?, 'Horse Horses Coast Wind');";
            PreparedStatement pstmt = c.prepareStatement(ps);
            String y = "2019";
            for(int m = 3; m < 10; m++){
                for(int d = 1; d < 10; d++){
                    String v1 = "0"+d+"0"+m+y;
                    pstmt.setString(1,v1);
                    String v2 = "HraKTAk" + v1 + "1400";
                    pstmt.setString(2,v2);
                    pstmt.executeUpdate();
                    pstmt.clearParameters();
                }
                for(int d = 10; d < 31; d++){
                    String v1 = d+"0"+m+y;  // Annað álíka ógeð fyrir mánuði 10 11 og 12
                    pstmt.setString(1,v1);  // Þarf að gera switch statement til að búa til streng
                    String v2 = "HraKTAk" + v1 + "1400";
                    pstmt.setString(2,v2);
                    pstmt.executeUpdate();
                    pstmt.clearParameters();
                }
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        System.out.println("Sekúndurnar sem það tók að setja inn gildin: " + ((System.nanoTime()-t0)/Math.pow(10,9)));
    }

 }

     /* Gamla main fallið héðan
    {
        // Miðað við default port: 3306
        String url = "jdbc:mysql://den1.mysql1.gear.host:3306/";
        String dbName = "hbv401v19cl3";
        String userName = "hbv401v19cl3";
        String password = "Xi6VcurQ~x-6";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.out.println("Fekk ekki driver");
        }
        

        Connection conn = null;
        

        // Rifja upp kosti og galla autocommit
        boolean USE_AUTOCOMMIT = false;
        boolean USE_INDEX = false;

        try
        {
            
            conn = DriverManager.getConnection(url+dbName, userName, password);
            System.out.println("Connected to database");
            conn.setAutoCommit(USE_AUTOCOMMIT);
            
        
            
            // Forritið sem smíðar töfluna þyrfti að vera betra.
             InsertTours("Horse-riding Adventure", "Kattegat Travel", "Akureyri", conn);

            
            Statement stmt = conn.createStatement();
            String s = "select * from tour";
            ResultSet resultSet = stmt.executeQuery(s);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
        }
            System.out.println("Still prints this");
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(conn != null)
                  conn.close();
                  System.out.println("Connection closed");
            }
            catch(SQLException e)
            {
                System.err.println(e);
            }
        }
    }
    */

// GÆTI ÞURFT AD DOWNLOAD'A OG SPECIFY CLASSPATH
/* 
PS C:\Users\Erling Oskar\Documents\hi\h18\gsfr\2019> javac .\MySQLpractice.java
PS C:\Users\Erling Oskar\Documents\hi\h18\gsfr\2019> java MySQLpractice
com.mysql.jdbc.Driver
No suitable driver found for jdbc:mysql://localhost:3306/mysql
Connection closed
PS C:\Users\Erling Oskar\Documents\hi\h18\gsfr\2019>
*/