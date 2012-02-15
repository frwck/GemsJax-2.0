package org.gemsjax.server.communication.channel.handler;

import org.gemsjax.server.module.OnlineUser;

public interface RequestChannelHandler {

	public void onAcceptRequest(OnlineUser user, int requestId, String referenceId);
	public void onRejectRequest(OnlineUser user, int requestId, String referenceId);
	
	public void onGetAllRequests(OnlineUser user, String referenceId);
}
