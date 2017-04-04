package ro.droptable.labproblems.client.service;

import ro.droptable.labproblems.client.tcp.TcpClient;
import ro.droptable.labproblems.common.Message;
import ro.droptable.labproblems.common.service.ProblemService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by stefana on 3/28/2017.
 */
@Deprecated
public class ProblemServiceClient implements ProblemService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public ProblemServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<String> addProblem(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(ProblemService.ADD_PROBLEM, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> deleteProblem(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(ProblemService.DELETE_PROBLEM, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> updateProblem(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(ProblemService.UPDATE_PROBLEM, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> findOneProblem(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(ProblemService.FIND_ONE_PROBLEM, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> findAllProblems(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(ProblemService.FIND_ALL_PROBLEMS, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> filterProblemsByTitle(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(ProblemService.FILTER_PROBLEMS_BY_TITLE, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }
}
