package org.gemsjax.server.communication.channel.handler;

import org.gemsjax.server.communication.channel.LogoutChannel;
import org.gemsjax.server.module.OnlineUser;

/**
 * The handler for {@link LogoutChannel}s
 * @author Hannes Dorfmann
 *
 */
public interface LogoutChannelHandler {

	public void onLogoutReceived(OnlineUser user);
}
