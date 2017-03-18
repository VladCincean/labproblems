package ro.droptable.labproblems.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Created by vlad on 04.03.2017.
 */
public class XmlHelper {

    public static Document loadDocument(String fileName) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace(); // TODO: ...
        }

        Document document = null;
        try {
            document = documentBuilder != null ? documentBuilder.parse(fileName) : null;
        } catch (SAXException | IOException e) {
            e.printStackTrace(); // TODO: ...
        }

        return document;
    }

    public static void saveDocument(String fileName, Document document) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace(); // TODO: ...
        }

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(fileName));
        try {
            if (transformer != null) {
                transformer.transform(source, result);
            }
        } catch (TransformerException e) {
            e.printStackTrace(); // TODO: ...
        }
    }
}