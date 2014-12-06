package poc.gates.nightlybatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class NightlyBatchRunner {

    Logger log = LoggerFactory.getLogger(NightlyBatchRunner.class);

    public static void main(String[] args) {

        NightlyBatchRunner obj = new NightlyBatchRunner();
        obj.run();

    }

    private void run() {

//        String[] springConfig = { "spring/batch/config/job-definitions.xml" };
        String[] springConfig = { "spring/batch/config/job-definitions-jobstep.xml" };

        ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        JobExplorer jobExplorer = (JobExplorer) context.getBean("jobExplorer");
        JobOperator jobOperator = (JobOperator) context.getBean("jobOperator");
        JobRepository jobRepository = (JobRepository) context.getBean("jobRepository");
        Job job = (Job) context.getBean("gates-nightly-batch-job");
//        Job job = (Job) context.getBean("create-orders-job");
//        Job job = (Job) context.getBean("create-invoices-job");

        try {

            JobParameters param = new JobParametersBuilder().addLong("id", 1L).toJobParameters();
            //JobParameters param = new JobParametersBuilder().addString("name", "user_c").toJobParameters();

            JobExecution execution = jobRepository.getLastJobExecution(job.getName(), param);
            if(null != execution){

                if(!BatchStatus.COMPLETED.equals(execution.getStatus())){
                    log.info("lastJobExecutionStatus: {}, execution: {}", execution.getStatus(), execution);
                    execution.upgradeStatus(BatchStatus.STOPPED);
                    execution.setEndTime(new Date());
                    jobRepository.update(execution);
                    Long restart = jobOperator.restart(execution.getId());
                    log.info("Restarted: {}", restart);
                }
            }else {
                log.info("No previous executions, starting a new execution/instance");
                execution = jobLauncher.run(job, param);
            }
            log.info("Job Status : {}", execution.getStatus());
            log.info("Failure Exceptions: {}", execution.getAllFailureExceptions());


        } catch (Exception e) {
            log.error("Execution failure", e);

        }

        System.out.println("Done");

    }

}
