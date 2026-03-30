


// Game class


/**
*
* A Game pulled from an XML file with a specific ID linked to its information
* such as its Name, Description, and Reviews
* 
*/

public Game implements Comparable<Game> {


    /**
    *
    * Array List for ID's of a particular game read from the Review class
    *
    */

    ArrayList<Review> reviewIDs = new ArrayList<Review>();

    for (Review r: reviewIDs) 
        r.getReviews();


    @Override
    public compareTo(Game otherGame) {
        return otherGame.Game();
    }


/* // default constructor --> don't think we need a default constructor
    public Game() {
        ID = 0;
        gameName = null;
        gameDesc = null;
        gameRev = null;
    }

*/

// Constructor
    public Game(int i, /*String r*/, String d, String n) {
        ID = i;
        //gameRev = r; --> We don't read reviews from XML
        gameDesc = d;
        gameName = n;
    }


// Accessors
    public String getName() {
        return gameName;
    }

    public String getReviews() {
        return gameRev;
    }

    public String getDescription() {
        return gameDesc;
    }

    public void setName(String n) {
        gameName = n;
    }

    public void setDescription(String d) {
        gameDesc = d;
    }

    public void addReview(int r) {
        
    }



  // Variable declared

  private String gameName; // The game name tied to the game
  private String gameDesc; // The game description tied to the game
  private String gameRev; // All reviews of the game 
  private int ID; // ID of the game
  
  // Declared variables end

}
