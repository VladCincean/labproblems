package ro.droptable.labproblems.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.droptable.labproblems.client.service.AssignmentServiceClient;
import ro.droptable.labproblems.client.service.ProblemServiceClient;
import ro.droptable.labproblems.client.service.StudentServiceClient;
//import ro.droptable.labproblems.client.tcp.TcpClient;
import ro.droptable.labproblems.client.ui.ClientUi;
import ro.droptable.labproblems.common.service.StudentService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vlad on 27.03.2017.
 */
public class ClientApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                "ro.droptable.labproblems.client.config"
        );

        StudentServiceClient studentServiceClient = context.getBean(StudentServiceClient.class);
        ProblemServiceClient problemServiceClient = context.getBean(ProblemServiceClient.class);
        AssignmentServiceClient assignmentServiceClient = context.getBean(AssignmentServiceClient.class);

        ClientUi ui = new ClientUi(
                studentServiceClient,
                problemServiceClient,
                assignmentServiceClient
        );

        ui.run();
    }
}
