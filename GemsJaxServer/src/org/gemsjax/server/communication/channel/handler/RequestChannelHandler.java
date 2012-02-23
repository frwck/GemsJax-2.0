package org.gemsjax.server.communication.channel.handler;

import org.gemsjax.server.module.OnlineUser;

public interface RequestChannelHandler {

	public void onAcceptRequest(OnlineUser user, long requestId, String referenceId);
	public void onRejectRequest(OnlineUser user, long requestId, String referenceId);
	
	public void onGetAllRequests(OnlineUser user, String referenceId);
}
