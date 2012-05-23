package org.gemsjax.server.module;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.gemsjax.server.communication.channel.CollaborateableFileChannel;
import org.gemsjax.server.communication.channel.CollaborationChannel;
import org.gemsjax.server.communication.channel.ExperimentChannel;
import org.gemsjax.server.communication.channel.FriendsLiveChannel;
import org.gemsjax.server.communication.channel.LogoutChannel;
import org.gemsjax.server.communication.channel.NotificationChannel;
import org.gemsjax.server.communication.channel.RequestChannel;
import org.gemsjax.server.communication.channel.SearchChannel;
import org.gemsjax.server.communication.channel.StandardOutputChannel;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.user.User;
import org.gemsjax.shared.user.UserOnlineState;


/**
 *  A {@link OnlineUser} is the server side representation of an authenticated {@link User}, which resides on the other end of the connection (with a browser).
 *  <br /><br />More detail: A {@link OnlineUser} is authenticated, has a valid {@link HttpSession} object and has a working {@link CommunicationConnection} (which is accessible via {@link OutputChannel} and {@link InputChannel}). 
 * <br /> <br />
 * A {@link OnlineUser} wraps a {@link User} and his current {@link OutputChannel} and {@link InputChannel}s.
 * In other words: A {@link OnlineUser} maps a {@link User} to his {@link OutputChannel} and his {@link InputChannel}s.
 * 
 *@author Hannes Dorfmann
 *
 */
public class OnlineUser {
	
	private User user;
	private OutputChannel outputChannel;
	private FriendsLiveChannel friendChannel;
	private LogoutChannel logoutChannel;
	private Set<InputChannel> inputChannels;
	private RequestChannel requestChannel;
	private ExperimentChannel experimentChannel;
	private NotificationChannel notificationChannel;
	private CollaborationChannel collaborationChannel;
	private CollaborateableFileChannel collaborateableFileChannel;

	private HttpSession httpSession;
	
	
	
	
	private OnlineUser(User user, HttpSession httpSession)
	{
		this.user = user;
		inputChannels = new HashSet<InputChannel>();
		this.httpSession = httpSession;
		this.setOnlineState(UserOnlineState.ONLINE);
	}
	
	
	public void setFriendLiveChannel(FriendsLiveChannel channel)
	{
		this.friendChannel = channel;
	}
	
	
	public FriendsLiveChannel getFriendChannel()
	{
		return friendChannel;
	}

	public User getUser()
	{
		return user;
	}

	/**
	 * This Channel can be used, to send any kind of message to a {@link User}, which is not clearly part of another 
	 * {@link OutputChannel} like {@link NotificationChannel}, {@link FriendsLiveChannel}, {@link RequestChannel}, etc.
	 * 
	 * @return
	 */
	public OutputChannel getStandardOutputChannel() {
		return outputChannel;
	}


	public void setStandardOutputChannel(OutputChannel outputChannel) {
		this.outputChannel = outputChannel;
	}
	
	
	public void addInputChannel(InputChannel c)
	{
		inputChannels.add(c);
	}
	
	
	public void removeInputChannel (InputChannel c)
	{
		inputChannels.remove(c);
	}
	
	
	
	/**
	 * Get the unique {@link User} id by calling {@link User#getId()}
	 * @return
	 */
	public Integer getId()
	{
		return user.getId();
	}
	
	
	public HttpSession getHttpSession()
	{
		return httpSession;
	}
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof OnlineUser) ) return false;
		
		final OnlineUser that = (OnlineUser) other;
		
		if (user.getId()!= null && that.user.getId() != null)
			return this.user.getId().equals(that.user.getId());
		
		return false;
	}
		
	@Override
	public int hashCode() {
		if (user.getId() != null)
			return user.getId().hashCode();
		else
			return super.hashCode();
	}
	
		
	

	/**
	 * Create a {@link OnlineUser} with a {@link StandardOutputChannel} and all needed {@link InputChannel}s:
	 * <ul>
	 * <li> </li>
	 * </ul>
	 * @param user
	 * @param connection The {@link CommunicationConnection} which can send push messages from server to client
	 * @return
	 */
	public static OnlineUser create(User user, CommunicationConnection connection, HttpSession httpSession)
	{
		OnlineUser u = new OnlineUser(user, httpSession);
		
		setClosedListener(connection);
		
		// Set the output Channels
		u.setStandardOutputChannel(new StandardOutputChannel(connection));
		
		// LogoutChannel
		setLogoutChannel(u, connection);
		
		// Set the FriendsChannel
		FriendsLiveChannel fc = new FriendsLiveChannel(connection, u);
		fc.addFriendsChannelHandler(FriendModule.getInstance());
		u.setFriendLiveChannel(fc);
		
		
		// Request channel
		
		RequestChannel rc = new RequestChannel(connection, u);
		rc.addRequestChannelHandler(RequestModule.getInstance());
		u.setRequestChannel(rc);
		
		// NotificationChannel
		NotificationChannel nc = new NotificationChannel(connection, u);
		nc.addNotificationChannelHandler(NotificationModule.getInstance());
		u.setNotificationChannel(nc);
		
		// SearchChannel
		SearchChannel sc = new SearchChannel(connection, u);
		
		// CollaborateableFileChannel
		CollaborateableFileChannel collFileChannel = new CollaborateableFileChannel(connection, u);
		collFileChannel.addCollaborateableFileChannelHandler(CollaboratableFileModule.getInstance());
		u.setCollaborateableFileChannel(collFileChannel);
		
		// collaborationChannel
		CollaborationChannel colChannel = new CollaborationChannel(connection, u);
		colChannel.addCollaborationChannelHandler(CollaborationModule.getInstance());
		u.setCollaborationChannel(colChannel);
		
		// ExperimentChannel
		ExperimentChannel expChannel = new ExperimentChannel(connection, u);
		expChannel.addExperimentChannelHandler(ExperimentModule.getInstance());
		u.setExperimentChannel(expChannel);
		
		return u;
	}
	
	
	/**
	 * Create a {@link OnlineUser} for an experiment participant with a {@link StandardOutputChannel} and  for the Experiment needed {@link InputChannel}s:
	 * <ul>
	 * <li> </li>
	 * </ul>
	 * @param user The authenticated {@link User}
	 * @param connection The {@link CommunicationConnection}
	 * @return The {@link OnlineUser} with all needed {@link InputChannel}s and a {@link OutputChannel}
	 */
	public static OnlineUser createForExperiment(User user, CommunicationConnection connection, HttpSession httpSession)
	{
		OnlineUser u = new OnlineUser(user, httpSession);
		
		setClosedListener(connection);
		
		// Set the output Channels
		u.setStandardOutputChannel(new StandardOutputChannel(connection));
		
		// Set LogoutChannel
		setLogoutChannel(u, connection);
		
		
		// collaborationChannel
		CollaborationChannel colChannel = new CollaborationChannel(connection, u);
		colChannel.addCollaborationChannelHandler(CollaborationModule.getInstance());
		u.setCollaborationChannel(colChannel);
		
		return u;
	}

	
	private static void setLogoutChannel(OnlineUser u, CommunicationConnection connection)
	{
		// Set LogoutChannel
		LogoutChannel lc = new LogoutChannel(connection, u.getHttpSession());
		u.setLogoutChannel(lc);
		lc.addLogoutChannelHandler(OnlineUserManager.getInstance());
	}

	
	private static void setClosedListener(CommunicationConnection connection)
	{
		connection.addCloseListener(OnlineUserManager.getInstance());
	}
	
	public UserOnlineState getOnlineState() {
		return user.getOnlineState();
	}


	public void setOnlineState(UserOnlineState onlineState) {
		user.setOnlineState(onlineState);
	}


	public LogoutChannel getLogoutChannel() {
		return logoutChannel;
	}


	public void setLogoutChannel(LogoutChannel logoutChannel) {
		this.logoutChannel = logoutChannel;
	}


	public RequestChannel getRequestChannel() {
		return requestChannel;
	}


	public void setRequestChannel(RequestChannel requestChannel) {
		this.requestChannel = requestChannel;
	}


	public NotificationChannel getNotificationChannel() {
		return notificationChannel;
	}


	public void setNotificationChannel(NotificationChannel notificationChannel) {
		this.notificationChannel = notificationChannel;
	}


	public CollaborateableFileChannel getCollaborateableFileChannel() {
		return collaborateableFileChannel;
	}


	public void setCollaborateableFileChannel(CollaborateableFileChannel collaborateableFileChannel) {
		this.collaborateableFileChannel = collaborateableFileChannel;
	}


	public CollaborationChannel getCollaborationChannel() {
		return collaborationChannel;
	}


	public void setCollaborationChannel(CollaborationChannel collaborationChannel) {
		this.collaborationChannel = collaborationChannel;
	}


	public ExperimentChannel getExperimentChannel() {
		return experimentChannel;
	}


	public void setExperimentChannel(ExperimentChannel experimentChannel) {
		this.experimentChannel = experimentChannel;
	}
	
	

}
