
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

    // Added selected item to show in dialog panel. It will be used in the View metot.
    private ProductModel selectedProduct;

    // Getter and Setter methods for selectedPersona
    public ProductModel getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductModel selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    // Wrote code to detect the clicked value for dialog
    public void viewProduct(ProductModel product) {
        selectedProduct = product;
    }

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

            // set up the database connection
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT * FROM products";

                // Execute the query to fetch product data from the database
                try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

                    // We add the products in the database to our products list
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

    // add product to cart page with selected product
    public void addProductToCart(String username, int product_Id, int quantity) {
        try {
            String url = "jdbc:postgresql://localhost:5432/shopme";
            String dbUsername = "postgres";
            String dbPassword = "yunus744";

            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String query = "INSERT INTO user_products (username, product_id, quantity) VALUES (?, ?, ?) ";

                try (PreparedStatement statement = connection.prepareStatement(query)) {

                    statement.setString(1, username);
                    statement.setInt(2, product_Id);
                    statement.setInt(3, quantity);

                    statement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProductModel> getProductInList(String username) throws ClassNotFoundException {
        List<ProductModel> cartProducts = new ArrayList<>();

        try {
            // Database connection parameters
            String url = "jdbc:postgresql://localhost:5432/shopme";
            String dbUsername = "postgres";
            String dbPassword = "yunus744";

            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                // SQL query to retrieve products for a specific user
                String query = "SELECT p.* FROM products p INNER JOIN user_products up ON p.id = up.product_id WHERE up.username = ?";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    // Set the username parameter in the SQL query
                    statement.setString(1, username);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        // Loop through the query results and create ProductModel objects
                        // to represent each product in the user's cart
                        while (resultSet.next()) {
                            ProductModel product = new ProductModel();
                            product.setId(resultSet.getInt("id"));
                            product.setName(resultSet.getString("name"));
                            product.setPrice(resultSet.getInt("price"));
                            product.setRating(resultSet.getDouble("rating"));
                            product.setQuantity(resultSet.getInt("quantity"));
                            product.setImagePath(resultSet.getString("imagePath"));
                            product.setDescription(resultSet.getString("description"));
                            cartProducts.add(product);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions related to loading the database driver or executing queries
            e.printStackTrace();
        }

        // Return the list of products in the user's cart
        return cartProducts;
    }

    public void updateCartProductQuantity(String username, int productId, int newQuantity) {
        try {
            String url = "jdbc:postgresql://localhost:5432/shopme";
            String dbUsername = "postgres";
            String dbPassword = "yunus744";

            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String query = "UPDATE user_products SET quantity = ? WHERE username = ? AND product_id = ?";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, newQuantity);
                    statement.setString(2, username);
                    statement.setInt(3, productId);
                    statement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
