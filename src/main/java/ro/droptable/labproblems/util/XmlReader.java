package ro.droptable.labproblems.util;

import ro.droptable.labproblems.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads all entities from an xml file containing entities of type T with id of type ID.
 *
 * @author radu.
 */
public class XmlReader<ID, T extends BaseEntity<ID>> {
    private String fileName;

    public XmlReader(String fileName) {
        this.fileName = fileName;
    }

    public List<T> loadEntities() {
        List<T> entities = new ArrayList<>();
        //TODO implement reader
        return entities;
    }


}
