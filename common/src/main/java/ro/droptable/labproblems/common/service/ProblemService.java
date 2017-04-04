package ro.droptable.labproblems.common.service;

import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Created by stefana on 3/28/2017.
 */

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
     * Saves the given entity.
     *
     * @param title
     *            must not be null.
     * @param description
     *            must not be null.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void addProblem(String title, String description) throws ValidatorException;

    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     *
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    public void deleteProblem(Long id) throws ValidatorException;


    public void updateProblem(long id, String title, String description) throws NoSuchElementException, ValidatorException;

    public Optional<Problem> findOneProblem(Long id);

    public Set<Problem> findAllProblems();

    /**
     * Returns all the Problems having a given title
     *
     * @param string
     *            must not be null - title
     * @return an {@code Iterable} - containing the list of the matching entities, empty string if there is no match
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    public Set<Problem> filterProblemsByTitle(String string);
}
