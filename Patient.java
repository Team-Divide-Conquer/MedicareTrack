import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Patient {
    private DatabaseConnector dbConnector;

    public Patient(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // Adds a new patient by their user ID
    public boolean addPatient(String userid) {
        String sql = "INSERT INTO patient(userid) VALUES (?)";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Example additional method: View patient details
    public void viewPatientDetails(String userid) {
        String sql = "SELECT p.userid, pr.fname, pr.lname, pr.dob, pr.gender, pr.address, pr.phone FROM patient p JOIN person pr ON p.userid = pr.userid WHERE p.userid = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("User ID: " + rs.getString("userid"));
                System.out.println("First Name: " + rs.getString("fname"));
                System.out.println("Last Name: " + rs.getString("lname"));
                System.out.println("Date of Birth: " + rs.getDate("dob"));
                System.out.println("Gender: " + rs.getString("gender"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Phone Number: " + rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
