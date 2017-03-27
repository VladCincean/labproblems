package ro.droptable.labproblems.server.tcp;

import ro.droptable.labproblems.common.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

/**
 * Created by vlad on 27.03.2017.
 */
public class TcpServer {
    private ExecutorService executorService;
    private String serverHost;
    private int serverPort;

    private Map<String, UnaryOperator<Message>> methodHandlers = new HashMap<>();

    public TcpServer(ExecutorService executorService, String serverHost, int serverPort) {
        this.executorService = executorService;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void addHandler(String methodName, UnaryOperator<Message> methodHandler) {
        methodHandlers.put(methodName, methodHandler);
    }

    public void startServer() {
        System.out.println("Starting server...");
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server socket created.");
            while (true) {
                System.out.println("Waiting for clients...");
                Socket socket = serverSocket.accept();
                System.out.println("Client accepted.");
                executorService.submit(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO!!!: throw exception
            throw new RuntimeException(e); // TODO: remove this
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {
                Message request = new Message();
                byte[] bytes = new byte[1024];
                int len = inputStream.read(bytes);
                request.readFrom(new ByteArrayInputStream(bytes, 0, len));
                System.out.println("Received request: " + request);

                UnaryOperator<Message> methodHandler = methodHandlers.get(request.header());
                Message response = methodHandler.apply(request);
                System.out.println("Computed response: " + response);
                // TODO: handle exceptions

                response.writeTo(outputStream);
                outputStream.flush();
                System.out.println("Response written to outputStream");
            } catch (IOException e) {
                e.printStackTrace();
                // TODO!!: throw new custom exception
                throw new RuntimeException(e); // TODO: remove this
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
