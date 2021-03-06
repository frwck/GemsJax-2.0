package org.gemsjax.shared;

import org.gemsjax.shared.user.RegisteredUser;

/**
 * This is a configuration class containing the paths to servers various servlets
 * @author Hannes Dorfmann
 *
 */
public class ServletPaths {
	
	

	public static final String SERVER_URL="http://localhost:8081";
	
	
	/**
	 * The path to the live communication web socket.
	 */
	public static final String LIVE_WEBSOCKET = "/servlets/liveCommunication";
	
	
	/**
	 * The Path to the Registration servlet, to create a new {@link RegisteredUser} account.
	 */
	public static final String REGISTRATION = "/servlets/registration";
	
	
	
	public static final String SEARCH = "/servlets/search";
	
	
	public static final String ICON_UPLOAD ="/servlets/icon-upload";
	
	
	public static final String EXPERIMENT ="/experiment";
	
	
	
	
	private ServletPaths(){}
	
}
