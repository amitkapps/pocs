package com.poc.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class EmailSender {

	public static final String CONTENT_TYPE_PDF = "application/pdf";
	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
	
	
	private static Logger log = Logger.getLogger(EmailSender.class);
	
	private String subject = "";
	private Address fromEmailAddress= null;
	private Address[] replyToEmailAddress = null;
	private String plainTextMessage = "";
	private String htmlMessage = "";
	private ArrayList emailAttachments = new ArrayList(1);
	
	private Address[] toEmailAddresses = null;
	
	private EmailSender(){
		//not accessible. We need some minimum parameters to be set at creation.
	}
	
	/**
	 * Constructor accepting list of email ids.
	 * 
	 * @param toEmailsIds EmailIds to send the email to. Can be separated by
	 * either of space or commas
	 */
	public EmailSender(String toEmailsIds)
		throws Exception{
		
		if ( null== toEmailsIds || "".equals(toEmailsIds.trim())) {
			throw new Exception("Email Ids passed were either null or blank.");
		}
		
		//Need proper validation on the passed address.
		try{
			toEmailAddresses = InternetAddress.parse(toEmailsIds, false);
		}catch(Exception ex){
			log.warn("Could not validate to email addresses-" + toEmailsIds);
			throw new Exception("Could not validate to Email addresses", ex);
		}
		
	}

	/**
	 * sets the from Email for this email message.
	 * @param fromEmailId
	 * @throws VINSightInternalException
	 */
	public void setFromEmailAddress(String fromEmailId)
	throws Exception{
	
		if ( null== fromEmailId || "".equals(fromEmailId.trim())) {
			throw new Exception("Email Ids passed were either null or blank.");
		}
		
		//Need proper validation on the passed address.
		try{
			Address[] addresses = InternetAddress.parse(fromEmailId, false);
			if( null!= addresses && addresses.length > 0)
				this.fromEmailAddress = addresses[0];
		}catch(Exception ex){
			log.warn("Could not validate from email address-" + fromEmailId);
			throw new Exception("Could not validate From Email address", ex);
		}
	
	}


	public void setReplyToEmailAddress(String replyToEmailIds)
	throws Exception{
	
		if ( null== replyToEmailIds || "".equals(replyToEmailIds.trim())) {
			throw new Exception("Email Ids passed were either null or blank.");
		}
		
		//Need proper validation on the passed address.
		try{
			replyToEmailAddress = InternetAddress.parse(replyToEmailIds, false);
		}catch(Exception ex){
			log.warn("Could not validate from email address-" + replyToEmailIds);
			throw new Exception("Could not validate to Email addresses", ex);
		}
	
	}
	
	
	public String getPlainTextMessage() {
		return plainTextMessage;
	}

	public void setPlainTextMessage(String plainTextMessage) {
		this.plainTextMessage = plainTextMessage;
	}

	public String getHtmlMessage() {
		return htmlMessage;
	}

	public void setHtmlMessage(String htmlMessage) {
		this.htmlMessage = htmlMessage;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}	
	
	/**
	 * Method to add a binary content as attachment.
	 * @param attachmentName
	 * @param contentAsByteArray
	 * @param contentType
	 * @throws IllegalArgumentException
	 */
	public void addInMemoryBinaryAttachment(String attachmentName, byte[] contentAsByteArray, String contentType)
	throws IllegalArgumentException{
		this.emailAttachments.add(new EmailAttachment(attachmentName, contentAsByteArray, contentType));
	}
	
	/**
	 * Method to add text/html attachment.
	 * @param attachmentName
	 * @param contentAsString
	 * @param contentType
	 * @throws IllegalArgumentException
	 */
	public void addInMemoryHtmlTextAttachment(String attachmentName, String contentAsString)
	throws IllegalArgumentException{
		this.emailAttachments.add(new EmailAttachment(attachmentName, contentAsString, CONTENT_TYPE_TEXT_HTML));
	}
	
	private Session createNewMailSession(){
		
		String host = "mailhost";
        // Create properties, get Session
        Properties props = new Properties();

        // If using static Transport.send(),
        // need to specify which host to send it to
        props.put("mail.smtp.host", host);
        // To see what is going on behind the scene
        props.put("mail.debug", "false");
        return Session.getInstance(props);
		
	}
	
	private void addEmailBody(Multipart emailMultiPart)
	throws MessagingException{
		
		Multipart emailBodyMultipart = null;
		
		if( hasTextContent() && hasHTMLContent() )
			emailBodyMultipart = new MimeMultipart("alternative");
		else
			emailBodyMultipart = new MimeMultipart();

		//Add the text body part
		if(    ( hasTextContent() && hasHTMLContent() )
			|| ! hasHTMLContent()
		  ){
			MimeBodyPart emailBodyTextPart = new MimeBodyPart();
			//mimeBodyPartText.setText(plainTextMessage);
			emailBodyTextPart.setContent(plainTextMessage, CONTENT_TYPE_TEXT_PLAIN);
			emailBodyTextPart.setDisposition(MimeBodyPart.INLINE);
			emailBodyMultipart.addBodyPart(emailBodyTextPart);
		}
		
		//Add the HTML body part
		if( hasHTMLContent() ){
			MimeBodyPart emailBodyHtmlPart = new MimeBodyPart();
			emailBodyHtmlPart.setDataHandler(new DataHandler(htmlMessage, CONTENT_TYPE_TEXT_HTML ));
			emailBodyHtmlPart.setDisposition(MimeBodyPart.INLINE);
			emailBodyMultipart.addBodyPart(emailBodyHtmlPart);
		}
		//set the body multipart as the body part for the email multipart
		MimeBodyPart emailBody = new MimeBodyPart();
		emailBody.setContent(emailBodyMultipart);
		emailMultiPart.addBodyPart(emailBody);

	}
	
	private void addEmailAttachments(Multipart emailMultiPart)
	throws MessagingException, IOException{
		
		//Add attachments for the email
		if(null != this.emailAttachments){
			
			for (Iterator iter = this.emailAttachments.iterator(); iter.hasNext();) {
				EmailAttachment emailAttachment = (EmailAttachment) iter.next();
				
				Multipart emailAttachmentMultipart = new MimeMultipart("related");
				MimeBodyPart emailAttachmentPart = new MimeBodyPart();
				
				//Set contents based on what the attachment type is
				if( emailAttachment.isTextAttachment() ){
					emailAttachmentPart.setContent(emailAttachment.getAttachmentAsString(), emailAttachment.getContentType());
				}
				else{
					emailAttachmentPart
						.setDataHandler( new DataHandler( new ByteArrayDataSource( emailAttachment.getAttachmentAsByteArray(), 
																				   emailAttachment.getContentType()) 
														) 
										);
				}
				
				emailAttachmentPart.setFileName(emailAttachment.getAttachmentName());
				emailAttachmentPart.setDisposition(MimeBodyPart.ATTACHMENT);
				emailAttachmentMultipart.addBodyPart(emailAttachmentPart);
				
				MimeBodyPart attachmentBodyPart = new MimeBodyPart();
				attachmentBodyPart.setContent(emailAttachmentMultipart);
				emailMultiPart.addBodyPart(attachmentBodyPart);
			}
		}
		
	}
	
	public void sendMail(){
		try{

			//Get the mail session
			Session session = createNewMailSession();
	
			//Create a new message object
			Message msg = new MimeMessage(session);
			
			//Set the from email address
			if(null != this.fromEmailAddress )
				msg.setFrom(this.fromEmailAddress);
			else
				msg.setFrom();
			
			//Set reply to email address if it exists
			if( null != this.replyToEmailAddress )
				msg.setReplyTo(replyToEmailAddress);
			
			//set recipients
			msg.setRecipients(Message.RecipientType.TO, toEmailAddresses );
			//set subject
			msg.setSubject(subject);
			//Set sent date
			msg.setSentDate(new Date());
			
			//Create a multipart to add email body parts to it
			Multipart emailMultiPart = new MimeMultipart("mixed");
			
			//Add the email body to the multipart
			addEmailBody(emailMultiPart);
			
			//Add email attachments to the multipart
			addEmailAttachments(emailMultiPart);
			
			//Add the multipart as content to the message
			msg.setContent(emailMultiPart);
			
			// Send the message.
			Transport.send(msg);
			
		}catch(Exception ex){
			log.error("Failed Sending Email Message", ex);
		}		
		
	}
		
	
	public boolean hasTextContent(){
		return ( null!= plainTextMessage && !"".equals(plainTextMessage.trim()) );
	}
	
	public boolean hasHTMLContent(){
		return ( null!= htmlMessage && !"".equals(htmlMessage.trim()) );
	}

	
	
	/**
	 * Inner class to be used only by the EmailSender class.
	 * This class is like a value object that holds information related to an
	 * email attachment.
	 * @author axk
	 *
	 */
	private class EmailAttachment{
		
		private String attachmentName;
		private byte[] attachmentAsByteArray;
		private String attachmentAsString;
		private String contentType;
		private boolean isTextAttachment;
		
		
		/**
		 * No access to the default constructor.
		 */
		private EmailAttachment(){
			throw new IllegalArgumentException("Not a valid constructor!!");
		};
		
		/**
		 * Constructor for the file attachment
		 * @param attachmentName The display name for the attachment 
		 * @param attachmentAsByteArray the contents of the attachment as byteArray
		 * @param contentType the content type of the file - e.g. "application/pdf"
		 */
		public EmailAttachment(String attachmentName, byte[] attachmentAsByteArray, String contentType)
		throws IllegalArgumentException{
			
			if(  null == attachmentName || "".equals(attachmentName) ){
				throw new IllegalArgumentException("Passed attachment name null or blank!");
			}

			if(  null == attachmentAsByteArray ){
				throw new IllegalArgumentException("Passed attachmentAsByteArray null!");
			}

			if(  null == contentType || "".equals(contentType) ){
				throw new IllegalArgumentException("Passed content type null or blank!");
			}

			this.attachmentName = attachmentName;
			this.attachmentAsByteArray = attachmentAsByteArray;
			this.contentType = contentType;
			this.isTextAttachment = false;
		}

		/**
		 * Constructor for the file attachment
		 * @param attachmentName The display name for the attachment 
		 * @param attachmentAsString the contents of the attachment as a String
		 * @param contentType the content type of the file - e.g. "application/pdf"
		 */
		public EmailAttachment(String attachmentName, String attachmentAsString, String contentType)
		throws IllegalArgumentException{
			
			if(  null == attachmentName || "".equals(attachmentName) ){
				throw new IllegalArgumentException("Passed attachment name null or blank!");
			}

			if(  null == attachmentAsString ){
				throw new IllegalArgumentException("Passed attachmentAsString null!");
			}

			if(  null == contentType || "".equals(contentType) ){
				throw new IllegalArgumentException("Passed content type null or blank!");
			}

			this.attachmentName = attachmentName;
			this.attachmentAsString = attachmentAsString;
			this.contentType = contentType;
			this.isTextAttachment = true;
		}
		
		
		public byte[] getAttachmentAsByteArray() {
			return attachmentAsByteArray;
		}
		public String getAttachmentAsString() {
			return attachmentAsString;
		}
		/**
		 * A utility method to return the appropriate attachment object
		 * String - if it is text/html else byte[] otherwise. 
		 * @return the appropriate attachment Object
		 */
		public Object getAttachment(){
			if(isTextAttachment)
				return attachmentAsString;
			else 
				return attachmentAsByteArray;
		}
		public String getAttachmentName() {
			return attachmentName;
		}
		public String getContentType() {
			return contentType;
		}

		public boolean isTextAttachment() {
			return isTextAttachment;
		}
	}
}
