import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;


void main() {

    ArrayList<Game> gamesList;
    try {
        gameParser dom = new gameParser("C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml.xml");
        gamesList = dom.retrieveGameList();


        for (Game game : gamesList)
        {
            System.out.println("Hi I'm " + game.getName() + " with ID " + game.getID() + "\n");
            game.addReview(10);
        }

        dom.saveGamesList(gamesList, "C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml2.xml");

    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (ParserConfigurationException e) {
        throw new RuntimeException(e);
    } catch (TransformerException e) {
        throw new RuntimeException(e);
    }


    System.out.println("Wow we made it to the end.\n");

}
