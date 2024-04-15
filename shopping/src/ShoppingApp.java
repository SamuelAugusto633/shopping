import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingApp extends JFrame implements ListObserver {
    private final ArrayList<ListSubject> lists = new ArrayList<>();
    private final JComboBox<String> listsComboBox;
    private final DefaultListModel<String> productsListModel;
    private final JButton addButton;
    private final JButton modifyButton;
    private final JButton deleteButton;
    private final JButton readyButton;
    private final JButton saveButton;
    private final JButton loadButton; 
    private final JButton deleteListButton; 

    public ShoppingApp() {
        setTitle("Listas de compras");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());





        JPanel topPanel = new JPanel(new FlowLayout());
        JTextField listNameField = new JTextField(15);
        addButton = new JButton("Adicionar Produtos");
        modifyButton = new JButton("Modificar Produtos");
        deleteButton = new JButton("Deletar Produto");
        readyButton = new JButton("Pronto");
        saveButton = new JButton("Salvar Lista"); 
        loadButton = new JButton("Carregar Lista"); 
        deleteListButton = new JButton("Deletar Lista"); 

        listsComboBox = new JComboBox<>();
        listsComboBox.setPreferredSize(new Dimension(200, 25));
        listsComboBox.addActionListener(e -> displaySelectedList());

        topPanel.add(new JLabel("Nome da lista: "));
        topPanel.add(listNameField);
        topPanel.add(addButton);
        topPanel.add(modifyButton);
        topPanel.add(deleteButton);
        topPanel.add(readyButton);
        topPanel.add(saveButton); 
        topPanel.add(loadButton); 
        topPanel.add(deleteListButton); 
        topPanel.add(listsComboBox);

        add(topPanel, BorderLayout.NORTH);

        JList<String> productsList = new JList<>();
        productsListModel = new DefaultListModel<>();
        productsList.setModel(productsListModel);

        //fonte personalizada para os itens da lista
        Font customFont = new Font("Arial", Font.PLAIN, 30); 
        productsList.setFont(customFont);



        add(new JScrollPane(productsList), BorderLayout.CENTER);

        addButton.addActionListener(e -> addProduct(listNameField.getText()));
        modifyButton.addActionListener(e -> modifyProduct(productsList.getSelectedValue()));
        deleteButton.addActionListener(e -> deleteProduct(productsList.getSelectedIndex()));
        readyButton.addActionListener(e -> passList());
        saveButton.addActionListener(e -> saveLists()); // Adicionando ação para salvar
        loadButton.addActionListener(e -> loadLists()); // Adicionando ação para carregar
        deleteListButton.addActionListener(e -> deleteList()); // Adicionando ação para deletar lista


        ListLoader listLoader = new ListLoader(); // Criando uma instância de ListLoader
        lists.addAll(listLoader.loadLists()); // Usando a instância para carregar as listas


        // Carregar as listas ao iniciar o app
        
        for (ListSubject list : lists) {
            list.attach(this);
            if (list instanceof ShoppingList) {
                listsComboBox.addItem(((ShoppingList) list).getName());
            }
        }



        listsComboBox.addActionListener(e -> {
            String selectedListName = (String) listsComboBox.getSelectedItem();
            if (selectedListName != null) {
                listNameField.setText(selectedListName);
            }
        });
        





        setVisible(true);
    }

    private void addProduct(String listName) {
        ListSubject listSubject = findOrCreateList(listName);
        if (listSubject != null && listSubject instanceof ShoppingList) {
            String productName = JOptionPane.showInputDialog("Nome do Produto:");
            if (productName != null && !productName.isEmpty()) {
                ((ShoppingList) listSubject).addProduct(new Product(productName));
            }
        }
    }

    private void modifyProduct(String productName) {
        if (productName != null && !productName.isEmpty()) {
            String modifiedName = JOptionPane.showInputDialog("Novo nome do produto:");
            if (modifiedName != null && !modifiedName.isEmpty()) {
                int selectedIndex = productsListModel.indexOf(productName);
                if (selectedIndex != -1) {
                    productsListModel.setElementAt(modifiedName, selectedIndex);
                    updateListSubject(selectedIndex, modifiedName);
                }
            }
        }
    }

    private void deleteProduct(int index) {
        if (index != -1) {
            productsListModel.remove(index);
            updateListSubject(index, null);
        }
    }

    private ListSubject findOrCreateList(String name) {
        for (ListSubject list : lists) {
            if (list instanceof ShoppingList && ((ShoppingList) list).getName().equals(name)) {
                return list;
            }
        }

        if (lists.size() < 10) {
            ListFactory listFactory = new ShoppingListFactory();
            ListSubject newList = listFactory.createList(name);
            lists.add(newList);
            listsComboBox.addItem(name);
            return newList;
        } else {
            JOptionPane.showMessageDialog(null, "Número máximo de listas alcançado (10)");
            return null;
        }
    }

    private void displaySelectedList() {
        int selectedIndex = listsComboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            ListSubject selectedList = lists.get(selectedIndex);
            productsListModel.clear();
            if (selectedList instanceof ShoppingList) {
                for (Product product : ((ShoppingList) selectedList).getProducts()) {
                    productsListModel.addElement(product.getName());
                }
            }
        }
    }

    private void passList() {
        int selectedIndex = listsComboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            ListSubject selectedList = lists.get(selectedIndex);
            if (selectedList instanceof ShoppingList) {
                if (((ShoppingList) selectedList).getProducts().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A lista está vazia!");
                } else {
                    new ShoppingListDisplay(selectedList); // Abre a janela  da lista
                }
            }
        }
    }

    private void saveLists() {
        ListSaver.saveLists(lists);
        JOptionPane.showMessageDialog(null, "Listas salvas com sucesso!");
    }

    private void loadLists() {
        ListLoader listLoader = new ListLoader();
        lists.clear();
        lists.addAll(ListLoader.loadLists());
        listsComboBox.removeAllItems();
        for (ListSubject list : lists) {
            list.attach(this);
            if (list instanceof ShoppingList) {
                listsComboBox.addItem(((ShoppingList) list).getName());
            }
        }
        JOptionPane.showMessageDialog(null, "Listas carregadas com sucesso!");
    }

    private void updateListSubject(int index, String newName) {
        int selectedIndex = listsComboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            ListSubject selectedList = lists.get(selectedIndex);
            if (selectedList instanceof ShoppingList) {
                ShoppingList shoppingList = (ShoppingList) selectedList;
                ArrayList<Product> products = shoppingList.getProducts();
                if (index >= 0 && index < products.size()) {
                    if (newName != null) {
                        products.get(index).setName(newName);
                    } else {
                        products.remove(index);
                    }
                    ListSaver.saveLists(lists);
                    displaySelectedList();
                }
            }
        }
    }

    private void deleteList() {
        int selectedIndex = listsComboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            int option = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir esta lista?", "Excluir lista", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                lists.remove(selectedIndex);
                listsComboBox.removeItemAt(selectedIndex);
                JOptionPane.showMessageDialog(null, "Lista excluída com sucesso!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma lista selecionada!");
        }
    }

    @Override
    public void updateList() {
        displaySelectedList();
    }

    @Override
    public void dispose() {
        // Salvar as listas ao fechar o aplicativo
        ListSaver.saveLists(lists);
        super.dispose();
    }

}
