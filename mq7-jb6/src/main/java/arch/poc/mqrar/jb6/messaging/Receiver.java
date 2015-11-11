package arch.poc.mqrar.jb6.messaging;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

@Component
public class Receiver {


    /**
     */
    @JmsListener(destination = "java:/jboss/jms/wmq/queue/Queue")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}