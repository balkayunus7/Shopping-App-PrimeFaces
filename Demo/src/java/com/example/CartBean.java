
import com.example.PersonaModel;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ManagedBean
@SessionScoped
public class CartBean {

    private List<PersonaModel> selectedItems = new ArrayList<>();
    private double totalPrice;
    private PersonaModel selectedPersona;
    private MessageBean messageBean;

    // Getter and Setter methods for MessageBean
    public MessageBean getMessageBean() {
        return messageBean;
    }

    public void setMessageBean(MessageBean messageBean) {
        this.messageBean = messageBean;
    }

    // Getter and Setter methods for selectedPersona
    public PersonaModel getSelectedPersona() {
        return selectedPersona;
    }

    public void setSelectedPersona(PersonaModel selectedPersona) {
        this.selectedPersona = selectedPersona;
    }

    // Getter and Setter methods for selectedItems
    public List<PersonaModel> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<PersonaModel> selectedItems) {
        this.selectedItems = selectedItems;
    }

    /////////////////////////////////////////////////////////////////////////
    // Method that calculates the total amount of all products in the basket
    public double calculateTotalPrice() {
        double total = 0;
        for (PersonaModel item : selectedItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // Getter and Setter methods for totalPrice
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
     // Method that calculates the total amount of all products in the basket
    public void updateTotalPrice() {
        double total = calculateTotalPrice();
        // Set the updated total price to a variable in the CartBean class
        // This variable can be used in the XHTML to display the updated total price
        setTotalPrice(total);
    }

    // Method to remove a product from the cart
    public void deleteCart(PersonaModel persona) {
        selectedPersona = persona;
        try {
            if (selectedItems.contains(selectedPersona)) {
                // If the product is in the cart, remove the product from the cart
                messageBean.showDeleteMessage(selectedPersona);

                // Schedule the redirect after 1 second (1000 milliseconds)
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> {
                    goToPage();
                }, 1, TimeUnit.SECONDS);
                scheduler.shutdown();
                selectedPersona.setQuantity(1); // Set the quantity of the product in the cart to 1
                selectedItems.remove(selectedPersona);
            } else {
                // If the product is not in the cart, do not take any action
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateTotalPrice();
    }


    // Method to add a product to the cart
    public void addCart(PersonaModel persona) {
        selectedPersona = persona;
        try {
            int index = selectedItems.indexOf(selectedPersona);
            if (index >= 0) {
                // If the product is already in the cart, just increase the number of items
                PersonaModel existingItem = selectedItems.get(index);
                existingItem.setQuantity(existingItem.getQuantity() + 1);
            } else {
                // If the item is not in the cart, add it to the cart
                messageBean.showMessage(persona);

                // Schedule the redirect after 1 second (1000 milliseconds)
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> {
                    goToPage();
                }, 1, TimeUnit.SECONDS);
                scheduler.shutdown();
                selectedPersona.setQuantity(1);
                selectedItems.add(selectedPersona);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateTotalPrice();
    }

    // Method to remove a product from the cart
    public void removeCart(PersonaModel persona) {
        selectedPersona = persona;
        try {
            int index = selectedItems.indexOf(selectedPersona);
            if (index >= 0) {
                PersonaModel existingItem = selectedItems.get(index);
                int quantity = existingItem.getQuantity();
                if (quantity > 1) {
                    // If the quantity is more than 1, decrease the number of items
                    existingItem.setQuantity(quantity - 1);
                } else {
                    // If the quantity is 1 or less, remove the item from the cart
                    selectedItems.remove(index);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateTotalPrice();
    }

    // Method to redirect to the cart page
    public String goToPage() {
        return "cart_page.xhtml?faces-redirect=true";
    }

    // Method to redirect to the main page
    public String goBacktoPage() {
        return "index.xhtml?faces-redirect=true";
    }

    // Method to redirect to the payment page
    public String goPaymentPage() {
        return "payment_page.xhtml?faces-redirect=true";
    }

    // Other methods related to the cart...
}
