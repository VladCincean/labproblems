package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.repository.Repository;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by stefana on 3/5/2017.
 */
public class AssignmentService extends Service<Assignment> {
    private StudentService studentService;
    private ProblemService problemService;
    public AssignmentService(
            Repository<Long, Assignment > repository,
            StudentService studentService,
            ProblemService problemService) {
        this.repository = repository;
        this.studentService = studentService;
        this.problemService = problemService;
    }

    /**
     * Saves the given entity.
     *
     * @param studentId
     *            must not be null.
     *@param problemId
     *            must not be null.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void add(long studentId, long problemId) {
        Class assignmentClass;

        try {
            assignmentClass = Class.forName("ro.droptable.labproblems.domain.Assignment");
            Assignment assignmentInstance = (Assignment) assignmentClass.newInstance();

            Field idField = assignmentClass.getSuperclass().getDeclaredField("id");
            Field currentIdField = assignmentClass.getDeclaredField("currentId");
            idField.setAccessible(true);
            currentIdField.setAccessible(true);
            idField.set(assignmentInstance, currentIdField.getLong(assignmentInstance));
            currentIdField.set(assignmentInstance, currentIdField.getLong(assignmentInstance) + 1);
            idField.setAccessible(false);
            currentIdField.setAccessible(false);

            Field studentIdField = assignmentClass.getDeclaredField("studentId");
            studentIdField.setAccessible(true);
            studentIdField.set(assignmentInstance, studentId);
            studentIdField.setAccessible(false);

            Field problemIdField = assignmentClass.getDeclaredField("problemId");
            problemIdField.setAccessible(true);
            problemIdField.set(assignmentInstance, problemId);
            problemIdField.setAccessible(false);

            Field gradeField = assignmentClass.getDeclaredField("grade");
            gradeField.setAccessible(true);
            gradeField.set(assignmentInstance, 0);
            gradeField.setAccessible(false);

            Student student = studentService.findOne(studentId).get();
            try {
                Objects.requireNonNull(student);
            } catch (NullPointerException e) {
                throw new ValidatorException("student not found");
            }

            Problem problem = problemService.findOne(problemId).get();
            try {
                Objects.requireNonNull(problem);
            } catch (NullPointerException e) {
                throw new ValidatorException("problem not found");
            }
            repository.save(assignmentInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException |
                InstantiationException |
                NoSuchFieldException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }



}
