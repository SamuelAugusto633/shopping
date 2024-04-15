import java.util.ArrayList;

public class ShoppingListFactory implements ListFactory {
    @Override
    public ListSubject createList(String name) {
        return new ShoppingList(name);
    }
}











