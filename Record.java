import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Record {
    private DatabaseConnector dbConnector;

    public Record(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public boolean addRecord(String patientid, String obsname, String obstype, Timestamp obsdate, float obsvalue, Timestamp recdate) {
        String sql = "INSERT INTO record(patientid, obsname, obstype, obsdate, obsvalue, recdate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientid);
            pstmt.setString(2, obsname);
            pstmt.setString(3, obstype);
            pstmt.setTimestamp(4, obsdate);
            pstmt.setFloat(5, obsvalue);
            pstmt.setTimestamp(6, recdate);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Record added successfully for Patient ID: " + patientid);
                return true;
            } else {
                System.out.println("No record was added. Check if the input values are correct and if the referenced keys exist.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void viewRecords(String patientid) {
        String sql = "SELECT * FROM record WHERE patientid = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientid);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No records found for Patient ID: " + patientid);
            } else {
                do {
                    System.out.println("Observation Name: " + rs.getString("obsname"));
                    System.out.println("Observation Type: " + rs.getString("obstype"));
                    System.out.println("Observation Date: " + rs.getTimestamp("obsdate"));
                    System.out.println("Observation Value: " + rs.getFloat("obsvalue"));
                    System.out.println("Record Date: " + rs.getTimestamp("recdate"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
