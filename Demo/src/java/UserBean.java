
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
