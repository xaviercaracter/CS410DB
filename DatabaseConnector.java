import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection connect(String port, String user, String password) {
        // try connecting to the database
        String url = "jdbc:mysql://localhost:" + port;
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
              return conn;
        } catch (SQLException e) {
              System.err.println("SQLState: " + e.getSQLState());
              System.err.println("Error Code: " + e.getErrorCode());
              System.err.println("Message: " + e.getMessage());
              e.printStackTrace();
        }
        return null;
    }
    
}