import javax.swing.*;
import java.awt.*;

public class Login_View extends JPanel {

    // declare private variables section
    private JTextField usernameField; // text box for user input
    private JPasswordField passwordField; // text box for user password
    private JButton loginButton; // 
    private JButton createAccountButton;
    // end private variables section

    

    // Constructor
    public Login_View() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");

        add(loginButton);
        add(createAccountButton);
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
