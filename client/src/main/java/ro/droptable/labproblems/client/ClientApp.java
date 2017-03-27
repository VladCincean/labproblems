package ro.droptable.labproblems.client;

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
        // TODO: the rest
        executorService.shutdownNow();
    }
}
