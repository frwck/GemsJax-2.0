package org.gemsjax.shared.communication.channel;

import java.util.Map;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.message.Message;

/**
 * A {@link InputMessage} is a message that is delivered by the underlying {@link CommunicationConnection} to
 * the registered {@link InputChannel}s by calling {@link InputChannel#onMessageReceived(InputMessage)}.
 * 
 * <br /><br />
 * This class wraps 2 kinds of communication data, that is received on the connections end point:
 * <ul>
 * 	<li>A normal XML message like the {@link Message#toXml()} content. This is normally the case when the client receives a textual XML representation from the server (this is the standard use case).
 * 		In that case use the {@link #InputMessage(String)} constructor or the {@link #InputMessage(int, String)} constructor. 
 * 		<b>Note</b> {@link #getText()} returns the message and optionally {@link #getStatusCode()} returns the http server response status code, {@link #getParameter(String)} returns null and {@link #getParametersMap()} returns null </li>
 * 	<li>Parameters Map: This is normally used by the server by creating a {@link InputMessage} with this {@link #InputMessage(Map)} constructor,
 * to work more comfortable with {@link HttpServletRequest}s (with servlets).
 * In that case use {@link #getParameter(String)} and {@link #getParametersMap()} to access the HTTP POST or GET parameters. <b>Note</b> that {@link #getStatusCode()} returns null and {@link #getText()} returns null
 * 		
 * </li>
 * </ul>
 * 
 * @author Hannes Dorfmann
 *
 */
public class InputMessage {
	
	
	/**
	 * The HTTP response status code.<br />
	 * For example: 200, 404, 500, 300, ... <br />
	 * 
	 */
	private Integer statusCode;
	
	/**
	 * The response message text, received from the server.  
	 * Is only set, if {@link ResponseStatus#VALID}
	 */
	private String message;
	
	/**
	 * Used to map parameters name to parameters value
	 */
	private Map<String, String[]> parametersMap;
	
	/**
	 * Create a new {@link InputMessage}. This is the constructor, the client should use, to wrap received messages from the server.
	 * @param message
	 */
	public InputMessage(String message)
	{
		this.message = message;
		this.statusCode = null;
		this.parametersMap = null;
	}
	
	/**
	 * Create a new {@link InputMessage}. This is the constructor, the client should use, to wrap received messages from the server.
	 * @param statusCode The HTTP status code
	 * @param message The received message as string (must be parsed by a parser)
	 * @see ResponseStatus
	 */
	public InputMessage(int statusCode, String message)
	{
		this.statusCode = statusCode;
		this.message = message;
		this.parametersMap = null;
	}
	
	/**
	 * Create a InputMessage with a {@link Map} containing the parameter as key and the parameter values as value.
	 * <b>Note</b> that a value in this map is an Array of Strings. So a parameter key, can have more than one then one values.
	 * That is not the normal way (normally you simply have one key mapped to one value), but however javax.servlets support this
	 * multiple value per key. 
	 * 
	 * @param parametersMap
	 */
	public InputMessage(Map<String, String[]> parametersMap)
	{
		this.parametersMap = parametersMap;
		this.statusCode = null;
		this.message = null;
	}
	
	/**
	 * Get the associated parameter value for the passed parameters name.
	 * This method is normally used only on server side to work with {@link HttpServletRequest}s in a more comfortable way.
	 * @param parameterName
	 * @return The associated value or null, if no value is set for this parameterName
	 * @throws RuntimeException if more than one value is found for the passed parameterName
	 */
	public String getParameter(String parameterName) throws RuntimeException
	{
		if (parametersMap == null)
			return null;
		
		String[] values = parametersMap.get(parameterName);
		
		if (values == null || values.length==0)
			return null;
		
		
		if (values.length>1)
			throw new RuntimeException("The parameterName \""+parameterName+"\" is not unique, because there are more than one values");
		
		return values[0];
	}
	
	/**
	 * Get a {@link Map} containing the parameter as key and the parameter values as value.
	 * <b>Note</b> that a value in this map is an Array of Strings. So a parameter key, can have more than one then one values.
	 * That is not the normal way (normally you simply have one key mapped to one value), but however javax.servlets support this
	 * multiple value per key. 
	 * @return The {@link Map}&lt;String, String[]&gt; or null, if the {@link #parametersMap} is not set
	 */
	public Map<String, String[]> getParametersMap()
	{
		return parametersMap;
	}
	
	
	/**
	
	 * Get the HTTP response status code.<br />
	 * For example: 200, 404, 500, 300, ... <br />
	 * @return the HTTP status code or null, if not set
	 */
	public Integer getStatusCode() {
		return statusCode;
	}


	/**
	 * Get the response message text (normally the xml formatted server response)  
	 * @return the received message text or null, if not set
	 */
	public String getText() {
		return message;
	}


}
