package ro.droptable.labproblems.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import ro.droptable.labproblems.common.service.ProblemService;
import ro.droptable.labproblems.server.repository.ProblemDbRepository;
import ro.droptable.labproblems.server.service.ProblemServiceImpl;

/**
 * Created by stefana on 4/4/2017.
 */
@Configuration
public class ProblemServiceConfig {
    @Bean
    public ProblemService problemService() {
        return new ProblemServiceImpl();
    }

    @Bean
    public ProblemDbRepository problemDbRepository() {
        return new ProblemDbRepository();
    }

    @Bean
    public RmiServiceExporter rmiServiceProblem() {
        RmiServiceExporter rmiService = new RmiServiceExporter();
        rmiService.setServiceName("ProblemService");
        rmiService.setServiceInterface(ProblemService.class);
        rmiService.setService(problemService());
        return rmiService;
    }
}
