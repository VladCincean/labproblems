package ro.droptable.labproblems.common.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by stefana on 3/5/2017.
 *
 * Class that represents a 'real-world' {@code Problem}
 */
public class Problem extends BaseEntity<Long> implements Serializable {
    private String title;
    private String description;
    private static long currentId = 1;

    public Problem() {
    }

    public Problem(String title, String description) {
        this.title = title;
        this.description = description;
        this.setId(currentId++);
    }

    public Problem(Long id, String title, String description) {
        this.setId(id);
        this.title = title;
        this.description = description;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
        currentId = id > currentId ? id + 1: currentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) &&
                Objects.equals(this.title, ((Problem)o).getTitle()) &&
                Objects.equals(this.description, ((Problem)o).getDescription());
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result +  title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }

    public String toCsv() {
        return getId() + "," + title + "," + description;
    }
}
