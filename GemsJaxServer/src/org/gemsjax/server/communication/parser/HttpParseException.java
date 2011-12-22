package org.gemsjax.server.communication.parser;

/**
 * This exception is thrown, when a http request can not be parsed, because the parameters are not set as expected
 * @author Hannes Dorfmann
 *
 */
public class HttpParseException extends Exception {
	
	
	public HttpParseException(String msg)
	{
		super(msg);
	}

}
