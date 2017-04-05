package ro.droptable.labproblems.common.service;

import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * Created by stefana on 3/28/2017.
 */

public interface AssignmentService {

    /**
     * Saves the given {@code Assignment}.
     *
     * @param studentId
     *            must not be null.
     * @param problemId
     *            must not be null.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    void addAssignment(long studentId, long problemId);


    /**
     * Removes the {@code Assignment} with the given id.
     *
     * @param id
     *            must not be null.
     *
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    void deleteAssignment(Long id);

    /**
     * Updates a {@code Problem}.
     * @param id
     *          must not be null.
     * @param studentId
     *          must be valid.
     * @param problemId
     *          must be valid.
     * @param grade
     *          must be between 1.0 and 10.0 or 0.0.
     * @throws NoSuchElementException
     *          if the given {@code id} does not refer to an existing {@code Assignment}.
     * @throws ValidatorException
     *          if the {@code Problem} being updates becomes invalid.
     */
    void updateAssignment(Long id, long studentId, long problemId, int grade) throws NoSuchElementException, ValidatorException;

    /**
     * Finds if there is a {@code Assignment} with the given {@code id} in {@code Repository}.
     * @param id
     *          must not be null.
     * @return
     *          an {@code Optional} with the found assignment, if it exists;
     *          an empty {@code Optional}, otherwise.
     */
    Optional<Assignment> findOneAssignment(Long id);

    /**
     * Finds all {@code Assignment}s from {@code Repository}.
     * @return
     *          a {@code Set} with all the {@code Assignment}s;
     *          an empty {@code Set}, if there is no {@code Assignment}.
     */
    Set<Assignment> findAllAssignments();

    /**
     * Finds all {@code Assignment}s from {@code Repository} having the given {@code grade}.
     * @param grade
     *          must be between 1.0 and 10.0 or 0.0.
     * @return
     *          a {@code Set} with all the {@code Assignment}s having the given {@code grade};
     *          an empty {@code Set}, if there is no {@code Assignment}.
     */
    Set<Assignment> filterAssignmentsByGrade(int grade);

}

