package com.report.business;

import java.util.Iterator;
import java.util.List;

import javax.naming.Context;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.report.db.hibernate.HibernateSessionFactory;

public class ActiveUnitAction {

	private static Logger log = Logger.getLogger(ActiveUnitAction.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String localProviderURL = "t3://localhost:7001";
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		System.setProperty(Context.PROVIDER_URL, localProviderURL);
		
		
		List activeUnits = listActiveUnits();
		for (Iterator iter = activeUnits.iterator(); iter.hasNext();) {
			//ActiveUnit activeUnit = (ActiveUnit) iter.next();
			System.out.println(iter.next());
			
		}
	}

	public static List listActiveUnits() {

	    Session session = HibernateSessionFactory.getSession();
	    log.debug("Loading Active Units");
	    List result = session.createQuery("from ActiveUnit").list();
	    log.debug("Done Loading Active Units");
	    session.close();
	    log.debug("Session Closed");
	    return result;
	}
}
