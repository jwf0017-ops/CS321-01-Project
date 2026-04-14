import javax.swing.*;
import java.awt.*;

public class Game_View extends JPanel {

    private JLabel nameLabel;
    private JTextArea descriptionArea;
    private JList<String> reviewList;

    public Game_View() {
        setLayout(new BorderLayout());

        nameLabel = new JLabel("Game Name");
        add(nameLabel, BorderLayout.NORTH);

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false); // non editable game description
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        reviewList = new JList<>();
        add(new JScrollPane(reviewList), BorderLayout.SOUTH);
    }

    public void setGameInfo(String name, String description) {
        nameLabel.setText(name);
        descriptionArea.setText(description);
    }

    public void setReviews(String[] reviews) {
        reviewList.setListData(reviews); // pull game reviews
    }
}
