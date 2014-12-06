package poc.gates.nightlybatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.dao.JdbcJobExecutionDao;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 12/6/14
 * Time: 03:28
 * To change this template use File | Settings | File Templates.
 */
public class AutoRestartFailedJobsJobLauncer extends SimpleJobLauncher {
    Logger log = LoggerFactory.getLogger(AutoRestartFailedJobsJobLauncer.class);

    private JobRepository jobRepository;
    private JobOperator jobOperator;
    private JobExplorer jobExplorer;

    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
        super.setJobRepository(jobRepository);
    }

    public JobOperator getJobOperator() {
        return jobOperator;
    }

    public void setJobOperator(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    public JobExplorer getJobExplorer() {
        return jobExplorer;
    }

    public void setJobExplorer(JobExplorer jobExplorer) {
        this.jobExplorer = jobExplorer;
    }

    @Override
    public JobExecution run(Job job, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        log.info("Attempting to execute job {} with parameters {}", job.getName(), jobParameters);
        JobExecution execution = jobRepository.getLastJobExecution(job.getName(), jobParameters);
        if (null != execution) {

            if (!BatchStatus.COMPLETED.equals(execution.getStatus())) {
                log.info("lastJobExecutionStatus: {}, execution: {}", execution.getStatus(), execution);
                execution.upgradeStatus(BatchStatus.STOPPED);
                execution.setEndTime(new Date());
                jobRepository.update(execution);
                Long restart = null;
                try {
                    log.info("Restarting job execution {} for job {}", execution.getId(), job.getName());
                    restart = jobOperator.restart(execution.getId());
                    return jobExplorer.getJobExecution(restart);
                } catch (NoSuchJobExecutionException e) {
                    log.error("Can't find the job execution", e);
                    throw new RuntimeException(e);
                } catch (NoSuchJobException e) {
                    log.error("Can't find the job", e);
                    throw new RuntimeException(e);
                }
            } else {
                log.info("Job {} with parameters {} is already complete, executionId {} ", new Object[]{job.getName(), jobParameters, execution.getId()});
                return execution;
            }
        } else {
            log.info("job {}, params {} had No previous executions, starting a new execution/instance", job.getName(), jobParameters);
            return super.run(job, jobParameters);
        }
    }
}

