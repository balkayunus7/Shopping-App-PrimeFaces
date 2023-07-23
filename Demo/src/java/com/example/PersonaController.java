package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class PersonaController {

    private List<PersonaModel> personaList = new ArrayList<>();
    private List<PersonaModel> selectedItems = new ArrayList<>();
    private double totalPrice;
    private PersonaModel selectedPersona;

    private final String imageUrl = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/91c40919-88ec-49ba-9eb5-ee8340b50613/air-jordan-legacy-312-low-ayakkab%C4%B1s%C4%B1-xvhbSF.png";
    private final String imageUrl2 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/3f0a3ad8-dee0-4549-87d8-ee993bb11a99/air-jordan-1-low-ayakkab%C4%B1s%C4%B1-m6nkFh.png";
    private final String imageUrl3 = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/5e546de6-53ca-4a0e-9cfc-78991cd3cb46/lebron-20-basketbol-ayakkab%C4%B1s%C4%B1-756ZMJ.png";
    private final String imageUrl4 = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/8c3fc113-2937-4c6c-ada1-b25241e70a50/air-jordan-37-low-basketbol-ayakkab%C4%B1s%C4%B1-nktz7g.png";
    private final String imageUrl5 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/0a588d2e-8d93-4937-8091-ec1270a953cf/air-force-1-shadow-ayakkab%C4%B1s%C4%B1-fNw903.png";
    private final String imageUrl6 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/91c40919-88ec-49ba-9eb5-ee8340b50613/air-jordan-legacy-312-low-ayakkab%C4%B1s%C4%B1-xvhbSF.png";
    private final String imageUrl7 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/3f0a3ad8-dee0-4549-87d8-ee993bb11a99/air-jordan-1-low-ayakkab%C4%B1s%C4%B1-m6nkFh.png";
    private final String imageUrl8 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/e0b75c0f-dd12-4463-81af-b0ccc2d4716e/court-vision-mid-next-nature-ayakkab%C4%B1s%C4%B1-QLllQ9.png";
    private final String imageUrl9 = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/23e27f6f-7422-4d9f-8ca9-a0a24e96ba29/10-billie-eilish-air-force-1-high-07-ayakkab%C4%B1-9f1Qr8.png";

    // Getter and Setter metodhs
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void updateTotalPrice() {
        // Method that calculates the total amount of all products in the basket
        double total = calculateTotalPrice();
        // Set the updated total price to a variable in the PersonaController class
        // This variable can be used in the XHTML to display the updated total price
        setTotalPrice(total);
    }

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

    public void deleteCart(PersonaModel persona) {
        selectedPersona = persona;
        try {
            if (selectedItems.contains(selectedPersona)) {
                // If the product is in the cart, remove the product from the cart
                showDeleteMessage(selectedPersona);
                // Schedule the redirect after 1 second (1000 milliseconds)
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> {
                    goToPage();
                }, 1, TimeUnit.SECONDS);
                scheduler.shutdown();
                selectedPersona.setQuantity(1); // Sepetteki ürün adedini 1'e düşür
                selectedItems.remove(selectedPersona);
            } else {
                // If the product is not in the cart, do not take any action
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateTotalPrice();
    }

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
                showMessage(persona);
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

    // Method that calculates the total amount of all products in the basket
    public double calculateTotalPrice() {
        double total = 0;
        for (PersonaModel item : selectedItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // Getter ve Setter methods Selected Lİst
    public List<PersonaModel> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<PersonaModel> selectedItems) {
        this.selectedItems = selectedItems;
    }

    // Go to Cart Page
    public String goToPage() {
        return "cart_page.xhtml?faces-redirect=true";
    }
    // Go to Main Page

    public String goBacktoPage() {
        return "index.xhtml?faces-redirect=true";
    }
    public String goPaymentPage() {
        return "payment_page.xhtml?faces-redirect=true";
    }

    // Wrote code to detect the clicked value for dialog
    public void view(PersonaModel persona) {
        selectedPersona = persona;
    }

    // Getter and Setter methods for MainLİst
    public List<PersonaModel> getList() {
        return personaList;
    }

    public void setList(List<PersonaModel> personaList) {
        this.personaList = personaList;
    }

    // Main List of products
    @PostConstruct
    public void init() {
        personaList.add(new PersonaModel(0, 10, "Air Jordan Legacy 312 Low", 0, 4.5, imageUrl, "Erkek Ayakkabısı"));
        personaList.add(new PersonaModel(1, 7, "Air Jordan 1 Low", 0, 4.2, imageUrl2, "Erkek Ayakkabısı"));
        personaList.add(new PersonaModel(2, 12, "Lebron 4", 0, 3.2, imageUrl3, "Basketbol Ayakkabısı"));
        personaList.add(new PersonaModel(3, 55, "Jordan One Take 4", 0, 2.7, imageUrl4, "Basketbol Ayakkabısı"));
        personaList.add(new PersonaModel(4, 4, "Nike Air Force 1 Shadow", 10, 4.8, imageUrl5, "Kadın Ayakkabısı"));
        personaList.add(new PersonaModel(5, 200, "Air Jordan Legacy 312", 0, 3.9, imageUrl6, "Basketbol Ayakkabısı"));
        personaList.add(new PersonaModel(6, 4, "Air Jordan 1 Low", 0, 1.6, imageUrl7, "Unisex"));
        personaList.add(new PersonaModel(7, 500, "Nike Court Vision Mid Next Nature", 0, 4, imageUrl8, "Unisex"));
        personaList.add(new PersonaModel(8, 3, "Nike x Billie Eilish Air Force 1 High '07", 0, 3, imageUrl9, "Kadın Ayakkabısı"));
    }
}
