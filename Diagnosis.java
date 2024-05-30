import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Diagnosis {
    private DatabaseConnector dbConnector;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // For consistent date parsing

    public Diagnosis(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public boolean addDiagnosis(String patientid, String diseaseid, String sinceStr, String endDateStr) {
        String sql = "INSERT INTO diagnosis(patientid, diseaseid, since, enddate) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'))";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dbConnector.getConnection();
            if (conn == null) {
                System.out.println("Failed to obtain database connection.");
                return false;
            }

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, patientid);
            pstmt.setString(2, diseaseid);
            pstmt.setString(3, sinceStr); // Directly set the string, TO_DATE will handle it in the query
            pstmt.setString(4, endDateStr); // Directly set the string, TO_DATE will handle it in the query

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Diagnosis added successfully for Patient ID: " + patientid);
                return true;
            } else {
                System.out.println("No rows affected. Check if the input values are correct.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
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
