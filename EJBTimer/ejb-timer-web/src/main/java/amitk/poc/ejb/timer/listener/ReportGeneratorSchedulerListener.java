package amitk.poc.ejb.timer.listener;

import amitk.poc.ejb.timer.ReportGenerator;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 2/16/13
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportGeneratorSchedulerListener implements ServletContextListener {
    Logger logger = Logger.getLogger(ReportGeneratorSchedulerListener.class.getCanonicalName());

    public static final String jndi = "ReportGeneratorBean";

    //DOESNT WORK
    @EJB(mappedName = jndi, beanInterface = ReportGenerator.class)
    private ReportGenerator reportGenerator;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Inside Scheduler");
        try {
            reportGenerator = (ReportGenerator) new InitialContext().lookup(jndi);
            if(null == reportGenerator){
                logger.info("Report Generator is not running on this node");
                return;
            }

            //Jboss does not cause multiple timer callbacks if several executions were missed due to downtime
            if(!reportGenerator.timerExists("ReportGenerator")){
                logger.info("Timer doesn't exist, we'll create one");
                reportGenerator.scheduleTimer(20000, 20000, "ReportGenerator");
            }else {
                logger.info("Timer already exists");
            }
        } catch (NamingException e) {
            logger.info("The EJB is not deployed on this server, ignore...");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        logger.info("Cancelling ReportGenerator schedule");
//        if(null != reportGenerator)
//            reportGenerator.cancelTimer("ReportGenerator");
    }
}
