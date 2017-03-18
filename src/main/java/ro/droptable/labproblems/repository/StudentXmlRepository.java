package ro.droptable.labproblems.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.droptable.labproblems.domain.Student;
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
 * Extension of {@code FileRepository} for CRUD operations on a repository for type {@code Student}
 *      while maintaining XML persistence
 */
public class StudentXmlRepository extends FileRepository<Long, Student> {

    public StudentXmlRepository(Validator<Student> validator, String fileName) {
        super(validator, fileName);

        loadData();
    }

    private Student getEntity(Element element) {
        Long id = Long.valueOf(
                element.getElementsByTagName("id").item(0).getTextContent().trim()
        );
        String serialNumber = element.getElementsByTagName("serialNumber").item(0).getTextContent().trim();
        String name = element.getElementsByTagName("name").item(0).getTextContent().trim();
        int group = Integer.parseInt(
                element.getElementsByTagName("group").item(0).getTextContent().trim()
        );

        return new Student(id, serialNumber, name, group);
    }

    @Override
    protected void loadData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);

            Element root = document.getDocumentElement(); // students
            NodeList nodeList = root.getElementsByTagName("student");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i); // student
//                if (node instanceof Element) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    Student student = getEntity(element);

                    try {
                        super.saveInMemory(student);
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
    protected void saveToFile(Student entity) {
        throw new NotImplementedException();
    }
}
