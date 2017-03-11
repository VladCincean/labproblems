package ro.droptable.labproblems;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.AssignmentValidator;
import ro.droptable.labproblems.domain.validators.ProblemValidator;
import ro.droptable.labproblems.domain.validators.StudentValidator;
import ro.droptable.labproblems.repository.InMemoryRepository;
import ro.droptable.labproblems.repository.Repository;
import ro.droptable.labproblems.service.*;
import ro.droptable.labproblems.ui.Console;

/**
 * Created by vlad on 04.03.2017.
 */
public class Main {
    public static void main(String[] args) {

        Repository<Long, Student> studentRepository = new InMemoryRepository<>(new StudentValidator());
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>(new ProblemValidator());
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(new AssignmentValidator());

        StudentService studentService = new StudentService(studentRepository);
        ProblemService problemService = new ProblemService(problemRepository);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);
        GeneralService generalService = new GeneralService(studentService, problemService, assignmentService);

        Console console = new Console(generalService);
        console.run();
    }
}
