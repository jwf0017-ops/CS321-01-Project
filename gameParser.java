import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

    public ArrayList<Integer> retrieveReviewList(Node gameNode)
    {
        String fieldText;

            reviewList = new ArrayList<Integer>();

            // retrieve the top level node in the tree, items
            Element items =  (Element) gameNode;
            NodeList xmlcollectionList = items.getElementsByTagName("review");

            for (int collectionNumber = 0; collectionNumber < xmlcollectionList.getLength(); collectionNumber++) {
                Node Review = xmlcollectionList.item(collectionNumber);
                NamedNodeMap attributes = Review.getAttributes();


                reviewList.add(Integer.parseInt(attributes.getNamedItem("value").getNodeValue()));


                //currentcollectionList.add(parseNextcollection(collection));
            }


        return reviewList;
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
        minAge = parseIntegerField(xmlGameNode, "minage");




        return new Game(bgg_id,desc, title, minAge, minPlayers, maxPlayers, year, retrieveReviewList(xmlGameNode));
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
                String wowzests = field.getTextContent();
                if (wowzers != null)
                {
                    fieldText = wowzers.getNodeValue();
                }
                else if(wowzests != null)
                {
                    fieldText = wowzests;
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

    public void saveGamesList(ArrayList<Game> gamesList, String outputFileName) throws FileNotFoundException, ParserConfigurationException, TransformerException {
        File outputFileTest = new File(outputFileName);
        if (!outputFileTest.exists()) {
            throw new FileNotFoundException(outputFileName+" not found.");
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("items");
        doc.appendChild(rootElement);

        for(int x=0; x<gamesList.size(); x++)
        {
            Element game = doc.createElement("item");
            rootElement.appendChild(game);
            Attr gid = doc.createAttribute("id");
            gid.setValue(String.valueOf(gamesList.get(x).getID()));
            game.setAttributeNode(gid);

            Element name = doc.createElement("name");
            game.appendChild(name);
            Attr nameStr = doc.createAttribute("value");
            nameStr.setValue(gamesList.get(x).getName());
            name.setAttributeNode(nameStr);

            Element desc = doc.createElement("description");
            game.appendChild(desc);
            desc.appendChild(doc.createTextNode(gamesList.get(x).getDescription()));

            Element year = doc.createElement("yearpublished");
            game.appendChild(year);
            Attr yearStr = doc.createAttribute("value");
            yearStr.setValue(String.valueOf(gamesList.get(x).getYearPublished()));
            year.setAttributeNode(yearStr);

            Element age = doc.createElement("minage");
            game.appendChild(age);
            Attr minAge = doc.createAttribute("value");
            minAge.setValue(String.valueOf(gamesList.get(x).getMinAge()));
            age.setAttributeNode(minAge);

            Element reviewElement = doc.createElement("reviews");
            game.appendChild(reviewElement);



            ArrayList<Integer> reviews = gamesList.get(x).getReviewIDs();
            for(int y=0; y<reviews.size(); y++)
            {
                Element review = doc.createElement("review");
                reviewElement.appendChild(review);
                Attr rid = doc.createAttribute("value");
                rid.setValue(String.valueOf(reviews.get(y)));
                review.setAttributeNode(rid);

            }




        }


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set indentation size
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        DOMSource source = new DOMSource(doc);
        FileOutputStream output = new FileOutputStream(outputFileTest);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);



    }

        private Document xmlDocumentTree;
        private ArrayList<Game> currentGameList;
        private ArrayList<Integer> reviewList;
}
