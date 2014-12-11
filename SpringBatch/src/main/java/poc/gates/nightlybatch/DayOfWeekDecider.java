package poc.gates.nightlybatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 12/11/14
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class DayOfWeekDecider implements JobExecutionDecider {
    private Logger logger = LoggerFactory.getLogger(DayOfWeekDecider.class);

    private final int dayOfWeek;

    public DayOfWeekDecider(){
        dayOfWeek=-1;
    }


    public DayOfWeekDecider(int dayOfWeek){
        this.dayOfWeek = dayOfWeek;
    }


    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == this.dayOfWeek){
            logger.info("Today is the day");
            return new FlowExecutionStatus("TODAY_MATCHES");
        }
        else{
            logger.info("Today is not the day");
            return FlowExecutionStatus.COMPLETED;
        }
    }
}
