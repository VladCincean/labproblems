package ro.droptable.labproblems.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.util.XmlHelper;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type {@code Assignment}
 *      while maintaining XML persistence
 */
public class AssignmentXmlRepository extends InMemoryRepository<Long, Assignment> {
    private String fileName;

    public AssignmentXmlRepository(Validator<Assignment> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

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

    private void loadData() {
        Document document = XmlHelper.loadDocument(fileName);

        if (document == null) {
            return;
        }

        Element root = document.getDocumentElement(); // assignments
        NodeList nodeList = root.getElementsByTagName("assignment");
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i); // assignment
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                Element element = (Element)node;
//                Assignment assignment = getEntity(element);
//
//                try {
//                    super.save(assignment); // save in memory
//                } catch (ValidatorException e) {
//                    e.printStackTrace(); // TODO: do something else
//                }
//            }
//        }

        IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item)
                .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                .map(node -> (Element)node)
                .forEach(element -> {
                    Assignment assignment = getEntity(element);

                    try {
                        super.save(assignment); // save in memory
                    } catch (ValidatorException e) {
                        e.printStackTrace(); // TODO: ...
                    }
                });
    }

    protected void saveToFile(Assignment entity) {
        Document document = XmlHelper.loadDocument(fileName);

        if (document == null) {
            return;
        }

        Element root = document.getDocumentElement(); // assignments
        Element element = document.createElement("assignment");

        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(entity.getId().toString()));

        Element studentId = document.createElement("studentId");
        studentId.appendChild(document.createTextNode(Long.toString(entity.getStudentId())));

        Element problemId = document.createElement("problemId");
        problemId.appendChild(document.createTextNode(Long.toString(entity.getProblemId())));

        Element grade = document.createElement("grade");
        grade.appendChild(document.createTextNode(Double.toString(entity.getGrade())));

        element.appendChild(id);
        element.appendChild(studentId);
        element.appendChild(problemId);
        element.appendChild(grade);

        root.appendChild(element);

        XmlHelper.saveDocument(fileName, document);
    }

    @Override
    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        Optional<Assignment> optional = super.save(entity);

        optional.orElseGet(() -> {
            this.saveToFile(entity);
            return null;
        });
        return optional.isPresent() ? optional : Optional.empty();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Assignment> delete(Long id) {
        Optional<Assignment> optional = super.delete(id);
        optional.ifPresent(a -> {
            Document document = XmlHelper.loadDocument(fileName);

            if (document == null) {
                return;
            }

            NodeList nodeList = document.getElementsByTagName("assignment");
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element)node;
//
//                    if (element.getElementsByTagName("id").item(0).getTextContent().equals(id.toString())) {
//                        element.getParentNode().removeChild(element);
//                    }
//                }
//            }

            IntStream.range(0, nodeList.getLength())
                    .mapToObj(nodeList::item)
                    .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                    .map(node -> (Element)node)
                    .filter(element -> element
                            .getElementsByTagName("id")
                            .item(0)
                            .getTextContent()
                            .equals(id.toString()))
                    .forEach(element -> element.getParentNode().removeChild(element));

            XmlHelper.saveDocument(fileName, document);
        });

        return optional.isPresent() ? optional : Optional.empty();
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException {
        Optional<Assignment> optional = super.update(entity);
        optional.ifPresent(a -> {
            Document document = XmlHelper.loadDocument(fileName);

            if (document == null) {
                return;
            }

            NodeList nodeList = document.getElementsByTagName("assignment");
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element)node;
//
//                    if (element.getElementsByTagName("id").item(0)
//                            .getTextContent().equals(entity.getId().toString())) {
//                        element.getElementsByTagName("studentID").item(0)
//                                .setTextContent(Long.toString(entity.getStudentId()));
//                        element.getElementsByTagName("problemId").item(0)
//                                .setTextContent(Long.toString(entity.getProblemId()));
//                        element.getElementsByTagName("grade").item(0)
//                                .setTextContent(Double.toString(entity.getGrade()));
//                    }
//                }
//            }

            IntStream.range(0, nodeList.getLength())
                    .mapToObj(nodeList::item)
                    .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                    .map(node -> (Element)node)
                    .filter(element -> element
                            .getElementsByTagName("id")
                            .item(0)
                            .getTextContent()
                            .equals(entity.getId().toString()))
                    .forEach(element -> {
                        element.getElementsByTagName("studentId").item(0)
                                .setTextContent(Long.toString(entity.getStudentId()));
                        element.getElementsByTagName("problemId").item(0)
                                .setTextContent(Long.toString(entity.getProblemId()));
                        element.getElementsByTagName("grade").item(0)
                                .setTextContent(Double.toString(entity.getGrade()));
                    });

            XmlHelper.saveDocument(fileName, document);
        });

        return optional.isPresent() ? optional : Optional.empty();
    }
}
