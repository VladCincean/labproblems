package ro.droptable.labproblems.common.domain.validators;

/**
 * @author radu.
 */

public class ValidatorException extends LabProblemsException {
    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }
}
