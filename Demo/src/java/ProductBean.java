import com.example.PersonaModel;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class ProductBean {

    private List<PersonaModel> personaList = new ArrayList<>();

    private final String imageUrl = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/91c40919-88ec-49ba-9eb5-ee8340b50613/air-jordan-legacy-312-low-ayakkab%C4%B1s%C4%B1-xvhbSF.png";
    private final String imageUrl2 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/3f0a3ad8-dee0-4549-87d8-ee993bb11a99/air-jordan-1-low-ayakkab%C4%B1s%C4%B1-m6nkFh.png";
    private final String imageUrl3 = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/5e546de6-53ca-4a0e-9cfc-78991cd3cb46/lebron-20-basketbol-ayakkab%C4%B1s%C4%B1-756ZMJ.png";
    private final String imageUrl4 = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/8c3fc113-2937-4c6c-ada1-b25241e70a50/air-jordan-37-low-basketbol-ayakkab%C4%B1s%C4%B1-nktz7g.png";
    private final String imageUrl5 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/0a588d2e-8d93-4937-8091-ec1270a953cf/air-force-1-shadow-ayakkab%C4%B1s%C4%B1-fNw903.png";
    private final String imageUrl6 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/91c40919-88ec-49ba-9eb5-ee8340b50613/air-jordan-legacy-312-low-ayakkab%C4%B1s%C4%B1-xvhbSF.png";
    private final String imageUrl7 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/3f0a3ad8-dee0-4549-87d8-ee993bb11a99/air-jordan-1-low-ayakkab%C4%B1s%C4%B1-m6nkFh.png";
    private final String imageUrl8 = "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/e0b75c0f-dd12-4463-81af-b0ccc2d4716e/court-vision-mid-next-nature-ayakkab%C4%B1s%C4%B1-QLllQ9.png";
    private final String imageUrl9 = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/23e27f6f-7422-4d9f-8ca9-a0a24e96ba29/10-billie-eilish-air-force-1-high-07-ayakkab%C4%B1-9f1Qr8.png";

    // Ana Listeyi getir ve ayarla metotları
    public List<PersonaModel> getList() {
        return personaList;
    }

    public void setList(List<PersonaModel> personaList) {
        this.personaList = personaList;
    }

    // Ürünlerin ana listesi
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
