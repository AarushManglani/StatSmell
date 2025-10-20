import java.sql.*;
public class JDBCps {
    public static void main(String[] args){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app","root","aarush");

            String query = "INSERT INTO test(a,b) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1,"Aarush");
            ps.setInt(2,4);
            ps.setString(1,"Aaru");
            ps.setInt(2,3);
            int rows =ps.executeUpdate();
            System.out.println(rows+" rows inserted");

            con.close();

        }catch(Exception e){
            System.out.println("Exception");
        }

    }
}
