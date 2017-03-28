package ro.droptable.labproblems.common;

import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.Future;

/**
 * Created by vlad on 28.03.2017.
 */
public interface StudentService {
    String SERVICE_HOST = "localhost";
    int SERVICE_PORT = 1234;

    String ADD_STUDENT = "addStudent";
    String DELETE_STUDENT = "deleteStudent";
    String UPDATE_STUDENT = "updateStudent";
    String FIND_ONE_STUDENT = "findOneStudent";
    String FIND_ALL_STUDENTS = "findAllStudents";
    String FILTER_STUDENTS_BY_NAME = "filterStudentsByName";
    String FILTER_LARGEST_GROUP = "filterLargestGroup";

    Future<String> addStudent(String string) throws ValidatorException;

    Future<String> deleteStudent(String string);

    Future<String> updateStudent(String string);

    Future<String> findOneStudent(String string);

    Future<String> findAllStudents(String string);

    Future<String> filterStudentsByName(String string);

    Future<String> filterLargestGroup();
}
