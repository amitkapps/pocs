package poc.gates.nightlybatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 11/25/14
 * Time: 10:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorkListProcessor implements ItemProcessor<WorkList, WorkList> {

    Logger log = LoggerFactory.getLogger(WorkListProcessor.class);
    private StepExecution stepExecution;
    private String errorString = "XXX";

    @BeforeStep
    public void storeStepExecution(StepExecution stepExecution){
        this.stepExecution = stepExecution;
    }

    public void setErrorString(String errorString){
        this.errorString = errorString;
    }

    public WorkList process(WorkList workList) throws Exception {
        log.info("Step {}, Processing workList item {}", stepExecution.getStepName(), workList.getId() + workList.getWorkName());
        if(workList.getWorkName().contains(errorString)){
            throw new RuntimeException("ErrorString: " + errorString + " at " + workList.getId() + workList.getWorkName() + ", step: " + stepExecution.getStepName());
        }
        workList.setProcessedBy(workList.getProcessedBy()+stepExecution.getStepName() + ";");
        Thread.sleep(1000);
        return workList;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
