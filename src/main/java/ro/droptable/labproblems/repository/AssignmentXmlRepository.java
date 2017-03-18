package ro.droptable.labproblems.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.droptable.labproblems.domain.Assignment;
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
 * Extension of {@code FileRepository} for CRUD operations on a repository for type {@code Assignment}
 *      while maintaining XML persistence
 */
public class AssignmentXmlRepository extends FileRepository<Long, Assignment> {

    public AssignmentXmlRepository(Validator<Assignment> validator, String fileName) {
        super(validator, fileName);

        loadData();
    }

    private Assignment getEntity(Element element) {
        Long id = Long.valueOf(
                element.getElementsByTagName("id").item(0).getTextContent().trim()
        );
        long studentId = Long.parseLong(
                element.getElementsByTagName("studentId").item(0).getTextContent().trim()
        );
        long problemId = Long.parseLong(
                element.getElementsByTagName("problemId").item(0).getTextContent().trim()
        );
        double grade = Double.parseDouble(
                element.getElementsByTagName("grade").item(0).getTextContent().trim()
        );

        Assignment assignment = new Assignment(id, studentId, problemId);
        assignment.setGrade(grade);
        return assignment;
    }

    @Override
    protected void loadData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);

            Element root = document.getDocumentElement(); // assignments
            NodeList nodeList = root.getElementsByTagName("assignment");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i); // assignment
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    Assignment assignment = getEntity(element);

                    try {
                        super.saveInMemory(assignment);
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
    protected void saveToFile(Assignment entity) {
        throw new NotImplementedException();
    }
}
