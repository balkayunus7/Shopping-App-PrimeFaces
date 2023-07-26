
import com.example.ProductModel;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class DatabaseBean {

    private List<ProductModel> products;

    // This method is automatically executed after the bean is constructed and initialized.
    @PostConstruct
    public void init() {
        products = new ArrayList<>();

        try {
            // Database connection parameters
            String url = "jdbc:postgresql://localhost:5432/shopme";
            String username = "postgres";
            String password = "yunus744";

            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the database connection
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT * FROM contacts";

                // Execute the query to fetch product data from the database
                try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

                    // Process each row of the result set and create ProductModel objects
                    while (resultSet.next()) {
                        ProductModel product = new ProductModel();
                        product.setId(resultSet.getInt("id"));
                        product.setName(resultSet.getString("name"));
                        product.setPrice(resultSet.getInt("price"));
                        product.setRating(resultSet.getDouble("rating"));
                        product.setQuantity(resultSet.getInt("quantity"));
                        product.setImagePath(resultSet.getString("imagePath"));
                        product.setDescription(resultSet.getString("description"));
                        products.add(product);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Getter method for the list of products
    public List<ProductModel> getProduct() {
        return products;
    }

    // Setter method for the list of products
    public void setProduct(List<ProductModel> products) {
        this.products = products;
    }

    // Method to register a new user in the database
    public boolean registerUser(String username, String password, String email) {
        try {
            // Database connection parameters
            String url = "jdbc:postgresql://localhost:5432/shopme";
            String dbUsername = "postgres";
            String dbPassword = "yunus744";

            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the database connection
            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

                // Execute the query to insert user data into the 'users' table
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, username);
                    statement.setString(2, email);
                    statement.setString(3, password);
                    statement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to check if a user can log in
    public boolean loginUser(String username, String password) {
        try {
            // Database connection parameters
            String url = "jdbc:postgresql://localhost:5432/shopme";
            String dbUsername = "postgres";
            String dbPassword = "yunus744";

            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the database connection
            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";

                // Execute the query to check if the provided username and password match a user in the 'users' table
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, username);
                    statement.setString(2, password);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            // Login successful, you can print the username and password to the screen.
                            System.out.println("Username: " + resultSet.getString("username"));
                            System.out.println("Password: " + resultSet.getString("password"));
                            return true;
                        } else {
                            System.out.println("Login failed!");
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("*************************************************");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
