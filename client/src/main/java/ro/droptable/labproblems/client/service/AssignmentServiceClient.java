package ro.droptable.labproblems.client.service;

import ro.droptable.labproblems.client.tcp.TcpClient;
import ro.droptable.labproblems.common.AssignmentService;
import ro.droptable.labproblems.common.Message;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

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
    public CompletableFuture<String> addAssignment(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(AssignmentService.ADD_ASSIGNMENT, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> deleteAssignment(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(AssignmentService.DELETE_ASSIGNMENT, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> updateAssignment(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(AssignmentService.UPDATE_ASSIGNMENT, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> findOneAssignment(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(AssignmentService.FIND_ONE_ASSIGNMENT, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> findAllAssignments(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(AssignmentService.FIND_ALL_ASSIGNMENTS, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> filterAssignmentsByGrade(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(AssignmentService.FILTER_ASSIGNMENTS_BY_GRADE, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }
}
