
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListLoader {
    // Instância única da classe ListLoader
    private static final ListLoader INSTANCE = new ListLoader();

    // Construtor protected para evitar instanciação externa
    protected ListLoader() {
    }

    // Método estático para acessar a instância única
    public static ListLoader getInstance() {
        return INSTANCE;
    }

    // Método para carregar listas
    public static ArrayList<ListSubject> loadLists() {
        ArrayList<ListSubject> lists = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("lists.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("END")) {
                    String listName = line;
                    ShoppingList shoppingList = new ShoppingList(listName);
                    while (!(line = reader.readLine()).equals("END")) {
                        shoppingList.addProduct(new Product(line));
                    }
                    lists.add(shoppingList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lists;
    }
}



