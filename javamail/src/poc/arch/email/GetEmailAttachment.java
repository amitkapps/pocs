package poc.arch.email;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.mail.*;

/**
 * This class proecess the BNSF email attachment data and update last free day and event type of 
 * the Chassis object if required.
 * @author SZV
 *
 */
public class GetEmailAttachment{

    public static void main(String[] args){
        new GetEmailAttachment().processEmailAttachemnt();
    }

    public GetEmailAttachment() {}

    private Vector emailMsgVector = null;

    /**
     * This method get the email message(s) from email box for specific username
     *
     */
    public void processEmailAttachemnt() {

        try {
            System.out.println("######### email process is started @ " + new java.util.Date() + " ######### \n");

            //ch = null;// make ChassisHandler null
            emailMsgVector = new Vector();

            String host = "10.3.4.33";
            String username = "RNDev";
            String password = "matson12!";
            System.out.println("host : " + host + ", email username : " + username + ", password : " + password);
            /* * IMAP configuration */
            Properties system_properties = System.getProperties();

            system_properties.put("mail.imap.partialfetch", "true");
            system_properties.put("mail.imap.fetchsize", "51200");
            // Get session
            Session session = Session.getDefaultInstance(system_properties, null);

            // get the Store object for IMAP
            Store store = session.getStore("imap");

            store.connect(host, username, password);

            // Get folder
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            // Get directory
            Message message[] = folder.getMessages();
            int totalMessage = message.length;
            System.out.println(totalMessage + " messages in inbox");
            // LOOP through the messages in INBOX
            for (int i=0; i < totalMessage; i++) {

                try {
                    System.out.println("\nMessage # " + (i+1) + ", From: "
                            + message[i].getFrom()[0]
                            + ", Sub.: " + message[i].getSubject() + ", Date: " + message[i].getSentDate() + "\n");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.err.println("Error while getting BNSF Email details : " + e);
                }

                Object content = message[i].getContent();

                if (content instanceof Multipart) {
                    handleMultipart((Multipart)content);
                }
            }

            if(totalMessage > 0) {
                boolean bmoveMsg = udpateChassisData(emailMsgVector);
                emailMsgVector.clear();

                //folder.setFlags(message, new Flags(Flags.Flag.FLAGGED) , true);\
                logger.debug("MoveMessage() called :: " + bmoveMsg);
                if(bmoveMsg) {
                    MoveMessage(folder, store, message);
                }
            } else {
                logger.debug("NO NEW MESSAGES IN INBOX \n");
            }
            emailMsgVector = null;
            // Close connection
            folder.close(true);
            store.close();
            System.out.println("######### email process is ended ######### \n");
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("NoSuchProvider Error :: " + e);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Messaging Error :: " + e);
        } /*catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("IO Error :: " + e);
        } */catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error :: " + e);
        }

    }

/*
    */
/**
     * This method loop through the Multipart object and call handlePart(Part)
     * method for further process.
     * @param multipart
     * @throws MessagingException
     * @throws IOException
     *//*

    public void handleMultipart(Multipart multipart)
            throws MessagingException, IOException {
        for (int i=0, n=multipart.getCount(); i<n; i++) {
            handlePart(multipart.getBodyPart(i));
        }
    }

    */
/**
     * This method checks if part has attachement then it will call
     * parseFile(filename, inputstream) for parsing the attachment txt file.
     * @param part
     * @throws MessagingException
     * @throws IOException
     *//*

    public void handlePart(Part part)
            throws MessagingException, IOException {

        String disposition = part.getDisposition();
        //String contentType = part.getContentType();

        if (disposition!= null && (disposition.equalsIgnoreCase(Part.ATTACHMENT) ||
                disposition.equalsIgnoreCase(Part.INLINE)) ) {

            parseFile(part.getFileName(),part.getInputStream());

        } else { // Should never happen
            // do nothing
        }
    }

    */
/**
     * This method saves the attachement data to a file and call
     * parseTextAttachment(File) to further process attachment data.
     * @param fileName
     * @param input
     * @throws IOException
     *//*

    public void parseFile(String fileName,
                          InputStream input) throws IOException {

        File file = new File("attachment.txt");

        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(input);
        int bufRead = 0;
        byte[] buffer = new byte[1024];

        while(true) {
            bufRead = bis.read(buffer,0,1024);
            if (bufRead == -1) break;
            fos.write(buffer, 0, bufRead);
        }

        if(bis !=null)bis.close();
        if(fos!=null)fos.close();
        bis=null;
        fos=null;

        ParseEmailTextFile pet = new ParseEmailTextFile();
        Vector emailDataVct = pet.parseTextAttachment(file);
        logger.debug("Total Record(s) Found from Message : " + emailDataVct.size());

        if(emailDataVct.size()>0){
            //ch = new ChassisHandler();
            emailMsgVector.addAll(emailDataVct);
            //udpateChassisData(emailDataVct);
        }
        file.delete();

    }

    */
/**
     * This method moves the message of INBOX to a specific BackUp folder.
     * @param folder
     * @param store
     * @param msgs
     *//*

    public void MoveMessage(Folder folder, Store store, Message[] msgs) {

        try {
            //Open destination folder, create if reqd
            Folder dfolder = store.getFolder(Constant.EMAIL_BACKUP_FOLDER);

            if (!dfolder.exists())
                dfolder.create(Folder.HOLDS_MESSAGES);

            // Get the message objects to copy
            logger.debug("\nMoving " + msgs.length + " message(s) to BackUp folder\n");

            if (msgs.length != 0) {
                folder.copyMessages(msgs, dfolder);
                folder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);

                // Dump out the Flags of the moved messages, to insure that
                // all got deleted
                for (int i = 0; i < msgs.length; i++) {
                    if (!msgs[i].isSet(Flags.Flag.DELETED))
                        logger.debug("Message # " + msgs[i] +
                                " not deleted");
                }
                folder.expunge();
            }
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.debug("Error in moving message from INBOX to BackUp Folder", e);
        }

    }

    */
/**
     * This method updates the last free day date and event type of chassis object
     * if LFD(last free day date) of chassis object and LFD of email attachment for specific container
     * number does not match
     * @param emailDataVct
     * @param emailAttachmentDate
     *//*

    private boolean udpateChassisData(Vector emailDataVct) {

        boolean bReturn = true;
        try {

            //logger.debug("inside udpateChassisData method before initializing ChassisHandler");
            //ChassisHandler ch = new ChassisHandler();
            //logger.debug("inside udpateChassisData method after initializing ChassisHandler :: " + ch);

            */
/*Calendar c = Calendar.getInstance();
                 c.setTime(emailAttachmentDate);
                 c.add(Calendar.DAY_OF_MONTH, -2);
                 emailAttachmentDate = c.getTime();
                 *//*

            logger.debug("Total Record(s) from email Attachemnts : " + emailDataVct.size());

            logger.debug("ChassisHandler Initialized :: " + ch.bChInitialized);
            if(!ch.bChInitialized) { // create new ChassisHandler if not initialized preperly
                ch = new ChassisHandler();
            }

            if(!ch.bChInitialized) {
                bReturn = false;
                logger.debug("ChassisHandler is not Initialized properly, email process will be delayed...");
                return bReturn;
            }

            //logger.debug("emailAttachmentDate  ::: " + emailAttachmentDate);
            for(int i=0; i<emailDataVct.size(); i++) {
                logger.debug((i+1)+". " + ((ContainerFreeDayVO)emailDataVct.get(i)).getContainerNumber() + ", " + ((ContainerFreeDayVO)emailDataVct.get(i)).getFreedayDate() + ", " + ((ContainerFreeDayVO)emailDataVct.get(i)).getEmailAttachDate());
                String containerNumber =  ((ContainerFreeDayVO)emailDataVct.get(i)).getContainerNumber();
                Date lastFreedayDate = ((ContainerFreeDayVO)emailDataVct.get(i)).getFreedayDate();
                Date emailAttachmentDate = ((ContainerFreeDayVO)emailDataVct.get(i)).getEmailAttachDate();

                if(null == lastFreedayDate) {
                    logger.debug("LFD for containerNumber : " + containerNumber + " is null, seems like container is empty.");
                }	else {
                    Chassis chs = ch.getChassis(containerNumber, emailAttachmentDate, Constant.RAIL_ROAD_NAME_BNSF);

                    if(chs != null) {
                        logger.debug("Chassis ID : " + chs.getChassisId() + " for containerNumber : " + containerNumber);

                        boolean result = isEqualDate(lastFreedayDate,chs.getLastFreeDay());
                        // if calculated LFD and current LFD does not match OR both are same but EVENT_TYPE is NTF
                        // then udpate LFD and EVENT_TYPE
                        if(!result || ( result && chs.getEventType().equalsIgnoreCase(Constant.NOTIFY_EVENT_TYPE)) ) {
                            // udpate chassis last free day date & Evene Type to 'EML'
                            chs.setLastFreeDay(lastFreedayDate);
                            chs.setEventType(Constant.EMAIL_EVENT_TYPE);
                            ch.updateChassisForLFD(chs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.debug("ERROR WHILE UPDATING CHASSIS DATA :: " + e);
        }

        return bReturn;
    }

    */
/**
     * This method checks wether two dates are same or not
     * @param lastFreedayDate
     * @param chassisLastFreeDay
     * @return boolean - true if date matches else false
     *//*

    private boolean isEqualDate(Date lastFreedayDate,Date chassisLastFreeDay) {
        boolean isEqual = false;

        Calendar c1 = Calendar.getInstance();
        c1.setTime(lastFreedayDate);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(chassisLastFreeDay);

        isEqual = c1.equals(c2);

        return isEqual;
    }
*/
}
