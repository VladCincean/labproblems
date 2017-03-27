package ro.droptable.labproblems.common.domain;

/**
 * @author radu.
 *
 * A generic class for representing a 'real-world' entity that can be identified by an object of type {@code ID}
 */
public class BaseEntity<ID> {
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
