package amitk.poc.quartz.simple;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Mar 25, 2010
 * Time: 12:09:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleStatefulJob implements StatefulJob{
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Integer executionNo = (Integer)jobExecutionContext.getJobDetail().getJobDataMap().get("ExecutionNo");
        if(null == executionNo)
            executionNo = 0;

        jobExecutionContext.getJobDetail().getJobDataMap().put("ExecutionNo", ++executionNo);

        System.out.println("Starting Execution of " + jobExecutionContext.getJobDetail().getName() + ", Exec#: " + executionNo);

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("ENDING Execution of " + jobExecutionContext.getJobDetail().getName() + ", Exec#: " + executionNo);
    }
}
