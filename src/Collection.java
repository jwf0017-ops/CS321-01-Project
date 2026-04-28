import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

// I'm considering adding an "active" flag to collection that's just an indicator of whether the collection's frame is open
// If a panel showing a particular collection is open, then we would set active for that collection to high, and we would load in the arrayList of games to make some operations
// Like sort and filter easier


public class Collection
{

    public Collection(int inID, String inName, ArrayList<Integer> inList)
    {
        ID = inID;
        name = inName;
        gameIDList = inList;
        try {
            gameParser dom = new gameParser("src/bgg90Games.xml");
            makeList(inList, dom);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeList(ArrayList<Integer> inList, gameParser inParse) {
        gameList = inParse.retrieveGameList();
        gameList.removeIf(game -> (!inList.contains(game.getID())));
    }

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

    public void deleteGame(Game inGame)
    {
        gameIDList.remove(inGame.getID());
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

//    public ArrayList<Game> filter(int filterParam, )
//    {
//
//    }

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
