package ro.droptable.labproblems.common.service;

import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * Created by vlad on 28.03.2017.
 */
public interface StudentService {

    /**
     * Saves the given {@code Student}.
     *
     * @param serialNumber
     *            must not be null.
     * @param name
     *            must not be null.
     * @param group
     *            must not be null.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    void addStudent(String serialNumber, String name, int group) throws ValidatorException;

    /**
     * Deletes the {@code Student} with the given {@code id}.
     * @param id
     *          must not be null.
     */
    void deleteStudent(Long id);

    /**
     * Updates a {@code Student}'s state.
     * @param id
     *          must not be null.
     * @param serialNumber
     *          must not be null.
     * @param name
     *          must not be null.
     * @param group
     *          must not be null.
     * @throws NoSuchElementException
     *          if the given {@code id} does not refer to an existing {@code Student} in {@code Repository}.
     * @throws ValidatorException
     *          if the {@code Student} being updates becomes invalid.
     */
    void updateStudent(Long id, String serialNumber, String name, int group) throws ValidatorException;

    /**
     * Finds if there is a {@code Student} with the given {@code id} in {@code Repository}.
     * @param id
     *          must not be null.
     * @return
     *          an {@code Optional} with the found student, if it exists;
     *          an empty {@code Optional}, otherwise.
     */
    Optional<Student> findOneStudent(Long id);

    /**
     * Finds all {@code Student}s from {@code Repository}.
     * @return
     *          a {@code Set} with all the {@code Student}s;
     *          an empty {@code Set}, if there is no {@code Student}.
     */
    Set<Student> findAllStudents();

    /**
     * Finds all {@code Student}s from {@code Repository} having the given {@code name}.
     * @param name
     *          must not be null.
     * @return
     *          a {@code Set} with all the {@code Student}s having the given {@code name};
     *          an empty {@code Set}, if there is no {@code Student}.
     */
    Set<Student> filterStudentsByName(String name);

    /**
     * Returns the group containing the largest number of {@code Students}.
     *
     * @return the largest group of {@code Student}s.
     */
    int filterLargestGroup();

    Map<Student, Double> reportStudentAverage();
}
