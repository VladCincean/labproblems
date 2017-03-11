package ro.droptable.labproblems.domain.validators;

import ro.droptable.labproblems.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by vlad on 04.03.2017.
 *
 * Implementation of {@code Validator} for validating {@code Student} instances
 */
public class StudentValidator implements Validator<Student> {
    private List<String> errors;

    public StudentValidator() {
        this.errors = new ArrayList<>();
    }

    @Override
    public void validate(Student entity) throws ValidatorException {
        Stream.of(entity.getName())
                .filter(n -> n.equals(""))
                .forEach(e -> errors.add("student name is empty"));
        Stream.of(entity.getSerialNumber())
                .filter(sn -> sn.equals(""))
                .forEach(e -> errors.add("student serial number is empty"));
        Stream.of(entity.getGroup())
                .filter(g -> (g % 10 == 0) || (g / 10) % 10 == 0 || g >= 1000 || g <= 110)
                .forEach(e -> errors.add("student group is invalid"));

        errors.stream()
                .reduce((acc, it) -> acc + "; " + it)
                .ifPresent(opt -> {
                    errors.clear();
                    throw new ValidatorException(opt);
                });
    }
}
