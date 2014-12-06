package poc.gates.nightlybatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.listener.JobListenerFactoryBean;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 12/6/14
 * Time: 04:32
 * To change this template use File | Settings | File Templates.
 */
public class ParentJobListener implements JobExecutionListener{
    Logger logger = LoggerFactory.getLogger(ParentJobListener.class);

    public void beforeJob(JobExecution jobExecution) {
        logger.info("Before job {} with param {}", jobExecution.getJobInstance().getJobName(), jobExecution.getJobInstance().getJobParameters());
    }

    public void afterJob(JobExecution jobExecution) {
        logger.info("After job {} with param {}", jobExecution.getJobInstance().getJobName(), jobExecution.getJobInstance().getJobParameters());
    }
}
