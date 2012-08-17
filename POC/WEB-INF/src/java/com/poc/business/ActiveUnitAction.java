package com.poc.business;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.poc.db.hibernate.HibernateSessionFactory;
import com.poc.vo.ActiveUnit;

public class ActiveUnitAction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List activeUnits = listActiveUnits();
		for (Iterator iter = activeUnits.iterator(); iter.hasNext();) {
			//ActiveUnit activeUnit = (ActiveUnit) iter.next();
			System.out.println(iter.next());
			
		}
	}

	private static List listActiveUnits() {

	    Session session = HibernateSessionFactory.getSession();
	    session.beginTransaction();
	    List result = session.createQuery("Select new map(unitId as VINSight_No) from ActiveUnit").list();
	    session.getTransaction().commit();
	    return result;
	}
}
