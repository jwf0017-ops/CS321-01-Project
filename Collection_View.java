import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    private HashMap<String, ArrayList<Integer>> collections;

    private final String DEFAULT_COLLECTION = "Favorites";

    private String lastSelectedCollection = null;
    private Integer lastSelectedGame = null;

    private ArrayList<Game> allGames;

    public Collection_View() {

        setLayout(new BorderLayout());
        collections = new HashMap<>();

        collectionModel = new DefaultListModel<>();
        collectionList = new JList<>(collectionModel);
        collectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        gameModel = new DefaultListModel<>();
        gameList = new JList<>(gameModel);
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(collectionList),
                new JScrollPane(gameList)
        );

        splitPane.setDividerLocation(150);
        add(splitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        addCollectionButton = new JButton("Add Collection");
        renameButton = new JButton("Rename");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addCollectionButton);
        buttonPanel.add(renameButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.NORTH);

        collectionModel.addElement(DEFAULT_COLLECTION);
        collections.put(DEFAULT_COLLECTION, new ArrayList<>());

        addCollectionButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Collection name:");
            if (name == null || name.trim().isEmpty()) return;

            if (!collections.containsKey(name)) {
                collectionModel.addElement(name);
                collections.put(name, new ArrayList<>());
            }
        });

        collectionList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                int index = collectionList.locationToIndex(e.getPoint());
                if (index == -1) return;

                String clicked = collectionModel.getElementAt(index);

                if (clicked.equals(lastSelectedCollection)) {
                    collectionList.clearSelection();
                    gameModel.clear();
                    lastSelectedCollection = null;
                    lastSelectedGame = null;
                } else {
                    collectionList.setSelectedIndex(index);
                    lastSelectedCollection = clicked;

                    gameModel.clear();

                    for (Integer id : collections.get(clicked)) {
                        gameModel.addElement(getGameNameByID(id));
                    }
                }
            }
        });
    }

    public void setGames(ArrayList<Game> games) {
        this.allGames = games;
    }

    private String getGameNameByID(int id) {
        for (Game g : allGames) {
            if (g.getID() == id) return g.getName();
        }
        return "Unknown";
    }

    public void addGameToSelectedCollection(Game game) {

        String selected = collectionList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a collection first.");
            return;
        }

        ArrayList<Integer> list = collections.get(selected);

        if (list.contains(game.getID())) {
            JOptionPane.showMessageDialog(this, "Game already in collection.");
            return;
        }

        list.add(game.getID());
        gameModel.addElement(game.getName());
    }

    public HashMap<String, ArrayList<Integer>> getCollections() {
        return collections;
    }

    public void loadFromUser(User user) {

        collections.clear();
        collectionModel.clear();

        collectionModel.addElement(DEFAULT_COLLECTION);
        collections.put(DEFAULT_COLLECTION, new ArrayList<>(user.getCollactionsList()));

        for (Integer id : user.getCollactionsList()) {
            gameModel.addElement(getGameNameByID(id));
        }
    }
}
