
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ApplicationScoped
@ManagedBean
public class RoutingBean implements Serializable {


    // Method to redirect to the main page
    public String goBacktoPage() {
        return "index.xhtml?faces-redirect=true";
    }

    public String returnRegister() {
        return "register_page.xhtml"; // After logging out, redirect to the login page (login_page.xhtml)
    }

    public String returnOrderPage() {
        return "order_page.xhtml"; // After logging out, redirect to the login page (login_page.xhtml)
    }

    public String returnCartPage() {
        return "cart_page.xhtml"; // After logging out, redirect to the login page (login_page.xhtml)
    }

    public String returnLogin() {
        return "login_page.xhtml"; // After logging out, redirect to the login page (login_page.xhtml)
    }

    public String returnPayment() {
        return "payment_page.xhtml"; // After logging out, redirect to the login page (login_page.xhtml)
    }
}
