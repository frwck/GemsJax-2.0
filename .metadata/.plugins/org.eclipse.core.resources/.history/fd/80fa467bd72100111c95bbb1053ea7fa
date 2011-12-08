package org.gemsjax.client.communication;

import java.io.IOException;

import org.gemsjax.client.communication.exception.WebSocketSendException;
import org.gemsjax.client.util.RegExFactory;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.model.ModelClass;


/**
 * A {@link CollaborationChannel} is a {@link InputChannel} that receives collaboration {@link Message}
 * @author Hannes Dorfmann
 *
 */
public class CollaborationChannel implements InputChannel, OutputChannel {
	
	/**
	 * The {@link Collaborateable} on that this {@link CollaborationChannel} works
	 */
	private Collaborateable collaborateable;
	
	/**
	 * The 
	 */
	private String filterRegEx;
	
	public CollaborationChannel(Collaborateable collaborateable)
	{
		this.collaborateable = collaborateable;
		filterRegEx = RegExFactory.generate("col", "on", ""+collaborateable.getId());
		
	}
	

	@Override
	public String getFilterRegEx() {
		return filterRegEx;
	}


	@Override
	public void onMessageReceived(String xmlMsg) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void send(Message message) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
