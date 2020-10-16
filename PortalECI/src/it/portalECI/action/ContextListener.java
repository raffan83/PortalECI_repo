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
import it.portalECI.Util.Costanti;
 
@WebListener("application context listener")
public class ContextListener implements ServletContextListener {
 
    /**
     * Initialize log4j when the application is being started
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // initialize log4j here
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
         
        try {
        	//configCostantApplication();
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
     
//    public void configCostantApplication() throws Exception {
//	
//
//    	//String resourceName = "config.properties"; // could also be a constant
//    	String resourceName = "config_svil.properties"; // could also be a constant
//   
//    	ClassLoader loader = Thread.currentThread().getContextClassLoader();
//    	Properties props = new Properties();
//    	try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
//    	    props.load(resourceStream);
//    	}
//    	
//    	AsymmetricCryptography ac = new AsymmetricCryptography();
//	//	PrivateKey privateKey = ac.getPrivate("c:\\privateKey");
//		PublicKey publicKey = ac.getPublic("c:\\pKey\\publicKey");
//		
//    	
//		Costanti.PATH_FOLDER=ac.decryptText(props.getProperty("PATH_FOLDER"), publicKey);
//		Costanti.PATH_FOLDER_CAMPIONI=ac.decryptText(props.getProperty("PATH_FOLDER_CAMPIONI"), publicKey);
//		Costanti.CON_STR_MYSQL=ac.decryptText(props.getProperty("CON_STR_MYSQL"), publicKey);
//		Costanti.CON_STR_MYSQL_USR=ac.decryptText(props.getProperty("CON_STR_MYSQL_USR"), publicKey);
//		Costanti.CON_STR_MYSQL_PASS=ac.decryptText(props.getProperty("CON_STR_MYSQL_PASS"), publicKey);
//		Costanti.CON_STR_SQLSRV=ac.decryptText(props.getProperty("CON_STR_SQLSRV"), publicKey);
//		Costanti.PATH_FOLDER_CALVER=ac.decryptText(props.getProperty("PATH_FOLDER_CALVER"), publicKey);
//		Costanti.PATH_FOLDER_LOGHI=ac.decryptText(props.getProperty("PATH_FOLDER_LOGHI"), publicKey);
//		Costanti.PATH_SCHEDA_ANAGRAFICA=ac.decryptText(props.getProperty("PATH_SCHEDA_ANAGRAFICA"), publicKey);
//		Costanti.PATH_FIRMA_DIGITALE=ac.decryptText(props.getProperty("PATH_FIRMA_DIGITALE"), publicKey);
//		Costanti.USR_SQL_SVR=ac.decryptText(props.getProperty("USR_SQL_SVR"), publicKey);
//		Costanti.USR_PASS_SVR=ac.decryptText(props.getProperty("USR_PASS_SVR"), publicKey);
//		Costanti.STATO_STRUMENTO_IN_SERVIZIO=Integer.parseInt(ac.decryptText(props.getProperty("STATO_STRUMENTO_IN_SERVIZIO"), publicKey));
//		Costanti.STATO_STRUMENTO_NON_IN_SERVIZIO=Integer.parseInt(ac.decryptText(props.getProperty("STATO_STRUMENTO_NON_IN_SERVIZIO"), publicKey));
//		Costanti.ID_TIPO_RAPPORTO_SVT=Integer.parseInt(ac.decryptText(props.getProperty("ID_TIPO_RAPPORTO_SVT"), publicKey));
//		Costanti.CIFRE_SIGNIFICATIVE=Integer.parseInt(ac.decryptText(props.getProperty("CIFRE_SIGNIFICATIVE"), publicKey));
//		Costanti.LOGO_EMAIL_FOOTER=ac.decryptText(props.getProperty("LOGO_EMAIL_FOOTER"), publicKey);
//		Costanti.EMAIL_EXCEPTION_REPORT=ac.decryptText(props.getProperty("EMAIL_EXCEPTION_REPORT"), publicKey);
//		Costanti.SALT_PEC=ac.decryptText(props.getProperty("SALT_PEC"), publicKey);
//		Costanti.HOST_MAIL_SYSTEM=ac.decryptText(props.getProperty("HOST_MAIL_SYSTEM"), publicKey);
//		Costanti.HOST_MAIL_SYSTEM_PWD=ac.decryptText(props.getProperty("HOST_MAIL_SYSTEM_PWD"), publicKey);
//		Costanti.HOST_MAIL_SYSTEM_SENDER=ac.decryptText(props.getProperty("HOST_MAIL_SYSTEM_SENDER"), publicKey);
//		Costanti.HOST_MAIL_SYSTEM_PORT=ac.decryptText(props.getProperty("HOST_MAIL_SYSTEM_PORT"), publicKey);
//		Costanti.MAIL_DEST_ALERT_PACCO=props.getProperty("MAIL_DEST_ALERT_PACCO");
//		
//	}

	@Override
    public void contextDestroyed(ServletContextEvent event) {
        // do nothing
    }  
    
    
    public void startScheduler() throws SchedulerException {
    	
    	JobDetail job = JobBuilder.newJob(AggiornaCampioneScheduler.class).withIdentity("aggiornaCampione", "group1").build();
    	
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
