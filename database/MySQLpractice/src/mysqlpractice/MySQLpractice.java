package mysqlpractice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Virkar í Command Prompt:
 javac MySQLpractice.java
 java -cp .;mysql-connector-java-8.0.14.jar MySQLpractice
 Bætti í system environmental variable paths
 C:\Program Files (x86)\MySQL\Connector J 8.0\mysql-connector-java-8.0.14
 sem dugði ekki fyrir VSC en að hafa þetta jar file í project library í netbeans dugði


*/

public class MySQLpractice
{
    public static void main( String[] args )
        throws Exception
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
        boolean USE_AUTOCOMMIT = true;
        boolean USE_INDEX = false;

        try
        {
            
            conn = DriverManager.getConnection(url+dbName, userName, password);
            System.out.println("Connected to database");
            conn.setAutoCommit(USE_AUTOCOMMIT);

            Statement stmt = conn.createStatement();
            String s = "select * from tour";
            ResultSet rs = stmt.executeQuery(s);

            while(rs.next())
            {
                boolean b = true;
                String a;
                try
                {
                    for(int i = 1; true ; i++)
                    {
                        a = rs.getString(i);
                        System.out.println(a);
                    }
                }
                catch(SQLException err)
                {
                    System.err.println(err.getMessage());
                    System.out.println("No problemo!");
                }
                
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
}

// GÆTI ÞURFT AD DOWNLOAD'A OG SPECIFY CLASSPATH
/* 
PS C:\Users\Erling Oskar\Documents\hi\h18\gsfr\2019> javac .\MySQLpractice.java
PS C:\Users\Erling Oskar\Documents\hi\h18\gsfr\2019> java MySQLpractice
com.mysql.jdbc.Driver
No suitable driver found for jdbc:mysql://localhost:3306/mysql
Connection closed
PS C:\Users\Erling Oskar\Documents\hi\h18\gsfr\2019>
*/