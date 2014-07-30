package poc.arch.email;

import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.*;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 12/16/13
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailSender {

    // File Name SendEmail.java

    public static void main(String [] args){
        sendEmails(1, 100, true, "110_20140715103155_0_notify.pdf");
    }

    private static void sendEmails(int totalEmails, long delay, boolean addAttachment, String fileName) {
        System.out.println("Send " + totalEmails + " mails with " + delay + " millis delay each via host");
        for(int i=1; i<=totalEmails; i++){
            sendEmail(i, addAttachment, fileName);
            delay(delay);
        }
    }

    private static void delay(long delay) {
        try {
            long millis = (long) (delay * Math.random());
            System.out.println("Delay " + millis + " millis");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static void sendEmail(int counter, boolean addAttachment, String fileName) {
        // Recipient's email ID needs to be mentioned.
//        String to = "amit.kapps@gmail.com";
//        String to = "akapoor@matson.com";
        //Test amazon ses email id to simulate bounce/out of the office/complaints(spam) etc.
//        String to = "akapoor@matson.com";
        //Amazon ses simulator - http://docs.aws.amazon.com/ses/latest/DeveloperGuide/mailbox-simulator.html
//        String to = "matson.ses@gmail.com";
//        String to = "bounce@simulator.amazonses.com";
        String to = "nobody234@matson.com";
//        String to = "ooto@simulator.amazonses.com";
//        String to = "complaint@simulator.amazonses.com";
//        String to = "suppressionlist@simulator.amazonses.com"; //emails that are on ses suppressionlist for previously known hard bounces

        // Sender's email ID needs to be mentioned
        String from = "akapoor@matson.com";
//        String from = "matson.ses@gmail.com";
        // String from = "notify@matson.com";
//        String from = "1webteamatphx@matson.com";

        // Assuming you are sending email from localhost
        //Local Centos
//        String host = "10.8.5.35"; //Local Centos
//        String port = "1025"; //Local Centos
        String host = "10.8.7.60"; // Dev apache
//        String host = "10.201.2.80"; // pre apache1
//        String host = "128.167.98.113"; //PRE LB
//        String host = "171.73.201.44"; //PROD LB
        String port = "25"; //Dev apache

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.debug", "false");
        properties.setProperty("mail.smtp.port", port);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try{
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            message.setReplyTo(new InternetAddress[]{ new InternetAddress("matson.ses@mgmail.com")});

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
//            String subject = "Amazon ses mail from remote java! count=" + counter;
            String subject = "This is a test email messages sent via amazon ses";
            message.setSubject(subject);

            // Now set the actual message
//            String bodyText = "remote java program > linux sendmail > amazon ses" + counter;
            String bodyText = "Test email sent via amazon which will bounce back and send us the original email with attachment via ses mailer daemon";
            message.setText(bodyText);


            if(addAttachment){
                BodyPart messageBodyPart = new MimeBodyPart();

                messageBodyPart.setText(bodyText + "\n\n");

                Multipart multipart = new MimeMultipart();

                multipart.addBodyPart(messageBodyPart);

                BodyPart messageFileAttachment = new MimeBodyPart();

                DataSource source = new FileDataSource(fileName);

                messageFileAttachment.setDataHandler(new DataHandler(source));

                messageFileAttachment.setFileName(fileName);

                multipart.addBodyPart(messageFileAttachment);

                message.setContent(multipart);
            }




            Transport transport = session.getTransport("smtp");
            transport.addTransportListener(new TransportListener(){
                @Override
                public void messageDelivered(TransportEvent transportEvent) {
                    System.out.println("Message Delivered" + transportEvent);
                }

                @Override
                public void messageNotDelivered(TransportEvent transportEvent) {
                    System.out.println("Message Not Delivered" + transportEvent);
                }

                @Override
                public void messagePartiallyDelivered(TransportEvent transportEvent) {
                    System.out.println("Message Partially Delivered" + transportEvent);
                }
            });

            // Send message
            transport.send(message);
            System.out.println("Sent message successfully from " + from+ ", to "+ to + " through mailhost "+ host +":"+port+", counter="+counter + ", with attachement [" + fileName + "] ?" + addAttachment);
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
