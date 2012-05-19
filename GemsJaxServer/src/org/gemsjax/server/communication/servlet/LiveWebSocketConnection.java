package org.gemsjax.server.communication.servlet;



import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.eclipse.jetty.websocket.WebSocket;
import org.gemsjax.server.communication.channel.UserAuthenticationChannel;
import org.gemsjax.server.communication.serialisation.XmlLoadingArchive;
import org.gemsjax.shared.collaboration.TransactionImpl;
import org.gemsjax.shared.collaboration.command.MoveMetaConnectionAchnorPointCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaClassAbstractCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaClassIconCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionIconsCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionMultiplicityCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaConnectionAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaConnectionCommand;
import org.gemsjax.shared.collaboration.command.metamodel.DeleteMetaAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.DeleteMetaConnectionAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.EditMetaAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.EditMetaConnectionAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.MoveMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.RenameMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.RenameMetaConnectionCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ResizeMetaClassCommand;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;
import org.gemsjax.shared.communication.message.collaboration.UnsubscribeCollaborateableMessage;
import org.gemsjax.shared.communication.message.system.KeepAliveMessage;
import org.gemsjax.shared.communication.serialisation.ObjectFactory;
import org.gemsjax.shared.communication.serialisation.XmlSavingArchive;
import org.gemsjax.shared.communication.serialisation.instantiators.LinkedHashMapInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.LinkedHashSetInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.LinkedListInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.TransactionInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaClassAbstractCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaClassIconCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaConnectionIconsCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaConnectionMultiplicityCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.CreateMetaAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.CreateMetaClassCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.CreateMetaConnectionAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.CreateMetaConnectionCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.DeleteMetaAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.DeleteMetaConnectionAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.EditMetaAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.EditMetaConnectionAttributeInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.MoveMetaClassCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.MoveMetaConnectionAchnorPointCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.RenameMetaClassCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.RenameMetaConnectionCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ResizeMetaClassCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.SubscribeCollaborateableMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.TransactionMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.UnsubscribeCollaborateableMessageInstantiator;



	/**
	 * A ClientManager manages the server-client connection
	 * and implements the server side communication protocol
	 * @author Hannes Dorfmann
	 *
	 */
	public class LiveWebSocketConnection implements CommunicationConnection, WebSocket.OnTextMessage {

		private Connection connection;
		private HttpSession session;
		
		
		private Set<InputChannel> inputChannels;
		
		private Set<ClosedListener> closedListeners;
		private Set<EstablishedListener> establishedListeners;
		private Set<ErrorListener> errorListeners;
		
		private Map<MessageType<?>, Set<InputChannel>> inputChannelMap;

		private static ObjectFactory objectFactory;
		
		public LiveWebSocketConnection(HttpSession session)
		{
			this.session = session;
			connection = null;
			inputChannels = new LinkedHashSet<InputChannel>();
			closedListeners = new LinkedHashSet<ClosedListener>();
			establishedListeners = new LinkedHashSet<EstablishedListener>();
			errorListeners = new LinkedHashSet<CommunicationConnection.ErrorListener>();
			
			inputChannelMap = new LinkedHashMap<MessageType<?>, Set<InputChannel>>();
			intiObjectFactory();
		}
		
		
		private void intiObjectFactory(){
		
			if (objectFactory == null){
				
				objectFactory = new ObjectFactory();
				objectFactory.register(LinkedHashSet.class.getName(), new LinkedHashSetInstantiator());
				objectFactory.register(LinkedList.class.getName(), new LinkedListInstantiator());
				objectFactory.register(LinkedHashMap.class.getName(), new LinkedHashMapInstantiator());
			
				// CollaboarionMessages
				objectFactory.register(SubscribeCollaborateableMessage.class.getName(), new SubscribeCollaborateableMessageInstantiator());
				objectFactory.register(UnsubscribeCollaborateableMessage.class.getName(), new UnsubscribeCollaborateableMessageInstantiator());
				objectFactory.register(TransactionMessage.class.getName(), new TransactionMessageInstantiator());
				objectFactory.register(TransactionImpl.class.getName(), new TransactionInstantiator());
				
				// MetaModel Commands
				objectFactory.register(CreateMetaClassCommand.class.getName(), new CreateMetaClassCommandInstantiator());
				objectFactory.register(MoveMetaClassCommand.class.getName(), new MoveMetaClassCommandInstantiator());
				objectFactory.register(ResizeMetaClassCommand.class.getName(), new ResizeMetaClassCommandInstantiator());
				objectFactory.register(CreateMetaAttributeCommand.class.getName(), new CreateMetaAttributeCommandInstantiator());
				objectFactory.register(EditMetaAttributeCommand.class.getName(), new EditMetaAttributeCommandInstantiator());
				objectFactory.register(DeleteMetaAttributeCommand.class.getName(), new DeleteMetaAttributeCommandInstantiator());
				objectFactory.register(RenameMetaClassCommand.class.getName(), new RenameMetaClassCommandInstantiator());
				objectFactory.register(ChangeMetaClassIconCommand.class.getName(), new ChangeMetaClassIconCommandInstantiator());
				objectFactory.register(ChangeMetaClassAbstractCommand.class.getName(), new ChangeMetaClassAbstractCommandInstantiator());
				// MetaConnection
				objectFactory.register(CreateMetaConnectionCommand.class.getName(), new CreateMetaConnectionCommandInstantiator());
				objectFactory.register(RenameMetaConnectionCommand.class.getName(), new RenameMetaConnectionCommandInstantiator());
				objectFactory.register(ChangeMetaConnectionMultiplicityCommand.class.getName(), new ChangeMetaConnectionMultiplicityCommandInstantiator());
				objectFactory.register(CreateMetaConnectionAttributeCommand.class.getName(), new CreateMetaConnectionAttributeCommandInstantiator());
				objectFactory.register(EditMetaConnectionAttributeCommand.class.getName(), new EditMetaConnectionAttributeInstantiator());
				objectFactory.register(DeleteMetaConnectionAttributeCommand.class.getName(), new DeleteMetaConnectionAttributeCommandInstantiator());
				objectFactory.register(MoveMetaConnectionAchnorPointCommand.class.getName(), new MoveMetaConnectionAchnorPointCommandInstantiator());
				objectFactory.register(ChangeMetaConnectionIconsCommand.class.getName(), new ChangeMetaConnectionIconsCommandInstantiator());
				

				
			}
			
		}
		
		
		@Override
		public void onOpen(Connection con) {
			this.connection = con;
			
			UserAuthenticationChannel u = new UserAuthenticationChannel(this, session);
			this.registerInputChannel(u);
			
			for (EstablishedListener e: establishedListeners)
				e.onEstablished();
		}

		@Override
		public void onMessage(String data) {
		
			if (data.equals("<"+KeepAliveMessage.TAG+" />")) // Ignore keepalive Messages
				return;
			
			// TODO remove println
			System.out.println("Received: "+data);
			
			
			try {
				XmlLoadingArchive archive = new XmlLoadingArchive(data, objectFactory);
				Message m = (Message)archive.deserialize();
				deliverReceivedMessage(m);
				
			} catch (Exception e) {
				
				try{
					// TODO remove deprecated stuff (backward compatibility)
					InputMessage im = new InputMessage(200, data);
					
			    	for (InputChannel c: inputChannels)
			        {
			        	if (c.isMatchingFilter(data))
			        		c.onMessageReceived(im);
			        }
				}catch(Throwable t)
				{
					t.printStackTrace();
					
				}
				
				
				/*if (e.getMessage()!=null)
					if (e.getMessage().startsWith("key missmatch"))
						return;
				*/
				e.printStackTrace();
				
		    	
			}
			
			
			
	    	
		}
		
		
		public void send(Message message) throws IOException
		{
			String toSend="";
		
			if (message.toXml() == null){
				XmlSavingArchive archive = new XmlSavingArchive();
				try {
					archive.serialize(message);
					toSend = archive.toXml();
				} catch (Exception e) {
					e.printStackTrace();
					throw new IOException(e);
				}
			}
			else
				toSend = message.toXml();
			
			System.out.println("Sending: "+toSend);
			connection.sendMessage(toSend);
		}

		@Override
		public void close() throws IOException {
			connection.disconnect();
			inputChannels.clear();
		}

		@Override
		public void connect() throws IOException {
			throw new UnsupportedOperationException("The connect() method is not supported on server side, since the client is the one who beginns always with the Handshake");
		}

		@Override
		public void deregisterInputChannel(InputChannel c) {
			
			inputChannels.remove(c);
			
			for (Map.Entry<MessageType<?>, Set<InputChannel>> e : inputChannelMap.entrySet())
				e.getValue().remove(c);
			
		}

		@Override
		public int getPort() {
			return 0;
		}

		@Override
		public String getRemoteAddress() {
			return connection.toString();
		}

		@Override
		public boolean isClosed() {
			return !connection.isOpen();
		}

		@Override
		public boolean isConnected() {
			return connection.isOpen();
		}

		@Override
		public boolean isKeepAlive() {
			// Its up to the client to keep the connection alive by sending a <ping /> from in a periodic time
			return false;
		}

		@Override
		public boolean isSupported() {
			return true;
		}

		@Override
		public void registerInputChannel(InputChannel c) {
			inputChannels.add(c);
		}
		
		@Override
		public void setKeepAlive(boolean keepAlive) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void addCloseListener(ClosedListener arg0) {
			closedListeners.add(arg0);
		}

		@Override
		public void addEstablishedListener(EstablishedListener arg0) {
			establishedListeners.add(arg0);
		}

		@Override
		public void removeCloseListener(ClosedListener arg0) {
			closedListeners.remove(arg0);
		}

		@Override
		public void removeEstablishedListener(EstablishedListener arg0) {
			establishedListeners.remove(arg0);
		}
		
		@Override
		public void onClose(int arg0, String arg1) {
			
			for (ClosedListener c: closedListeners)
				c.onClose(this);
		}

		@Override
		public void addErrorListener(ErrorListener arg0) {
			errorListeners.add(arg0);
		}

		@Override
		public void removeErrorListener(ErrorListener arg0) {
			errorListeners.remove(arg0);
		}
		
		
		public HttpSession getSession()
		{
			return session;
		}
		
		
		@Override
		public void registerInputChannel(InputChannel c, MessageType<?> type) {
			
			if (!inputChannelMap.containsKey(type)){ // key is not already there
				Set<InputChannel> channels =  new LinkedHashSet<InputChannel>();
				
				channels.add(c);
				
				inputChannelMap.put(type, channels);
			}
			else
			{	// Key is already present, so append the channel to the list
				Set<InputChannel> channels = inputChannelMap.get(type);
				channels.add(c);
			}
		}

	
	
	
	private void deliverReceivedMessage(Message m){
		Set<InputChannel> channels = inputChannelMap.get(m.getMessageType());
		
		for (InputChannel c : channels)
			c.onMessageRecieved(m);
	}

}

