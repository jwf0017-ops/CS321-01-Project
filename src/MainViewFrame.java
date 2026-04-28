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

    private User currentUser;

    private userParser uParser;
    private gameParser gParser;
    private collectionParser cParser;
    private reviewParser rParser;

    private ArrayList<Game> allGames;
    private ArrayList<Collection> allCollections;
    private ArrayList<User> allUsers;
    private ArrayList<Review> allReviews;

    public MainViewFrame() {

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Load Data
        try {
            gParser = new gameParser("src/bgg90Games.xml");
            allGames = gParser.retrieveGameList();
        } catch (Exception e) {
            allGames = new ArrayList<>();
            System.out.println("Failed to load games: " + e.getMessage());
        }

        try {
            cParser = new collectionParser("src/collectionsDatabase");
            allCollections = cParser.retrievecollectionList();
        }
        catch (Exception e) {
            allCollections = new ArrayList<Collection>();
        }

        try {
            uParser = new userParser("src/userDatabase");
            allUsers = uParser.retrieveUserList();
        }
        catch (Exception e) {
            allUsers = new ArrayList<User>();
        }

        try {
            rParser = new reviewParser("src/reviewDatabase");
            allReviews = rParser.retrievereviewsList();
        }
        catch (Exception e) {
            allReviews = new ArrayList<Review>();
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
        setSize(1500, 890);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Push games to Home View
        homeView.setGames(allGames);

        showView("LOGIN");
    }

    public ArrayList<Game> getAllGames() {
        return allGames;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public ArrayList<Collection> getAllCollections() {
        return allCollections;
    }

    public ArrayList<Review> getAllReviews() {
        return allReviews;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User newUser)
    {
        currentUser = newUser;
    }

    public void showView(String name) {
        cardLayout.show(mainPanel, name);
    }

    public Home_View getHomeView() { return homeView; }
    public Search_View getSearchView() { return searchView; }
    public Game_View getGameView() { return gameView; }
    public Login_View getLoginView() { return loginView; }
}
