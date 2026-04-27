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

    public Login_View(MainViewFrame frame) {
        try {
            userParser parser = new userParser("src/userDatabase.xml");
            userList = parser.retrieveUserList();
        } catch (Exception e) {
            userList = new ArrayList<>();
            System.out.println("Failed to load games: " + e.getMessage());
        }

        this.frame = frame;

        setLayout(new GridLayout(4, 2, 10, 10));
        // Username

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Buttons
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");

        add(loginButton);
        add(createAccountButton);

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

        // Create account(shows only this message right now!) -> **This needs to be implemented**
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
