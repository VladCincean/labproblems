package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.repository.Repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by stefana on 3/5/2017.
 */
public class AssignmentService extends Service<Assignment> {
    public AssignmentService(
            Repository<Long, Assignment > repository) {
        this.repository = repository;
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

            repository.save(assignmentInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException |
                InstantiationException |
                NoSuchFieldException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    public Optional<Assignment> getByAttributes(long studentId, long problemId)
    {
        Assignment a = new Assignment(studentId, problemId);
        return super.getByAttributes(a);
    }

    public Set<Assignment> filterByStudent(long studentId)
    {
        return getAll().stream().filter(assignment -> assignment.getStudentId() == studentId).collect(Collectors.toSet());
    }

    public Set<Assignment> filterByProblem(long problemId)
    {
        return getAll().stream().filter(assignment -> assignment.getProblemId() == problemId).collect(Collectors.toSet());
    }

    void update(long id, long studentId, long problemId, int grade) throws NoSuchElementException, ValidatorException{
        Assignment oldAssignment = this.repository.findOne(id).get();
        Class assignmentClass;

        try {
            assignmentClass = Class.forName("ro.droptable.labproblems.domain.Assignment");
            Assignment assignmentInstance = (Assignment) assignmentClass.newInstance();

            Field idField = assignmentClass.getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(assignmentInstance, id);
            idField.setAccessible(false);

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
            gradeField.set(assignmentInstance, grade);
            gradeField.setAccessible(false);

            this.repository.update(assignmentInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException |
                InstantiationException |
                NoSuchFieldException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    public Set<Assignment> filterAssignmentsByGrade(int g) {
        Iterable<Assignment> assignments = repository.findAll();
        Set<Assignment> filteredAssignments= new HashSet<>();
        assignments.forEach(filteredAssignments::add);
        filteredAssignments.removeIf(assignment -> assignment.getGrade() != g);
        return filteredAssignments;
    }
}
