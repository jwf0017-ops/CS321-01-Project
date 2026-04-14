//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;

public class MainViewFrame extends JFrame {

    public MainViewFrame() {
        setTitle("Game App");
        setSize(1500, 900); // default sizes are 600, 400; 1500, 900 are the height and width of my laptop
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new Login_View()); // start with login

    }

    public static void main(String[] args) {
        new MainViewFrame().setVisible(true);
    }
}
