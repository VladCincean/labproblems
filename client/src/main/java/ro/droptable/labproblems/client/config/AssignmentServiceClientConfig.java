package ro.droptable.labproblems.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ro.droptable.labproblems.client.service.AssignmentServiceClient;
import ro.droptable.labproblems.common.service.AssignmentService;

/**
 * Created by vlad on 04.04.2017.
 */
@Configuration
public class AssignmentServiceClientConfig {
    @Bean
    public RmiProxyFactoryBean problemService() {
        RmiProxyFactoryBean rmiProxy = new RmiProxyFactoryBean();
        rmiProxy.setServiceUrl("rmi://localhost:1099/ProblemService");
        rmiProxy.setServiceInterface(AssignmentService.class);
        return rmiProxy;
    }

    @Bean
    public AssignmentServiceClient problemServiceClient() {
        return new AssignmentServiceClient();
    }
}
