import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class firstTimeParser {

        /**
         * Constructor used to create a new XML parser object attached to the given file. If the file does not
         * exist, then this will throw an exception for the user to deal with
         * @param inputFileName filename containing xml text
         * @throws FileNotFoundException, IOException
         */
        public firstTimeParser(String inputFileName) throws FileNotFoundException, IOException {
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
        }

        public String findInputFile()
        {
            Element items = xmlDocumentTree.getDocumentElement();
            NodeList fileList = items.getElementsByTagName("filePath");
            NamedNodeMap attri = fileList.item(0).getAttributes();

            return attri.getNamedItem("value").getNodeValue();
        }

    private Document xmlDocumentTree;
}
