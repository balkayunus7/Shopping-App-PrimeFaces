
import com.example.ProductModel;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class DatabaseBean implements  Serializable{

    String url = "jdbc:postgresql://localhost:5432/shopme";
    String dbUsername = "postgres";
    String dbPassword = "yunus744";

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

            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // set up the database connection
            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
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
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the database connection
            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String query = "SELECT username, password FROM users WHERE username = ? AND password = ?";

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
                        System.out.println("Doesn't find resultSet");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // add product to cart page with selected product
    public void addProductToCart(String username, int product_Id) {
        try {

            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String query = "INSERT INTO user_products (username, product_id) VALUES (?, ?) ";

                try (PreparedStatement statement = connection.prepareStatement(query)) {

                    statement.setString(1, username);
                    statement.setInt(2, product_Id);

                    statement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProductFromCart(String username, int product_Id) {
        try {
            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String query = "DELETE FROM user_products WHERE username = ? AND product_id = ?";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, username);
                    statement.setInt(2, product_Id);

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
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                // SQL query to retrieve products for a specific user from the products table
                String productQuery = "SELECT p.* FROM products p INNER JOIN user_products up ON p.id = up.product_id WHERE up.username = ?";

                try (PreparedStatement productStatement = connection.prepareStatement(productQuery)) {
                    // Set the username parameter in the SQL query
                    productStatement.setString(1, username);

                    try (ResultSet productResultSet = productStatement.executeQuery()) {
                        // Loop through the query results and create ProductModel objects
                        // to represent each product in the user's cart
                        while (productResultSet.next()) {
                            ProductModel product = new ProductModel();
                            product.setId(productResultSet.getInt("id"));
                            product.setName(productResultSet.getString("name"));
                            product.setPrice(productResultSet.getInt("price"));
                            product.setRating(productResultSet.getDouble("rating"));
                            product.setImagePath(productResultSet.getString("imagePath"));
                            product.setDescription(productResultSet.getString("description"));

                            // Now, get the quantity value from a different table with a separate query
                            String quantityQuery = "SELECT quantity FROM user_products WHERE username = ? AND product_id = ?";
                            try (PreparedStatement quantityStatement = connection.prepareStatement(quantityQuery)) {
                                quantityStatement.setString(1, username);
                                quantityStatement.setInt(2, product.getId());
                                try (ResultSet quantityResultSet = quantityStatement.executeQuery()) {
                                    if (quantityResultSet.next()) {
                                        int quantity = quantityResultSet.getInt("quantity");
                                        product.setQuantity(quantity);
                                    } else {
                                        // If no quantity is found in the other table, set default quantity to 1 or any other desired value
                                        product.setQuantity(1);
                                    }
                                }
                            }

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
    
    

    public void updateProductQuantity(String username, int productId, int newQuantity) {
        try {
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
