package MQJavaClient;

import com.ibm.mq.*;
import com.ibm.mq.pcf.CMQC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by amitkapps on 12/29/15.
 */
public class PutMQMessagesMQApi {


        private MQQueueManager queueManager;
        private MQQueue queue;

        @Before
        public void init() throws MQException {
            initializeMQ();
            System.out.println("MQ Resources initialized");
        }

        @After
        public void destroy() throws MQException {
            queue.close();
            queueManager.disconnect();
            System.out.println("MQ Resources closed");
        }

        @Test
        public void test_putMessages() throws InterruptedException, IOException, MQException {

            System.out.println("Putting messages to MQ");

            MQPutMessageOptions pmo = new MQPutMessageOptions();

            for(int i=1; i<=1000; i++){
                MQMessage mqMessage = new MQMessage();
                mqMessage.writeInt(i);

                queue.put(mqMessage, pmo);
                System.out.println("Sent message: " + i);
//            Thread.sleep(100);
            }
        }

        private void initializeMQ() throws MQException {
            MQEnvironment.hostname = "mqpoc.dev.matson.com";
            MQEnvironment.port = 1421;
            MQEnvironment.channel = "SYSTEM.ADMIN.SVRCONN";

            queueManager = new MQQueueManager("MQMI_POC");
            System.out.println("Is Queue Manager Connected?" + queueManager.isConnected());
            queue = queueManager.accessQueue("TEST.QL", CMQC.MQOO_OUTPUT);
            System.out.println("Is Queue open? " + queue.isOpen());

        }

  }

