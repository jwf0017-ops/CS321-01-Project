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

        JButton reviewButton = new JButton("Leave Review: ");
        reviewPanel.add(reviewButton, BorderLayout.SOUTH);

        // Back Button
        backButton = new JButton("Back");
        add(backButton, BorderLayout.SOUTH);

        reviewButton.addActionListener(event -> {
            // The below 8 or so lines taken from someone on stackExchange who obviously knows more about GUIs than me (Jake)
            JPanel fields = new JPanel(new GridLayout(2, 1));
            JTextField field = new JTextField(10);
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});

            fields.add(field);
            fields.add(comboBox);

            int result = JOptionPane.showConfirmDialog(null, fields, "Rating", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            System.out.println(comboBox.getSelectedItem());
            System.out.println(field.getText().trim());
        });
        backButton.addActionListener(e -> frame.showView("HOME"));
    }

    // Set Game info
    public void setGameInfo(String name, String description) {
        nameLabel.setText(name);
        descriptionArea.setText(description);
        if (description.isEmpty())
        {
            descriptionArea.setText("No description found.");
        }
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
