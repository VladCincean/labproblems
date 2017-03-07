package ro.droptable.labproblems.domain.validators;

import ro.droptable.labproblems.domain.Problem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefana on 3/5/2017.
 */
public class ProblemValidator implements Validator<Problem> {
    private List<String> errors;

    public ProblemValidator() {
        this.errors = new ArrayList<>();
    }

    @Override
    public void validate(Problem entity) throws ValidatorException {

        if (entity.getDescription().equals("")) {
            errors.add("problem description is empty");
        }

        String error = errors.stream()
                .reduce("", (acc, it) -> acc + "; " + it);
//        if (error != null) {
        if (!errors.isEmpty()) {
            errors.clear();
            throw new ValidatorException(error);
        }
    }
}

