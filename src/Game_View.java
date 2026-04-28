import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Game_View extends JPanel {

    private MainViewFrame frame;

    private JLabel nameLabel;
    private JTextArea descriptionArea;

    private JList<String> reviewList;
    private DefaultListModel<String> reviewModel;

    private int gameID;

    private JButton backButton;

    public Game_View(MainViewFrame frame) {

        this.frame = frame;
        setLayout(new BorderLayout());

        // Title of Game
        nameLabel = new JLabel("Game Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(nameLabel, BorderLayout.NORTH);

        // Description of Game
        descriptionArea = new JTextArea();
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);

        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        // Review of Game
        reviewModel = new DefaultListModel<>();
        reviewList = new JList<>(reviewModel);

        JPanel reviewPanel = new JPanel(new BorderLayout());
        reviewPanel.add(new JLabel("Reviews:"), BorderLayout.NORTH);
        reviewPanel.add(new JScrollPane(reviewList), BorderLayout.CENTER);

        reviewPanel.setPreferredSize(new Dimension(300, 0));
        add(reviewPanel, BorderLayout.EAST);

        JButton reviewButton = new JButton("Leave Review: ");
        reviewPanel.add(reviewButton, BorderLayout.SOUTH);

        // Back Button
        backButton = new JButton("Back");
        add(backButton, BorderLayout.SOUTH);

        // Below is the process of making a review. Obviously this shouldn't be in Game_View but it's the night before this is due so I don't much care at the moment. If I had given myself more time to write this, I would refactor a lot of things in this program
        reviewButton.addActionListener(event -> {
            // The below 8 or so lines taken from someone on stackExchange who obviously knows more about GUIs than me (Jake)
            JPanel fields = new JPanel(new GridLayout(2, 1));
            JTextField field = new JTextField(10);
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});

            fields.add(field);
            fields.add(comboBox);

            int result = JOptionPane.showConfirmDialog(null, fields, "Rating", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            String reviewText = field.getText().trim();
            int rating = Integer.parseInt(comboBox.getSelectedItem().toString());

            reviewParser rParser;
            userParser uParser;
            gameParser gParser;
            ArrayList<Review> rList;
            ArrayList<Game> gList;
            ArrayList<User> uList;
            try {
                rParser = new reviewParser("src/reviewDatabase.xml");
                uParser = new userParser("src/userDatabase.xml");
                gParser = new gameParser(MainViewFrame.filePath);
                rList = rParser.retrievereviewsList();
                uList = uParser.retrieveUserList();
                gList = gParser.retrieveGameList();

            } catch (IOException e) {
                System.out.println("Failed to open file");
                rList = new ArrayList<Review>();
                uList = new ArrayList<User>();
                gList = new ArrayList<Game>();
                rParser = null;
                uParser = null;
                gParser = null;
            }

            boolean exists = false;
            int reviewID = -1;
            for (Review review : rList)
            {
                if (review.getUserID() == frame.getCurrentUser().GetID() && review.getGameID() == gameID)
                {
                    exists = true;
                    review.setDesc(reviewText);
                    review.setRating(rating);
                    reviewID = review.getID();
                }
            }
            if (!exists)
            {
                Review newReview = new Review(frame.getCurrentUser().GetID(), gameID, rating, reviewText, rParser);
                rList.add(newReview);
                reviewID = newReview.getID();
            }

            for (User user : uList)
            {
                if (user.GetID() == frame.getCurrentUser().GetID())
                {
                    user.addReview(reviewID);
                }
            }

            for (Game game : gList)
            {
                if (game.getID() == gameID)
                {
                    game.addReview(reviewID);
                }
            }


            try {
                uParser.saveUsersList(uList, "src/userDatabase.xml");
                gParser.saveGamesList(gList, MainViewFrame.filePath);
                rParser.savereviewsList(rList, "src/reviewDatabase.xml");

            } catch (FileNotFoundException | ParserConfigurationException | TransformerException e)
            {
                throw new RuntimeException(e);
            }

        });
        backButton.addActionListener(e -> frame.showView("HOME"));
    }

    // Set Game info
    public void setGameInfo(String name, String description, int inID) {
        nameLabel.setText(name);
        descriptionArea.setText(description);
        gameID = inID;
        if (description.isEmpty())
        {
            descriptionArea.setText("No description found.");
        }
        descriptionArea.setCaretPosition(0); // scroll to top
    }

    // Set Reviews
    public void setReviews(ArrayList<String> reviews) {

        reviewModel.clear();

        if (reviews != null) {
            for (String r : reviews) {
                reviewModel.addElement(r);
            }
        } else {
            reviewModel.addElement("No reviews available");
        }
    }


}
