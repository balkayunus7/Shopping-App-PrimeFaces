package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface DatabaseConnection {
    
    // CONNECTION
    String URL = "jdbc:postgresql://localhost:5432/shopme";
    String DBUSERNAME = "postgres";
    String DBPASSWORD = "yunus744";
    
    // SQL
    String SQLINITPRODUCTS = "SELECT * FROM products";
  
    
    

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DBUSERNAME, DBPASSWORD);
    }

    public static void loadPostgreSQLDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found.");
        }
    }
}
