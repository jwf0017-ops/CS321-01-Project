
import java.util.ArrayList;

// Game class


/**
*
* A Game pulled from an XML file with a specific ID linked to its information
* such as its Name, Description, and Reviews
* 
*/
public class Game implements Comparable<Game> {

    @Override
    public int compareTo(Game otherGame) {
        return gameName.compareTo(otherGame.getName());
    }

// Constructor
    public Game(int inID, String inDesc, String inName, int inAge, int inMinPlayers, int inMaxPlayers, int inYear, ArrayList<Integer> reviews) {
        gameID = inID;
        gameDesc = inDesc;
        gameName = inName;
        minAge = inAge;
        minPlayers = inMinPlayers;
        maxPlayers = inMaxPlayers;
        yearPublished = inYear;
        reviewIDs = new ArrayList<Integer>();
        reviewIDs.addAll(reviews);
    }


// Accessors
    public String getName() {
        return gameName;
    }

    public String getDescription() {
        return gameDesc;
    }

    public int getID() {
        return gameID;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

//    public ArrayList<String> getCategories() {
//        return (ArrayList<String>) categories.clone();
//    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void addReview(int r) {
        reviewIDs.add(r);
    }

    /**
     * This method will be called by the constructor to bring the game's reviews into the arraylist
     */
    public void reviewSetUp() {
        // The intention is for this method to access the file where we do keep the list of review IDs
    }

    public ArrayList<Integer> getReviewIDs()
    {
        return reviewIDs;
    }



  // Variable declared

  private final String gameName; // The game name tied to the game
  private final String gameDesc; // The game description tied to the game
  private final int gameID; // ID of the game
  private ArrayList<Integer> reviewIDs;
  private final int minAge;
  private final int minPlayers;
  private final int maxPlayers;
  private final int yearPublished;
  //private ArrayList<String> categories = new ArrayList<String>();
    // private ArrayList<Review> reviewList; // IDK if we're gonna use this so I'm leaving it commented out for now.
  
  // Declared variables end

}
