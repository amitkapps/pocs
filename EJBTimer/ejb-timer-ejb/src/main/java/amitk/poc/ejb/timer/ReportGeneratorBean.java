package amitk.poc.ejb.timer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

/**
 https://community.jboss.org/wiki/DeployingEJB3TimersInCluster
 NOTE: make sure the DefaultDS of the cluster members point to the same DB schema.
 https://community.jboss.org/message/204473
 https://community.jboss.org/wiki/SetUpMysqlAsDefaultDS (Old Jboss, mentions locations of samples)

 */
@Stateless
@Remote(ReportGenerator.class)
public class ReportGeneratorBean implements ReportGenerator {
    Logger logger = Logger.getLogger(ReportGenerator.class.getSimpleName());

    @Resource
    private SessionContext ctx;


    @PostConstruct
    public void init(){
        logger.info("ReportGenerator running on this node");
    }

    @Override
    public void scheduleTimer(long initialDuration, long intervalDuration, Serializable info) {
        logger.info("Create Timer info=" + info);
        ctx.getTimerService().createTimer(initialDuration, intervalDuration, info);
    }

    @Override
    public void cancelTimer(Serializable info)
    {
        logger.info("Cancel timer [info=" + info + "]");
        Timer timer = getTimer(info);
        if(null != timer)
            timer.cancel();
        else
            logger.info("Timer not found");
    }

    private Timer getTimer(Serializable info){
        Collection<Timer> timers = ctx.getTimerService().getTimers();
        for (Timer timer : timers) {
            if (timer.getInfo().equals(info)) {
                logger.info("Timer[info=" + info + "] found");
                return timer;
            }
        }
        logger.info("Timer info=" + info + " NOT found");
        return null;
    }

    @Override
    public boolean timerExists(Serializable info){
        return null != getTimer(info);
    }

    @Timeout
    public void timeoutHandler(Timer timer) throws Exception
    {
        logger.info("Received timer event on " + System.getProperty("weblogic.Name") + ", Info:" + timer.getInfo());
    }
}
