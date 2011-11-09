package org.gemsjax.client.communication;

import org.gemsjax.client.communication.exception.WebSocketSendException;
import org.gemsjax.client.util.RegExFactory;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.model.ModelClass;


/**
 * A {@link CollaborationChannel} is a {@link Channel} that is observing the {@link WebSocket} for incoming 
 * collaboration messages which were parsed and applied on the associated {@link Collaborateable} (like {@link MetaModel} or {@link Model}) or on
 * child elements of this {@link Collaborateable} (like {@link MetaClass}, {@link MetaConnection}, {@link ModelClass} etc. ).
 * 
 *  Messages can also be sent to the server via {@link WebSocket} by calling {@link #sendMessage(String)}
 * 
 * See the protocol paper for detailed description
 * @author Hannes Dorfmann
 *
 */
public class CollaborationChannel implements Channel{
	
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
	public void onError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageReceived(String xmlMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String xmlMsg) throws WebSocketSendException {
		// TODO Auto-generated method stub
		
	}

}
