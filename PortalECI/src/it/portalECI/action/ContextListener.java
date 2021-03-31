package it.portalECI.action;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import it.portalECI.Exception.ECIException;


/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	// initialize log4j here
        ServletContext context = event.getServletContext();
       
         
        try {
        	
			startScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			ECIException.callException(e);
			e.printStackTrace();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
    
public void startScheduler() throws SchedulerException {
    	
    	JobDetail job = JobBuilder.newJob(AggiornamentoCampioneScheduler.class).withIdentity("aggiornaCampione", "group1").build();    	
    	
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("aggiornaCampione", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(12).repeatForever())
                .build();
        


        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }
	
}
