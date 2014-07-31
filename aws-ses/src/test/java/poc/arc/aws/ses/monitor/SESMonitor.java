package poc.arc.aws.ses.monitor;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 7/8/14
 * Time: 7:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SESMonitor {

    static Logger log = LoggerFactory.getLogger(SESMonitor.class);

    @Test
    public void monitor() {


        try {
            AmazonSimpleEmailServiceClient sesClient = getSESClient();
            GetSendStatisticsResult sendStatistics = sesClient.getSendStatistics();

            List<SendDataPoint> sendDataPoints = sendStatistics.getSendDataPoints();
            Collections.sort(sendDataPoints, new Comparator<SendDataPoint>() {
                @Override
                public int compare(SendDataPoint o1, SendDataPoint o2) {
                    if (o1.getTimestamp().before(o2.getTimestamp()))
                        return -1;
                    else if (o1.getTimestamp().equals(o2.getTimestamp()))
                        return 0;
                    else
                        return +1;
                }
            });
//            Collections.reverse(sendDataPoints);
//            log.info("SendStats: {}", sendDataPoints);
            for (SendDataPoint sendDataPoint : sendDataPoints) {
                log.info("{}", sendDataPoint);
            }

            log.info("SendQuota: {}", sesClient.getSendQuota());


        } catch (Exception e) {
            log.error("Error using ses", e);
        }

    }

    private static AmazonSimpleEmailServiceClient getSESClient() {
        //            sesClient.setEndpoint("");  //if we are pointing to a region other than North Virginia
        return new AmazonSimpleEmailServiceClient(
                new BasicAWSCredentials("AKIAIW6UFOSYCVRQXJLQ", "PhU/Gfv8uqjapG8esMpNEctZDwQRj+xqxuqTkTyU"));
    }

    @Test
    public void sendTestEmail() {
        SendEmailResult result = getSESClient().sendEmail(new SendEmailRequest("akapoor@matson.com"
                , new Destination(Arrays.asList("akapoor@matson.com"))
                , new Message(new Content("Hello from ses client")
                , new Body(new Content("Body"))
        )
        )
        );
        log.info("result: {}", result);
    }


}
