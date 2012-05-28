package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.AuthenticationChannel;
import org.gemsjax.client.communication.channel.handler.AuthenticationChannelHandler;
import org.gemsjax.client.experiment.ExperimentUserImpl;
import org.gemsjax.client.module.handler.AuthenticationModuleHandler;
import org.gemsjax.shared.communication.message.system.LoginMessage;
import org.gemsjax.shared.communication.message.system.LogoutMessage;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage.LoginAnswerStatus;
import org.gemsjax.shared.communication.message.system.LogoutMessage.LogoutReason;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * This module handles the authentication between client and server and will do the login and logout operations
 * @author Hannes Dorfmann
 *
 */
public class AuthenticationModule implements AuthenticationChannelHandler {
	
	private AuthenticationChannel authenticationChannel;
	private RegisteredUser authenticatedUser;
	private Set<AuthenticationModuleHandler> handlers;
	
	public AuthenticationModule(AuthenticationChannel channel)
	{
		authenticationChannel = channel;
		authenticationChannel.addAuthenticationChannelHandler(this);
		handlers = new LinkedHashSet<AuthenticationModuleHandler>();
	}
	
	public void addAuthenticationModuleHandler(AuthenticationModuleHandler handler)
	{
		handlers.add(handler);
	}
	
	
	public void removeAuthenticationonLoginSuccessfulModuleHandler(AuthenticationModuleHandler h)
	{
		handlers.remove(h);
	}

	/**
	 * Do a login (sign in) by sending a {@link LoginMessage} over the {@link AuthenticationChannel} to the server
	 * @param username
	 * @param password
	 * @throws IOException
	 */
	public void doLogin(String username, String password) throws IOException
	{
		authenticationChannel.doLogin(username, password);
	}
	
	
	public void doExperimentLogin(String verificationCode, String password) throws IOException{
		authenticationChannel.doExperimentLogin(verificationCode, password);
	}

	/**
	 *  Do a logout by sending a {@link LogoutMessage} over the {@link AuthenticationChannel} to the server
	 * @throws IOException
	 */
	public void doLogout() throws IOException
	{
		authenticationChannel.doLogout();
	}
	
	
	public RegisteredUser getCurrentlyAuthenticatedUser()
	{
		return authenticatedUser;
	}
	
	
	@Override
	public void onLogout(LogoutReason reason) {
		fireOnLogout(reason);
	}

	
	
	

	@Override
	public void onLoginAnswer(LoginAnswerStatus answerStatus, RegisteredUser authenticatedUser, long unreadNotificationRequest) {
		
		
		if (answerStatus==LoginAnswerStatus.FAIL)
		{
			fireOnLoginFailed();
		}
		else
		if (answerStatus==LoginAnswerStatus.OK)
		{
			this.authenticatedUser = authenticatedUser;
			fireOnLoginSuccessful(authenticatedUser, unreadNotificationRequest);
		}
		
		
	}

	@Override
	public void onParseError(Exception e) {
		fireOnParseError(e);
	}
	
	

	private void fireOnLogout(LogoutReason reason)
	{
		for (AuthenticationModuleHandler h: handlers)
			h.onLogoutReceived(reason);
	}
	
	
	private void fireOnLoginSuccessful(RegisteredUser authenticatedUser, long unreadNotificationRequest)
	{
		for (AuthenticationModuleHandler h: handlers)
			h.onLoginSuccessful(authenticatedUser,unreadNotificationRequest);
	}

	
	private void fireOnLoginFailed()
	{
		for (AuthenticationModuleHandler h: handlers)
			h.onLoginFailed();
	}
	
	
	private void fireOnParseError(Exception e)
	{
		for (AuthenticationModuleHandler h: handlers)
			h.onParseError(e);
	}

	@Override
	public void onExperimentLoginSuccessful(ExperimentUserImpl user) {
		
		for (AuthenticationModuleHandler h: handlers)
			h.onExperimentLoginSuccessful(user);
		
	}
	

}
