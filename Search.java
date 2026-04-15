import java.util.ArrayList;

//using this class to hold the filters, genres that we will need in the software
//then also a fildGame using a string input and Collection ResultsList
//not alot of details so going to pick the filter i think we will need and Genre filters
//fild game is pretty stright fowrard and collectio n Results not alot of detail so soing to try
//and figure that out
public class Search{

    //first going to filter by number of players
public int players_filter(players){
    //somehow going to get a array list of either all the games with the goal amount of players or
    //assuming the longest option which is just getting all the games ID with the player count

    int[] gamesarray = new int [100];// here set the the import recived from the a function in the games files = to game

    //print checks
    System.out.println("Game array sucessfully working");
    System.out.println(Arrays.toString(gamearray));
    //print check fo the array

    //using 100 as a place holder
    length = games.length
            System.out.print(length);
    int[] gamesMatchSize;
    int i = 0;
    int x = 0;
    //then going to use each game id to get the player. again assuming longest way might be simipiler then this
    while(i < length) {
        //using the get player function to get the players for each game id
        int[] playerNumber = new int[100]
        //the 100 is a place holder
        playerNumber[i] = gamesarray[i]; //gamearray will go into a function to get the miniunm number of players
        if (playerNumber == players) {
            //print checks
            System.out.printf("GameID: %d PlayerNumber: %.2f", playerNumber[i], gamesarray[i]);
            x = x = +1;
            //this will give us the number of possible games so we can make our array


        }
        i += 1;
    }
    //print checks
    System.out.printf("i: %d x: %.2f", i, x);
        i = 0;//reseting i to use again
        gamesMatchSize = new int[x];//applying found array size
        x = 0;//reseting x o use again
        while (x < gamesMatchSize.length + 1){
            if (playerNumber[i] == players){//should put the game ids of the games that match the players size given by user input
            gamesMatchSize[x] = gamesarray[i];


            //print checks
            System.out.println(Arrays.toString(gamearray[i]));
            System.out.println(Arrays.toString(gamesMatchSize[x]));
            System.out.println(Arrays.toString(playerNumber[i]));
            //print checks



             x += 1;   //if true move on to the next game match size input and games array input


            }
            i += 1;//else only increment the game array and player number array






        }
    }


}
//can't think of any other number based filter so going to move onto genre but can use that same formatt for any search
//also could change for range
public class Genres(genres){
    //I think we will probly get error matchinf input with xml file string so maybe have give each genre a number
    //going to use the same formatt as players
    //assuming the number of diffrent genres are unknown
    //this should for any formatt we get the genres onky minor changes will need to be made
    int i = 0;
    int x = 0;
    int[] gamesarray = new int [100];// here set the the import recived from the a function in the games files = to game

    //print checks
    System.out.println("Game array sucessfully working");
    System.out.println(Arrays.toString(gamearray));
    //print check fo the array

    //using 100 as a place holder
    length = games.length
            System.out.print(length);
    int[] gamesMatchgenre;
    int[] genrearray = new int[100];
    //the 100 is a place holder
    while (i < length)

    {
        genrearray[i] = gamesarray[i];//gamearray will go into a function to get the generes
        i += 1;
        if (genre == genre){
            x+= 1;


        }
    }

    gameMatchgenre = new int [x];
    i = 0;//reseting so we can reuse
    x = 0;
    while (x <  gameMatchgenre.length){
        if (genrearray[i] == genre){
            gamesMatchgenre[x] = gamesarray[i];
            x += 1;


        }
        //if else
        //incerment game & genre
        i += 1;


    }

}
}