package arch.poc.javanet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;

/**
 * Hello world!
 */
public class Server {
    private static Logger log = LoggerFactory.getLogger(Server.class);
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException, InterruptedException {
        InetAddress localhost = InetAddress.getByName("localhost");
        log.info("Starting server at host {} port {}", localhost, port);
        serverSocket = new ServerSocket(port, 50, localhost);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listen();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        t.start();
        log.info("server started");
    }

    public void listen() throws IOException {
        while (true) {
            Socket client = serverSocket.accept();
            handleClient(client);
        }
    }

    private void handleClient(final Socket client) throws IOException {

        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    log.info("Server accepted a connection from port " + client.getPort());
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                    while (true) {
                        String message = in.readLine();
                        log.info("Server read message: {}", message);
                        //Send data back to client
                        out.println("ServerSays:" + message);
                        log.info("Server posted a response");
                    }
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        t.start();
    }
}
