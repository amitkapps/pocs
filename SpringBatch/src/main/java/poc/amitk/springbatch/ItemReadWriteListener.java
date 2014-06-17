package poc.amitk.springbatch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 5/28/14
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemReadWriteListener implements org.springframework.batch.core.ItemReadListener<Object>
        , ItemWriteListener<Object>{

    Logger log = LoggerFactory.getLogger(ItemReadWriteListener.class);
    private StepExecution stepExecution;

    public void beforeRead() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void afterRead(Object item) {
        log.info("Item: {}", item);
        stepExecution.getExecutionContext().putString(ItemReadWriteListener.class + ".itemRead", String.valueOf(item));
        if(((User)item).id == 17)
            throw new RuntimeException("id=17");
    }

    public void onReadError(Exception ex) {
        //To change body of implemented methods use File | Settings | File Templates.
        log.error("Read Exception {}", ex.getCause());

    }

    public void beforeWrite(List<?> items) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void afterWrite(List<?> items) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onWriteError(Exception exception, List<?> items) {
        //To change body of implemented methods use File | Settings | File Templates.
        log.error("Write Error:", exception);
    }

    @BeforeStep
    public void storeStepExecution(StepExecution stepExecution){
        this.stepExecution = stepExecution;
    }

}
