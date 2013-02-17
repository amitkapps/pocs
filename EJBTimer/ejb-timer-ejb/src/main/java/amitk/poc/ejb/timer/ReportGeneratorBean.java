package amitk.poc.ejb.timer;

import javax.annotation.Resource;
import javax.ejb.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 2/16/13
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@Remote(ReportGenerator.class)
public class ReportGeneratorBean implements ReportGenerator {
    Logger logger = Logger.getLogger(ReportGenerator.class.getSimpleName());

    @Resource
    private SessionContext ctx;


    @Override
    public void scheduleTimer(long initialDuration, long intervalDuration, Serializable info) {
        logger.info("Create initial+interval timer [info=" + info + "]");
        ctx.getTimerService().createTimer(initialDuration, intervalDuration, info);
    }

    @Override
    public void cancelTimer(Serializable info)
    {
        logger.info("Cancel timer [info=" + info + "]");
        Collection<Timer> timers = ctx.getTimerService().getTimers();
        for (Timer timer : timers)
        {
            if (timer.getInfo().equals(info))
            {
                logger.info("Timer[info=" + info + "] found, cancelling...");
                timer.cancel();
                logger.info("Timer[info=" + info + "] cancelled");
            }
        }
    }

    @Timeout
    public void timeoutHandler(Timer timer) throws Exception
    {
        logger.info("Received timer event on " + System.getProperty("weblogic.Name") + ", Info:" + timer.getInfo());
    }
}
