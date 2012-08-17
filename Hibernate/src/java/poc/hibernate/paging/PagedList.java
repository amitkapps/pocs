package poc.hibernate.paging;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import poc.hibernate.util.HibernateSessionFactory;

public class PagedList {
	public static void main(String[] args) {
		HibernateSessionFactory.setConfigFile("greeting.hibernate.cfg.xml");
		Session session = HibernateSessionFactory.getSession();
		
		Query query = session.createQuery("select count(*) from UserGreeting UserGreeting where UPPER(UserGreeting.userId) like UPPER('1%') ");
		//String queryString = "select * from (" + query.getQueryString() + ") ";
		//SQLQuery sqlQuery = session.createSQLQuery(queryString);
		//sqlQuery.setParameter(0, "ANHBUS01");
		//sqlQuery.addEntity("", "bookingDisplay", false, LockMode.READ);

		System.out.println(query.uniqueResult());
	}

}
