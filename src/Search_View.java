import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Search_View extends JPanel {

    private MainViewFrame frame;

    private JTextField searchField;
    private JButton searchButton;
    private JButton backButton;

    private JComboBox<String> filterBox;
    private JList<String> resultsList;

    private Search searchLogic;

    // Store actual objects, not just strings
    private ArrayList<Game> allGames;
    private ArrayList<Game> currentResults;

    public Search_View(MainViewFrame frame) {

        this.frame = frame;
        setLayout(new BorderLayout());

        // Get shared game data
        this.allGames = frame.getAllGames();
        this.currentResults = new ArrayList<>();

        searchLogic = new Search();

        // Top of Panel
        JPanel topPanel = new JPanel();

        searchField = new JTextField(15);

        filterBox = new JComboBox<>(new String[]{
                "Name", "Players", "Age", "Year"
        });

        searchButton = new JButton("Search");
        backButton = new JButton("Back");

        topPanel.add(searchField);
        topPanel.add(filterBox);
        topPanel.add(searchButton);
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);

        // Results of Game List
        resultsList = new JList<>();
        add(new JScrollPane(resultsList), BorderLayout.CENTER);

        // Search Button
        searchButton.addActionListener(e -> {

            String query = searchField.getText().trim();
            String filter = (String) filterBox.getSelectedItem();

            try {
                switch (filter) {

                    case "Name":
                        currentResults = searchLogic.filterByName(allGames, query);
                        break;

                    case "Players":
                        currentResults = searchLogic.filterByPlayers(allGames, Integer.parseInt(query));
                        break;

                    case "Age":
                        currentResults = searchLogic.filterByAge(allGames, Integer.parseInt(query));
                        break;

                    case "Year":
                        currentResults = searchLogic.filterByYear(allGames, Integer.parseInt(query));
                        break;
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
                return;
            }

            // Convert Games -> names for UI
            String[] display = new String[currentResults.size()];

            for (int i = 0; i < currentResults.size(); i++) {
                display[i] = currentResults.get(i).getName();
            }

            resultsList.setListData(display);
        });

        // Back to Home Page
        backButton.addActionListener(e -> frame.showView("HOME"));

        // Click Game -> Then view it
        resultsList.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int index = resultsList.getSelectedIndex();

                if (index != -1 && index < currentResults.size()) {

                    Game selectedGame = currentResults.get(index);

                    frame.getGameView().setGameInfo(
                            selectedGame.getName(),
                            selectedGame.getDescription(),
                            selectedGame.getID()
                    );

                    if (selectedGame.getReviewIDs().isEmpty()) {
                        ArrayList<String> newList = new ArrayList<String>();
                        newList.add("No reviews yet");
                        frame.getGameView().setReviews(newList);
                    } else {
                        reviewParser rParse;
                        ArrayList<Review> rList;
                        try {
                            rParse = new reviewParser("src/reviewDatabase.xml");
                            rList = rParse.retrievereviewsList();
                        } catch (IOException error) {
                            throw (new RuntimeException(error));
                        }
                        ArrayList<String> revArray = new ArrayList<String>();
                        for (Review review : rList) {
                            if (review.getGameID() == selectedGame.getID()) {
                                revArray.add(review.getRating() + " Stars: " + review.getDesc());
                            }
                        }

                        frame.getGameView().setReviews(revArray);

                        frame.showView("GAME");
                    }
                }
            }

        });
    }
}