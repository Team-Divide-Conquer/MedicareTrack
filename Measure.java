import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Measure {
    private DatabaseConnector dbConnector;

    public Measure(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public boolean addMeasure(String obsname, String obstype) {
        String sql = "INSERT INTO measure(obsname, obstype) VALUES (?, ?)";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obsname);
            pstmt.setString(2, obstype);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    public void viewMeasures() {
        String sql = "SELECT * FROM measure";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Observation Name: " + rs.getString("obsname"));
                System.out.println("Observation Type: " + rs.getString("obstype"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
