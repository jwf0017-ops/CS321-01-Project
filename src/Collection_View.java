

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

public class Collection_View extends JPanel {

    private MainViewFrame frame;

    private DefaultListModel<String> collectionModel;
    private JList<String> collectionList;

    private DefaultListModel<String> gameModel;
    private JList<String> gameList;
    Collection currentCollection;
    ArrayList<Collection> collectionArrayList;
    collectionParser theParser;
    gameParser freeMeFromThisGameparser;

    private JButton addCollectionButton;
    private JButton renameButton;
    private JButton deleteButton;

    private HashMap<String, ArrayList<String>> collections;

    private final String DEFAULT_COLLECTION = "Favorites";

    // Track selections
    private String lastSelectedCollection = null;
    private String lastSelectedGame = null;

    public Collection_View(MainViewFrame frame) {


        try {
            theParser = new collectionParser("src/collectionsDatabase.xml");
            freeMeFromThisGameparser = new gameParser("src/gameDatabase.xml");
            collectionArrayList = theParser.retrievecollectionList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




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
        addCollectionButton.addActionListener(event -> {

            String name = JOptionPane.showInputDialog("Collection name:");

            if (name == null || name.trim().isEmpty()) return;

            if (!collections.containsKey(name)) {
                collectionModel.addElement(name);
                collections.put(name, new ArrayList<>());

                ArrayList<Integer> newList = new ArrayList<Integer>();
                Collection newCollection = new Collection(collections.size() + 1, name, newList);
                collectionArrayList.add(newCollection);

                try {
                    theParser.saveCollectionsList(collectionArrayList, "src/collectionsDatabase.xml");
                    User cUser = frame.getCurrentUser();
                    userParser uParser = new userParser("src/userDatabase.xml");
                    ArrayList<User> uList = uParser.retrieveUserList();

                    for (User user : uList)
                    {
                        if (user.GetName().equals(cUser.GetName()))
                        {
                            user.AddCollection(newCollection.getID());
                        }
                    }
                    uParser.saveUsersList(uList, "src/userDatabase.xml");
                }
                catch (IOException | ParserConfigurationException | TransformerException e) {
                    throw new RuntimeException(e);
                }

            }
            else {
                JOptionPane.showMessageDialog(this, "Collection already exists.");
            }
        });

        // Rename collection button
        renameButton.addActionListener(event -> {

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

                currentCollection.setName(newName);
                try {
                    theParser.saveCollectionsList(collectionArrayList, "src/collectionsDatabase");
                }
                catch (IOException | ParserConfigurationException | TransformerException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Delete collection/game button
        deleteButton.addActionListener(event -> {

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

                    ArrayList<Game> fullGameList = freeMeFromThisGameparser.retrieveGameList();
                    for (Game g : fullGameList)
                    {
                        if (g.getName().equals(selectedGame))
                        {
                            currentCollection.deleteGame(g);
                            break;
                        }
                    }
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

                collectionArrayList.remove(currentCollection);
                try {
                    theParser.saveCollectionsList(collectionArrayList, "src/collectionsDatabase");
                    frame.getCurrentUser().DeleteCollection(currentCollection.getID());
                }
                catch (IOException | ParserConfigurationException | TransformerException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Select/Deselect Collection
        collectionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {

                int index = collectionList.locationToIndex(event.getPoint());
                if (index == -1) return;

                String clicked = collectionModel.getElementAt(index);

                if (clicked.equals(lastSelectedCollection)) {

                    collectionList.clearSelection();
                    gameModel.clear();

                    lastSelectedCollection = null;
                    lastSelectedGame = null;
                    currentCollection = null;
                }
                else {

                    collectionList.setSelectedIndex(index);
                    lastSelectedCollection = clicked;

                    gameModel.clear();

                    for (String g : collections.get(clicked)) {
                        gameModel.addElement(g);
                    }

                    lastSelectedGame = null;
                }



                for (Collection collection : collectionArrayList)
                {
                    if (collection.getName().equals(clicked))
                    {
                        currentCollection = collection;
                        break;
                    }
                }
            }
        });

        // Select/Deselect Game
        gameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getClickCount() >= 2) {

                    ArrayList<Game> games = currentCollection.getGameList();

                    int index = gameList.getSelectedIndex();

                    if (index != -1 && games != null) {

                        Game selectedGame = games.get(index);

                        frame.getGameView().setGameInfo(
                                selectedGame.getName(),
                                selectedGame.getDescription(),
                                selectedGame.getID()
                        );

                        if (selectedGame.getReviewIDs().isEmpty())
                        {
                            ArrayList<String> newList = new ArrayList<String>();
                            newList.add("No reviews yet");
                            frame.getGameView().setReviews(newList);
                        }
                        else
                        {
                            reviewParser rParse;
                            ArrayList<Review> rList;
                            try {
                                rParse = new reviewParser("src/reviewDatabase.xml");
                                rList = rParse.retrievereviewsList();
                            } catch (IOException error) {
                                throw (new RuntimeException(error));
                            }
                            ArrayList<String> revArray = new ArrayList<String>();
                            for (Review review : rList)
                            {
                                if (review.getGameID() == selectedGame.getID())
                                {
                                    revArray.add(review.getRating() + " Stars: " + review.getDesc());
                                }
                            }

                            frame.getGameView().setReviews(revArray);
                        }

                        frame.showView("GAME");
                    }
                }

                else {
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
            }
        });
    }

    // Adding game to collection logic
    public void addGameToSelectedCollection(Game game) {

        String selected = collectionList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a collection first.");
            return;
        }

        ArrayList<String> games = collections.get(selected);

        if (games.contains(game.getName())) {
            JOptionPane.showMessageDialog(this, "Game already in collection.");
            return;
        }

        games.add(game.getName());
        gameModel.addElement(game.getName());
        currentCollection.addGame(game);
        try {
            theParser.saveCollectionsList(collectionArrayList, "src/collectionsDatabase.xml");
        }
        catch (IOException | ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Collection> populateCollections(User inUser, ArrayList<Collection> collectionList)
    {
        ArrayList<Collection> newList = new ArrayList<Collection>();
        for (Collection collection : collectionList)
        {
            System.out.println("Collection ID = " + collection.getID() + " and User ID = " + inUser.GetID());
            if (inUser.getCollactionsList().contains(collection.getID()))
            {
                newList.add(collection);
                collectionModel.addElement(collection.getName());
                collections.put(collection.getName(), new ArrayList<>());

                ArrayList<String> games = collections.get(collection.getName());

                for (Game game : collection.getGameList())
                {
                    games.add(game.getName());
                    gameModel.addElement(game.getName());
                    this.collectionList.clearSelection();
                    gameModel.clear();

                    lastSelectedCollection = null;
                    lastSelectedGame = null;
                    currentCollection = null;
                }
            }
        }
        return newList;

    }


}
