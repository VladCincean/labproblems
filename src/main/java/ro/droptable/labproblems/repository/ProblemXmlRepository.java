package ro.droptable.labproblems.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code FileRepository} for CRUD operations on a repository for type {@code Problem}
 *      while maintaining XML persistence
 */
public class ProblemXmlRepository extends FileRepository<Long, Problem> {

    public ProblemXmlRepository(Validator<Problem> validator, String fileName) {
        super(validator, fileName);

        loadData();
    }

    private Problem getEntity(Element element) {
        Long id = Long.valueOf(
                element.getElementsByTagName("id").item(0).getTextContent().trim()
        );
        String title = element.getElementsByTagName("title").item(0).getTextContent().trim();
        String description = element.getElementsByTagName("description").item(0).getTextContent().trim();

        return new Problem(id, title, description);
    }

    @Override
    protected void loadData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);

            Element root = document.getDocumentElement(); // problems
            NodeList nodeList = root.getElementsByTagName("problem");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i); // problem
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    Problem problem = getEntity(element);

                    try {
                        super.saveInMemory(problem);
                    } catch (ValidatorException e) {
                        e.printStackTrace(); // TODO: do something else
                    }
                }
            }
        } catch (ParserConfigurationException
                | SAXException
                | IOException e) {
            e.printStackTrace(); // TODO: ...
        }
    }

    @Override
    protected void saveToFile(Problem entity) {
        throw new NotImplementedException();
    }
}
