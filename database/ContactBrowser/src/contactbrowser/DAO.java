package contactbrowser;
public interface DAO {
   public static final String DB_URL = "jdbc:mysql://localhost:3306/"
   + "addressbook?zeroDateTimeBehavior=convertToNull";;
   public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   public static final String USER = "root";
   public static final String PASS = "secret";
}