package ro.droptable.labproblems.server;

import ro.droptable.labproblems.common.Message;
import ro.droptable.labproblems.common.StudentService;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.StudentValidator;
import ro.droptable.labproblems.server.repository.Repository;
import ro.droptable.labproblems.server.repository.StudentDbRepository;
import ro.droptable.labproblems.server.service.StudentServiceImpl;
import ro.droptable.labproblems.server.tcp.TcpServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by vlad on 27.03.2017.
 */
public class ServerApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        String url = "jdbc:postgresql://localhost:5432/labproblems";
        String username = "postgres";
        String password = "admin";

        Repository<Long, Student> studentRepository = new StudentDbRepository(
                new StudentValidator(),
                url,
                username,
                password
        );

        StudentService studentService = new StudentServiceImpl(executorService, studentRepository);
        TcpServer tcpServer = new TcpServer(executorService, StudentService.SERVICE_HOST, StudentService.SERVICE_PORT);

        addTcpServerHandlers(studentService, tcpServer);

        tcpServer.startServer();
    }

    private static void addTcpServerHandlers(StudentService studentService, TcpServer tcpServer) {
        tcpServer.addHandler(StudentService.ADD_STUDENT, (request) -> {
            Future<String> result = studentService.addStudent(request.body());
            try {
                return new Message(Message.OK, result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return new Message(Message.ERROR, "");
        });

        tcpServer.addHandler(StudentService.DELETE_STUDENT, (request) -> {
            Future<String> result = studentService.deleteStudent(request.body());
            try {
                return new Message(Message.OK, result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return new Message(Message.ERROR, "");
        });

        tcpServer.addHandler(StudentService.UPDATE_STUDENT, (request) -> {
            Future<String> result = studentService.updateStudent(request.body());
            try {
                return new Message(Message.OK, result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return new Message(Message.ERROR, "");
        });

        tcpServer.addHandler(StudentService.FIND_ONE_STUDENT, (request) -> {
            Future<String> result = studentService.findOneStudent(request.body());
            try {
                return new Message(Message.OK, result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return new Message(Message.ERROR, "");
        });

        tcpServer.addHandler(StudentService.FIND_ALL_STUDENTS, (request) -> {
            Future<String> result = studentService.findAllStudents(request.body());
            try {
                return new Message(Message.OK, result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return new Message(Message.ERROR, "");
        });

        tcpServer.addHandler(StudentService.FILTER_STUDENTS_BY_NAME, (request) -> {
            Future<String> result = studentService.filterStudentsByName(request.body());
            try {
                return new Message(Message.OK, result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return new Message(Message.ERROR, "");
        });
    }

}
