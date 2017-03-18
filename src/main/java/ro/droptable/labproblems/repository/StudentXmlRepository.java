package ro.droptable.labproblems.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.util.XmlHelper;

import java.util.Optional;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type {@code Student}
 *      while maintaining XML persistence
 */
public class StudentXmlRepository extends InMemoryRepository<Long, Student> {
    private String fileName;

    public StudentXmlRepository(Validator<Student> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

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

    private void loadData() {
        Document document = XmlHelper.loadDocument(fileName);

        if (document == null) {
            return;
        }

        Element root = document.getDocumentElement(); // students
        NodeList nodeList = root.getElementsByTagName("student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i); // student
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element)node;
                Student student = getEntity(element);

                try {
                    super.save(student); // save in memory
                } catch (ValidatorException e) {
                    e.printStackTrace(); // TODO: do something else
                }
            }
        }
    }

    private void saveToFile(Student entity) {
        Document document = XmlHelper.loadDocument(fileName);

        if (document == null) {
            return;
        }

        Element root = document.getDocumentElement(); // students
        Element element = document.createElement("student");

        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(entity.getId().toString()));

        Element serialNumber = document.createElement("serialNumber");
        serialNumber.appendChild(document.createTextNode(entity.getSerialNumber()));

        Element name = document.createElement("name");
        name.appendChild(document.createTextNode(entity.getName()));

        Element group = document.createElement("group");
        group.appendChild(document.createTextNode(Integer.toString(entity.getGroup())));

        element.appendChild(id);
        element.appendChild(serialNumber);
        element.appendChild(name);
        element.appendChild(group);

        root.appendChild(element);

        XmlHelper.saveDocument(fileName, document);
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
        Optional<Student> optional = super.save(entity);

        optional.orElseGet(() -> {
           this.saveToFile(entity);
           return null;
        });
        return optional.isPresent() ? optional : Optional.empty();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Student> delete(Long id) {
        Optional<Student> optional = super.delete(id);
        optional.ifPresent(s -> {
            Document document = XmlHelper.loadDocument(fileName);

            if (document == null) {
                return;
            }

            NodeList nodeList = document.getElementsByTagName("student");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;

                    if (element.getElementsByTagName("id").item(0).getTextContent().equals(id.toString())) {
                        element.getParentNode().removeChild(element);
                    }
                }

            }

            XmlHelper.saveDocument(fileName, document);
        });

        return optional.isPresent() ? optional : Optional.empty();
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException {
        Optional<Student> optional = super.update(entity);
        optional.ifPresent(s -> {
            Document document = XmlHelper.loadDocument(fileName);

            if (document == null) {
                return;
            }

            NodeList nodeList = document.getElementsByTagName("student");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;

                    if (element.getElementsByTagName("id").item(0)
                            .getTextContent().equals(entity.getId().toString())) {
                        element.getElementsByTagName("serialNumber").item(0)
                                .setTextContent(entity.getSerialNumber());
                        element.getElementsByTagName("name").item(0)
                                .setTextContent(entity.getName());
                        element.getElementsByTagName("group").item(0)
                                .setTextContent(Integer.toString(entity.getGroup()));
                    }
                }
            }

            XmlHelper.saveDocument(fileName, document);
        });

        return optional.isPresent() ? optional : Optional.empty();
    }
}
