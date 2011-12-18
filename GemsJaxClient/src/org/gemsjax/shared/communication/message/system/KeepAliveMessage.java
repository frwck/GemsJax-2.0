package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.message.Message;

/**
 * This {@link Message} is used only to keep the {@link CommunicationConnection} alive.
 * (Prevent that the connection is closed by timeout, inactivity etc.)
 * @author Hannes Dorfmann
 *
 */
public class KeepAliveMessage extends SystemMessage{

	@Override
	public String toXml() {
		return "<ping />";
	}
	

}
