import javax.swing.*;
import java.awt.*;

public class Collection_View extends JPanel {

    private JButton newCollection;
    private JLabel titleLabel;
    private JList<String> collectionList;

    public Collection_View() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        newCollection = new JButton("Add Collection");

        topPanel.add(newCollection);

        add(topPanel, BorderLayout.NORTH);

        titleLabel = new JLabel("Collection");
        add(titleLabel, BorderLayout.NORTH);

        collectionList = new JList<>();
        add(new JScrollPane(collectionList), BorderLayout.CENTER);
    }


    public JButton setNewCollection() {
        return newCollection;
    }


    public void setCollectionName(String name) {
        titleLabel.setText(name);
    }

    public void setGames(String[] games) {
        collectionList.setListData(games);
    }
}