package amitk.poc.batch.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.SerializationException;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.support.ClassPathXmlApplicationContextFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class SchedulerController extends MultiActionController {
	
	private static Log log = LogFactory.getLog(SchedulerController.class);

	public ModelAndView schedule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String date = (new Date()).toString();
        log.info("Running job on " + date);

        //log.info("Serialization utils class loaded from : " + SerializationUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        
        ApplicationContext context = new ClassPathXmlApplicationContext("launch-context.xml");
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher", JobLauncher.class);
        Job job = (Job) context.getBean("job1", Job.class);
        
        log.info("Executing job: " + job);
        jobLauncher.run(job, new JobParameters());
        
        return new ModelAndView("scheduler", "date", date);
	}
	
	public static void main(String[] args) throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("launch-context.xml");
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher", JobLauncher.class);
        Job job = (Job) context.getBean("job1", Job.class);
        
        log.info("Executing job: " + job);
        jobLauncher.run(job, new JobParameters());
		
	}

}
