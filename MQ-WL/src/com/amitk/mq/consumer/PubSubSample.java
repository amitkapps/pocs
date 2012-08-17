package com.amitk.mq.consumer;

// ===========================================================================
//
// Licensed Materials - Property of IBM
//
// 5724-H27, 5655-L82, 5724-L26
//
// (c) Copyright IBM Corp. 1995,2002,2005
//
// ===========================================================================
// 
// A TopicConnectionFactory object is retrieved from LDAP; this is used to
// create a TopicConnection. The TopicConnection is used to create a
// TopicSession, which creates two publishers and two subscribers. Both
// publishers subscribe to a topic; both subscribers then receive.
// 
// @(#) jms/samples/PubSubSample.java, jms, j000 1.1 04/12/03 14:31:41

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.*;
import javax.naming.directory.InitialDirContext;

public class PubSubSample {

    // To use LDAP the Initial Context Factory and the URL need to be specified
    // Change these to suit your particular installatoin
    static String icf = "com.sun.jndi.fscontext.RefFSContextFactory";
    
    static String url = "file:/C:/AmitK/work/project/EclipseWorkSpace/MQ-WL/env/MQTopic";//the bindings are listed in the .bindings file under this directory
    //To create a bindings file to map to MQ-JMS objects use the JMSAdmin.bat utility.

    static String tcfLookup = "ReceiverTCF"; // TopicConnectionFactory (TCF) lookup
    private static String tLookup = "MyMDBTopic"; // topic lookup


    // Pub/Sub objects used by this program
    private static TopicConnectionFactory fact = null;
    private static Topic topic = null;

    public static void main(String args[]) {
        // Initialise JNDI properties
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, icf);
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.REFERRAL, "throw");

        Context ctx = null;
        try {
            System.out.print("Initializing JNDI... ");
            ctx = new InitialDirContext(env);
            System.out.println("Done!");
        } catch (NamingException nx) {
            System.out.println("ERROR: " + nx);
            System.exit(-1);
        }

        // Lookup TCF
        try {
            System.out.print("Obtaining MQ Topic connection factory from JNDI - bound to name ReceiverTCF... ");
            fact = (TopicConnectionFactory) ctx.lookup(tcfLookup);
            System.out.println("Done!");
        } catch (NamingException nx) {
            System.out.println("ERROR: " + nx);
            System.exit(-1);
        }

        // Lookup Topic
        try {
            System.out.print("Obtaining MQ Topic from JNDI- bound to name MyMDBTopic... ");
            topic = (Topic) ctx.lookup(tLookup);
            System.out.println("Done!");
        } catch (NamingException nx) {
            System.out.println("ERROR: " + nx);
            System.exit(-1);
        }

        try {
            ctx.close();
        } catch (NamingException nx) {
            // Just ignore an exception on closing the context
        }

        try {
            // Create connection
            TopicConnection conn = fact.createTopicConnection();
            // Start connection
            conn.start();
            // Session
            TopicSession sess = conn.createTopicSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            // Create a topic dynamically
            // Publisher
            TopicPublisher pub = sess.createPublisher(topic);
            // Subscriber
            TopicSubscriber sub = sess.createSubscriber(topic);

            // Publish "Hello World"
            TextMessage hello = sess.createTextMessage();
            //hello.setText("Hello World");
            //pub.publish(hello);
            hello.setText("Message From Publisher");
            pub.publish(hello);

            // Receive message
            hello = (TextMessage) sub.receive();
            System.out.println("Message Text = " + hello.getText());

            // Close publishers and subscribers
            pub.close();
            sub.close();

            // Close session and connection
            sess.close();
            conn.close();
            System.exit(0);
        } catch (JMSException je) {
            System.out.println("ERROR: " + je);
            System.out.println("LinkedException: " + je.getLinkedException());
            System.exit(-1);
        }
    }
}