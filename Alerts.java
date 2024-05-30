import java.sql.*;

public class Alerts {
    private DatabaseConnector dbConnector;

    public Alerts(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public boolean addAlert(String patientid, String alerttype, Date generatedate, String message) {
        String sql = "INSERT INTO activealerts(patientid, alerttype, generatedate, message) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientid);
            pstmt.setString(2, alerttype);
            pstmt.setDate(3, generatedate); // Using java.sql.Date here
            pstmt.setString(4, message);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewAlerts(String patientid) {
        String sql = "SELECT * FROM activealerts WHERE patientid = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientid);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No alerts found for Patient ID: " + patientid);
                return;
            }
            while (rs.next()) {
                System.out.println("Alert Type: " + rs.getString("alerttype"));
                System.out.println("Generate Date: " + rs.getDate("generatedate"));
                System.out.println("Message: " + rs.getString("message"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteAlert(String patientid, String alerttype) {
        String sql = "DELETE FROM activealerts WHERE patientid = ? AND alerttype = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientid);
            pstmt.setString(2, alerttype);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
