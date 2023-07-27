
import com.example.ProductModel;
import java.io.Serializable;
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
        List<ProductModel> cartProducts = databaseBean.getProductInList(username);

        try {

            // Check if the product is already in the user's cart before adding it
            for (ProductModel cartProduct : cartProducts) {
                if (cartProduct.getId() == product.getId()) {
                    int newQuantity = cartProduct.getQuantity() + 1;

                    // Product is already in the cart, show a message and return without adding it again
                    databaseBean.updateCartProductQuantity(username, cartProduct.getId(), newQuantity);
                    return;
                }
            }

            // If the product is not already in the cart, add it
            databaseBean.addProductToCart(username, product.getId(), product.getQuantity());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product added to cart.", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding product to cart.", null));
        }
    }

    public List<ProductModel> getCartProducts() throws ClassNotFoundException {
        DatabaseBean databaseBean = new DatabaseBean();
        return databaseBean.getProductInList(username);
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
