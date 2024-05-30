import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Observation {
    private DatabaseConnector dbConnector;

    public Observation(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // Method to add a new observation
    public boolean addObservation(String patientId, String obsName, String obsType, Timestamp obsDate, float obsValue, Timestamp recDate) {
        String sql = "INSERT INTO observations(patientId, obsName, obsType, obsDate, obsValue, recDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientId);
            pstmt.setString(2, obsName);
            pstmt.setString(3, obsType);
            pstmt.setTimestamp(4, obsDate);
            pstmt.setFloat(5, obsValue);
            pstmt.setTimestamp(6, recDate);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to view observations
    public void viewObservations(String patientId) {
        String sql = "SELECT * FROM observations WHERE patientId = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientId);
            var rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No observations found for Patient ID: " + patientId);
                return;
            }
            while (rs.next()) {
                System.out.println("Observation Name: " + rs.getString("obsName"));
                System.out.println("Observation Type: " + rs.getString("obsType"));
                System.out.println("Observation Date: " + rs.getTimestamp("obsDate"));
                System.out.println("Observation Value: " + rs.getFloat("obsValue"));
                System.out.println("Record Date: " + rs.getTimestamp("recDate"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
