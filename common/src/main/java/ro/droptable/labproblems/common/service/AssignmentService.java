package ro.droptable.labproblems.common.service;

import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Created by stefana on 3/28/2017.
 */

public interface AssignmentService {
//    String SERVICE_HOST = "localhost";
//    int SERVICE_PORT = 1234;
//
//    String ADD_ASSIGNMENT = "addAssignment";
//    String DELETE_ASSIGNMENT = "deleteAssignment";
//    String UPDATE_ASSIGNMENT = "updateAssignment";
//    String FIND_ONE_ASSIGNMENT = "findOneAssignment";
//    String FIND_ALL_ASSIGNMENTS = "findAllAssignments";
//    String FILTER_ASSIGNMENTS_BY_GRADE = "filterAssignmentsByGrade";


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
    public void addAssignment(long studentId, long problemId);


    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     *
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    public void deleteAssignment(Long id);

    void updateAssignment(long id, long studentId, long problemId, int grade) throws NoSuchElementException, ValidatorException;

    Optional<Assignment> findOneAssignment(Long id);


    Set<Assignment> findAllAssignments();

    Set<Assignment> filterAssignmentsByGrade(int g);

}

