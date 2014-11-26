package poc.gates.nightlybatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowire;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 11/26/14
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class StepFileNameGenerator {
    Logger log = LoggerFactory.getLogger(StepFileNameGenerator.class);

    private StepExecution stepExecution;

    @BeforeStep
    public void storeStepExecution(StepExecution stepExecution){
        this.stepExecution = stepExecution;
    }

    public String getFileName(){
        log.info("Step Execution: {}", stepExecution);
        return  "csv/outputs/workList"+stepExecution.getStepName()+".txt";
    }
}
