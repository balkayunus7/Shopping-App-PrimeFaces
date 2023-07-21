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
    private PersonaModel selectedPersona;
    private final String imageUrl = "https://img.freepik.com/free-vector/hand-drawn-flat-design-stack-books-illustration_23-2149330605.jpg?w=2000";
    private final String imageUrl2 = "https://cdn.shopify.com/s/files/1/2610/9472/products/BL2189_BENTLEY_NOTEBOOK_BLACK_SILVER_01_2048x2048.jpg?v=1685033948";
    private final String imageUrl3 = "https://img.freepik.com/premium-vector/pencil-cartoon-style-vector-illustration_484148-216.jpg?w=360";
    private final String imageUrl4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShUfZgUm5lK2zf0w71PZkoxeP32RD1DazxTw&usqp=CAU";
    private final String imageUrl5 = "https://www.kikajoy.com/kika-renkli-mukavva-5070-karisik-mukavvalar-kika-6716-93-B.jpg";
    private final String imageUrl6 = "https://previews.123rf.com/images/ylivdesign/ylivdesign1609/ylivdesign160907036/63328021-computer-desk-icon-in-cartoon-style-isolated-on-white-background.-furniture-symbol-vector-illustration.jpg";
    private final String imageUrl7 = "https://www.atlas.lk/myshop/wp-content/uploads/2022/10/WF7140202-600x600.jpg";
    private final String imageUrl8 = "https://easydrawingguides.com/wp-content/uploads/2020/02/how-to-draw-a-table-featured-image-1200.png";
    private final String imageUrl9 = "https://w7.pngwing.com/pngs/441/130/png-transparent-pencil-sharpeners-drawing-crayon-pencil-rectangle-orange.png";

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

    //   Add to cart
    public void addCart(PersonaModel persona) {
        selectedPersona = persona;
        try {
            showMessage(persona);
            // Schedule the redirect after 3 seconds
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                goToPage();
            }, 1, TimeUnit.SECONDS);
            scheduler.shutdown();
            selectedItems.add(selectedPersona);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Selected items size: " + selectedItems.size()); // Kontrol için ekrana liste boyutu yazdırılıyor.
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
        personaList.add(new PersonaModel(0, 10, "Kitap", 128, 4.5, imageUrl));
        personaList.add(new PersonaModel(1, 7, "Defter", 250, 4.2, imageUrl2));
        personaList.add(new PersonaModel(2, 12, "Kalem", 2200, 3.2, imageUrl3));
        personaList.add(new PersonaModel(3, 55, "Pastel Boya", 22, 2.7, imageUrl4));
        personaList.add(new PersonaModel(4, 4, "Mukavva", 130, 4.8, imageUrl5));
        personaList.add(new PersonaModel(5, 200, "Sıra", 10, 3.9, imageUrl6));
        personaList.add(new PersonaModel(6, 4, "Silgi", 88, 1.6, imageUrl7));
        personaList.add(new PersonaModel(7, 500, "Masa", 20, 4, imageUrl8));
        personaList.add(new PersonaModel(8, 3, "Kalemtıraş", 122, 3, imageUrl9));
    }
}
