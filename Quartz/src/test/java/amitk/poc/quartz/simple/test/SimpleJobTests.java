package amitk.poc.quartz.simple.test;

import static junit.framework.TestCase.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

import amitk.poc.quartz.simple.SimpleJob;

public class SimpleJobTests {
	
	static Scheduler scheduler;
	static int scheduleCount = 0; 
	
	@BeforeClass
	public static void setup() throws Exception{
		SchedulerFactory sFactory = new StdSchedulerFactory();
		scheduler = sFactory.getScheduler();
		scheduler.start();
	}
	
	@Test
	public void startSimpleJob() throws Exception{
		scheduleCount = 0;
		scheduler.addJobListener(new SimpleJobListener());

		JobDetail jobDetail = new JobDetail("simpleJob", null, SimpleJob.class);
		jobDetail.addJobListener("SimpleJobListener");
		jobDetail.setVolatility(true);
		Trigger trigger = TriggerUtils.makeImmediateTrigger(0, 1);
		//trigger.setStartTime(TriggerUtils.getEvenHourDate(new Date()));
		trigger.setName("Every minute simple job trigger");
		
		
		scheduler.scheduleJob(jobDetail, trigger);

		Thread.sleep(2000);
		assertEquals(1, scheduleCount);
	}
	
	public class SimpleJobListener implements JobListener{

		public String getName() {
			// TODO Auto-generated method stub
			return "SimpleJobListener";
		}

		public void jobExecutionVetoed(JobExecutionContext context) {
			// TODO Auto-generated method stub
			
		}

		public void jobToBeExecuted(JobExecutionContext context) {
			// TODO Auto-generated method stub
			
		}

		public void jobWasExecuted(JobExecutionContext context,
				JobExecutionException jobException) {
			scheduleCount++;
			
		}

	}
}
