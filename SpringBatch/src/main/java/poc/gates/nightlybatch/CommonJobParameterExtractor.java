package poc.gates.nightlybatch;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.step.job.JobParametersExtractor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class CommonJobParameterExtractor implements JobParametersExtractor {

    public JobParameters getJobParameters(Job job, StepExecution stepExecution) {

//		String jobName = job.getName();		
//		ExecutionContext ex = stepExecution.getJobExecution().getExecutionContext();
//		Date last_run_date = (Date)ex.get("last_run_date");
//		Date current_run_date = (Date)ex.get("current_run_date");
        JobParametersBuilder builder = new JobParametersBuilder();
        Map<String, JobParameter> jobParameters = stepExecution.getJobParameters().getParameters();
        for (String key : jobParameters.keySet()) {
            builder.addParameter(key, jobParameters.get(key));
        }
//		builder.addParameter("last_run_date", new JobParameter(last_run_date));
//		builder.addParameter("current_run_date", new JobParameter(current_run_date));

        return builder.toJobParameters();
    }

}
