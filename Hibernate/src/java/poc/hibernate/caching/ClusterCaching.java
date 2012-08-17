package poc.hibernate.caching;

import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import poc.hibernate.emp.vo.Project;
import poc.hibernate.util.HibernateSessionFactory;

public class ClusterCaching {

	public static void main(String[] args) throws Exception{
		int times = 3000;
		long sleepTime = 5000;
		initConfig();
		boolean flushCache = false;
		
		for (int i = 0; i < times; i++) {
			start();
			Session session = sessionFactory.openSession();
			fireQuery(session, flushCache);
			session.close();
			end();
			showElapsed();
			Thread.sleep(sleepTime);
		}
	}
	
	private static void fireQuery(Session session, boolean flushCache){
		//fireNamedQuery(session, flushCache);
		fireHQL(session);
		//fireNativeSql(session, flushCache);
	}
	
	private static SessionFactory sessionFactory;
	public static void initConfig(){
		if(sessionFactory == null){
			System.out.println("Initializing session factory & getting session");
			HibernateSessionFactory.setConfigFile("/poc/hibernate/caching/ClusterCaching.hibernate.cfg2.xml");
			sessionFactory = HibernateSessionFactory.getSessionFactory();
		}
	}
	
	static boolean toggle = true;
	
	public static void fireHQL(Session session){

		//String queryString = "from Project where projectId = 1";
		//It isn't affected by random spaces at least
		//queryString += getRandomSpaces();
		//Query query = session.createQuery(queryString);
		
		//System.out.println("HQL: data= " + query.list());
		System.out.println("HQL: data= " + session.load(Project.class, new Long((toggle ? 1 : 2))));
		toggle = !toggle;
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
