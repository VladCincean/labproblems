package ro.droptable.labproblems.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ro.droptable.labproblems.client.service.ProblemServiceClient;
import ro.droptable.labproblems.common.service.ProblemService;

/**
 * Created by vlad on 04.04.2017.
 */
@Configuration
public class ProblemServiceClientConfig {
    @Bean
    public RmiProxyFactoryBean problemService() {
        RmiProxyFactoryBean rmiProxy = new RmiProxyFactoryBean();
        rmiProxy.setServiceUrl("rmi://localhost:1099/ProblemService");
        rmiProxy.setServiceInterface(ProblemService.class);
        return rmiProxy;
    }

    @Bean
    public ProblemServiceClient problemServiceClient() {
        return new ProblemServiceClient();
    }
}