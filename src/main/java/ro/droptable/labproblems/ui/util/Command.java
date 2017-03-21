package ro.droptable.labproblems.ui.util;

/**
 * Created by vlad on 05.03.2017.
 */
@Deprecated
public abstract class Command {
    private String key;
    private String description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public abstract void execute();

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
