package ro.droptable.labproblems.common.service;

import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.Future;

/**
 * Created by stefana on 3/28/2017.
 */
@Deprecated
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
     * Adds the {@code Assignment} given in the csv string
     *
     * @param string
     *            csv, must not be null
     * @return an {@code Future} - result of the computation
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    Future<String> addAssignment(String string) throws ValidatorException;

    /**
     * Deletes the {@code Assignment} having the {@code id} the first member in the csv string
     *
     * @param string
     *            csv, must not be null
     * @return an {@code Future} - result of the computation
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    Future<String> deleteAssignment(String string);

    /**
     * Updates the {@code Assignment} having the {@code id} the first member in the csv string
     *
     * @param string
     *            csv, must not be null
     * @return an {@code Future} - result of the computation
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    Future<String> updateAssignment(String string);

    /**
     * Finds the {@code Assignment} having the {@code id} the first member in the csv string
     *
     * @param string
     *            csv, must not be null - id of the entity
     * @return an {@code Future} - a csv string containing the fields of the found entity, empty string if it does not exist
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    Future<String> findOneAssignment(String string);

    /**
     * Returns all the entities as a string of csv-s
     *
     * @param string
     *            must not be null - empty string is ignored
     * @return an {@code Future} - a csv string containing the list of the found entities, empty string if the repo is empty
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    Future<String> findAllAssignments(String string);

    /**
     * Returns all the Assignments having a given grade
     *
     * @param string
     *            must not be null - grade
     * @return an {@code Future} - a csv string containing the list of the matching entities, empty string if there is no match
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    Future<String> filterAssignmentsByGrade(String string);
}

