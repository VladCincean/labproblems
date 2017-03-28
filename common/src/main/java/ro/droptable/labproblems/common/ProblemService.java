package ro.droptable.labproblems.common;

import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.Future;

/**
 * Created by stefana on 3/28/2017.
 */
public interface ProblemService {
    String SERVICE_HOST = "localhost";
    int SERVICE_PORT = 1234;

    String ADD_PROBLEM = "addProblem";
    String DELETE_PROBLEM = "deleteProblem";
    String UPDATE_PROBLEM = "updateProblem";
    String FIND_ONE_PROBLEM = "findOneProblem";
    String FIND_ALL_PROBLEMS = "findAllProblems";
    String FILTER_PROBLEMS_BY_TITLE = "filterProblemsByTitle";

    Future<String> addProblem(String string) throws ValidatorException;

    Future<String> deleteProblem(String string);

    Future<String> updateProblem(String string);

    Future<String> findOneProblem(String string);

    Future<String> findAllProblems(String string);

    Future<String> filterProblemsByTitle(String string);
}
