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
    }

    public boolean addGame(int inID)
    {
        boolean exists = gameExists(inID);
        if (!exists)
        {
            gameIDList.add(inID);
        }
        return exists;
    }

    public void deleteGame(int inID)
    {
//        gameList.remove(inID);
        // If we do the active high deal, we'll need to check for that and refresh panel if open
    }

    public void sort(String sortPar)
    {
//        // This is going to be a lot more complicated when we build up the Game class and its various sort classes
//        Collections.sort(gameList);
    }

    public void filter()
    {
//        // I'm not sure how we'd pass the filter options into this method
//        // Maybe we have a "filter" class that is just all the mutators/accessors for the filter options
//        // And then use that to work on filtering our list
//        // I guess we'd have user buttons affect filter class which affects this filter method
//
//        // If we do the active to high thing, we can just use the already available gameList
//        // And don't have to build a new one here before we start
//        ArrayList<Game> filteredList;
//
//        for (Game game : gameList)
//        {
//
//        }
    }

    private boolean gameExists(int inID)
    {
//        boolean exists = false;
//        for (Game game : gameList)
//        {
//            if (game.getGameID() == inID)
//            {
//                exists = true;
//                break;
//            }
//        }
//
//        return exists;
        return true;
    }

    ArrayList<Integer> gameIDList;
 //   ArrayList<Game> gameList;
    String name;
    int ID;
}
