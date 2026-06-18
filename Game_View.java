import javax.swing.*;
import java.awt.*;



public class Game_View extends JPanel {

    private MainViewFrame frame;

    private JLabel nameLabel;
    private JTextArea descriptionArea;

    private JList<String> reviewList;
    private DefaultListModel<String> reviewModel;

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

        // Back Button
        backButton = new JButton("Back");
        add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(e -> frame.showView("HOME"));
    }

    // Set Game info
    public void setGameInfo(String name, String description) {
        nameLabel.setText(name);
        descriptionArea.setText(description);
        descriptionArea.setCaretPosition(0); // scroll to top
    }

    // Set Reviews
    public void setReviews(String[] reviews) {

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
