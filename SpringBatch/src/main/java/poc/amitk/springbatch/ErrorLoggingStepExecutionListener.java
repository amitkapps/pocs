package poc.amitk.springbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 5/29/14
 * Time: 1:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class ErrorLoggingStepExecutionListener implements StepExecutionListener{
    Logger log = LoggerFactory.getLogger(ErrorLoggingStepExecutionListener.class);
    public void beforeStep(StepExecution stepExecution) {
        //To change body of implemented methods use File | Settings | File Templates.
        log.info("Starting step: {}", stepExecution.getSummary());
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        if(stepExecution.getStatus().isUnsuccessful()){
            log.error("Step summary: {}, Step Execution Context: {}", stepExecution.getSummary(), stepExecution.getExecutionContext());
            log.error("Exceptions: {}", stepExecution.getFailureExceptions());
        }
        return stepExecution.getExitStatus();
    }
}
