package ro.droptable.labproblems.domain.validators;

import ro.droptable.labproblems.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 04.03.2017.
 */
public class StudentValidator implements Validator<Student> {
    private List<String> errors;

    public StudentValidator() {
        this.errors = new ArrayList<>();
    }

    @Override
    public void validate(Student entity) throws ValidatorException {

        if (entity.getName().equals("")) {
            errors.add("student name is empty");
        }
        if (entity.getSerialNumber().equals("")) {
            errors.add("student serial number is empty");
        }
        int group = entity.getGroup();
        if (group % 10 == 0 || (group / 10) % 10 == 0 || group >= 1000 || group <= 110) {
            errors.add("student group is invalid");
        }

        String error = errors.stream()
                .reduce("\n", (acc, it) -> acc + it + "; ");
//        if (error != null) {
        if (!errors.isEmpty()) {
            errors.clear();
            throw new ValidatorException(error);
        }
    }
}
