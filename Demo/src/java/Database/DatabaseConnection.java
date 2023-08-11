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
    String SQLREGISTER = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    String SQLLOGIN = "SELECT username, password FROM users WHERE username = ? AND password = ?";
    String SQLADDPRODUCTDATABASE = "INSERT INTO user_products (username, product_id) VALUES (?, ?) ";
    String SQLREMOVEPRODUCTDATABASE = "DELETE FROM user_products WHERE username = ? AND product_id = ?";
    String SQLGETPRODUCTINLIST = "SELECT p.* FROM products p INNER JOIN user_products up ON p.id = up.product_id WHERE up.username = ?";
    String SQLSELECTQUANTITY = "SELECT quantity FROM user_products WHERE username = ? AND product_id = ?";
    String SQLUPDATEQUANTITY = "UPDATE user_products SET quantity = ? WHERE username = ? AND product_id = ?";
    String SQLCREATEORDER = "INSERT INTO orders (username,created_at,total_price,number) VALUES (?,CURRENT_TIMESTAMP,?,?) RETURNING id";
    String SQLADDORDERITEM = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
    String SQLGETORDERS = "SELECT * FROM orders WHERE username = ? ORDER BY created_at DESC";
    String SQLGETORDERITEMS = "SELECT oi.*, p.* FROM order_items oi INNER JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";
    
    

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
