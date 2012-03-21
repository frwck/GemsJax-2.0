package org.gemsjax.client.util;
/**
 * A little utility to log string messages in the browsers console
 * @author Hannes Dorfmann
 *
 */
public class Console {

	public static native void log(String str)  /*-{
		console.log(str);
	}-*/;
	
	private static String generateStackTrace(Throwable caught)
	{
		String st = caught.getClass().getName() + ": " + caught.getMessage();
		for (StackTraceElement ste : caught.getStackTrace())
			st += "\n" + ste.toString();
		
		return st;
	}
	
	public static native void logException(Throwable t)  /*-{
		console.log(@org.gemsjax.client.util.Console::generateStackTrace(Ljava/lang/Throwable;)( t ) );
	
}-*/;
}

