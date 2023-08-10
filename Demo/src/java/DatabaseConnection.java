
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface DatabaseConnection {

    String url = "jdbc:postgresql://localhost:5432/shopme";
    String dbUsername = "postgres";
    String dbPassword = "yunus744";
    String sqlInitProducts = "SELECT * FROM products";
    String sqlRegister = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    String sqlLogin = "SELECT username, password FROM users WHERE username = ? AND password = ?";
    String sqlAddProductDatabase = "INSERT INTO user_products (username, product_id) VALUES (?, ?) ";
    String sqlRemoveProductDatabase = "DELETE FROM user_products WHERE username = ? AND product_id = ?";
    String sqlGetProductInList = "SELECT p.* FROM products p INNER JOIN user_products up ON p.id = up.product_id WHERE up.username = ?";
    String sqlSelectQuantity = "SELECT quantity FROM user_products WHERE username = ? AND product_id = ?";
    String sqlUpdateQuantity = "UPDATE user_products SET quantity = ? WHERE username = ? AND product_id = ?";
    String sqlCreateOrder = "INSERT INTO orders (username,created_at,total_price,number) VALUES (?,CURRENT_TIMESTAMP,?,?) RETURNING id";
    String sqlAddOrderItem = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
    String sqlGetOrders = "SELECT * FROM orders WHERE username = ? ORDER BY created_at DESC";
    String sqlGetOrderItems = "SELECT oi.*, p.* FROM order_items oi INNER JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUsername, dbPassword);
    }

    public static void loadPostgreSQLDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("PostgreSQL JDBC driver not found.");
        }
    }
}
