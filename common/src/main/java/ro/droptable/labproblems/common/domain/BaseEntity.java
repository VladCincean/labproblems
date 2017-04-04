package ro.droptable.labproblems.common.domain;

import java.io.Serializable;

/**
 * @author radu.
 *
 * A generic class for representing a 'real-world' entity that can be identified by an object of type {@code ID}
 */
public class BaseEntity<ID> implements Serializable {
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
