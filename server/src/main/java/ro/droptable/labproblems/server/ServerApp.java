package ro.droptable.labproblems.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by vlad on 27.03.2017.
 */
public class ServerApp {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext("ro.droptable.labproblems.server.config");
    }
}
