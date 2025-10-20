import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class testsql {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/app";
        String user = "root";
        String password = "aarush";

        try {
            // Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to database");

            // Insert SQL with parameters
            String sql = "INSERT INTO test (a, b, c, d) VALUES (?, ?, ?, ?)";

            // Use PreparedStatement (safer than Statement)
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Set values
            pstmt.setString(1, "hello");  // a: CHAR(6)
            pstmt.setInt(2, 123);         // b: INT
            pstmt.setDate(3, java.sql.Date.valueOf("2025-09-16")); // c: DATE
            pstmt.setString(4, "inserted row"); // d: VARCHAR(40)

            // Execute insert
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Rows inserted: " + rows);

            // Close
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
