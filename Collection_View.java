

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

    private HashMap<String, ArrayList<String>> collections;

    private final String DEFAULT_COLLECTION = "Favorites";

    // Track selections
    private String lastSelectedCollection = null;
    private String lastSelectedGame = null;

    public Collection_View() {

        setLayout(new BorderLayout());
        collections = new HashMap<>();

        // Collection List
        collectionModel = new DefaultListModel<>();
        collectionList = new JList<>(collectionModel);
        collectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Game list
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

        // buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        addCollectionButton = new JButton("Add Collection");
        renameButton = new JButton("Rename");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addCollectionButton);
        buttonPanel.add(renameButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.NORTH);

        // Default collection is Favorites
        collectionModel.addElement(DEFAULT_COLLECTION);
        collections.put(DEFAULT_COLLECTION, new ArrayList<>());

        // Add collection button
        addCollectionButton.addActionListener(e -> {

            String name = JOptionPane.showInputDialog("Collection name:");

            if (name == null || name.trim().isEmpty()) return;

            if (!collections.containsKey(name)) {
                collectionModel.addElement(name);
                collections.put(name, new ArrayList<>());
            } else {
                JOptionPane.showMessageDialog(this, "Collection already exists.");
            }
        });

        // Rename collection button
        renameButton.addActionListener(e -> {

            String selected = collectionList.getSelectedValue();
            if (selected == null) return;

            if (selected.equals(DEFAULT_COLLECTION)) {
                JOptionPane.showMessageDialog(this, "Favorites cannot be renamed.");
                return;
            }

            String newName = JOptionPane.showInputDialog("New name:", selected);

            if (newName != null && !newName.trim().isEmpty()) {

                ArrayList<String> games = collections.remove(selected);
                collections.put(newName, games);

                int index = collectionList.getSelectedIndex();
                collectionModel.set(index, newName);

                lastSelectedCollection = newName;
            }
        });

        // Delete collection/game button
        deleteButton.addActionListener(e -> {

            String selectedCollection = collectionList.getSelectedValue();
            String selectedGame = gameList.getSelectedValue();

            if (selectedCollection == null) {
                JOptionPane.showMessageDialog(this, "Select a collection or game.");
                return;
            }

            // Delete game portion
            if (selectedGame != null) {

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete \"" + selectedGame + "\" from the collection \"" + selectedCollection + "\"?",
                        "Delete Game",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {

                    collections.get(selectedCollection).remove(selectedGame);
                    gameModel.removeElement(selectedGame);

                    gameList.clearSelection();
                    lastSelectedGame = null;
                }

                return;
            }

            // Delete collection portion
            if (selectedCollection.equals(DEFAULT_COLLECTION)) {
                JOptionPane.showMessageDialog(this, "Favorites cannot be deleted.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the collection \"" + selectedCollection + "\"?",
                    "Delete Collection",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                collections.remove(selectedCollection);
                collectionModel.removeElement(selectedCollection);

                gameModel.clear();
                collectionList.clearSelection();

                lastSelectedCollection = null;
                lastSelectedGame = null;
            }
        });

        // Select/Deselect Collection
        collectionList.addMouseListener(new MouseAdapter() {
            @Override
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

                    for (String g : collections.get(clicked)) {
                        gameModel.addElement(g);
                    }

                    lastSelectedGame = null;
                }
            }
        });

        // Select/Deselect Game
        gameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int index = gameList.locationToIndex(e.getPoint());
                if (index == -1) return;

                String clickedGame = gameModel.getElementAt(index);

                if (clickedGame.equals(lastSelectedGame)) {

                    gameList.clearSelection();
                    lastSelectedGame = null;

                } else {

                    gameList.setSelectedIndex(index);
                    lastSelectedGame = clickedGame;
                }
            }
        });
    }

    // Adding game to collection logic
    public void addGameToSelectedCollection(String game) {

        String selected = collectionList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a collection first.");
            return;
        }

        ArrayList<String> games = collections.get(selected);

        if (games.contains(game)) {
            JOptionPane.showMessageDialog(this, "Game already in collection.");
            return;
        }

        games.add(game);
        gameModel.addElement(game);
    }
}
