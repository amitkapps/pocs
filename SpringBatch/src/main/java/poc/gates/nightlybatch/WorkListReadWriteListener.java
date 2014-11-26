package poc.gates.nightlybatch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 5/28/14
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkListReadWriteListener implements ItemReadListener<WorkList>
        , ItemWriteListener<WorkList>{

    Logger log = LoggerFactory.getLogger(WorkListReadWriteListener.class);
    private StepExecution stepExecution;

    public void beforeRead() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void afterRead(WorkList item) {
//        log.info("Item: {}", item);
        stepExecution.getExecutionContext().putString(stepExecution.getStepName() + ".itemRead", String.valueOf(item));
    }

    public void onReadError(Exception ex) {
        //To change body of implemented methods use File | Settings | File Templates.
        log.error("Read Exception {}", ex.getCause());

    }

    @BeforeStep
    public void storeStepExecution(StepExecution stepExecution){
        this.stepExecution = stepExecution;
    }

    public void beforeWrite(List<? extends WorkList> items) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void afterWrite(List<? extends WorkList> items) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onWriteError(Exception exception, List<? extends WorkList> items) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
