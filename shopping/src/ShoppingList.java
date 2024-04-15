import javax.swing.*;
import java.util.ArrayList;

public class ShoppingList extends ListSubject {
    private final String name;
    private final ArrayList<Product> products = new ArrayList<>();

    ShoppingList(String name) {
        this.name = name;
    }

    void addProduct(Product product) {
        if (products.size() < 20) {
            products.add(product);
            notifyObservers();
        } else {
            JOptionPane.showMessageDialog(null, "Máximo de produtos alcançado (20)");
        }
    }

    ArrayList<Product> getProducts() {
        return products;
    }

    String getName() {
        return name;
    }
}


