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
}
