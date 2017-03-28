package ro.droptable.labproblems.client;

import ro.droptable.labproblems.client.service.StudentServiceClient;
import ro.droptable.labproblems.client.tcp.TcpClient;
import ro.droptable.labproblems.client.ui.ClientUi;
import ro.droptable.labproblems.common.StudentService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vlad on 27.03.2017.
 */
public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        TcpClient tcpClient = new TcpClient(StudentService.SERVICE_HOST, StudentService.SERVICE_PORT);
        StudentService studentService = new StudentServiceClient(executorService, tcpClient);
        ClientUi clientUi = new ClientUi(studentService);
        clientUi.run();
        executorService.shutdownNow();
    }
}
