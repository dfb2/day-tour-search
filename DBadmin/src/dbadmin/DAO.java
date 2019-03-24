package dbadmin;
/**
 * Usage: class fooDAO implements DAO
 * Grants these final variables to fooDAO so it can connect to the database
 * 
 * The package needs "mysql-connector-java-8.0.15.jar" or similar to connect,
 * You can either keep this in your path, or:
 *      Add Jar to Compile-time Libraries under Project Properties in NetBeans
 * as I've done here.
 * Jar can be downloaded at "https://dev.mysql.com/downloads/file/?id=484819"
 * 
 * @author Erling Óskar Kristjánsson eok4@hi.is
 */
public interface DAO {
   public static final String DB_URL = "jdbc:mysql://den1.mysql1.gear.host:3306/"
                                                            + "hbv401v19cl3";
   public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   public static final String USER = "hbv401v19cl3";
   public static final String PASS = "Xi6VcurQ~x-6";
}