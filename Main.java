import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;


void main() {

    ArrayList<Game> gamesList;
    ArrayList<Collection> collectionsList;
    ArrayList<Review> reviewsList;
    ArrayList<User> usersList;
    try {
        gameParser dom = new gameParser("C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml.xml");
        gamesList = dom.retrieveGameList();
        collectionParser dom2 = new collectionParser("C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml3.xml");
        collectionsList = dom2.retrievecollectionList();
        reviewParser dom3 = new reviewParser("C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml5.xml");
        reviewsList = dom3.retrievereviewsList();
        userParser dom4 = new userParser("C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml8.xml");
        usersList = dom4.retrieveUserList();
        for (int x=0; x<gamesList.size(); x++)
        {
            System.out.println("Hi I'm " + gamesList.get(x).getName() + " with ID " + gamesList.get(x).getID() + "\n");
            gamesList.get(x).addReview(10);
        }
        for(int x=0; x<collectionsList.size(); x++)
        {
            System.out.println(collectionsList.get(x).getName() + " ID:" + collectionsList.get(x).getID());
            System.out.println(collectionsList.get(x).getIDList());
        }

        dom.saveGamesList(gamesList, "C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml2.xml");
        dom2.saveCollectionsList(collectionsList ,"C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml4.xml");
        dom3.savereviewsList(reviewsList ,"C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml6.xml");
        dom4.saveUsersList(usersList, "C:\\Users\\avery\\IdeaProjects\\CS321-01-Projectk\\testxml7.xml");
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (ParserConfigurationException e) {
        throw new RuntimeException(e);
    } catch (TransformerException e) {
        throw new RuntimeException(e);
    }


    System.out.println("Wow we made it to the end.\n");

}
