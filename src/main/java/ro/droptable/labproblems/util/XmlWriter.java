package ro.droptable.labproblems.util;

import ro.droptable.labproblems.domain.BaseEntity;

/**
 * Appends an entity of type T with id of type ID to an existing xml file.
 * The xml structure is assumed to be like the one from data/students.xml.
 *
 * @author radu.
 */
public class XmlWriter<ID, T extends BaseEntity<ID>> {

    private String fileName;

    public XmlWriter(String fileName) {
        this.fileName = fileName;
    }

    public void save(T entity) {
        //TODO implement writer
    }


}
