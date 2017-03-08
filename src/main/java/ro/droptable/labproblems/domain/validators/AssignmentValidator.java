package ro.droptable.labproblems.domain.validators;

import ro.droptable.labproblems.domain.Assignment;

import java.util.stream.Stream;

/**
 * Created by vlad on 05.03.2017.
 *
 * Implementation of {@code Validator} for validating {@code Assignment} instances
 */
public class AssignmentValidator implements Validator<Assignment> {

    public AssignmentValidator() {
    }

    @Override
    public void validate(Assignment entity) throws ValidatorException {
        Stream.of(entity.getGrade())
                .filter(g -> !(g == 0.0 || (1.0 <= g && g <= 10.0)))
                .forEach(e -> {
                    throw new ValidatorException("assignment grade must be 0 or between 1 and 10");
                });
    }
}
