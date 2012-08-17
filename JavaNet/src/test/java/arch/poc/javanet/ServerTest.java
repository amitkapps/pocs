package arch.poc.javanet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerTest{

    private static Logger log = LoggerFactory.getLogger(ServerTest.class);

    @Test
    public void testStartServer() throws IOException, InterruptedException {
        new Server(1025);

        Client client = new Client("1", "localhost", 1025);
        client.speakNListen("Hello");

        Client client2 = new Client("2", "localhost", 1025);
        client2.speakNListen("Hello");

    }

}