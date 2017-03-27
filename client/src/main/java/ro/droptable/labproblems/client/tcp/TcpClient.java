package ro.droptable.labproblems.client.tcp;

import ro.droptable.labproblems.common.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by vlad on 27.03.2017.
 */
public class TcpClient {
    private String serviceHost;
    private int servicePort;

    public TcpClient(String serviceHost, int servicePort) {
        this.serviceHost = serviceHost;
        this.servicePort = servicePort;
    }

    public Message sendAndREceive(Message request) {
        OutputStream outputStream = null;
        System.out.println("Connecting to service...");
        try (Socket socket = new Socket(serviceHost, servicePort)) {
            outputStream = socket.getOutputStream();
            request.writeTo(outputStream);
            outputStream.flush();
            System.out.println("Request sent: " + request.toString());

            Message response = new Message();
            response.readFrom(socket.getInputStream());
            if (response.header().equalsIgnoreCase(Message.OK)) {
                System.out.println("Response OK: " + response.toString());
                return response;
            } else {
                System.out.println("Response ERROR: " + response.toString());
                // TODO!!: throw exception here (see example)
                throw new RuntimeException(response.body()); // TODO: delete this
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO!!: throw exception here (see example)
            throw new RuntimeException(e); // TODO: delete this
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
