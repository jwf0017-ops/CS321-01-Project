
import javax.swing.*;
import java.awt.*;

    public class Home_View extends JPanel {

        private JButton searchButton;
        private JButton logoutButton;
        private JList<String> gameList;

        public Home_View() {
            setLayout(new BorderLayout());

            JPanel topPanel = new JPanel();
            searchButton = new JButton("Search");
            logoutButton = new JButton("Logout");

            topPanel.add(searchButton);
            topPanel.add(logoutButton);

            add(topPanel, BorderLayout.NORTH);

            gameList = new JList<>();
            add(new JScrollPane(gameList), BorderLayout.CENTER);
        }

        public void setGameList(String[] games) {
            gameList.setListData(games);
        }

        public JButton getSearchButton() {
            return searchButton;
        }

        public JButton getLogoutButton() {
            return logoutButton;
        }

        public JList<String> getGameList() {
            return gameList;
        }
    }

