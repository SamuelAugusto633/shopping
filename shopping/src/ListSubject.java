import java.util.ArrayList;

public class ListSubject {
    private final ArrayList<ListObserver> observers = new ArrayList<>();

    // Método para anexar um observador à lista de observadores
    void attach(ListObserver observer) {
        observers.add(observer);
    }

    // Método para notificar todos os observadores quando ocorrem mudanças
    void notifyObservers() {
        for (ListObserver observer : observers) {
            observer.updateList();
        }
    }
}













