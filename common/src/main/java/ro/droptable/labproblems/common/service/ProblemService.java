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

    /**
     * Saves the given {@code Problem}.
     * @param title
     *            must not be null.
     * @param description
     *            must not be null.
     * @throws ValidatorException
     *            if the {@code Problem} being created is invalid.
     */
    void addProblem(String title, String description) throws ValidatorException;

    /**
     * Removes the {@code Problem} with the given id.
     *
     * @param id
     *            must not be null.
     *
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    void deleteProblem(Long id) throws ValidatorException;

    /**
     * Updates a {@code Problem}.
     * @param id
     *          must not be null.
     * @param title
     *          must not be null.
     * @param description
     *          must not be null.
     * @throws NoSuchElementException
     *          if the given {@code id} does not refer to an existing {@code Problem}.
     * @throws ValidatorException
     *          if the {@code Problem} being updates becomes invalid.
     */
    void updateProblem(Long id, String title, String description) throws NoSuchElementException, ValidatorException;

    /**
     * Finds if there is a {@code Problem} with the given {@code id} in {@code Repository}.
     * @param id
     *          must not be null.
     * @return
     *          an {@code Optional} with the found problem, if it exists;
     *          an empty {@code Optional}, otherwise.
     */
    Optional<Problem> findOneProblem(Long id);

    /**
     * Finds all {@code Problem}s from {@code Repository}.
     * @return
     *          a {@code Set} with all the {@code Problem}s;
     *          an empty {@code Set}, if there is no {@code Problem}.
     */
    Set<Problem> findAllProblems();

    /**
     * Returns all the Problems having a given title.
     *
     * @param title
     *            must not be null.
     * @return an {@code Iterable} - containing the list of the matching entities, empty string if there is no match
     * @throws IllegalArgumentException
     *             if the given {@code title} is null.
     */
    Set<Problem> filterProblemsByTitle(String title);
}
