import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Login_View extends JPanel {

    private MainViewFrame frame;

    private JTextField usernameField;
    private Image backgroundImage;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private ArrayList<User> userList;
    private userParser parser;
    private ArrayList<Collection> cList;

    public Login_View(MainViewFrame frame) {
        collectionParser cParser;
        try {
            parser = new userParser("src/userDatabase.xml");
            userList = parser.retrieveUserList();

            cParser = new collectionParser("src/collectionsDatabase.xml");
            cList = cParser.retrievecollectionList();
        } catch (Exception e) {
            userList = new ArrayList<>();
            cList = new ArrayList<Collection>();
            System.out.println("Failed to load users: " + e.getMessage());
            cParser = null;
        }

        this.frame = frame;
        backgroundImage = new ImageIcon("src/background.jpg").getImage();
        //userList = frame.getAllUsers();

        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);
        JPanel loginBox = new JPanel();
        loginBox.setOpaque(false);
        loginBox.setLayout(new GridBagLayout());
        loginBox.setPreferredSize(new Dimension(300, 200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginBox.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        loginBox.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginBox.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        loginBox.add(passwordField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 30));
        loginBox.add(loginButton, gbc);

        gbc.gridy = 3;
        createAccountButton = new JButton("Create Account");
        createAccountButton.setPreferredSize(new Dimension(150, 30));
        loginBox.add(createAccountButton, gbc);

        centerPanel.add(loginBox);
        add(centerPanel, BorderLayout.CENTER);

        // Login action -> go to home page
        loginButton.addActionListener(event -> {
            String username = getUsername();
            String password = getPassword();
            boolean failed = true;

            // Optional validation logic but ensures the user to input both username and password
            if (!username.isEmpty() && !password.isEmpty()) {
                for (User user : userList)
                {
                    if (user.GetName().equals(username) && user.getPass().equals(password))
                    {
                        frame.showView("HOME");
                        frame.setCurrentUser(user);
                        frame.getHomeView().getCollectionView().populateCollections(user, cList);
                        failed = false;
                        break;
                    }
                }
                if (failed)
                {
                    JOptionPane.showMessageDialog(this, "Invalid username/password combination. Please try again.");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
            }
        });

        // Create account
        collectionParser finalCParser = cParser;
        createAccountButton.addActionListener(event -> {
            String username = getUsername();
            String password = getPassword();
            if (username != null && password != null)
            {
                for (User user : userList)
                {
                    if (username.equals(user.GetName()))
                    {
                        JOptionPane.showMessageDialog(this, "User with this username already exists.");
                    }
                }

                int newID = cList.size();

                ArrayList<Integer> newGames = new ArrayList<Integer>();
                Collection newFavorites = new Collection(newID, "Favorites", newGames);
                cList.add(newFavorites);

                ArrayList<Integer> newCollections = new ArrayList<Integer>();
                newCollections.add(newID);
                ArrayList<Integer> newReviews = new ArrayList<Integer>();
                User newbie = new User(username, password, userList.size(), newCollections, newReviews);
                userList.add(newbie);



                try {
                    parser.saveUsersList(userList, "src/userDatabase.xml");
                    finalCParser.saveCollectionsList(cList, "src/collectionsDatabase.xml");
                } catch (Exception e) {
                    System.out.println("Failed to save games: " + e.getMessage());
                }

                JOptionPane.showMessageDialog(this, "Please log in with your new account");
                MainView.main();
                frame.dispose();
            }

        });
    }

    // Getters
    public String getUsername() {

        return usernameField.getText();
    }

    public String getPassword() {

        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {

        return loginButton;
    }

    public JButton getCreateAccountButton() {
        return createAccountButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }


}
