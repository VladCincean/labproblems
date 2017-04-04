package ro.droptable.labproblems.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.service.StudentService;
import ro.droptable.labproblems.server.repository.Repository;
import ro.droptable.labproblems.server.repository.StudentDbRepository;
import ro.droptable.labproblems.server.service.StudentServiceImpl;

/**
 * Created by vlad on 04.04.2017.
 */
@Configuration
public class StudentServiceConfig {
    @Bean
    public StudentService studentService() {
        return new StudentServiceImpl();
    }

    @Bean
    public Repository<Long, Student> studentRepository() {
        return new StudentDbRepository();
    }

    @Bean
    public RmiServiceExporter rmiService() {
        RmiServiceExporter rmiService = new RmiServiceExporter();
        rmiService.setServiceName("StudentService");
        rmiService.setServiceInterface(StudentService.class);
        rmiService.setService(studentService());
        return rmiService;
    }
}
