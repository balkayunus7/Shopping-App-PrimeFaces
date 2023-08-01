
import com.example.ProductModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    private String username;
    private String password;
    private String email;
    private boolean loggedIn;
    private int newQuantity;

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
            return "index.xhtml"; // If login is successful, redirect to the personalized user page (index.xhtml)
        } else {
            return "login_page.xhtml"; // If login fails, redirect back to the login page (login_page.xhtml)
        }
    }

    // Method to logout the user
    public String logout() {
        // Terminate the user session
        loggedIn = false;
        return "login_page.xhtml"; // After logging out, redirect to the login page (login_page.xhtml)
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
        return databaseBean.getProductInList(username);
    }

    public void decreaseCart(ProductModel product) throws ClassNotFoundException {

        DatabaseBean databaseBean = new DatabaseBean();
        List<ProductModel> cartProductsList = databaseBean.getProductInList(username);

        // Find the product in the cart
        for (ProductModel cartProduct : cartProductsList) {
            System.out.println("fora girdi");
            if (cartProduct.equals(product)) {
                System.out.println(cartProduct.getName() + cartProduct.getQuantity());
                int newDecQuantity = cartProduct.getQuantity() - 1;

                if (newDecQuantity > 0) {
                    System.out.println("Azaltmaya girdiiiiii");
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
            databaseBean.removeProductFromCart(username, product.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product removed from cart.", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error removing product from cart.", null));
        }
    }

    // Method to redirect to the main page
    public String goBacktoPage() {
        return "index.xhtml?faces-redirect=true";
    }

    public String returnRegister() {
        return "register_page.xhtml"; // After logging out, redirect to the login page (login_page.xhtml)
    }

    public String returnLogin() {
        return "login_page.xhtml"; // After logging out, redirect to the login page (login_page.xhtml)
    }

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

}
