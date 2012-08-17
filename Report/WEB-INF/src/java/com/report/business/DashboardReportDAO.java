package com.report.business;

import java.util.Iterator;
import java.util.List;

import javax.naming.Context;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.report.db.hibernate.HibernateSessionFactory;

public class DashboardReportDAO {

	private static Logger log = Logger.getLogger(DashboardReportDAO.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String localProviderURL = "t3://localhost:7001";
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		System.setProperty(Context.PROVIDER_URL, localProviderURL);
		
		
		List activeUnits = listAgingUnitsByLocation(100);
		for (Iterator iter = activeUnits.iterator(); iter.hasNext();) {
			//ActiveUnit activeUnit = (ActiveUnit) iter.next();
			System.out.println(iter.next());
			
		}
	}

	public static List listActiveUnitsByLocation() {

	    Session session = HibernateSessionFactory.getSession();
	    log.debug("Loading active units by location");
	    List result = 
	    	session.createQuery("select au.portCd, count(au) from ActiveUnit au " +
	    						"where au.portCd is not null " +
	    						"group by au.portCd " +
	    						"order by count(au) desc "
	    						).list();
	    log.debug("Done Loading Active Units");
	    session.close();
	    log.debug("Session Closed");
	    return result;
	}
	
	
	public static List listAgingUnitsByLocation(int noOfDaysSinceReceive) {

	    Session session = HibernateSessionFactory.getSession();
	    log.debug("Loading aging units by location");
	    List result = 
	    	session.createQuery(" select au.portCd, count(au) " +
	    						" from ActiveUnit au " +
	    						" where au.receiveDt < (sysdate - :noOfDaysSinceReceive) " +
	    						"  and au.portCd is not null " +
	    						" group by au.portCd " +
	    						" order by count(au) desc ")
	    						.setInteger("noOfDaysSinceReceive", noOfDaysSinceReceive)
	    						.list();
	    log.debug("Done Loading Aging Units");
	    session.close();
	    log.debug("Session Closed");
	    return result;
	}

}
