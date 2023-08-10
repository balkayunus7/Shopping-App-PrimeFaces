import com.example.OrderModel;
import com.example.ProductModel;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseOperations {
    void init() throws ClassNotFoundException, SQLException;
    void sortProductsByPrice();
    void sortProductsByPriceReverse();
    boolean registerUser(String username, String password, String email);
    boolean loginUser(String username, String password);
    void addProductToCart(String username, int productId);
    void removeProductFromCart(String username, int productId);
    List<ProductModel> getProductInList(String username) throws ClassNotFoundException;
    void updateProductQuantity(String username, int productId, int newQuantity);
    int createOrder(String username, double total_price, int number) throws ClassNotFoundException, SQLException;
    void addOrderItem(int orderId, int productId, int quantity) throws ClassNotFoundException, SQLException;
    List<OrderModel> getOrdersByUsername(String username);
    List<ProductModel> getOrderItemsForOrder(int orderId, int newQuantity) throws ClassNotFoundException;
}