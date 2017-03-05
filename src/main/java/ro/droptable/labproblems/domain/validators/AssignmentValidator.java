package ro.droptable.labproblems.domain.validators;

import ro.droptable.labproblems.domain.Assignment;

/**
 * Created by vlad on 05.03.2017.
 */
public class AssignmentValidator implements Validator<Assignment> {

    public AssignmentValidator() {
    }

    @Override
    public void validate(Assignment entity) throws ValidatorException {
        if (!(entity.getGrade() == 0.0 || (1.0 <= entity.getGrade() && entity.getGrade() <= 10.0)))
            throw new ValidatorException("assignment grade must be 0 or between 1 and 10");
    }
}
