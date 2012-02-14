package org.gemsjax.server.module;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;

public class NotificationModule {

	
	public static NotificationModule INSTANCE = new NotificationModule();
	
	
	private NotificationModule()
	{
		
	}
	
	public static NotificationModule getInstance(){
		return INSTANCE;
	}
	
	
	
	
	public void onFriendshipAccepted(RegisteredUser receiver, RegisteredUser acceptor)
	{
		// TODO implement
	}
	
	
	public void onFriendshipRejected(RegisteredUser receiver, RegisteredUser acceptor)
	{
		// TODO implement
	} 
	
	
	public void onAdminExperimentAccepted(RegisteredUser receiver, RegisteredUser acceptor, Experiment experiment)
	{
		// TODO implement
	}
	
	public void onAdminExperimentRejected(RegisteredUser receiver, RegisteredUser acceptor, Experiment experiment)
	{
		// TODO implement
	}
	
	
	public void onCollaborationAccepted(RegisteredUser receiver, RegisteredUser acceptor, Collaborateable collaborateable)
	{
		// TODO implement
	}
	
	public void onCollaborationRejected(RegisteredUser receiver, RegisteredUser acceptor, Collaborateable collaborateable)
	{
		// TODO implement
	}
	
}
