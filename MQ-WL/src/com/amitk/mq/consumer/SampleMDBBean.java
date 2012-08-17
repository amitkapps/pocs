package com.amitk.mq.consumer;

/*****************************************************************************/
/*                                                                           */
/* (c) Copyright IBM Corp. 2006  All rights reserved.                        */
/*                                                                           */
/* This sample program is owned by International Business Machines           */
/* Corporation or one of its subsidiaries ("IBM") and is copyrighted         */
/* and licensed, not sold.                                                   */
/*                                                                           */
/* You may copy, modify, and distribute this sample program in any           */
/* form without payment to IBM,  for any purpose including developing,       */
/* using, marketing or distributing programs that include or are             */
/* derivative works of the sample program.                                   */
/*                                                                           */
/* The sample program is provided to you on an "AS IS" basis, without        */
/* warranty of any kind.  IBM HEREBY  EXPRESSLY DISCLAIMS ALL WARRANTIES,    */
/* EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED     */
/* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.       */
/* Some jurisdictions do not allow for the exclusion or limitation of        */
/* implied warranties, so the above limitations or exclusions may not        */
/* apply to you.  IBM shall not be liable for any damages you suffer as      */
/* a result of using, modifying or distributing the sample program or        */
/* its derivatives.                                                          */
/*                                                                           */
/*****************************************************************************/
/*                                                                           */
/* Program name: SampleMDB                                                   */
/*                                                                           */
/* Description: Sample MDB program that puts a message to a queue             */
/*                                                                           */
/*****************************************************************************/

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SampleMDBBean implements javax.ejb.MessageDrivenBean,
		javax.jms.MessageListener {
	private javax.ejb.MessageDrivenContext fMessageDrivenCtx;

	/**
	 * getMessageDrivenContext
	 */
	public javax.ejb.MessageDrivenContext getMessageDrivenContext() {
		return fMessageDrivenCtx;
	}

	/**
	 * setMessageDrivenContext
	 */
	public void setMessageDrivenContext(javax.ejb.MessageDrivenContext ctx) {
		fMessageDrivenCtx = ctx;
	}

	/**
	 * ejbCreate
	 */
	public void ejbCreate() {
	}

	/**
	 * onMessage
	 */
	public void onMessage(javax.jms.Message msg) {
		try {
			System.out.println("Message Received: " + msg);
			//System.out.println("puting the message to MyReplyQueue");
			//putMessage(msg);
		} catch (Exception e) {
			System.out.println("exception: ");
			e.printStackTrace();
		}
	}

	public void putMessage(javax.jms.Message msg) {
		QueueConnectionFactory queueConnectionFactory = null;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		Context jndiContext = null;
		Queue queue = null;
		QueueSender queueSender = null;
		String queueName = "java:comp/env/jms/WLMyReplyQueue";
		try {
			jndiContext = new InitialContext();
			queueConnectionFactory = (QueueConnectionFactory) jndiContext
					.lookup("java:comp/env/jms/WLSenderQCF");
			System.out.println("looked up QueueConnectionFactory: "
					+ queueConnectionFactory);
			queue = (Queue) jndiContext.lookup(queueName);
			System.out.println("looked up Queue: " + queue);
		} catch (NamingException e) {
			System.out.println("JNDI Problem: ");
			e.printStackTrace();
		}

		try {
			queueConnection = queueConnectionFactory.createQueueConnection();
			queueSession = queueConnection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			queueSender = queueSession.createSender(queue);
			queueSender.send(msg);
			System.out.println("Message send");

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();
			if (e instanceof JMSException) {
				((JMSException) e).getLinkedException().printStackTrace();
			}
		} finally {
			try {
				System.out.println("Closing Connection");
				queueSession.close();
				queueConnection.close();

			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
				if (e instanceof JMSException) {
					((JMSException) e).getLinkedException().printStackTrace();
				}
			}

		}

	}

	/**
	 * ejbRemove
	 */
	public void ejbRemove() {
	}
}

