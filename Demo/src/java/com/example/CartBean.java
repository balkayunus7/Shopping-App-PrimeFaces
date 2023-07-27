package com.example;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class CartBean {
    
    private List<ProductModel> selectedItems = new ArrayList<>();
    private double totalPrice;
    private ProductModel selectedProduct;

    // Getter and Setter methods for selectedPersona
    public ProductModel getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductModel selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    // Getter and Setter methods for selectedItems
    public List<ProductModel> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<ProductModel> selectedItems) {
        this.selectedItems = selectedItems;
    }

    /////////////////////////////////////////////////////////////////////////
    // Method that calculates the total amount of all products in the basket
    public double calculateTotalPrice() {
        double total = 0;
        for (ProductModel item : selectedItems) {
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
    public void deleteCart(ProductModel product) {
        selectedProduct = product;
        try {
            if (selectedItems.contains(selectedProduct)) {
                // Schedule the redirect after 1 second (1000 milliseconds)
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> {
                    goToPage();
                }, 1, TimeUnit.SECONDS);
                scheduler.shutdown();
                selectedProduct.setQuantity(1); // Set the quantity of the product in the cart to 1
                selectedItems.remove(selectedProduct);
            } else {
                // If the product is not in the cart, do not take any action
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateTotalPrice();
    }

    // Method to add a product to the cart
    public void addCart(ProductModel persona) {
        selectedProduct = persona;
        try {
            int index = selectedItems.indexOf(selectedProduct);
            if (index >= 0) {
                // If the product is already in the cart, just increase the number of items
                ProductModel existingItem = selectedItems.get(index);
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
                selectedProduct.setQuantity(1);
                selectedItems.add(selectedProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateTotalPrice();
    }

    // Method to remove a product from the cart
    public void removeCart(ProductModel product) {
        selectedProduct = product;
        try {
            int index = selectedItems.indexOf(selectedProduct);
            if (index >= 0) {
                ProductModel existingItem = selectedItems.get(index);
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

    // Wrote code to detect the clicked value for dialog
    public void view(ProductModel product) {
        selectedProduct = product;
    }

    // Method to redirect to the cart page
    public String goToPage() {
        return "cart_page.xhtml?faces-redirect=true";
    }

    

    // Method to redirect to the payment page
    public String goPaymentPage() {
        return "payment_page.xhtml?faces-redirect=true";
    }

  

    public void showMessage(ProductModel product) {
        selectedProduct = product;
        String productName = selectedProduct.getName();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!!!!!!!!!", "Product added to cart. "));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Name:", productName));
    }
}
