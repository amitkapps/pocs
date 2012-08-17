package com.poc.jxpath.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.log4j.Logger;

import com.report.business.ActiveUnitAction;
import com.report.vo.ActiveUnit;

public class JXPathTest {

	private static Logger log = Logger.getLogger(JXPathTest.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String localProviderURL = "t3://localhost:7001";
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		System.setProperty(Context.PROVIDER_URL, localProviderURL);

		
		//load some sample activeUnits
		List activeUnits = ActiveUnitAction.listActiveUnits();
		log.debug("Loaded " + activeUnits.size() + " active units");
		
		/*
		ActiveUnit firstActiveUnit = (ActiveUnit) activeUnits.get(0);
		//create JXPath context
		JXPathContext context = JXPathContext.newContext(firstActiveUnit);
		String consignee = (String) context.getValue("consigneeName");
		log.debug("Consignee Name=" + consignee);
		*/
		
		// get all active units for consignee SAIPAN EGG
		JXPathContext context = JXPathContext.newContext(activeUnits);
		context.setLenient(false);
		Iterator iter = context.iterate(".[consigneeName='SAIPAN EGG' and lastReportedLocationTypeCd='V']");
		
		log.info("Non zero result? = " + (null != iter && iter.hasNext()) );
		for (; iter!=null && iter.hasNext();) {
			ActiveUnit activeUnit = (ActiveUnit) iter.next();
			log.debug("UnitId=" + activeUnit.getUnitId() + ", consignee=" + activeUnit.getConsigneeName() 
					  + ", last loc type=" + activeUnit.getLastReportedLocationTypeCd() );
		}
		
	}

}
