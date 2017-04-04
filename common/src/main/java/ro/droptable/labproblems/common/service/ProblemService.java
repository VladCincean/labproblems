package ro.droptable.labproblems.common.service;

import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.Future;

/**
 * Created by stefana on 3/28/2017.
 */
@Deprecated
public interface ProblemService {
//    String SERVICE_HOST = "localhost";
//    int SERVICE_PORT = 1234;
//
//    String ADD_PROBLEM = "addProblem";
//    String DELETE_PROBLEM = "deleteProblem";
//    String UPDATE_PROBLEM = "updateProblem";
//    String FIND_ONE_PROBLEM = "findOneProblem";
//    String FIND_ALL_PROBLEMS = "findAllProblems";
//    String FILTER_PROBLEMS_BY_TITLE = "filterProblemsByTitle";

    /**
     * Adds the {@code Problem} given in the csv string
     *
     * @param string
     *            csv, must not be null
     * @return an {@code Future} - result of the computation
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    Future<String> addProblem(String string) throws ValidatorException;

    /**
     * Deletes the {@code Problem} having the {@code id} the first member in the csv string
     *
     * @param string
     *            csv, must not be null
     * @return an {@code Future} - result of the computation
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    Future<String> deleteProblem(String string);

    /**
     * Updates the {@code Problem} having the {@code id} the first member in the csv string
     *
     * @param string
     *            csv, must not be null
     * @return an {@code Future} - result of the computation
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    Future<String> updateProblem(String string);

    /**
     * Finds the {@code Problem} having the {@code id} the first member in the csv string
     *
     * @param string
     *            csv, must not be null - id of the entity
     * @return an {@code Future} - a csv string containing the fields of the found entity, empty string if it does not exist
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    Future<String> findOneProblem(String string);

    /**
     * Returns all the entities as a string of csv-s
     *
     * @param string
     *            must not be null - empty string is ignored
     * @return an {@code Future} - a csv string containing the list of the found entities, empty string if the repo is empty
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    Future<String> findAllProblems(String string);

    /**
     * Returns all the Problems having a given title
     *
     * @param string
     *            must not be null - title
     * @return an {@code Future} - a csv string containing the list of the matching entities, empty string if there is no match
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    Future<String> filterProblemsByTitle(String string);
}
