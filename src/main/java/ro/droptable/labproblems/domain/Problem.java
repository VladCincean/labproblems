package ro.droptable.labproblems.domain;

/**
 * Created by stefana on 3/5/2017.
 */
public class Problem extends BaseEntity<Long> {
    private Long id;
    private String description;
    private static long currentId = 1;

    public Problem() {
    }

    public Problem(Long id, String description) {
        this.id = id;
        this.description = description;
        this.setId(currentId++);
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Problem student = (Problem) o;

        if (id != student.id) return false;
        if (!description.equals(student.description)) return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + id.intValue();
        return result;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
