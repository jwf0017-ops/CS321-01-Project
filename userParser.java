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

public class userParser {
    /**
     * Constructor used to create a new XML parser object attached to the given file. If the file does not
     * exist, then this will throw an exception for the user to deal with
     * @param inputFileName filename containing xml text
     * @throws FileNotFoundException, IOException
     */
    public userParser(String inputFileName) throws FileNotFoundException, IOException {
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

        currentUserList = null;   // start with unallocated list
        reviewList = null;
        collectionsList = null;
        // at this point, if there were no exceptions, the member variable
        // xmlDocumentTree contains all of the nodes found in the XML file.
    }

    /**
     * Retrieves the entire User list from the XML object
     * @return an array list of User objects, in the order they were found in the file
     */
    public ArrayList<User> retrieveUserList() {
        // do the work to build this list only if needed
        if (currentUserList == null) {
            currentUserList = new ArrayList<User>();

            // retrieve the top level node in the tree, items
            Element items =  xmlDocumentTree.getDocumentElement();
            NodeList xmlUserList = items.getElementsByTagName("user");

            for (int UserNumber = 0; UserNumber < xmlUserList.getLength(); UserNumber++) {
                Node User = xmlUserList.item(UserNumber);
                currentUserList.add(parseNextUser(User));
            }
        }

        return currentUserList;
    }


    public ArrayList<Integer> retrieveReviewList(Node userNode)
    {
        String fieldText;

        reviewList = new ArrayList<Integer>();
            // retrieve the top level node in the tree, items
            Element items =  (Element) userNode;
            NodeList xmlUserList = items.getElementsByTagName("review");

            for (int UserNumber = 0; UserNumber < xmlUserList.getLength(); UserNumber++) {
                Node Review = xmlUserList.item(UserNumber);
                NamedNodeMap attributes = Review.getAttributes();


                reviewList.add(Integer.parseInt(attributes.getNamedItem("value").getNodeValue()));


                //currentUserList.add(parseNextUser(User));
            }


        return reviewList;
    }

    public ArrayList<Integer> retrieveCollectionList(Node userNode)
    {

        collectionsList = new ArrayList<Integer>();
            // retrieve the top level node in the tree, items
            Element items =  (Element) userNode;
            NodeList xmlUserList = items.getElementsByTagName("collection");

            for (int UserNumber = 0; UserNumber < xmlUserList.getLength(); UserNumber++) {
                Node Collection = xmlUserList.item(UserNumber);
                NamedNodeMap attributes = Collection.getAttributes();


                collectionsList.add(Integer.parseInt(attributes.getNamedItem("value").getNodeValue()));
            }


        return collectionsList;
    }

    /**
     * Each child node of the main root node is an "item" node in the file. (Tagged with <item )
     * Parse all of the User attributes and fields out of the item.
     * @param xmlUserNode The User node from the DOM tree
     * @return a User object containing the parsed attributes
     */
    private User parseNextUser(Node xmlUserNode) {
        Integer bgg_id;
        //String thumburi ="tbd";
        String title="tbd";
        //String desc = "filler";
        //Integer minAge = 0;
        //Integer year = 0;
        //Integer minPlayers = 0;
        //Integer maxPlayers = 0;

        NamedNodeMap attributes = xmlUserNode.getAttributes();  // for this item, get its attributes
        bgg_id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());


        title = parseTextField(xmlUserNode,"name");
        //thumburi = parseTextField(xmlUserNode, "thumbnail");
        //year = parseIntegerField(xmlUserNode, "yearpublished");
        //desc = parseTextField(xmlUserNode, "description");
        //minAge = parseIntegerField(xmlUserNode, "minAge");




        return new User(title, bgg_id, retrieveCollectionList(xmlUserNode), retrieveReviewList(xmlUserNode)); //Add in array parsing for reviews and collections
    }

    /**
     * Some User data is stored as child elements in the XML <fieldname value="...">
     * Given a single User node from the DOM object, extract the given field from its child nodes.
     * @param xmlUserNode  a User node from DOM tree
     * @param fieldname the field information to extract
     * @return a string containing the field value
     */
    private String parseTextField(Node xmlUserNode, String fieldname) {
        NodeList fields = xmlUserNode.getChildNodes();
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
     * Some User data is stored as child elements in the XML <fieldname value="...">
     * Given a single User node from the DOM object, extract the given field from its child nodes,
     * as an Integer value
     * @param xmlUserNode  a User node from DOM tree
     * @param fieldname the field information to extract
     * @return the integer value found in the field, or 0 if the field is invalid
     */
    private Integer parseIntegerField(Node xmlUserNode, String fieldname) {
        NodeList fields = xmlUserNode.getChildNodes();
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

    public void saveUsersList(ArrayList<User> UsersList, String outputFileName) throws FileNotFoundException, ParserConfigurationException, TransformerException {
        File outputFileTest = new File(outputFileName);
        if (!outputFileTest.exists()) {
            throw new FileNotFoundException(outputFileName+" not found.");
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("users");
        doc.appendChild(rootElement);

        for(int x=0; x<UsersList.size(); x++)
        {
            Element User = doc.createElement("user");
            rootElement.appendChild(User);
            Attr gid = doc.createAttribute("id");
            gid.setValue(String.valueOf(UsersList.get(x).GetID()));
            User.setAttributeNode(gid);

            Element name = doc.createElement("name");
            User.appendChild(name);
            Attr nameStr = doc.createAttribute("value");
            nameStr.setValue(UsersList.get(x).GetName());
            name.setAttributeNode(nameStr);



            Element reviewElement = doc.createElement("reviews");
            User.appendChild(reviewElement);



            ArrayList<Integer> reviews = UsersList.get(x).getReviewList();
            for(int y=0; y<reviews.size(); y++)
            {
                Element review = doc.createElement("review");
                reviewElement.appendChild(review);
                Attr rid = doc.createAttribute("value");
                rid.setValue(String.valueOf(reviews.get(y)));
                review.setAttributeNode(rid);

            }

            Element collectionElement = doc.createElement("collections");
            User.appendChild(collectionElement);

            ArrayList<Integer> collections = UsersList.get(x).getCollactionsList();
            for(int y=0; y<reviews.size(); y++)
            {
                Element review = doc.createElement("collection");
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
    private ArrayList<User> currentUserList;
    private ArrayList<Integer> collectionsList; //List of Collection IDs
    private ArrayList<Integer> reviewList; //List of Review IDs
}
