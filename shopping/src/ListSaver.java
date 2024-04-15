import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ListSaver {
    public static void saveLists(ArrayList<ListSubject> lists) {
        try (FileWriter writer = new FileWriter("lists.txt")) {
            for (ListSubject list : lists) {
                if (list instanceof ShoppingList) {
                    writer.write(((ShoppingList) list).getName() + "\n");
                    for (Product product : ((ShoppingList) list).getProducts()) {
                        writer.write(product.getName() + "\n");
                    }
                    writer.write("END\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
