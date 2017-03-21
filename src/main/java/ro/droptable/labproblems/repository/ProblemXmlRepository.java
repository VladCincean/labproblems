package ro.droptable.labproblems.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.util.XmlHelper;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type {@code Problem}
 *      while maintaining XML persistence
 */
public class ProblemXmlRepository extends InMemoryRepository<Long, Problem> {
    private String fileName;

    public ProblemXmlRepository(Validator<Problem> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

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

    private void loadData() {
        Document document = XmlHelper.loadDocument(fileName);

        if (document == null) {
            return;
        }

        Element root = document.getDocumentElement(); // problems
        NodeList nodeList = root.getElementsByTagName("problem");
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i); // problem
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                Element element = (Element)node;
//                Problem problem = getEntity(element);
//
//                try {
//                    super.save(problem); // save in memory
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
                    Problem problem = getEntity(element);

                    try {
                        super.save(problem); // save in memory
                    } catch (ValidatorException e) {
                        e.printStackTrace(); // TODO: ...
                    }
                });
    }

    private void saveToFile(Problem entity) {
        Document document = XmlHelper.loadDocument(fileName);

        if (document == null) {
            return;
        }

        Element root = document.getDocumentElement(); // problems
        Element element = document.createElement("problem");

        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(entity.getId().toString()));

        Element title = document.createElement("title");
        title.appendChild(document.createTextNode(entity.getTitle()));

        Element description = document.createElement("description");
        description.appendChild(
                document.createTextNode("\n" + entity.getDescription() + "\n")
        );

        element.appendChild(id);
        element.appendChild(title);
        element.appendChild(description);

        root.appendChild(element);

        XmlHelper.saveDocument(fileName, document);
    }

    @Override
    public Optional<Problem> save(Problem entity) throws ValidatorException {
        Optional<Problem> optional = super.save(entity);

        optional.orElseGet(() -> {
            this.saveToFile(entity);
            return null;
        });
        return optional.isPresent() ? optional : Optional.empty();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Problem> delete(Long id) {
        Optional<Problem> optional = super.delete(id);
        optional.ifPresent(p -> {
            Document document = XmlHelper.loadDocument(fileName);

            if (document == null) {
                return;
            }

            NodeList nodeList = document.getElementsByTagName("problem");
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
    public Optional<Problem> update(Problem entity) throws ValidatorException {
        Optional<Problem> optional = super.update(entity);
        optional.ifPresent(p -> {
            Document document = XmlHelper.loadDocument(fileName);

            if (document == null) {
                return;
            }

            NodeList nodeList = document.getElementsByTagName("student");
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element)node;
//
//                    if (element.getElementsByTagName("id").item(0)
//                            .getTextContent().equals(entity.getId().toString())) {
//                        element.getElementsByTagName("title").item(0)
//                                .setTextContent(entity.getTitle());
//                        element.getElementsByTagName("description").item(0)
//                                .setTextContent(entity.getDescription());
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
                        element.getElementsByTagName("title").item(0)
                                .setTextContent(entity.getTitle());
                        element.getElementsByTagName("description").item(0)
                                .setTextContent(entity.getDescription());
                    });

            XmlHelper.saveDocument(fileName, document);
        });

        return optional.isPresent() ? optional : Optional.empty();
    }
}
