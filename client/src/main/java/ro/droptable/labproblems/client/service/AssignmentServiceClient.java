package ro.droptable.labproblems.client.service;

import ro.droptable.labproblems.client.tcp.TcpClient;
import ro.droptable.labproblems.common.AssignmentService;
import ro.droptable.labproblems.common.Message;
import ro.droptable.labproblems.common.AssignmentService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by stefana on 3/28/2017.
 */
public class AssignmentServiceClient implements AssignmentService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public AssignmentServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> addAssignment(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(AssignmentService.ADD_ASSIGNMENT, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> deleteAssignment(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(AssignmentService.DELETE_ASSIGNMENT, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> updateAssignment(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(AssignmentService.UPDATE_ASSIGNMENT, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> findOneAssignment(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(AssignmentService.FIND_ONE_ASSIGNMENT, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> findAllAssignments(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(AssignmentService.FIND_ALL_ASSIGNMENTS, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> filterAssignmentsByGrade(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(AssignmentService.FILTER_ASSIGNMENTS_BY_GRADE, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }
}
