package poc.hibernate.hello;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.matson.booking.vo.BookingDisplay;

import poc.hibernate.util.HibernateSessionFactory;

public class HelloHibernate {
	public static void main(String[] args) {
		HibernateSessionFactory.setConfigFile("greeting.hibernate.cfg.xml");
		Session session = HibernateSessionFactory.getSession();
		
		Criteria crit = session.createCriteria(UserGreeting.class);//createQuery("from UserGreeting");
		List list = crit.list();
		//String queryString = "select * from (" + query.getQueryString() + ") ";
		//SQLQuery sqlQuery = session.createSQLQuery(queryString);
		//sqlQuery.setParameter(0, "ANHBUS01");
		//sqlQuery.addEntity("bookingDisplay");

		System.out.println(list);
	}

}
