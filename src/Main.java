import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;


void main() {

    ArrayList<Game> gamesList;
    try {
        gameParser dom = new gameParser("src/bgg90Games.xml");
        gamesList = dom.retrieveGameList();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    for (Game game : gamesList)
    {
        System.out.println("Hi I'm " + game.getName() + " with ID " + game.getID() + "\n");
    }

    System.out.println("Wow we made it to the end.\n");

}
