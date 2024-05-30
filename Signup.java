import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class Signup {
    private DatabaseConnector dbConnector;

    public Signup(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public boolean registerUser(String userid, String fname, String lname, Date dob, String gender, String address, String phone, String password) {
        String sql = "INSERT INTO person(userid, fname, lname, dob, gender, address, phone, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setDate(4, dob);  // Directly using java.sql.Date which should be passed to this method
            pstmt.setString(5, gender);
            pstmt.setString(6, address);
            pstmt.setString(7, phone);
            pstmt.setString(8, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
