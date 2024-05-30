import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile {
    private DatabaseConnector dbConnector;

    public Profile(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void viewProfile(String userid) {
        String sql = "SELECT * FROM person WHERE userid = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("User ID: " + rs.getString("userid"));
                System.out.println("Name: " + rs.getString("fname") + " " + rs.getString("lname"));
                System.out.println("DOB: " + rs.getDate("dob"));
                System.out.println("Gender: " + rs.getString("gender"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Phone: " + rs.getString("phone"));
            } else {
                System.out.println("Profile not found for UserID: " + userid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProfile(String userid, String fname, String lname, Date dob, String gender, String address, String phone) {
        String sql = "UPDATE person SET fname = ?, lname = ?, dob = ?, gender = ?, address = ?, phone = ? WHERE userid = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setDate(3, dob);
            pstmt.setString(4, gender);
            pstmt.setString(5, address);
            pstmt.setString(6, phone);
            pstmt.setString(7, userid);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Profile updated successfully for UserID: " + userid);
            } else {
                System.out.println("No rows affected. Check if the UserID exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteProfile(String userid) {
        String sql = "DELETE FROM person WHERE userid = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnector.getConnection();
            if (conn == null) {
                System.out.println("Failed to obtain database connection.");
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Profile deleted successfully for UserID: " + userid);
                return true;
            } else {
                System.out.println("No rows affected. Check if the UserID exists.");
                return false;
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 2292) { // ORA-02292: integrity constraint violation - child record found
                System.out.println("Cannot delete profile. There are related records in other tables.");
            } else {
                System.out.println("SQL Error: " + e.getMessage());
            }
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

}
