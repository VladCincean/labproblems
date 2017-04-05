package ro.droptable.labproblems.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ro.droptable.labproblems.client.service.StudentServiceClient;
import ro.droptable.labproblems.common.service.StudentService;

/**
 * Created by vlad on 04.04.2017.
 */
@Configuration
public class StudentServiceClientConfig {
    @Bean
    public RmiProxyFactoryBean studentService() {
        RmiProxyFactoryBean rmiProxy = new RmiProxyFactoryBean();
        rmiProxy.setServiceUrl("rmi://localhost:1099/StudentService");
        rmiProxy.setServiceInterface(StudentService.class);
        return rmiProxy;
    }

    @Bean
    public StudentServiceClient studentServiceClient() {
        return new StudentServiceClient();
    }
}
