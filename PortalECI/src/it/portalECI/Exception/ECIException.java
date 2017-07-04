package it.portalECI.Exception;


import org.apache.log4j.Logger;

public class ECIException {
	
	static final Logger logger = Logger.getLogger(ECIException.class);
	
	public static String[] callException(Exception ex)
	{
		//logger.debug(ex);
		logger.error( "failed!", ex );
		StackTraceElement[] element=ex.getStackTrace();
		String[] buff= new String[element.length+1];
		buff[0]=ex.toString();
		for(int i=0;i<element.length;i++)
		{
		buff[i+1]=(element[i].getClassName()+"."+element[i].getMethodName()+"("+element[i].getFileName()+":"+element[i].getLineNumber()+")");
		}
		
		return buff;
	}
}