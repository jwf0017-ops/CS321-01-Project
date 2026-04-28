import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class collectionParser {
    /**
     * Constructor used to create a new XML parser object attached to the given file. If the file does not
     * exist, then this will throw an exception for the collection to deal with
     * @param inputFileName filename containing xml text
     * @throws FileNotFoundException, IOException
     */
    public collectionParser(String inputFileName) throws FileNotFoundException, IOException {
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

        currentcollectionList = null;   // start with unallocated list
        gameList = null;
        // at this point, if there were no exceptions, the member variable
        // xmlDocumentTree contains all of the nodes found in the XML file.
    }

    /**
     * Retrieves the entire collection list from the XML object
     * @return an array list of collection objects, in the order they were found in the file
     */
    public ArrayList<Collection> retrievecollectionList() {
        // do the work to build this list only if needed
        if (currentcollectionList == null) {
            currentcollectionList = new ArrayList<Collection>();

            // retrieve the top level node in the tree, items
            Element items =  xmlDocumentTree.getDocumentElement();
            NodeList xmlcollectionList = items.getElementsByTagName("collection");

            for (int collectionNumber = 0; collectionNumber < xmlcollectionList.getLength(); collectionNumber++) {
                Node collection = xmlcollectionList.item(collectionNumber);
                currentcollectionList.add(parseNextcollection(collection));
            }
        }

        return currentcollectionList;
    }


    public ArrayList<Integer> retrieveGameList(Node collectionNode)
    {
        String fieldText;

        gameList = new ArrayList<Integer>();


        // retrieve the top level node in the tree, items
        Element items = (Element) collectionNode;
        NodeList xmlcollectionList = items.getElementsByTagName("game");

        for (int collectionNumber = 0; collectionNumber < xmlcollectionList.getLength(); collectionNumber++) {
            Node Review = xmlcollectionList.item(collectionNumber);
            NamedNodeMap attributes = Review.getAttributes();


            gameList.add(Integer.parseInt(attributes.getNamedItem("value").getNodeValue()));

        }


        return gameList;
    }



    /**
     * Each child node of the main root node is an "item" node in the file. (Tagged with <item )
     * Parse all of the collection attributes and fields out of the item.
     * @param xmlcollectionNode The collection node from the DOM tree
     * @return a collection object containing the parsed attributes
     */
    private Collection parseNextcollection(Node xmlcollectionNode) {
        Integer collID;
        String title="tbd";


        NamedNodeMap attributes = xmlcollectionNode.getAttributes();  // for this item, get its attributes
        collID = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
        title = parseTextField(xmlcollectionNode,"name");


        return new Collection(collID, title, retrieveGameList(xmlcollectionNode)); //Add in array parsing for reviews and collections
    }

    /**
     * Some collection data is stored as child elements in the XML <fieldname value="...">
     * Given a single collection node from the DOM object, extract the given field from its child nodes.
     * @param xmlcollectionNode  a collection node from DOM tree
     * @param fieldname the field information to extract
     * @return a string containing the field value
     */
    private String parseTextField(Node xmlcollectionNode, String fieldname) {
        NodeList fields = xmlcollectionNode.getChildNodes();
        String fieldText = "unknown";
        for (int i = 0; i < fields.getLength(); i++) {
            Node field = fields.item(i);
            if (field.getNodeName().equals(fieldname)) {
                NamedNodeMap attributes = field.getAttributes();
                Node wowzers = attributes.getNamedItem("value");
                if (wowzers != null)
                {
                    fieldText = wowzers.getNodeValue();
                }

            }
        }
        return fieldText;
    }


    /**
     * Some collection data is stored as child elements in the XML <fieldname value="...">
     * Given a single collection node from the DOM object, extract the given field from its child nodes,
     * as an Integer value
     * @param xmlcollectionNode  a collection node from DOM tree
     * @param fieldname the field information to extract
     * @return the integer value found in the field, or 0 if the field is invalid
     */
    private Integer parseIntegerField(Node xmlcollectionNode, String fieldname) {
        NodeList fields = xmlcollectionNode.getChildNodes();
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

    public void saveCollectionsList(ArrayList<Collection> collectionsList, String outputFileName) throws FileNotFoundException, ParserConfigurationException, TransformerException {
        File outputFileTest = new File(outputFileName);
        if (!outputFileTest.exists()) {
            throw new FileNotFoundException(outputFileName+" not found.");
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("collections");
        doc.appendChild(rootElement);

        for(int x=0; x<collectionsList.size(); x++)
        {
            Element game = doc.createElement("collection");
            rootElement.appendChild(game);
            Attr gid = doc.createAttribute("id");
            gid.setValue(String.valueOf(collectionsList.get(x).getID()));
            game.setAttributeNode(gid);

            Element name = doc.createElement("name");
            game.appendChild(name);
            Attr nameStr = doc.createAttribute("value");
            nameStr.setValue(collectionsList.get(x).getName());
            name.setAttributeNode(nameStr);

            Element reviewElement = doc.createElement("games");
            game.appendChild(reviewElement);



            ArrayList<Integer> games = collectionsList.get(x).getIDList();
            for(int y=0; y<games.size(); y++)
            {
                Element review = doc.createElement("game");
                reviewElement.appendChild(review);
                Attr rid = doc.createAttribute("value");
                rid.setValue(String.valueOf(games.get(y)));
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
    private ArrayList<Collection> currentcollectionList;

    private ArrayList<Integer> gameList; //List of Game IDs
}