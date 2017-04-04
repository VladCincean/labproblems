package ro.droptable.labproblems.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.service.StudentService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.Optional;
import java.util.Set;

/**
 * Created by vlad on 28.03.2017.
 */
public class StudentServiceClient implements StudentService {
//    private ExecutorService executorService;

    @Autowired
    private StudentService studentService;


//    public StudentServiceClient(ExecutorService executorService, TcpClient tcpClient) {
//        this.executorService = executorService;
//        this.tcpClient = tcpClient;
//    }

    @Override
    public void addStudent(String serialNumber, String name, int group) throws ValidatorException {
        studentService.addStudent(serialNumber, name, group);
    }

    @Override
    public void deleteStudent(Long id) {
        studentService.deleteStudent(id);
    }

    @Override
    public void updateStudent(Long id, String serialNumber, String name, int group) throws ValidatorException {
        studentService.updateStudent(id, serialNumber, name, group);
    }

    @Override
    public Optional<Student> findOneStudent(Long id) {
        return studentService.findOneStudent(id);
    }

    @Override
    public Set<Student> findAllStudents() {
        return studentService.findAllStudents();
    }

    @Override
    public Set<Student> filterStudentsByName(String name) {
        return studentService.filterStudentsByName(name);
    }

//    @Override
//    public CompletableFuture<String> filterLargestGroup() throws ValidatorException {
//        return CompletableFuture.supplyAsync(() -> {
//            Message request = new Message(StudentService.FILTER_LARGEST_GROUP, "");
//            Message response = tcpClient.sendAndReceive(request);
//            return response.body();
//        }, executorService);
//    }
//
//    @Override
//    public CompletableFuture<String> reportStudentAverage() throws ValidatorException {
//        return CompletableFuture.supplyAsync(() -> {
//            Message request = new Message(StudentService.REPORT_STUDENT_AVERAGE, "");
//            Message response = tcpClient.sendAndReceive(request);
//            return response.body();
//        }, executorService);
//    }
}
