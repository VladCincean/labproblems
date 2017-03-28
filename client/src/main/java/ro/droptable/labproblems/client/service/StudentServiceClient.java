package ro.droptable.labproblems.client.service;

import ro.droptable.labproblems.client.tcp.TcpClient;
import ro.droptable.labproblems.common.Message;
import ro.droptable.labproblems.common.StudentService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
    public Future<String> addStudent(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(StudentService.ADD_STUDENT, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> deleteStudent(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(StudentService.DELETE_STUDENT, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> updateStudent(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(StudentService.UPDATE_STUDENT, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> findOneStudent(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(StudentService.FIND_ONE_STUDENT, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> findAllStudents(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(StudentService.FIND_ALL_STUDENTS, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> filterStudentsByName(String string) throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(StudentService.FILTER_STUDENTS_BY_NAME, string);
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }

    @Override
    public Future<String> filterLargestGroup() throws ValidatorException {
        return executorService.submit(() -> {
            Message request = new Message(StudentService.FILTER_LARGEST_GROUP, "");
            Message response = tcpClient.sendAndREceive(request);
            return response.body();
        });
    }
}
