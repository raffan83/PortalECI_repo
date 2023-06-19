package it.portalECI.action;

import java.io.File;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.Properties;

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
import it.portalECI.Util.AsymmetricCryptography;
import it.portalECI.Util.Costanti;



/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
//    public ContextListener() {
//        // TODO Auto-generated constructor stub
//    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event)  { 
    	// initialize log4j here
        ServletContext context = event.getServletContext();

        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + "WEB-INF\\log4j.properties";
        try {
        	configCostantApplication();
			startScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			ECIException.callException(e);
			e.printStackTrace();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        PropertyConfigurator.configure(fullPath);
    }
    
    
    
    public void configCostantApplication() throws Exception {
    	
    	//String resourceName = "config.properties"; // could also be a constant
    	String resourceName = "config_svil.properties"; // could also be a constant
   
    	ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	Properties props = new Properties();
    	try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
    	    props.load(resourceStream);
    	}
    	
    	AsymmetricCryptography ac = new AsymmetricCryptography();
	//	PrivateKey privateKey = ac.getPrivate("c:\\privateKey");
		PublicKey publicKey = ac.getPublic("c:\\pKey\\publicKey");
		
    	
		Costanti.PASS_EMAIL=ac.decryptText(props.getProperty("PASS_EMAIL"), publicKey);
		Costanti.PASS_PEC=ac.decryptText(props.getProperty("PASS_PEC"),publicKey);
    	
    }
    
    
public void startScheduler() throws SchedulerException {
    	
    	JobDetail job = JobBuilder.newJob(AggiornamentoCampioneScheduler.class).withIdentity("aggiornaCampione", "group1").build();    	
    	JobDetail jobScadenzaVentennale = JobBuilder.newJob(ControlloScadenzaVentennaleScheduler.class).withIdentity("scadenzaVentennale", "group2").build();
    	
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("aggiornaCampione", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(12).repeatForever())
                .build();
        
        Trigger trigger2 = TriggerBuilder
                .newTrigger()
                .withIdentity("scadenzaVentennale", "group2").withSchedule(
                        CronScheduleBuilder.cronSchedule("0 0 7 * * ?"))
                   .build();
        


        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
        scheduler.scheduleJob(jobScadenzaVentennale, trigger2);

    }
	
}
