package ro.droptable.labproblems.client.service;

import ro.droptable.labproblems.client.tcp.TcpClient;
import ro.droptable.labproblems.common.Message;
import ro.droptable.labproblems.common.StudentService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by vlad on 28.03.2017.
 */
public class StudentServiceClient implements StudentService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public StudentServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<String> addStudent(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(StudentService.ADD_STUDENT, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> deleteStudent(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(StudentService.DELETE_STUDENT, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> updateStudent(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(StudentService.UPDATE_STUDENT, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> findOneStudent(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(StudentService.FIND_ONE_STUDENT, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> findAllStudents(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(StudentService.FIND_ALL_STUDENTS, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> filterStudentsByName(String string) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(StudentService.FILTER_STUDENTS_BY_NAME, string);
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> filterLargestGroup() throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(StudentService.FILTER_LARGEST_GROUP, "");
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> reportStudentAverage() throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(StudentService.REPORT_STUDENT_AVERAGE, "");
            Message response = tcpClient.sendAndReceive(request);
            return response.body();
        }, executorService);
    }
}
