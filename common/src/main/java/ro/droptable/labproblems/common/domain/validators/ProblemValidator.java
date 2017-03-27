package ro.droptable.labproblems.common.domain.validators;

import ro.droptable.labproblems.common.domain.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by stefana on 3/5/2017.
 *
 * Implementation of {@code Validator} for validating {@code Problem} instances
 */
public class ProblemValidator implements Validator<Problem> {
    private List<String> errors;

    public ProblemValidator() {
        this.errors = new ArrayList<>();
    }

    @Override
    public void validate(Problem entity) throws ValidatorException {
        Stream.of(entity.getTitle())
                .filter(t -> t.equals(""))
                .forEach(e -> errors.add("problem title is empty"));
        Stream.of(entity.getDescription())
                .filter(d -> d.equals(""))
                .forEach(e -> errors.add("problem description is empty"));

        errors.stream()
                .reduce((acc, it) -> acc + "; " + it)
                .ifPresent(opt -> {
                    errors.clear();
                    throw new ValidatorException(opt);
                });
    }
}
