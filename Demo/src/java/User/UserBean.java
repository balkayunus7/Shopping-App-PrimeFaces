import Database.DatabaseBean;
import Models.OrderModel;
import Models.ProductModel;
import Router.RoutingBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    private String username;
    private String password;
    private String email;
    private boolean loggedIn;
    private int newQuantity;
    RoutingBean router = new RoutingBean();

    // Getter and Setter methods for the properties
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method to register the user
    public String register() {
        // Save the user in the database
        try {
            DatabaseBean databaseBean = new DatabaseBean();
            databaseBean.registerUser(username, email, password);

        } catch (Exception e) {
            System.out.println("User not saved");
        }
        return "login_page.xhtml"; // Redirect to the login page after registration
    }

    // Method to check if the user is authenticated in the system
    public String login() {
        // Check if the user is authenticated in the database
        DatabaseBean databaseBean = new DatabaseBean();
        loggedIn = databaseBean.loginUser(username, password);
        if (loggedIn) {
            return router.goBacktoPage(); // If login is successful, redirect to the personalized user page (index.xhtml)
        } else {
            return router.returnLogin(); // If login fails, redirect back to the login page (login_page.xhtml)
        }
    }

    // Method to logout the user
    public String logout() {
        // Terminate the user session
        loggedIn = false;
        return router.returnLogin(); // After logging out, redirect to the login page (login_page.xhtml)
    }

    public void addCart(ProductModel product) throws ClassNotFoundException {
        DatabaseBean databaseBean = new DatabaseBean();
        List<ProductModel> cartProductsList = databaseBean.getProductInList(username);

        // Check if the product is already in the cart
        for (ProductModel cartProduct : cartProductsList) {
            if (cartProduct.getId() == product.getId()) {
                // Product is already in the cart, increment the quantity and update the database
                newQuantity = cartProduct.getQuantity() + 1;
                cartProduct.setQuantity(newQuantity);
                try {
                    databaseBean.updateProductQuantity(username, product.getId(), cartProduct.getQuantity());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product quantity updated in cart.", null));
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating product quantity in cart.", null));
                }
                //   System.out.println("Added product '" + cartProduct.getName() + "' with quantity: " + cartProduct.getQuantity());
                return; // Exit the method after updating the quantity
            }
        }

        // Product is not in the cart, add it to the cart and update the database
        product.setQuantity(1);
        cartProductsList.add(product);
        try {
            databaseBean.addProductToCart(username, product.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product added to cart.", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding product to cart.", null));
        }
    }

    public List<ProductModel> getCartProductsList() throws ClassNotFoundException {
        DatabaseBean databaseBean = new DatabaseBean();
        // Call the getProductInList method of the DatabaseBean class to retrieve the cart products for the given username
        return databaseBean.getProductInList(username);
    }

    public void decreaseCart(ProductModel product) throws ClassNotFoundException {

        DatabaseBean databaseBean = new DatabaseBean();
        List<ProductModel> cartProductsList = databaseBean.getProductInList(username);

        // Find the product in the cart
        for (ProductModel cartProduct : cartProductsList) {
            if (cartProduct.equals(product)) {
                System.out.println(cartProduct.getName() + cartProduct.getQuantity());
                int newDecQuantity = cartProduct.getQuantity() - 1;

                if (newDecQuantity > 0) {
                    // Decrement the quantity and update the database
                    cartProduct.setQuantity(newDecQuantity);
                    try {
                        databaseBean.updateProductQuantity(username, product.getId(), cartProduct.getQuantity());
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product quantity updated in cart.", null));
                    } catch (Exception e) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating product quantity in cart.", null));
                    }
                    System.out.println("Reduced product quantity: '" + cartProduct.getName() + "' - Quantity: " + cartProduct.getQuantity());
                } else {
                    // Remove the product from the cart and update the database
                    cartProductsList.remove(cartProduct);
                    try {
                        databaseBean.removeProductFromCart(username, product.getId());
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product removed from cart.", null));
                    } catch (Exception e) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error removing product from cart.", null));
                    }
                    System.out.println("Product removed from cart: '" + cartProduct.getName() + "'");
                }
                return; // Exit the method after updating the quantity or removing the product
            }
        }
    }

    public void removeProduct(ProductModel product) {
        try {
            DatabaseBean databaseBean = new DatabaseBean();
            // Call the removeProductFromCart method of the DatabaseBean class to remove the product from the cart in the database
            databaseBean.removeProductFromCart(username, product.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product removed from cart.", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error removing product from cart.", null));
        }
    }

    public double calculateTotalPrice() throws ClassNotFoundException {
        double totalPrice = 0.0;
        DatabaseBean databaseBean = new DatabaseBean();
        List<ProductModel> cartProductsList = databaseBean.getProductInList(username);

        // Calculate the total price by summing up the price of each product multiplied by its quantity
        for (ProductModel cartProduct : cartProductsList) {
            totalPrice += cartProduct.getPrice() * cartProduct.getQuantity();
        }
        return totalPrice;
    }

    // Getter method for the total price
    public double getTotalPrice() {
        try {
            return calculateTotalPrice();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public String createOrderUser() {

        try {
            DatabaseBean databaseBean = new DatabaseBean();
            List<ProductModel> cartProductsList = databaseBean.getProductInList(username);
            int orderId = databaseBean.createOrder(username, getTotalPrice(), returnRandomOrderNumber());

            for (ProductModel cartProduct : cartProductsList) {
                databaseBean.addOrderItem(orderId, cartProduct.getId(), cartProduct.getQuantity());
            }
            clearCart();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating order.", null));
        }
        return router.goBacktoPage();

    }

    private void clearCart() {
        try {
            DatabaseBean databaseBean = new DatabaseBean();
            List<ProductModel> cartProductsList = databaseBean.getProductInList(username);

            for (ProductModel cartProduct : cartProductsList) {
                // Her bir ürünü sepetten çıkarın
                databaseBean.removeProductFromCart(username, cartProduct.getId());
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sepet temizlenirken hata oluştu.", null));
        }
    }

    public List<OrderModel> getUserOrders() {
        try {
            DatabaseBean databaseBean = new DatabaseBean();
            return databaseBean.getOrdersByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    public List<ProductModel> getOrderItemsForOrderId(int orderId) {
        try {
            DatabaseBean databaseBean = new DatabaseBean();
            return databaseBean.getOrderItemsForOrder(orderId, newQuantity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    private List<ProductModel> selectedOrderItems;

    // Getter method for selectedOrderItems
    public List<ProductModel> getSelectedOrderItems() {
        return selectedOrderItems;
    }

    // Method to view the order items for a specific order ID
    public void viewOrderItems(int orderId) {
        // Retrieve the order items for the given order ID using getOrderItemsForOrderId method
        selectedOrderItems = getOrderItemsForOrderId(orderId);
        // Show the dialog with the order items
        PrimeFaces.current().executeScript("PF('orderItemsDialog').show();");
    }

    public int returnRandomNumber() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    public int returnRandomOrderNumber() {
        Random random = new Random();
        return random.nextInt(10000000) + 1;
    }

}