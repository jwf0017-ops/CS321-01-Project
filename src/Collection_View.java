import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Collection_View extends JPanel {

    private DefaultListModel<String> collectionModel;
    private JList<String> collectionList;

    private DefaultListModel<String> gameModel;
    private JList<String> gameList;

    private JButton addCollectionButton;
    private JButton renameButton;
    private JButton deleteButton;
    private JButton removeGameButton;

    private HashMap<String, ArrayList<String>> collections;

    private final String DEFAULT_COLLECTION = "Favorites";

    public Collection_View() {
        setLayout(new BorderLayout());

        collections = new HashMap<>();

        // Collection list
        collectionModel = new DefaultListModel<>();
        collectionList = new JList<>(collectionModel);

        // Game list
        gameModel = new DefaultListModel<>();
        gameList = new JList<>(gameModel);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(collectionList),
                new JScrollPane(gameList)
        );

        add(splitPane, BorderLayout.CENTER);

        //  Button panel
        JPanel buttonPanel = new JPanel();

        addCollectionButton = new JButton("Add Collection");
        renameButton = new JButton("Rename");
        deleteButton = new JButton("Delete");
        removeGameButton = new JButton("Remove Game");

        buttonPanel.add(addCollectionButton);
        buttonPanel.add(renameButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(removeGameButton);

        add(buttonPanel, BorderLayout.NORTH);

        //  create default "favorites list"
        collectionModel.addElement(DEFAULT_COLLECTION);
        collections.put(DEFAULT_COLLECTION, new ArrayList<>());

        //  Add collection
        addCollectionButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Collection name:");
            if (name == null || name.isEmpty()) {
                name = "New Collection";
            }

            // Can't make a collection with the same name
            if (!collections.containsKey(name)) {
                collectionModel.addElement(name);
                collections.put(name, new ArrayList<>());
            } else {
                JOptionPane.showMessageDialog(this, "Collection already exists.");
            }
        });

        // Rename collection
        renameButton.addActionListener(e -> {
            String selected = collectionList.getSelectedValue();
            if (selected == null) return;

            //  Prevent renaming Favorites
            if (selected.equals(DEFAULT_COLLECTION)) {
                JOptionPane.showMessageDialog(this, "Favorites cannot be renamed.");
                return;
            }

            String newName = JOptionPane.showInputDialog("New name:", selected);
            if (newName != null && !newName.isEmpty()) {

                ArrayList<String> games = collections.remove(selected);
                collections.put(newName, games);

                collectionModel.setElementAt(newName, collectionList.getSelectedIndex());
            }
        });

        //  Delete collection
        deleteButton.addActionListener(e -> {
            String selected = collectionList.getSelectedValue();
            if (selected == null) return;

            //  Prevent deleting Favorites
            if (selected.equals(DEFAULT_COLLECTION)) {
                JOptionPane.showMessageDialog(this, "Favorites cannot be deleted.");
                return;
            }

            collections.remove(selected);
            collectionModel.removeElement(selected);
            gameModel.clear();
        });

        // Remove game from collection
        removeGameButton.addActionListener(e -> {
            String selectedCollection = collectionList.getSelectedValue();
            String selectedGame = gameList.getSelectedValue();

            if (selectedCollection == null || selectedGame == null) {
                JOptionPane.showMessageDialog(this, "Select a game to remove.");
                return;
            }

            collections.get(selectedCollection).remove(selectedGame);
            gameModel.removeElement(selectedGame);
        });

        // Show games in selected collection
        collectionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = collectionList.getSelectedValue();
                gameModel.clear();

                if (selected != null) {
                    for (String g : collections.get(selected)) {
                        gameModel.addElement(g);
                    }
                }
            }
        });
    }

    // Add game to selected collection
    public void addGameToSelectedCollection(String game) {
        String selected = collectionList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a collection first.");
            return;
        }

        collections.get(selected).add(game);
        gameModel.addElement(game);
    }
}
