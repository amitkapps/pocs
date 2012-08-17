package arch.poc.javanet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: axk
 * Date: 5/2/12
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Client {

    private static Logger log = LoggerFactory.getLogger(Client.class);
    private final BufferedReader in;
    private final PrintWriter out;
    private String id;

    public Client(String id, String serverHost, int serverPort) throws IOException {
        this.id = id;
        log.info("Connecting to server at host {}, port {}", serverHost, serverPort);
        Socket server = new Socket(serverHost, serverPort);

        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        out = new PrintWriter(server.getOutputStream(), true);
        log.info("Connection to server established, local port: {}", server.getLocalPort());
    }

    public String speakNListen(String speak) throws IOException {
        String message = "["+id + "]: " + speak ;
        log.info("Message to server: {}", message);
        out.println(message);
        log.info("Pushed message to the server");
        String response = in.readLine();
        log.info("response from server:{}", response);
        return response;
    }
}
