
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Home_View extends JPanel {

    private MainViewFrame frame;

    private JButton searchButton;
    private JButton logoutButton;
    private JButton addToCollectionButton;

    private JList<String> gameList;
    private DefaultListModel<String> listModel;

    private ArrayList<Game> games;

    private Collection_View collectionView;

    public Home_View(MainViewFrame frame, Collection_View collectionView) {
        this.frame = frame;
        this.collectionView = collectionView;

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        searchButton = new JButton("Search");
        logoutButton = new JButton("Logout");
        addToCollectionButton = new JButton("Add to Collection");

        topPanel.add(searchButton);
        topPanel.add(addToCollectionButton);
        topPanel.add(logoutButton);

        add(topPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        gameList = new JList<>(listModel);

        add(new JScrollPane(gameList), BorderLayout.CENTER);

        add(collectionView, BorderLayout.EAST);

        logoutButton.addActionListener(e -> frame.showView("LOGIN"));
        searchButton.addActionListener(e -> frame.showView("SEARCH"));

        addToCollectionButton.addActionListener(e -> {

            int index = gameList.getSelectedIndex();

            if (index != -1 && games != null) {
                Game selectedGame = games.get(index);
                collectionView.addGameToSelectedCollection(selectedGame);
            } else {
                JOptionPane.showMessageDialog(this, "Select a game first.");
            }
        });

        gameList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int index = gameList.getSelectedIndex();

                    if (index != -1 && games != null) {

                        Game selectedGame = games.get(index);

                        frame.getGameView().setGameInfo(
                                selectedGame.getName(),
                                selectedGame.getDescription()
                        );

                        frame.getGameView().setReviews(new String[] {
                                "No reviews yet"
                        });

                        frame.showView("GAME");
                    }
                }
            }
        });
    }

    public void setGames(ArrayList<Game> games) {

        this.games = games;

        listModel.clear();

        if (games != null) {
            for (Game g : games) {
                listModel.addElement(g.getName());
            }
        }
    }
}
