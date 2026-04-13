import javax.swing.*;
import java.awt.*;

public class Search_View extends JPanel {

    private JTextField searchField;
    private JButton searchButton;
    private JList<String> resultsList;

    public Search_View() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        resultsList = new JList<>();
        add(new JScrollPane(resultsList), BorderLayout.CENTER);
    }

    public String getSearchQuery() {
        return searchField.getText();
    }

    public void setResults(String[] results) {
        resultsList.setListData(results);
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}