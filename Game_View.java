/*
import javax.swing.*;
import java.awt.*;

public class Game_View extends JPanel {

    private MainViewFrame frame;
    private JLabel nameLabel;
    private JTextArea descriptionArea;
    private JList<String> reviewList;
    private JButton backButton;

    public Game_View(MainViewFrame frame) {
        this.frame = frame;

        setLayout(new BorderLayout());

        nameLabel = new JLabel("Game Name");
        add(nameLabel, BorderLayout.NORTH);

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        reviewList = new JList<>();
        add(new JScrollPane(reviewList), BorderLayout.SOUTH);


        // Go back to the Home Page
        backButton = new JButton("Back");
        add(backButton, BorderLayout.WEST);

        backButton.addActionListener(e -> frame.showView("HOME"));
    }

    // Shows name of the game and its description
    public void setGameInfo(String name, String description) {
        nameLabel.setText(name);
        descriptionArea.setText(description);
    }

    public void setReviews(String[] reviews) {
        reviewList.setListData(reviews);
    }
}
*/