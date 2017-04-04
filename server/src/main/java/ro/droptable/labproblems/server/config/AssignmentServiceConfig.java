package ro.droptable.labproblems.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import ro.droptable.labproblems.common.service.AssignmentService;
import ro.droptable.labproblems.server.repository.AssignmentDbRepository;
import ro.droptable.labproblems.server.service.AssignmentServiceImpl;

/**
 * Created by stefana on 4/4/2017.
 */
@Configuration
public class AssignmentServiceConfig {

    @Bean
    public AssignmentService assignmentService() {
        return new AssignmentServiceImpl();
    }

    @Bean
    public AssignmentDbRepository assignmentDbRepository() {
        return new AssignmentDbRepository();
    }

    @Bean
    public RmiServiceExporter rmiServiceAssignment() {
        RmiServiceExporter rmiService = new RmiServiceExporter();
        rmiService.setServiceName("AssignmentService");
        rmiService.setServiceInterface(AssignmentService.class);
        rmiService.setService(assignmentService());
        return rmiService;
    }
}
