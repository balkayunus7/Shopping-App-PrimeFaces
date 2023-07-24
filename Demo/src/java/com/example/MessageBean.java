import com.example.PersonaModel;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class MessageBean {

    private PersonaModel selectedPersona;
// Created methods of the product to be selected with the button

    public PersonaModel getSelectedPersona() {
        return selectedPersona;
    }

    public void setSelectedPersona(PersonaModel selectedPersona) {
        this.selectedPersona = selectedPersona;
    }

    //  Show facesmessage
    public void showMessage(PersonaModel persona) {
        selectedPersona = persona;
        String productName = selectedPersona.getName();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!!!!!!!!!", "Product added to cart. "));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Name:", productName));
    }

    //  Show facesmessage    
    public void showDeleteMessage(PersonaModel persona) {
        selectedPersona = persona;
        String productName = selectedPersona.getName();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!!!!!!!!!", "Product deleted the cart. "));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Name:", productName));
    }

    // Mesajları görüntülemekle ilgili diğer metotlar
}
