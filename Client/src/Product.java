import java.io.Serializable;

public class Product implements Serializable {
    int id;
    String name;
    double price;

    @Override
    public String toString(){
        return id + ". " + name + ", " + price + " zł";
    }
}
