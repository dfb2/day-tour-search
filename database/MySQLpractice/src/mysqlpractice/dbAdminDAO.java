package mysqlpractice;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
/**
 * Hlutverk Klasans
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * @date Vor 2019
 * Háskóli Íslands
 */
public class dbAdminDAO implements DAO {
    
    private static final int OPERATOR_COLS = 6;
    private static final int TOUR_COLS = 6;

     // Eyðir öllu úr operator og smíðar upp á nýtt
    private static void ReadAndInsertTourOperators(Connection c) 
            throws FileNotFoundException, SQLException {
         String s = "delete from operator;";
         Statement stmt = c.createStatement();
         stmt.executeUpdate(s);
         
          // má breyta í relative path ef einhvern tímann þörf
         File file = new File("C:\\Users\\Erling Oskar\\Documents\\hi\\v19\\hbv\\DayTourSearch\\database\\MySQLpractice\\src\\mysqlpractice\\DatabaseValues\\TourOperators.txt");
         Scanner sc = new Scanner(file); 
        while (sc.hasNextLine()) {
            s = "insert into OPERATOR values (";
            s = s.concat(sc.nextLine()).concat(");");
            stmt.executeUpdate(s);
        }
    }
    
     // Eyðir öllu úr tour og smíðar upp á nýtt
    private static void ReadAndInsertTours(Connection c) 
            throws FileNotFoundException, SQLException {
    // Hægt að bæta boolean inntaki á fallið og sleppa því að eyða eftir að operators geta búið til sína eigin tours
         String s = "delete from TOUR;";
         Statement stmt = c.createStatement();
         stmt.executeUpdate(s);
         
         PreparedStatement pstmt;
         String y = "2019";
         String[] months = {"04", "05", "06", "07", "08", "09", "10", "11", "12"};   // Yey sleppum við febrúar (o.fl.)
         String[][] days = { {"01", "04", "07", "10", "13", "16", "19", "22", "25", "28"},
                                     {"02", "05", "08", "11", "14", "17", "20", "23", "26", "29"},
                                     {"03", "06", "09", "14", "15", "18", "21", "24", "27", "30"} };
         int r;
         
         // má breyta í relative path ef einhvern tímann þörf
         File file = new File("C:\\Users\\Erling Oskar\\Documents\\hi\\v19\\hbv\\DayTourSearch\\database\\MySQLpractice\\src\\mysqlpractice\\DatabaseValues\\Tours.txt");
         Scanner sc = new Scanner(file);
         String scl = sc.nextLine();  // Hendum fyrstu línunni, hún segir til um format
         System.out.println(scl);  // losna við warning unused value lol
         while (sc.hasNextLine()) {     // Hver tour
             scl = sc.nextLine();
             for(String m: months){        // Endurtekur sig í hverjum mánuði
                 r = (int)(Math.random()*3);    // þriðja hvern dag, upphafsdagur valinn af handahófi í hverjum mánuði
                 for(String d: days[r]){
                     s = "insert into TOUR values (";
                     s = s.concat(scl).concat(");");
                     pstmt = c.prepareStatement(s);
                     pstmt.setString(1,d+m+y);
                     pstmt.executeUpdate();
                     pstmt.clearParameters();
                 }
             }
        }
    }
    
    public static void main()
        throws Exception {
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Fekk ekki driver");
        }
        try  {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database");
            
            // Kalla á föll til að smíða gagnagrunn
            ReadAndInsertTourOperators(conn);
            ReadAndInsertTours(conn);
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }


}
