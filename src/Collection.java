import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

// I'm considering adding an "active" flag to collection that's just an indicator of whether the collection's frame is open
// If a panel showing a particular collection is open, then we would set active for that collection to high, and we would load in the arrayList of games to make some operations
// Like sort and filter easier


public class Collection
{

    /**
     * Constructor for a collection object, throws in provided information, as well as parsing the game file for the games
     * @param inID The ID representing the collection
     * @param inName The name chosen by the user for the collection
     * @param inList The list of integers representing the games of the collection
     */
    public Collection(int inID, String inName, ArrayList<Integer> inList, String filePath) {
        ID = inID;
        name = inName;
        gameIDList = inList;
        try {
            gameParser dom = new gameParser(filePath);
            makeList(inList, dom);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method takes a parser and an integer list to create the actual ArrayList of games
     * @param inList List of integers representing games
     * @param inParse Parser of the game file
     */
    public void makeList(ArrayList<Integer> inList, gameParser inParse) {
        gameList = inParse.retrieveGameList();
        gameList.removeIf(game -> (!inList.contains(game.getID())));
    }

    /**
     * Adds a game to both the integer list and the game list
     * @param inGame The game object to be added
     * @return Whether the game was already in the list or not
     */
    public boolean addGame(Game inGame)
    {
        boolean exists = gameExists(inGame.getID());
        if (!exists)
        {
            gameIDList.add(inGame.getID());
            gameList.add(inGame);
        }
        return exists;
    }

    /**
     * Removes a game from both the integer list and the ArrayList of games
     * @param inGame The game to be deleted
     */
    public void deleteGame(Game inGame)
    {
        gameIDList.remove((Integer) inGame.getID());
        gameList.remove(inGame);
    }

    /**
     * 0 = sort by ID (I assume this won't be used)
     * 1 = sort by name
     * 2 = sort by year published
     * 3 = sort by minimum age
     * 4 = sort by minimum players
     * 5 = sort by maximum players
     * @param sortPar This can take several values based on what we're sorting by
     */
    public ArrayList<Game> sort(int sortPar)
    {
        ArrayList<Game> fakeList = new ArrayList<Game>();
        fakeList.addAll(gameList);
        switch (sortPar) {
            case 0: {
                compID comp = new compID();
                Collections.sort(fakeList, comp);
            }

            case 1: {
                Collections.sort(fakeList);
            }

            case 2: {
                compYear comp = new compYear();
                Collections.sort(fakeList, comp);
            }

            case 3: {
                compMinAge comp = new compMinAge();
                Collections.sort(fakeList, comp);
            }

            case 4: {
                compMinPlayers comp = new compMinPlayers();
                Collections.sort(fakeList, comp);
            }

            case 5: {
                compMaxPlayers comp = new compMaxPlayers();
                Collections.sort(fakeList, comp);
            }

        }
        return fakeList;
    }


    private boolean gameExists(int inID)
    {
        boolean exists = false;
        for (Game game : gameList)
        {
            if (game.getID() == inID)
            {
                exists = true;
                break;
            }
        }

        return exists;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public void setName(String inName)
    {
        name = inName;
    }

    public ArrayList<Integer> getIDList() {

        return (ArrayList<Integer>) gameIDList.clone();
    }

    public ArrayList<Game> getGameList() { return gameList; }

    private ArrayList<Integer> gameIDList;
    private ArrayList<Game> gameList;
    private String name;
    private int ID;
}
