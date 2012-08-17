package poc.hibernate.caching;

import java.util.List;
import java.util.Random;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.AnyType;
import org.hibernate.type.LongType;
import org.hibernate.type.PrimitiveType;
import org.hibernate.type.StringType;

import poc.hibernate.util.HibernateSessionFactory;

public class QueryCaching {

	public static void main(String[] args) throws Exception{
		int times = 30;
		long sleepTime = 3000;
		Session thisSession = getSession();
		boolean flushCache = false;
		
		for (int i = 0; i < times; i++) {
			start();
			fireQuery(session, flushCache);
			end();
			showElapsed();
			Thread.sleep(sleepTime);
		}
		closeSession();
	}
	
	private static void fireQuery(Session session, boolean flushCache){
		//fireNamedQuery(session, flushCache);
		//fireHQL(session, flushCache);
		fireNativeSql(session, flushCache);
	}
	
	private static Session session;
	public static Session getSession(){
		if(session == null){
			System.out.println("Initializing session factory & getting session");
			HibernateSessionFactory.setConfigFile("/poc/hibernate/caching/caching.hibernate.cfg.xml");
			session = HibernateSessionFactory.getSession();
		}
		return session;
	}
	
	public static void closeSession(){
		if(session != null){
			System.out.println("Closing session.");
			session.close();
		}
	}
	
	public static void fireNamedQuery(Session session, boolean flushCache){
		String namedQuery = "greetings.from.oldies";
		String cacheRegion = "TEST_REGION";
		if(flushCache)
			session.getSessionFactory().evictQueries(cacheRegion);
		
		Query query = session.getNamedQuery(namedQuery);
		String queryString = "select userId from (" + query.getQueryString() + ") ";
		//Query caching uses exact query match to find the cached result.
		//queryString += getRandomSpaces();
		SQLQuery sqlQuery = session.createSQLQuery(queryString);
		//sqlQuery.addEntity("userGreeting");
		sqlQuery.addScalar("userId", new StringType());
		sqlQuery.setCacheable(true).setCacheRegion(cacheRegion);
		int resultSize = sqlQuery.list().size();
		
		//You need to get the results of the first query before you fire another one, else caching doesn't work!.
		String countQueryString = "select count(*) as counts from (" + query.getQueryString() + ") ";
		SQLQuery countSQLQuery = session.createSQLQuery(countQueryString);
		countSQLQuery.addScalar("counts", new StringType());
		countSQLQuery.setCacheable(true).setCacheRegion(cacheRegion);
		String counts = String.valueOf(countSQLQuery.uniqueResult());
		System.out.println("NAMED QUery: Counts= " + counts +  ", ResultSize= " + resultSize + ". ");
	}

	
	public static void fireHQL(Session session, boolean flushCache){
		String cacheRegion = "TEST_REGION";
		if(flushCache)
			session.getSessionFactory().evictQueries(cacheRegion);

		String queryString = "from Mail";
		//It isn't affected by random spaces at least
		//queryString += getRandomSpaces();
		Query query = session.createQuery(queryString);
		query.setCacheable(true);
		query.setCacheRegion(cacheRegion);
		
		int resultSize = query.list().size();
		//You need to get the results of the first query before you fire another one, else caching doesn't work!.
		String countQueryString = "select count(*) from Mail";
		Query countQuery = session.createQuery(countQueryString);
		countQuery.setCacheable(true);
		query.setCacheRegion(cacheRegion);
		String counts = String.valueOf(countQuery.uniqueResult());
		System.out.println("HQL: Counts= " + counts +  ", ResultSize= " + resultSize + ". ");
	}
	
	private static void fireNativeSql(Session session, boolean flushCache){
		String sqlQuery = "select emp_id, emp_first_name from employee_mt";
		SQLQuery query = session.createSQLQuery(sqlQuery);
		query.setCacheable(true).setCacheRegion("TEST_REGION");
		query.addScalar("emp_id");
		query.addScalar("emp_first_name");
		List list = query.list();
		Object obj = list.get(0);
		String counts = String.valueOf(list.size());
		System.out.println("NATIVE QUERY: Counts=" + counts);
	}
	
	private static long startTime;
	public static void start(){
		startTime = System.currentTimeMillis();
	}
	
	private static long endTime;
	public static void end(){
		endTime = System.currentTimeMillis();
	}
	
	public static void showElapsed(){
		System.out.println("--------------------------------------------------Elapsed " + (endTime - startTime) + " millis.");
	}
	
	private static Random random = new Random();
	public static String getRandomSpaces(){
		int noSpaces = random.nextInt(20);
		System.out.println(noSpaces);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < noSpaces; i++) {
			sb.append(" ");
		}
		
		return sb.toString();
	}
}
