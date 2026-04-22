import javax.swing.*;
import java.awt.*;

public class Login_View extends JPanel {

    private MainViewFrame frame;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;

    public Login_View(MainViewFrame frame) {
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
        loginButton.addActionListener(e -> {
            String username = getUsername();
            String password = getPassword();

            // Optional validation logic but ensures the user to input both username and password
            if (!username.isEmpty() && !password.isEmpty()) {
                frame.showView("HOME");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
            }
        });

        // Create account(shows only this message right now!) -> **This needs to be implemented**
        createAccountButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Create Account not implemented yet.");
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
