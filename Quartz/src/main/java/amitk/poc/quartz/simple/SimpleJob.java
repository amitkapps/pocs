package amitk.poc.quartz.simple;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("Executing simple job");

	}

}
