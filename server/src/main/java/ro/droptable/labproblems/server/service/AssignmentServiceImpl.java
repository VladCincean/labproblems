package ro.droptable.labproblems.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.droptable.labproblems.common.service.AssignmentService;
import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;
import ro.droptable.labproblems.server.repository.AssignmentDbRepository;
import ro.droptable.labproblems.server.repository.Repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 3/28/2017.
 */

public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
    AssignmentDbRepository repository;

    @Override
    public void addAssignment(long studentId, long problemId) {
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

    @Override
    public void deleteAssignment(Long id) {
        repository.delete(id);
    }

    @Override
    public void updateAssignment(long id, long studentId, long problemId, int grade) throws NoSuchElementException, ValidatorException{
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

    @Override
    public Optional<Assignment> findOneAssignment(Long id) {
        return repository.findOne(id);
    }

    @Override
    public Set<Assignment> findAllAssignments() {
        Iterable<Assignment> entities = repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Assignment> filterAssignmentsByGrade(int g) {
        Iterable<Assignment> assignments = repository.findAll();
        Set<Assignment> filteredAssignments= new HashSet<>();
        assignments.forEach(filteredAssignments::add);
        filteredAssignments.removeIf(assignment -> assignment.getGrade() != g);
        return filteredAssignments;
    }
}
