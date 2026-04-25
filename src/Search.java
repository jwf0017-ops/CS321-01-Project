import java.io.IOException;
import java.util.ArrayList;


public class Search {

    public Search()
    {
        try {
            gameParser dom = new gameParser("src/bgg90Games.xml"); // This is where the file is located on my system
            gamesList = dom.retrieveGameList();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<Game> minPlayerFilter(int players) {

        ArrayList<Game> minPlayersList = new ArrayList<Game>();
        for (Game kindaGame : gamesList) {
            if (players == kindaGame.getMinPlayers()) {
                minPlayersList.add(kindaGame);
            }
        }

        return minPlayersList;

    }


    public ArrayList<Game> maxPlayerFilter(int players) {

        ArrayList<Game> maxPlayersList = new ArrayList<Game>();

        for (Game kindaGame : gamesList) {
            if (players == kindaGame.getMaxPlayers()) {
                maxPlayersList.add(kindaGame);
            }
        }

        return maxPlayersList;

    }

    public ArrayList<Game> nameFilter(String gameName) {

        ArrayList<Game> nameList = new ArrayList<Game>();

        for (Game kindaGame : gamesList) {
            if (kindaGame.getName() != null && kindaGame.getName().contains(gameName)) {
                nameList.add(kindaGame);
            }
        }

        return nameList;

    }

    // There will probably be more of these but it'll follow this general format I think

    ArrayList<Game> gamesList;
}

//
////can't think of any other number based filter so going to move onto genre but can use that same formatt for any search
////also could change for range
//public class Genres(genres){
//    //I think we will probly get error matchinf input with xml file string so maybe have give each genre a number
//    //going to use the same formatt as players
//    //assuming the number of diffrent genres are unknown
//    //this should for any formatt we get the genres onky minor changes will need to be made
//    int i = 0;
//    int x = 0;
//    int[] gamesarray = new int [100];// here set the the import recived from the a function in the games files = to game
//
//    //print checks
//    System.out.println("Game array sucessfully working");
//    System.out.println(Arrays.toString(gamearray));
//    //print check fo the array
//
//    //using 100 as a place holder
//    length = games.length
//            System.out.print(length);
//    int[] gamesMatchgenre;
//    int[] genrearray = new int[100];
//    //the 100 is a place holder
//    while (i < length)
//
//    {
//        genrearray[i] = gamesarray[i];//gamearray will go into a function to get the generes
//        i += 1;
//        if (genre == genre){
//            x+= 1;
//
//
//        }
//    }
//
//    gameMatchgenre = new int [x];
//    i = 0;//reseting so we can reuse
//    x = 0;
//    while (x <  gameMatchgenre.length){
//        if (genrearray[i] == genre){
//            gamesMatchgenre[x] = gamesarray[i];
//            x += 1;
//
//
//        }
//        //if else
//        //incerment game & genre
//        i += 1;
//
//
//    }
//
//}
//}