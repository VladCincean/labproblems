package ro.droptable.labproblems.domain;

/**
 * Created by stefana on 3/5/2017.
 */
public class Problem extends BaseEntity<Long> {
    String title;
    private String description;
    private static long currentId = 1;

    public Problem() {
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
        currentId = id > currentId ? id : currentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Problem(String title, String description) {
        this.title = title;
        this.description = description;
        this.setId(currentId++);
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

        Problem problem = (Problem) o;

        if (!title.equals(problem.title)) return false;
        if (!description.equals(problem.description)) return false;
        return true;

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
}
