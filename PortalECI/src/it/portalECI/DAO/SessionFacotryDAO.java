package it.portalECI.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFacotryDAO {
	/*private static SessionFactory sessionFactory;
	
	public static SessionFactory get() throws Exception {
//		Otteniamo una SessionFactory per la nostra applicazione
		sessionFactory = new Configuration().configure().buildSessionFactory();
		return sessionFactory;
	}
	
	public static void shutDown(SessionFactory sf) throws Exception {
		if ( sf != null ) 
		{
			sf.close();
		}
	}*/

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory get() {
		return sessionFactory;
	}
	    
	public static void shutDown(SessionFactory sf) throws Exception {
		if ( sf != null ) {
			sf.close();
		}
	}
	
}
