package ro.droptable.labproblems.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.droptable.labproblems.common.service.AssignmentService;
//import ro.droptable.labproblems.common.Message;
import ro.droptable.labproblems.common.service.ProblemService;
import ro.droptable.labproblems.common.service.StudentService;
import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.AssignmentValidator;
import ro.droptable.labproblems.common.domain.validators.ProblemValidator;
import ro.droptable.labproblems.common.domain.validators.StudentValidator;
import ro.droptable.labproblems.server.repository.AssignmentDbRepository;
import ro.droptable.labproblems.server.repository.ProblemDbRepository;
import ro.droptable.labproblems.server.repository.Repository;
import ro.droptable.labproblems.server.repository.StudentDbRepository;
import ro.droptable.labproblems.server.service.AssignmentServiceImpl;
import ro.droptable.labproblems.server.service.ProblemServiceImpl;
import ro.droptable.labproblems.server.service.StudentServiceImpl;
//import ro.droptable.labproblems.server.tcp.TcpServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by vlad on 27.03.2017.
 */
public class ServerApp {
    public static void main(String[] args) {

        new AnnotationConfigApplicationContext("ro.droptable.labproblems.server.config");

//        ExecutorService executorService = Executors.newFixedThreadPool(
//                Runtime.getRuntime().availableProcessors()
//        );
//
//        String url = "jdbc:postgresql://localhost:5432/labproblems";
//        String username = "postgres";
//        String password = "admin";
//
//        Repository<Long, Student> studentRepository = new StudentDbRepository(
//                new StudentValidator(),
//                url,
//                username,
//                password
//        );
//
//        StudentService studentService = new StudentServiceImpl(executorService, studentRepository);
//
//        Repository<Long, Problem> problemRepository = new ProblemDbRepository(
//                new ProblemValidator(),
//                url,
//                username,
//                password
//        );
//
//        ProblemService problemService = new ProblemServiceImpl(executorService, problemRepository);
//
//        Repository<Long, Assignment> assignmentRepository = new AssignmentDbRepository(
//                new AssignmentValidator(),
//                url,
//                username,
//                password
//        );
//
//        AssignmentService assignmentService = new AssignmentServiceImpl(executorService, assignmentRepository);
//
//        TcpServer tcpServer = new TcpServer(executorService, StudentService.SERVICE_HOST, StudentService.SERVICE_PORT);
//
//        addStudentTcpServerHandlers(studentService, tcpServer);
//        addProblemTcpServerHandlers(problemService, tcpServer);
//        addAssignmentTcpServerHandlers(assignmentService, tcpServer);
//
//        tcpServer.startServer();
//    }
//
//    private static void addStudentTcpServerHandlers(StudentService studentService, TcpServer tcpServer) {
//        tcpServer.addHandler(StudentService.ADD_STUDENT, (request) -> {
//            Future<String> result = studentService.addStudent(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(StudentService.DELETE_STUDENT, (request) -> {
//            Future<String> result = studentService.deleteStudent(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(StudentService.UPDATE_STUDENT, (request) -> {
//            Future<String> result = studentService.updateStudent(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(StudentService.FIND_ONE_STUDENT, (request) -> {
//            Future<String> result = studentService.findOneStudent(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(StudentService.FIND_ALL_STUDENTS, (request) -> {
//            Future<String> result = studentService.findAllStudents(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(StudentService.FILTER_STUDENTS_BY_NAME, (request) -> {
//            Future<String> result = studentService.filterStudentsByName(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(StudentService.FILTER_LARGEST_GROUP, (request) -> {
//            Future<String> result = studentService.filterLargestGroup();
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(StudentService.REPORT_STUDENT_AVERAGE, (request) -> {
//            Future<String> result = studentService.reportStudentAverage();
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//    }
//
//    private static void addProblemTcpServerHandlers(ProblemService problemService, TcpServer tcpServer) {
//        tcpServer.addHandler(ProblemService.ADD_PROBLEM, (request) -> {
//            Future<String> result = problemService.addProblem(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(ProblemService.DELETE_PROBLEM, (request) -> {
//            Future<String> result = problemService.deleteProblem(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(ProblemService.UPDATE_PROBLEM, (request) -> {
//            Future<String> result = problemService.updateProblem(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(ProblemService.FIND_ONE_PROBLEM, (request) -> {
//            Future<String> result = problemService.findOneProblem(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(ProblemService.FIND_ALL_PROBLEMS, (request) -> {
//            Future<String> result = problemService.findAllProblems(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(ProblemService.FILTER_PROBLEMS_BY_TITLE, (request) -> {
//            Future<String> result = problemService.filterProblemsByTitle(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//    }
//
//    private static void addAssignmentTcpServerHandlers(AssignmentService assignmentService, TcpServer tcpServer) {
//        tcpServer.addHandler(AssignmentService.ADD_ASSIGNMENT, (request) -> {
//            Future<String> result = assignmentService.addAssignment(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(AssignmentService.DELETE_ASSIGNMENT, (request) -> {
//            Future<String> result = assignmentService.deleteAssignment(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(AssignmentService.UPDATE_ASSIGNMENT, (request) -> {
//            Future<String> result = assignmentService.updateAssignment(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(AssignmentService.FIND_ONE_ASSIGNMENT, (request) -> {
//            Future<String> result = assignmentService.findOneAssignment(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(AssignmentService.FIND_ALL_ASSIGNMENTS, (request) -> {
//            Future<String> result = assignmentService.findAllAssignments(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
//
//        tcpServer.addHandler(AssignmentService.FILTER_ASSIGNMENTS_BY_GRADE, (request) -> {
//            Future<String> result = assignmentService.filterAssignmentsByGrade(request.body());
//            try {
//                return new Message(Message.OK, result.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new Message(Message.ERROR, "");
//        });
    }
}
