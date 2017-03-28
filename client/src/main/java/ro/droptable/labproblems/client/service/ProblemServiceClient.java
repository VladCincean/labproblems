package ro.droptable.labproblems.client.service;

import ro.droptable.labproblems.client.tcp.TcpClient;
import ro.droptable.labproblems.common.Message;
import ro.droptable.labproblems.common.ProblemService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by stefana on 3/28/2017.
 */
public class ProblemServiceClient implements ProblemService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public ProblemServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> addProblem(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(ProblemService.ADD_PROBLEM, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> deleteProblem(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(ProblemService.DELETE_PROBLEM, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> updateProblem(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(ProblemService.UPDATE_PROBLEM, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> findOneProblem(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(ProblemService.FIND_ONE_PROBLEM, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> findAllProblems(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(ProblemService.FIND_ALL_PROBLEMS, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> filterProblemsByTitle(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(ProblemService.FILTER_PROBLEMS_BY_TITLE, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }
}
