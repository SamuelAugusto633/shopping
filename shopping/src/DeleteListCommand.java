import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteListCommand implements ActionListener {
    private final JFrame frame;
    private final JComboBox<String> listsComboBox;
    private final ArrayList<ListSubject> lists;

    DeleteListCommand(JFrame frame, JComboBox<String> listsComboBox, ArrayList<ListSubject> lists) {
        this.frame = frame;
        this.listsComboBox = listsComboBox;
        this.lists = lists;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = listsComboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            lists.remove(selectedIndex);
            listsComboBox.removeItemAt(selectedIndex);
            frame.dispose();
        }
    }
}





