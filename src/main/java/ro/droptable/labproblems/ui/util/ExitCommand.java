package ro.droptable.labproblems.ui.util;

/**
 * Created by vlad on 05.03.2017.
 */
@Deprecated
public class ExitCommand extends Command {

    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
