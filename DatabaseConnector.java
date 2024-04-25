import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DatabaseConnector {
    private Connection conn;

    public DatabaseConnector() {
        this.conn = null;
    }

    // return whether connection succeeded or not
    public boolean connect(String port, String user, String password) {
        // try connecting to the database
        String url = "jdbc:mysql://localhost:" + port;
        try {
            this.conn = DriverManager.getConnection(url, user, password);
            return (this.conn != null);
        } catch (SQLException e) {
              System.err.println("SQLState: " + e.getSQLState());
              System.err.println("Error Code: " + e.getErrorCode());
              System.err.println("Message: " + e.getMessage());
              e.printStackTrace();
        }
        return false;
    }

    // executes all statements in a file
    public void executeSqlFile(String sqlFileName) {
        try {
          // try opening the file
          File sqlFile = new File(sqlFileName);
          Scanner queryScanner = new Scanner(sqlFile);

          // buffer to store current query
          String currentQuery = "";
          while (queryScanner.hasNextLine()) {
              String nextline = queryScanner.nextLine();
              nextline = nextline.trim();
              // ignore comments when parsing
              String[] filterComments = nextline.split("--");
              if (filterComments.length == 0 || filterComments[0].length() == 0) continue;
              String filtered = filterComments[0];
              filtered = filtered.trim();
              int lastChar = filtered.length();
              currentQuery += filtered;
              currentQuery += "\n";
              // check for the end of query statement
              if (filtered.charAt(lastChar-1) == ';') {
                  System.out.println(currentQuery);
                  // execute the query and reset the buffer
                  executeSqlCommand(currentQuery);
                  currentQuery = "";
              }
              
          }
          
          // close the scanner
          queryScanner.close();
          
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }

    }
    
    public String executeSqlCommand(String query) {
        // for determining the type of command
        String queryTrimmed = query.trim().toLowerCase();
        String result = "";

        try (Statement stmt = this.conn.createStatement()) {
            if (queryTrimmed.startsWith("select") || queryTrimmed.startsWith("show") || queryTrimmed.startsWith("describe") || queryTrimmed.startsWith("explain")) {
                result = executeSqlQuery(query);
            }
            else {
                executeSqlUpdate(query);           
            }

        }
        catch (SQLException e) {
            System.err.println("Error executing SQL script: " + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    
    }
    
    // execute a single statement
    private void executeSqlUpdate(String query) {
        try (Statement stmt = this.conn.createStatement()) {
            stmt.executeUpdate(query);
            System.out.println("Query OK.\n");
        }
        catch (SQLException e) {
            System.err.println("Error executing SQL script: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // execute a single statement
    private String executeSqlQuery(String query) {
        String result = "";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            // number of columns in corresponding table
            int columnCount = rs.getMetaData().getColumnCount();
    
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i); // Get column value as string
                    result += columnValue;
                    result += '\t';
                }
                result += '\n';
            }
            System.out.println("Query OK.\n");
        } catch (SQLException e) {
            System.err.println("Error executing SQL script: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }    

    
}
