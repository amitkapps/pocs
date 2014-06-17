package poc.arch.email;

import java.util.*;
import javax.mail.*;
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
            sendEmails(100, 100);
        }

    private static void sendEmails(int totalEmails, long delay) {
        System.out.println("Send " + totalEmails + " mails with " + delay + " millis delay each");
        for(int i=1; i<=totalEmails; i++){
            sendEmail(i);
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

    private static void sendEmail(int counter) {
        // Recipient's email ID needs to be mentioned.
//        String to = "amit.kapps@gmail.com";
        String to = "matson.ses@gmail.com";
        //Test amazon ses email id to simulate bounce/out of the office/complaints(spam) etc.
//        String to = "bounce@simulator.amazonses.com";
//        String to = "ooto@simulator.amazonses.com";
//        String to = "complaint@simulator.amazonses.com";
//        String to = "suppressionlist@simulator.amazonses.com"; //emails that are on ses suppressionlist for previously known hard bounces

        // Sender's email ID needs to be mentioned
        String from = "akapoor@matson.com";
//        String from = "matson.ses@gmail.com";

        // Assuming you are sending email from localhost
        //Local Centos
//        String host = "10.8.5.35"; //Local Centos
//        String port = "1025"; //Local Centos
        String host = "10.8.7.60"; // Dev apache
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

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Amazon ses mail from remote java! count="+counter);

            // Now set the actual message
            message.setText("remote java program > linux sendmail > amazon ses"+counter);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully to "+ to + " through mailhost "+ host +":"+port+", counter="+counter);
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
