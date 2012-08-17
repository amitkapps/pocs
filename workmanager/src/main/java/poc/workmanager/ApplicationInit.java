package poc.workmanager;

import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commonj.work.Work;
import commonj.work.WorkManager;

public class ApplicationInit implements ServletContextListener{

	private static Logger log = LoggerFactory.getLogger(ApplicationInit.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			InitialContext ctx = new InitialContext();
			WorkManager workManager = (WorkManager)ctx.lookup("java:comp/env/wm/default");
			log.debug("got default workmanager: " + workManager);
			workManager = (WorkManager)ctx.lookup("java:comp/env/wm/cas-scheduler-wm");
			log.info("got workmanager: " + workManager);
			
			workManager.schedule(new Work() {
				
				@Override
				public void run() {
					log.info("Running from a workmanager" + this.getClass());
					
				}
				
				@Override
				public void release() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean isDaemon() {
					// TODO Auto-generated method stub
					return false;
				}
			});
			
		}catch (Exception e) {
			log.error("Error during workmanager lookup!", e);
			e.printStackTrace();
		}
	}

}
