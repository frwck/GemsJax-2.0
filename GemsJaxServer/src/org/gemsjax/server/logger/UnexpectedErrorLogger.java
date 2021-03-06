package org.gemsjax.server.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This is a logger to log unexpected errors
 * @author Hannes Dorfmann
 *
 */
public class UnexpectedErrorLogger {
	
	private static Logger logger = Logger.getLogger(UnexpectedErrorLogger.class.getName());
	
	private static Handler handler;
	private static Formatter formatter;
	
	
	private static UnexpectedErrorLogger INSTANCE = null;
	
	private UnexpectedErrorLogger() throws SecurityException, IOException
	{
		handler = new FileHandler("UnexpecterErrorLogger");
		formatter = new SimpleFormatter();
		
		handler.setFormatter(formatter);
		logger.addHandler(handler);
		
	}
	
	
	private static void initInstanceIfNeeded() throws SecurityException, IOException
	{
		if (INSTANCE == null) 
			INSTANCE = new UnexpectedErrorLogger();
	}
	
	
	public static synchronized void severe(String msg)
	{
		try {
			
			initInstanceIfNeeded();
			
			logger.severe(msg);
			
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static synchronized void warning(String msg)
	{
		try {
			
			initInstanceIfNeeded();
			
			logger.warning(msg);
			
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
