package User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ApplicationScoped
@ManagedBean
public class UserUtility {

    private String quantityUpdateMessage = "Product quantity updated in cart.";
    private String quantityUpdateErrMessage = "Error updating product quantity in cart.";
    private String firstAddedProductMessage = "Product added to cart.";
    private String firstAddedProductErrMessage = "Error adding product to cart.";
    private String productDecreaseErrMessage = "Error decrease product quantity in cart.";
    private String productRemoveErrMessage = "Error removing product from cart.";
    private String orderCreateErrMessage = "Error creating order.";
    private String orderClearErrMessage = "Error clear order";

    public String getQuantityUpdateMessage() {
        return quantityUpdateMessage;
    }

    public String getQuantityUpdateErrMessage() {
        return quantityUpdateErrMessage;
    }

    public String getFirstAddedProductMessage() {
        return firstAddedProductMessage;
    }

    public String getFirstAddedProductErrMessage() {
        return firstAddedProductErrMessage;
    }

    public String getProductDecreaseErrMessage() {
        return productDecreaseErrMessage;
    }

    public String getProductRemoveErrMessage() {
        return productRemoveErrMessage;
    }

    public String getOrderCreateErrMessage() {
        return orderCreateErrMessage;
    }

    public String getOrderClearErrMessage() {
        return orderClearErrMessage;
    }

    public static void addInfoMessage(String message) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

}
