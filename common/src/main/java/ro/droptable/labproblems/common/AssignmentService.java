package ro.droptable.labproblems.common;

import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.Future;

/**
 * Created by stefana on 3/28/2017.
 */
public interface AssignmentService {
    String SERVICE_HOST = "localhost";
    int SERVICE_PORT = 1234;

    String ADD_ASSIGNMENT = "addAssignment";
    String DELETE_ASSIGNMENT = "deleteAssignment";
    String UPDATE_ASSIGNMENT = "updateAssignment";
    String FIND_ONE_ASSIGNMENT = "findOneAssignment";
    String FIND_ALL_ASSIGNMENTS = "findAllAssignments";
    String FILTER_ASSIGNMENTS_BY_GRADE = "filterAssignmentsByGrade";

    Future<String> addAssignment(String string) throws ValidatorException;

    Future<String> deleteAssignment(String string);

    Future<String> updateAssignment(String string);

    Future<String> findOneAssignment(String string);

    Future<String> findAllAssignments(String string);

    Future<String> filterAssignmentsByGrade(String string);
}

