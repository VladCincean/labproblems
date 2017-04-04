package ro.droptable.labproblems.common.service;

import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Created by vlad on 28.03.2017.
 */
// TODO: update method specifications
public interface StudentService {
//    String SERVICE_HOST = "localhost";
//    int SERVICE_PORT = 1234;
//
//    String ADD_STUDENT = "addStudent";
//    String DELETE_STUDENT = "deleteStudent";
//    String UPDATE_STUDENT = "updateStudent";
//    String FIND_ONE_STUDENT = "findOneStudent";
//    String FIND_ALL_STUDENTS = "findAllStudents";
//    String FILTER_STUDENTS_BY_NAME = "filterStudentsByName";
//    String FILTER_LARGEST_GROUP = "filterLargestGroup";
//    String REPORT_STUDENT_AVERAGE = "reportStudentAverage";

    /**
     * Adds the {@code Student} given in the csv string
     *
     * @param string
     *            csv, must not be null
     * @return an {@code Future} - result of the computation
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    void addStudent(String serialNumber, String name, int group) throws ValidatorException;

    /**
     * Deletes the {@code Student} having the {@code id} the first member in the csv string
     *
     * @param string
     *            csv, must not be null
     * @return an {@code Future} - result of the computation
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    void deleteStudent(Long id);

    /**
     * Updates the {@code Student} having the {@code id} the first member in the csv string
     *
     * @param string
     *            csv, must not be null
     * @return an {@code Future} - result of the computation
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    void updateStudent(Long id, String serialNumber, String name, int group) throws ValidatorException;

    /**
     * Finds the {@code Student} having the {@code id} the first member in the csv string
     *
     * @param string
     *            csv, must not be null - id of the entity
     * @return an {@code Future} - a csv string containing the fields of the found entity, empty string if it does not exist
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    Optional<Student> findOneStudent(Long id);

    /**
     * Returns all the entities as a string of csv-s
     *
     * @param string
     *            must not be null - empty string is ignored
     * @return an {@code Future} - a csv string containing the list of the found entities, empty string if the repo is empty
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    Set<Student> findAllStudents();

    /**
     * Returns all the Students having a given name
     *
     * @param string
     *            must not be null - name
     * @return an {@code Future} - a csv string containing the list of the matching entities, empty string if there is no match
     * @throws IllegalArgumentException
     *             if the given string is null.
     */
    Set<Student> filterStudentsByName(String name);

//    /**
//     * Returns the group containing the largest number of Students
//     *
//     * @return an {@code Future} - result
//     */
//    Future<String> filterLargestGroup();
//
//    Future<String> reportStudentAverage();
}
