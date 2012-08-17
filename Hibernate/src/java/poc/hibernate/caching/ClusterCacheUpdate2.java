package poc.hibernate.caching;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import poc.hibernate.emp.vo.Project;
import poc.hibernate.util.HibernateSessionFactory;

public class ClusterCacheUpdate2 {

	public static void main(String[] args) throws Exception{
		initConfig();
		boolean flushCache = false;
		
		while(true){
			//start();
			Session session = sessionFactory.openSession();
			String transType = readInput("Transaction type-Read/Update (R/U)");
			if(transType.equalsIgnoreCase("U"))
				try{fireUpdateHQL(session);}catch(Exception e){e.printStackTrace();}
			else
				try{fireSelectHQL(session);}catch(Exception e){e.printStackTrace();}
			session.close();
			//end();
			//showElapsed();
		}
	}
	
	private static String readInput(String inputDescription) throws IOException{
		
		System.out.print(inputDescription + ":");
		String input = new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.println("Processing Input...");
		return input;
	}
	
	private static SessionFactory sessionFactory;
	public static void initConfig(){
		if(sessionFactory == null){
			System.out.println("Initializing session factory & getting session");
			HibernateSessionFactory.setConfigFile("/poc/hibernate/caching/ClusterCaching.hibernate.cfg.xml");
			sessionFactory = HibernateSessionFactory.getSessionFactory();
		}
	}

	public static void fireSelectHQL(Session session) throws IOException{

		String projectId = readInput("Select Project - Enter projectId");

		//It isn't affected by random spaces at least
		//queryString += getRandomSpaces();
		//Query query = session.createQuery(queryString);
		
		//System.out.println("HQL: data= " + query.list());
		System.out.println("Project: " + session.load(Project.class, new Long(projectId)));
	}
	
	
	public static void fireUpdateHQL(Session session) throws IOException{

		String projectCd = readInput("Update ProjectCd - Enter new ProjectCd");
		Project proj = (Project) session.load(Project.class, new Long(1));
		//String queryString = "Update Project set projectCd = ? where projectId = 1";
		proj.setProjectCd(projectCd);
		//It isn't affected by random spaces at least
		//queryString += getRandomSpaces();
		//Query query = session.createQuery(queryString);
		
		//System.out.println("HQL: data= " + query.list());
		session.beginTransaction();
		session.update(proj);
		session.getTransaction().commit();
		System.out.println("HQL: updated" + proj);
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
