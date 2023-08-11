package Database;

import Models.OrderModel;
import Models.ProductModel;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ManagedBean
@ApplicationScoped
public class DatabaseBean implements Serializable, DatabaseOperations, DatabaseConnection {

    private List<ProductModel> products = new ArrayList<>();
    private ProductModel selectedProduct;

    // for View method 
    public ProductModel getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductModel selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public void viewProduct(ProductModel product) {
        selectedProduct = product;
        
        // Use FacesContext to navigate to the product detail page
        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(context, null, "productDetails");

    }

    // for products at init time 
    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public void addProduct(ProductModel product) {
        products.add(product);
    }

    // This method is automatically executed after the bean is constructed and initialized.
    @PostConstruct
    @Override
    public void init() {
        products = new ArrayList<>();
        try {

            // Load the PostgreSQL JDBC driver
            DatabaseConnection.loadPostgreSQLDriver();

            // set up the database connection
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = DatabaseConnection.SQLINITPRODUCTS;

                // Execute the query to fetch product data from the database
                try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

                    // We add the products in the database to our products list
                    while (resultSet.next()) {
                        ProductModel product = new ProductModel();
                        product.setId(resultSet.getInt("id"));
                        product.setName(resultSet.getString("name"));
                        product.setPrice(resultSet.getInt("price"));
                        product.setRating(resultSet.getInt("rating"));
                        product.setQuantity(resultSet.getInt("quantity"));
                        product.setImagePath(resultSet.getString("imagePath"));
                        product.setDescription(resultSet.getString("description"));
                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
        }
    }

    // USER METOTS
    // Method to register a new user in the database
    @Override
    public boolean registerUser(String username, String password, String email) {
        try {
            // Load the PostgreSQL JDBC driver
            DatabaseConnection.loadPostgreSQLDriver();

            // Establish the database connection
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = DatabaseConnection.SQLREGISTER;

                // Execute the query to insert user data into the 'users' table
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, username);
                    statement.setString(2, email);
                    statement.setString(3, password);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
        }

        return false;
    }

    // Method to check if a user can log in
    @Override
    public boolean loginUser(String username, String password) {
        try {
            // Load the PostgreSQL JDBC driver
            DatabaseConnection.loadPostgreSQLDriver();

            // Establish the database connection
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = DatabaseConnection.SQLLOGIN;

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
                            System.out.println("Login Failed");
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("Doesn't find resultSet");
                    }
                }
            }
        } catch (SQLException e) {
        }

        return false;
    }

    // PRODUCT METOTS
    @Override
    public void addProductToCart(String username, int product_Id) {
        try {
            // Load the PostgreSQL JDBC driver
            DatabaseConnection.loadPostgreSQLDriver();

            // Establish a connection to the database using the provided credentials
            try (Connection connection = DatabaseConnection.getConnection()) {
                // Define the SQL query to insert a new entry into the 'user_products' table
                String query = DatabaseConnection.SQLADDPRODUCTDATABASE;

                // Create a prepared statement for executing the query
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    // Set the values of the parameters in the prepared statement
                    statement.setString(1, username);
                    statement.setInt(2, product_Id);

                    // Execute the update to add the product to the user's cart
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public void removeProductFromCart(String username, int product_Id) {
        try {
            // Load the PostgreSQL JDBC driver
            DatabaseConnection.loadPostgreSQLDriver();

            // Establish a connection to the database using the provided credentials
            try (Connection connection = DatabaseConnection.getConnection()) {
                // Define the SQL query to delete the entry from the 'user_products' table
                String query = DatabaseConnection.SQLREMOVEPRODUCTDATABASE;

                // Create a prepared statement for executing the query
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    // Set the values of the parameters in the prepared statement
                    statement.setString(1, username);
                    statement.setInt(2, product_Id);

                    // Execute the update to remove the product from the user's cart
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public List<ProductModel> getProductInList(String username) throws ClassNotFoundException {
        List<ProductModel> cartProducts = new ArrayList<>();

        try {
            // Load the PostgreSQL JDBC driver
            DatabaseConnection.loadPostgreSQLDriver();

            try (Connection connection = DatabaseConnection.getConnection()) {
                // SQL query to retrieve products for a specific user from the products table
                String productQuery = DatabaseConnection.SQLGETPRODUCTINLIST;

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
                            product.setRating(productResultSet.getInt("rating"));
                            product.setImagePath(productResultSet.getString("imagePath"));
                            product.setDescription(productResultSet.getString("description"));

                            // Now, get the quantity value from a different table with a separate query
                            String quantityQuery = DatabaseConnection.SQLSELECTQUANTITY;
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
        } catch (SQLException e) {
            // Handle exceptions related to loading the database driver or executing queries
        }

        // Return the list of products in the user's cart
        return cartProducts;
    }

    @Override
    public void updateProductQuantity(String username, int productId, int newQuantity) {
        try {
            // Load the PostgreSQL JDBC driver
            DatabaseConnection.loadPostgreSQLDriver();

            // Establish a connection to the database using the provided credentials
            try (Connection connection = DatabaseConnection.getConnection()) {
                // Define the SQL query to update the quantity of a product in the 'user_products' table
                String query = DatabaseConnection.SQLUPDATEQUANTITY;

                // Create a prepared statement for executing the query
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, newQuantity);
                    statement.setString(2, username);
                    statement.setInt(3, productId);

                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            // Handle any exceptions that may occur during database operations
        }
    }

    // ORDER METOTS
    @Override
    public int createOrder(String username, double total_price, int number) throws ClassNotFoundException, SQLException {
        int orderId = -1;

        // Load the PostgreSQL JDBC driver and establish a connection to the database
        DatabaseConnection.loadPostgreSQLDriver();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Define the SQL query to insert a new order for the user, including the created_at column
            String orderQuery = DatabaseConnection.SQLCREATEORDER;

            // Create a prepared statement for executing the query
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery)) {
                // Set the username parameter in the SQL query
                orderStatement.setString(1, username);
                orderStatement.setDouble(2, total_price);
                orderStatement.setDouble(3, number);

                // Execute the update to add the order and retrieve the generated order ID
                try (ResultSet orderResultSet = orderStatement.executeQuery()) {
                    if (orderResultSet.next()) {
                        orderId = orderResultSet.getInt(1);
                    }
                }
            }
        }

        return orderId;
    }

    @Override
    public void addOrderItem(int orderId, int productId, int quantity) throws ClassNotFoundException, SQLException {
        // Load the PostgreSQL JDBC driver and establish a connection to the database
        DatabaseConnection.loadPostgreSQLDriver();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Define the SQL query to insert a new order item for the order
            String orderItemQuery = SQLADDORDERITEM;

            // Create a prepared statement for executing the query
            try (PreparedStatement orderItemStatement = connection.prepareStatement(orderItemQuery)) {
                // Set the parameters in the prepared statement
                orderItemStatement.setInt(1, orderId);
                orderItemStatement.setInt(2, productId);
                orderItemStatement.setInt(3, quantity);

                // Execute the update to add the order item
                orderItemStatement.executeUpdate();
            }
        }
    }

    @Override
    public List<OrderModel> getOrdersByUsername(String username) {
        List<OrderModel> orders = new ArrayList<>();

        try {
            // Load the PostgreSQL JDBC driver
            DatabaseConnection.loadPostgreSQLDriver();

            // Establish a connection to the database using the provided credentials
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = DatabaseConnection.SQLGETORDERS;

                // Create a prepared statement for executing the query
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, username);

                    // Execute the query to fetch orders data from the database
                    try (ResultSet resultSet = statement.executeQuery()) {
                        // We add the orders in the database to our orders list
                        while (resultSet.next()) {
                            OrderModel order = new OrderModel();
                            order.setId(resultSet.getInt("id"));
                            order.setUsername(resultSet.getString("username"));
                            order.setCreatedAt(resultSet.getTimestamp("created_at"));
                            order.setTotal_price(resultSet.getDouble("total_price"));
                            order.setNumber(resultSet.getInt("number"));
                            orders.add(order);

                        }
                    }
                }
            }
        } catch (SQLException e) {
        }

        return orders;
    }

    @Override
    public List<ProductModel> getOrderItemsForOrder(int orderId, int newQuantity) throws ClassNotFoundException {
        List<ProductModel> orderItems = new ArrayList<>();

        try {
            // Load the PostgreSQL JDBC driver
            DatabaseConnection.loadPostgreSQLDriver();

            try (Connection connection = DatabaseConnection.getConnection()) {
                // SQL query to retrieve order items for a specific order from the order_items table
                String orderItemsQuery = DatabaseConnection.SQLGETORDERITEMS;

                try (PreparedStatement orderItemsStatement = connection.prepareStatement(orderItemsQuery)) {
                    // Set the orderId parameter in the SQL query
                    orderItemsStatement.setInt(1, orderId);

                    try (ResultSet orderItemsResultSet = orderItemsStatement.executeQuery()) {
                        // Loop through the query results and create ProductModel objects
                        // to represent each order item
                        while (orderItemsResultSet.next()) {
                            ProductModel orderItem = new ProductModel();
                            orderItem.setId(orderItemsResultSet.getInt("id"));
                            orderItem.setName(orderItemsResultSet.getString("name"));
                            orderItem.setPrice(orderItemsResultSet.getInt("price"));
                            orderItem.setRating(orderItemsResultSet.getInt("rating"));
                            orderItem.setImagePath(orderItemsResultSet.getString("imagePath"));
                            orderItem.setDescription(orderItemsResultSet.getString("description"));
                            orderItem.setQuantity(orderItemsResultSet.getInt("quantity"));

                            // Set the quantity from the order_items table
                            orderItems.add(orderItem);
                        }
                    }
                }
            }
        } catch (SQLException e) {
        }

        return orderItems;
    }

    // Methods that sort products by price on the home page
    @Override
    public void sortProductsByPrice() {
        Comparator<ProductModel> byPrice = Comparator.comparingDouble(ProductModel::getPrice);

        Collections.sort(products, byPrice);
    }

    @Override
    public void sortProductsByPriceReverse() {
        Comparator<ProductModel> byPrice = Comparator.comparingDouble(ProductModel::getPrice).reversed();

        Collections.sort(products, byPrice);
    }
}
