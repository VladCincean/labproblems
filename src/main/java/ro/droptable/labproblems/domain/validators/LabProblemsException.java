package ro.droptable.labproblems.domain.validators;

/**
 * Created by vlad on 04.03.2017.
 *
 * Custom {@code Exception} class that matches all other exceptions defined in {@code labproblems}
 */
@Deprecated
public class LabProblemsException extends RuntimeException {

    public LabProblemsException(String message) {
        super(message);
    }

    public LabProblemsException(String message, Throwable cause) {
        super(message, cause);
    }

    public LabProblemsException(Throwable cause) {
        super(cause);
    }
}
