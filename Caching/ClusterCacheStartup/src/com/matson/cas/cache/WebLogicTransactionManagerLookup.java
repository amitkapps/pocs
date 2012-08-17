package com.matson.cas.cache;

import javax.naming.InitialContext;
import javax.transaction.TransactionManager;

import org.jboss.cache.TransactionManagerLookup;


	/**
	 * Uses JNDI to lookup the weblogic TransactionManager.
	 */
	public class WebLogicTransactionManagerLookup implements TransactionManagerLookup {

	   public WebLogicTransactionManagerLookup() {
	   }

	   public TransactionManager getTransactionManager() throws Exception {
	      return (TransactionManager)new InitialContext().lookup("javax.transaction.TransactionManager");
	   }

	}
