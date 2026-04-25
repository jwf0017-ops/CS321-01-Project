import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;

public class gameParser {

        /**
         * Constructor used to create a new XML parser object attached to the given file. If the file does not
         * exist, then this will throw an exception for the user to deal with
         * @param inputFileName filename containing xml text
         * @throws FileNotFoundException, IOException
         */
        public gameParser(String inputFileName) throws FileNotFoundException, IOException {
            File inputFileTest = new File(inputFileName);
            if (!inputFileTest.exists()) {
                throw new FileNotFoundException(inputFileName+" not found.");
            }

            // if the given file exists, we will open it, and retrieve the XML document from it
            // if the XML is malformed, throw an exception
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setExpandEntityReferences(false);
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                xmlDocumentTree = db.parse(inputFileName);   // retrieves the XML text into a stored dom object
            } catch (Exception ex) {
                throw new java.io.IOException("Unable to parse XML document");
            }

            currentGameList = null;   // start with unallocated list
            // at this point, if there were no exceptions, the member variable
            // xmlDocumentTree contains all of the nodes found in the XML file.
        }

    /**
     * Retrieves the entire game list from the XML object
     * @return an array list of game objects, in the order they were found in the file
     */
    public ArrayList<Game> retrieveGameList() {
        // do the work to build this list only if needed
        if (currentGameList == null) {
            currentGameList = new ArrayList<Game>();

            // retrieve the top level node in the tree, items
            Element items =  xmlDocumentTree.getDocumentElement();
            NodeList xmlGameList = items.getElementsByTagName("item");

            for (int gameNumber = 0; gameNumber < xmlGameList.getLength(); gameNumber++) {
                Node game = xmlGameList.item(gameNumber);
                currentGameList.add(parseNextGame(game));
            }
        }

        return currentGameList;
    }

    /**
     * Each child node of the main root node is an "item" node in the file. (Tagged with <item )
     * Parse all of the game attributes and fields out of the item.
     * @param xmlGameNode The game node from the DOM tree
     * @return a Game object containing the parsed attributes
     */
    private Game parseNextGame(Node xmlGameNode) {
        Integer bgg_id;
        String thumburi ="tbd";
        String title="tbd";
        String desc = "filler";
        Integer minAge = 0;
        Integer year = 0;
        Integer minPlayers = 0;
        Integer maxPlayers = 0;

        NamedNodeMap attributes = xmlGameNode.getAttributes();  // for this item, get its attributes
        bgg_id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());


        title = parseTextField(xmlGameNode,"name");
        thumburi = parseTextField(xmlGameNode, "thumbnail");
        year = parseIntegerField(xmlGameNode, "yearpublished");
        desc = parseTextField(xmlGameNode, "description");
        minAge = parseIntegerField(xmlGameNode, "minAge");



        return new Game(bgg_id,desc, title, minAge, minPlayers, maxPlayers, year);
    }

    /**
     * Some game data is stored as child elements in the XML <fieldname value="...">
     * Given a single game node from the DOM object, extract the given field from its child nodes.
     * @param xmlGameNode  a game node from DOM tree
     * @param fieldname the field information to extract
     * @return a string containing the field value
     */
    private String parseTextField(Node xmlGameNode, String fieldname) {
        NodeList fields = xmlGameNode.getChildNodes();
        String fieldText = "unknown";
        for (int i = 0; i < fields.getLength(); i++) {
            Node field = fields.item(i);
            if (field.getNodeName().equals(fieldname)) {
                NamedNodeMap attributes = field.getAttributes();
                Node wowzers = attributes.getNamedItem("value");
                if (wowzers != null && attributes.getNamedItem("type").getNodeValue().compareTo("alternate") != 0)
                {
                    fieldText = wowzers.getNodeValue();
                }

            }
        }
        return fieldText;
    }


    /**
     * Some game data is stored as child elements in the XML <fieldname value="...">
     * Given a single game node from the DOM object, extract the given field from its child nodes,
     * as an Integer value
     * @param xmlGameNode  a game node from DOM tree
     * @param fieldname the field information to extract
     * @return the integer value found in the field, or 0 if the field is invalid
     */
    private Integer parseIntegerField(Node xmlGameNode, String fieldname) {
        NodeList fields = xmlGameNode.getChildNodes();
        Integer fieldValue = 0;
        for (int i = 0; i < fields.getLength(); i++) {
            Node field = fields.item(i);
            if (field.getNodeName().equals(fieldname)) {
                NamedNodeMap attributes = field.getAttributes();
                try {
                    fieldValue = Integer.parseInt(attributes.getNamedItem("value").getNodeValue());
                } catch (NumberFormatException e) {
                    fieldValue = 0;  // use a default value or maybe throw an exception to deal with
                }
            }
        }
        return fieldValue;
    }

        private Document xmlDocumentTree;
        private ArrayList<Game> currentGameList;
}
