package poc.gates.nightlybatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Oct 18, 2010
 * Time: 4:49:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleTasklet implements Tasklet{
    Logger log = LoggerFactory.getLogger(SampleTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String stepName = chunkContext.getStepContext().getStepName();
        log.info("Starting step {}", stepName);
        for (int i=0; i<10; i++){
            log.debug("Processing item# {}", i);
            Thread.sleep(1000);
        }
        log.info("Completing step {}", stepName);
        return RepeatStatus.FINISHED;
    }
}
