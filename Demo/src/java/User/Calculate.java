package User;

import java.io.Serializable;
import java.util.Random;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


@ManagedBean
@ApplicationScoped
public class Calculate implements Serializable {

    public int returnRandomNumber() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    public int returnRandomOrderNumber() {
        Random random = new Random();
        return random.nextInt(10000000) + 1;
    }

}
