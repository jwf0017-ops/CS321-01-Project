import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainViewFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private Login_View loginView;
    private Home_View homeView;
    private Search_View searchView;
    private Game_View gameView;

    private ArrayList<Game> allGames;

    public MainViewFrame() {

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Load Data
        try {
            gameParser parser = new gameParser("testxml.xml");
            allGames = parser.retrieveGameList();
        } catch (Exception e) {
            allGames = new ArrayList<>();
            System.out.println("Failed to load games: " + e.getMessage());
        }

        // Create Views
        loginView = new Login_View(this);
        homeView = new Home_View(this);
        searchView = new Search_View(this);
        gameView = new Game_View(this);

        // Add Card Layouts
        mainPanel.add(loginView, "LOGIN");
        mainPanel.add(homeView, "HOME");
        mainPanel.add(searchView, "SEARCH");
        mainPanel.add(gameView, "GAME");

        add(mainPanel);

        setTitle("Game App");
        setSize(1500, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Should push games to Home and Search Views
        homeView.setGames(allGames);
        searchView.setGames(allGames); // if you add this method

        showView("LOGIN");
    }

    public ArrayList<Game> getAllGames() {
        return allGames;
    }

    public void showView(String name) {
        cardLayout.show(mainPanel, name);
    }

    public Home_View getHomeView() { return homeView; }
    public Search_View getSearchView() { return searchView; }
    public Game_View getGameView() { return gameView; }
    public Login_View getLoginView() { return loginView; }
}
