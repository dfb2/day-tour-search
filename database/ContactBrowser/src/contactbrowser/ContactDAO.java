package contactbrowser;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ContactDAO implements DAO {
   private ContactPerson createContactPerson(ResultSet rs) {
      ContactPerson p = new ContactPerson();
      try {
         p.setId(rs.getInt("id"));
         p.setName(rs.getString("name"));
         p.setNickName(rs.getString("nick_name"));
         p.setAddress(rs.getString("address"));
         p.setHomePhone(rs.getString("home_phone"));
         p.setWorkPhone(rs.getString("work_phone"));
         p.setCellPhone(rs.getString("cell_phone"));
         p.setEmail(rs.getString("email"));
         p.setBirthDate(rs.getDate("birthday"));
         p.setWebSite(rs.getString("web_site"));
         p.setProfession(rs.getString("profession"));
      } catch (SQLException ex) {
      }
      return p;
   }
   public List<ContactPerson> getContacts() {
      String sql = "select * from contact";
      List<ContactPerson> list = new ArrayList<>();
      try {
         Class.forName(DRIVER);
         Connection con = DriverManager.getConnection
            (DB_URL, USER, PASS);
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while (rs.next()) {
            ContactPerson p = createContactPerson(rs);
            list.add(p);
         }
         rs.close();
         con.close();
      } catch (ClassNotFoundException | SQLException ex) {
      }
      return list;
   }

   public List<ContactPerson> getContactsForName(String name) {
      String sql = "select * from contact";
      List<ContactPerson> list = new ArrayList<>();
      try {
         Class.forName(DRIVER);
         Connection con = DriverManager.getConnection
            (DB_URL, USER, PASS);
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while (rs.next()) {
            ContactPerson p = createContactPerson(rs);
            list.add(p);
         }
         rs.close();
         con.close();
      } catch (ClassNotFoundException | SQLException ex) {
      }
      return list;
   }
}