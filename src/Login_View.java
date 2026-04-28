import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Login_View extends JPanel {

    private MainViewFrame frame;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private ArrayList<User> userList;
    userParser parser;

    public Login_View(MainViewFrame frame) {
        try {
            parser = new userParser("src/userDatabase.xml");
        } catch (Exception e) {
            userList = new ArrayList<>();
            System.out.println("Failed to load users: " + e.getMessage());
        }

        this.frame = frame;

        userList = frame.getAllUsers();

        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());

        JPanel loginBox = new JPanel();
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
        loginBox.add(loginButton, gbc);

        gbc.gridy = 3;
        createAccountButton = new JButton("Create Account");
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

                ArrayList<Integer> newCollections = new ArrayList<Integer>();
                ArrayList<Integer> newReviews = new ArrayList<Integer>();
                User newbie = new User(username, password, userList.size()+1, newCollections, newReviews);
                userList.add(newbie);

                try {
                    parser.saveUsersList(userList, "src/userDatabase.xml");
                } catch (Exception e) {
                    System.out.println("Failed to save games: " + e.getMessage());
                }

                JOptionPane.showMessageDialog(this, "Please log in with your new account");
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


}
