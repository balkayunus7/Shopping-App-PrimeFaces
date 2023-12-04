package Database;

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
public class DatabaseBean implements Serializable,  DatabaseConnection {

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

   

    // Methods that sort products by price on the home page
    public void sortProductsByPrice() {
        Comparator<ProductModel> byPrice = Comparator.comparingDouble(ProductModel::getPrice);

        Collections.sort(products, byPrice);
    }

    public void sortProductsByPriceReverse() {
        Comparator<ProductModel> byPrice = Comparator.comparingDouble(ProductModel::getPrice).reversed();

        Collections.sort(products, byPrice);
    }
}
