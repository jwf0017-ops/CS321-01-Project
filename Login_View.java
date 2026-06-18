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
        loginButton.addActionListener(e -> {
            String username = getUsername();
            String password = getPassword();

            // Optional validation logic here
            if (!username.isEmpty() && !password.isEmpty()) {
                frame.showView("HOME");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
            }
        });

        // Create account (placeholder)
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
