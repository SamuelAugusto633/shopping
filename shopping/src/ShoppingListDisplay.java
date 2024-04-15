import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingListDisplay extends JFrame {
    private final JCheckBox[] checkBoxes;
    private final JButton exitButton;

    public ShoppingListDisplay(ListSubject listSubject) {
        setTitle("Exibição da lista de compras");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ShoppingList shoppingList = (ShoppingList) listSubject;
        ArrayList<Product> products = shoppingList.getProducts();
        checkBoxes = new JCheckBox[products.size()];
        JPanel panel = new JPanel(new GridLayout(products.size(), 1));

        for (int i = 0; i < products.size(); i++) {
            checkBoxes[i] = new JCheckBox(products.get(i).getName());
            panel.add(checkBoxes[i]);
        }

        add(panel, BorderLayout.CENTER);

        exitButton = new JButton("Sair");
        exitButton.addActionListener(e -> checkCompletion());
        add(exitButton, BorderLayout.SOUTH);

        setVisible(true);
    }







    
    private void checkCompletion() {
        boolean allChecked = true;
        for (JCheckBox checkBox : checkBoxes) {
            if (!checkBox.isSelected()) {
                allChecked = false;
                break;
            }
        }
    
        if (allChecked) {
            JOptionPane.showMessageDialog(null, "Todos os itens comprados!");
            setVisible(false); // Oculta a janela de exibição da lista
        } else {
            JOptionPane.showMessageDialog(null, "Alguns itens ainda estão faltando!");
        }
    }
    





    
}
