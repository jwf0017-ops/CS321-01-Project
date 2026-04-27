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

public class reviewParser {
    /**
     * Constructor used to create a new XML parser object attached to the given file. If the file does not
     * exist, then this will throw an exception for the review to deal with
     * @param inputFileName filename containing xml text
     * @throws FileNotFoundException, IOException
     */
    public reviewParser(String inputFileName) throws FileNotFoundException, IOException {
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

        currentreviewsList = null;   // start with unallocated list

        // at this point, if there were no exceptions, the member variable
        // xmlDocumentTree contains all of the nodes found in the XML file.
    }

    /**
     * Retrieves the entire review list from the XML object
     * @return an array list of review objects, in the order they were found in the file
     */
    public ArrayList<Review> retrievereviewsList() {
        // do the work to build this list only if needed
        if (currentreviewsList == null) {
            currentreviewsList = new ArrayList<Review>();

            // retrieve the top level node in the tree, items
            Element items =  xmlDocumentTree.getDocumentElement();
            NodeList xmlreviewsList = items.getElementsByTagName("review");

            for (int reviewNumber = 0; reviewNumber < xmlreviewsList.getLength(); reviewNumber++) {
                Node review = xmlreviewsList.item(reviewNumber);
                currentreviewsList.add(parseNextreview(review));
            }
        }

        return currentreviewsList;
    }






    /**
     * Each child node of the main root node is an "item" node in the file. (Tagged with <item )
     * Parse all of the review attributes and fields out of the item.
     * @param xmlreviewNode The review node from the DOM tree
     * @return a review object containing the parsed attributes
     */
    private Review parseNextreview(Node xmlreviewNode) {
        Integer bgg_id;
        Integer gID;
        Integer uID;
        Integer rating;


        String desc="tbd";


        NamedNodeMap attributes = xmlreviewNode.getAttributes();  // for this item, get its attributes
        bgg_id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
        gID = parseIntegerField(xmlreviewNode, "gID");
        uID = parseIntegerField(xmlreviewNode, "uID");
        rating = parseIntegerField(xmlreviewNode, "rating");
        desc = parseTextField(xmlreviewNode,"description");
        //thumburi = parseTextField(xmlreviewNode, "thumbnail");
        //year = parseIntegerField(xmlreviewNode, "yearpublished");
        //desc = parseTextField(xmlreviewNode, "description");
        //minAge = parseIntegerField(xmlreviewNode, "minAge");




        return new Review(uID, gID, bgg_id, rating, desc); //Add in array parsing for reviews and collections
    }

    /**
     * Some review data is stored as child elements in the XML <fieldname value="...">
     * Given a single review node from the DOM object, extract the given field from its child nodes.
     * @param xmlreviewNode  a review node from DOM tree
     * @param fieldname the field information to extract
     * @return a string containing the field value
     */
    private String parseTextField(Node xmlreviewNode, String fieldname) {
        NodeList fields = xmlreviewNode.getChildNodes();
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
     * Some review data is stored as child elements in the XML <fieldname value="...">
     * Given a single review node from the DOM object, extract the given field from its child nodes,
     * as an Integer value
     * @param xmlreviewNode  a review node from DOM tree
     * @param fieldname the field information to extract
     * @return the integer value found in the field, or 0 if the field is invalid
     */
    private Integer parseIntegerField(Node xmlreviewNode, String fieldname) {
        NodeList fields = xmlreviewNode.getChildNodes();
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

    public void savereviewsList(ArrayList<Review> reviewsList, String outputFileName) throws FileNotFoundException, ParserConfigurationException, TransformerException {
        File outputFileTest = new File(outputFileName);
        if (!outputFileTest.exists()) {
            throw new FileNotFoundException(outputFileName+" not found.");
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("reviews");
        doc.appendChild(rootElement);

        for(int x=0; x<reviewsList.size(); x++)
        {
            Element game = doc.createElement("review");
            rootElement.appendChild(game);
            Attr gid = doc.createAttribute("id");
            gid.setValue(String.valueOf(reviewsList.get(x).getID()));
            game.setAttributeNode(gid);

            Element name = doc.createElement("gID");
            game.appendChild(name);
            Attr gIDStr = doc.createAttribute("value");
            gIDStr.setValue(String.valueOf(reviewsList.get(x).getGameID()));
            name.setAttributeNode(gIDStr);

            Element uID = doc.createElement("uID");
            game.appendChild(uID);
            Attr uIDStr = doc.createAttribute("value");
            uIDStr.setValue(String.valueOf(reviewsList.get(x).getUserID()));
            uID.setAttributeNode(uIDStr);

            Element year = doc.createElement("yearpublished");
            game.appendChild(year);
            Attr yearStr = doc.createAttribute("value");
            yearStr.setValue(String.valueOf(reviewsList.get(x).getDesc()));
            year.setAttributeNode(yearStr);

            Element rating = doc.createElement("rating");
            game.appendChild(rating);
            Attr ratingStr = doc.createAttribute("value");
            ratingStr.setValue(String.valueOf(reviewsList.get(x).getRating()));
            rating.setAttributeNode(ratingStr);







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
    private ArrayList<Review> currentreviewsList;

}
