package org.gemsjax.client.communication;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.gemsjax.client.communication.serialisation.GwtXmlLoadingArchive;
import org.gemsjax.client.util.Console;
import org.gemsjax.shared.ServletPaths;
import org.gemsjax.shared.collaboration.TransactionImpl;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaClassAbstractCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaClassIconCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionIconsCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionMultiplicityCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionSourceCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionTargetCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaConnectionAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaConnectionCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaInheritanceCommand;
import org.gemsjax.shared.collaboration.command.metamodel.DeleteMetaAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.DeleteMetaConnectionAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.EditMetaAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.EditMetaConnectionAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.MoveMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.MoveMetaConnectionAnchorPointCommand;
import org.gemsjax.shared.collaboration.command.metamodel.MoveMetaConnectionCommand;
import org.gemsjax.shared.collaboration.command.metamodel.RenameMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.RenameMetaConnectionCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ResizeMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ResizeMetaConnectionCommand;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.message.collaboration.CollaboratorJoinedMessage;
import org.gemsjax.shared.communication.message.collaboration.CollaboratorLeftMessage;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableErrorMessage;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableSuccessfulMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionErrorMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;
import org.gemsjax.shared.communication.message.collaboration.UnsubscribeCollaborateableMessage;
import org.gemsjax.shared.communication.message.experiment.CreateExperimentMessage;
import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentErrorMessage;
import org.gemsjax.shared.communication.message.experiment.ExperimentGroupDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentInvitationDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentSuccessfulMessage;
import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsAnswerMessage;
import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsMessage;
import org.gemsjax.shared.communication.message.experiment.UserDTO;
import org.gemsjax.shared.communication.message.system.KeepAliveMessage;
import org.gemsjax.shared.communication.serialisation.ObjectFactory;
import org.gemsjax.shared.communication.serialisation.XmlSavingArchive;
import org.gemsjax.shared.communication.serialisation.instantiators.LinkedHashMapInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.LinkedHashSetInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.LinkedListInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.CollaboratorInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.MetaBaseTypeInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.TransactionInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaClassAbstractCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaClassIconCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaConnectionIconsCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaConnectionMultiplicityCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaConnectionSourceCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaConnectionTargetCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.CreateMetaAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.CreateMetaClassCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.CreateMetaConnectionAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.CreateMetaConnectionCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.CreateMetaInheritanceCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.DeleteMetaAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.DeleteMetaConnectionAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.EditMetaAttributeCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.EditMetaConnectionAttributeInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.MoveMetaClassCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.MoveMetaConnectionAchnorPointCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.MoveMetaConnectionCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.RenameMetaClassCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.RenameMetaConnectionCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ResizeMetaClassCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ResizeMetaConnectionCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.experiment.ExperimentDTOInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.experiment.ExperimentGroupDTOInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.experiment.ExperimentInvitationDTOInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.CollaboratorJoinedMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.CollaboratorLeftMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.CreateExperimentMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.ExperimentErrorMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.ExperimentSuccessfulMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.GetAllExperimentsAnswerMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.GetAllExperimentsMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.SubscribeCollaborateableErrorMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.SubscribeCollaborateableMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.SubscribeCollaborateableSuccessfulMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.TransactionErrorMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.TransactionMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.message.UnsubscribeCollaborateableMessageInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.user.UserDTOInstantiator;
import org.gemsjax.shared.metamodel.impl.MetaBaseTypeImpl;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.util.SC;

/**
 * {@link WebSocketCommunicationConnection} is the way how the client communicates with the server.
 * But the client application should not use this {@link WebSocketCommunicationConnection} directly, but should use the {@link InputChannel}s to
 * send and receive messages.<br />
 * <b>Notice:</b> This is a singleton, so use {@link #getInstance()} to access the {@link WebSocketCommunicationConnection}
 * @author Hannes Dorfmann
 *
 */
public class WebSocketCommunicationConnection implements CommunicationConnection{
	
	
	/**
	 * This is a simple {@link Timer} that will keep the connection alive by 
	 * sending a simple ping (see communication protocol specification)
	 * @author Hannes Dorfmann
	 *
	 */
	private class KeepAliveTimer extends Timer
	{
		private int timeout = 20*1000;
		private final KeepAliveMessage keepAliveMessage= new KeepAliveMessage();

		@Override
		public void run() {
			
			try {
				WebSocketCommunicationConnection.this.send(keepAliveMessage);
			} catch (IOException e) {
				onError(e);
			}
		}
		
	}
	

	/**
	 * The singleton instance
	 */
	private static WebSocketCommunicationConnection INSTANCE = new WebSocketCommunicationConnection();
	
	/**
	 * The URL to the server without ws:// 
	 */
	private static final String serverURL="localhost";
	
	/**
	 * The absolute URL to the WebSocket Servlet on the {@link GemsJaxServer}.
	 * <br/><br />
	 * <b>Notice</b> The url is not needed, the server url is stored in {@link #serverURL}
	 */
	private static final String webSocketServletURL = ServletPaths.LIVE_WEBSOCKET;
	
	
	private final int port = 8081;
	private final int sslPort=8443;
	
	private final boolean useSsl = false;
	
	@Deprecated
	private Set<InputChannel> inputChannels;
	private Set<ClosedListener> closedListeners;
	private Set<EstablishedListener> establishedListeners;
	private Set<ErrorListener> errorListeners;
	
	private Map<MessageType<?>, Set<InputChannel>> inputChannelMap;
	
	private KeepAliveTimer keepAliveTimer = null;
	
	private boolean keepAlive = true;
	private boolean isConnected = false;
	
	private ObjectFactory objectFactory;
	 
    private WebSocketCommunicationConnection() {
        inputChannels = new LinkedHashSet<InputChannel>();
        establishedListeners = new LinkedHashSet<EstablishedListener>();
        closedListeners = new LinkedHashSet<ClosedListener>();
        errorListeners = new LinkedHashSet<CommunicationConnection.ErrorListener>();
        
        inputChannelMap = new LinkedHashMap<MessageType<?>, Set<InputChannel>>();
        
        intiObjectFactory();
    }
    
    

	private void intiObjectFactory(){
		objectFactory = new ObjectFactory();
			
		objectFactory.register(LinkedHashSet.class.getName(), new LinkedHashSetInstantiator());
		objectFactory.register(LinkedList.class.getName(), new LinkedListInstantiator());
		objectFactory.register(LinkedHashMap.class.getName(), new LinkedHashMapInstantiator());
		objectFactory.register("org.hibernate.collection.PersistentMap", new LinkedHashMapInstantiator());
		objectFactory.register("org.hibernate.collection.PersistentList", new LinkedListInstantiator());
		
	
		// CollaboarionMessages
		objectFactory.register(SubscribeCollaborateableSuccessfulMessage.class.getName(), new SubscribeCollaborateableSuccessfulMessageInstantiator());
		objectFactory.register(UnsubscribeCollaborateableMessage.class.getName(), new UnsubscribeCollaborateableMessageInstantiator());
		objectFactory.register(TransactionMessage.class.getName(), new TransactionMessageInstantiator());
		objectFactory.register(TransactionImpl.class.getName(), new TransactionInstantiator());
		objectFactory.register(CollaboratorJoinedMessage.class.getName(), new CollaboratorJoinedMessageInstantiator());
		objectFactory.register(CollaboratorLeftMessage.class.getName(), new CollaboratorLeftMessageInstantiator());
		objectFactory.register(Collaborator.class.getName(), new CollaboratorInstantiator());
		objectFactory.register(MetaBaseTypeImpl.class.getName(), new MetaBaseTypeInstantiator());
		objectFactory.register(TransactionErrorMessage.class.getName(), new TransactionErrorMessageInstantiator());
		objectFactory.register(SubscribeCollaborateableErrorMessage.class.getName(), new SubscribeCollaborateableErrorMessageInstantiator());
		
		
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
		objectFactory.register(MoveMetaConnectionAnchorPointCommand.class.getName(), new MoveMetaConnectionAchnorPointCommandInstantiator());
		objectFactory.register(ChangeMetaConnectionIconsCommand.class.getName(), new ChangeMetaConnectionIconsCommandInstantiator());
		objectFactory.register(MoveMetaConnectionCommand.class.getName(), new MoveMetaConnectionCommandInstantiator());
		objectFactory.register(ResizeMetaConnectionCommand.class.getName(), new ResizeMetaConnectionCommandInstantiator());
		objectFactory.register(ChangeMetaConnectionSourceCommand.class.getName(), new ChangeMetaConnectionSourceCommandInstantiator());
		objectFactory.register(ChangeMetaConnectionTargetCommand.class.getName(), new ChangeMetaConnectionTargetCommandInstantiator());
		
		
		// MetaInheritance
		objectFactory.register(CreateMetaInheritanceCommand.class.getName(), new CreateMetaInheritanceCommandInstantiator());
		
		// Experiment
		objectFactory.register(ExperimentDTO.class.getName(), new ExperimentDTOInstantiator());
		objectFactory.register(ExperimentGroupDTO.class.getName(), new ExperimentGroupDTOInstantiator());
		objectFactory.register(ExperimentInvitationDTO.class.getName(), new ExperimentInvitationDTOInstantiator());
		objectFactory.register(CreateExperimentMessage.class.getName(), new CreateExperimentMessageInstantiator());
		objectFactory.register(ExperimentErrorMessage.class.getName(), new ExperimentErrorMessageInstantiator());
		objectFactory.register(ExperimentSuccessfulMessage.class.getName(), new ExperimentSuccessfulMessageInstantiator());
		objectFactory.register(GetAllExperimentsMessage.class.getName(), new GetAllExperimentsMessageInstantiator());
		objectFactory.register(GetAllExperimentsAnswerMessage.class.getName(), new GetAllExperimentsAnswerMessageInstantiator());
		objectFactory.register(UserDTO.class.getName(), new UserDTOInstantiator());
		
		}
		
	
    
   
    
    
    public static WebSocketCommunicationConnection getInstance()
    {
    	if (INSTANCE == null)
    		INSTANCE = new WebSocketCommunicationConnection();
    	
    	return INSTANCE;
    }
    
    
    private void onOpen() {
    	
    	isConnected = true;
    	
    	
    	if (keepAlive)
		{
    		if (keepAliveTimer!=null)
    			keepAliveTimer.cancel();
    		
			keepAliveTimer = new KeepAliveTimer();
			keepAliveTimer.scheduleRepeating(keepAliveTimer.timeout);
		}
    	
    	
    	for (EstablishedListener e : establishedListeners)
    		e.onEstablished();
    
    	
    }

    
    private void onClose() {
    	isConnected = false;
    	
    	if (keepAliveTimer!=null)
    		keepAliveTimer.cancel();
    	
    	for (ClosedListener c: closedListeners)
    		c.onClose(this);
    }

    
    private void onMessage(String data) {
       
    	try {
			GwtXmlLoadingArchive archive = new GwtXmlLoadingArchive(data, objectFactory);
			Message m = (Message)archive.deserialize();
			deliverReceivedMessage(m);
			
		} catch (Exception e) {
			Console.log(e.getMessage());
			
			// TODO remove deprecated stuff (backward compatibility)
			InputMessage im = new InputMessage(200, data);
			
			e.printStackTrace();
	    	for (InputChannel c: inputChannels)
	        {
	        	if (c.isMatchingFilter(data))
	        		c.onMessageReceived(im);
	        }
		}
	    		
    }
    
	private void deliverReceivedMessage(Message m){
		Set<InputChannel> channels = inputChannelMap.get(m.getMessageType());
		
		for (InputChannel c : channels)
			c.onMessageRecieved(m);
	}
    	
    	
    private void onWebSocketNotSupported() throws IOException
    {
    	throw new IOException("WebSockets are not supported by this Browser");
    }
    
    private void notConnected()
    {
    	
    }
    
    private void generateSendError(String msgToSend) throws IOException
    {
    	throw new IOException("Could not send: "+msgToSend);
    }
    
    private void onError()
    {
    	Throwable t = new Throwable("WebSocket onError() is called");
    	
    	for (ErrorListener l : errorListeners)
    		l.onError(t);
    }

    
    private void onError(Throwable e)
    {
    
    	for (ErrorListener l : errorListeners)
    		l.onError(e);
    }
    
    public static native boolean doIsSupportedCheck() /*-{
	   return window.WebSocket;
	}-*/;
    
    
    private void checkConnection(int readyState) throws IOException
    {
    	if (readyState == 0 || readyState == 2 || readyState == 3)
    	{
    		throw new IOException("Connection not established");
    	}
    	
    }
    
    
    public native void doConnect(String server) throws IOException /*-{
        var that = this;
        if (!window.WebSocket) {
            that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onWebSocketNotSupported()();
            return;
        }
        
    
        console.log("WebSocket connecting to "+server);
        that._ws=new WebSocket(server);
        
        console.log("WebSocket connected "+that._ws.readyState);

        that._ws.onopen = function() {
            if(!that._ws) {
                console.log("WebSocket not really opened?");
                console.log("WebSocket["+server+"]._ws.onopen()");
                return;
            }
             console.log("onopen, readyState: "+that._ws.readyState);
             that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onOpen()();
             console.log("onopen done");
             
        };


        that._ws.onmessage = function(response) {
            console.log("WebSocket received: "+response.data);
           
            if (response.data) {
                that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onMessage(Ljava/lang/String;)( response.data );
            }
        };

        that._ws.onclose = function(m) {
             console.log("WebSocket["+server+"]_ws.onclose() state:"+that._ws.readyState);
             that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onClose()();
        };
        
        that._ws.onerror = function() {
             console.log("WebSocket["+server+"]_ws.onclose() state:"+that._ws.readyState);
             alert("Error");
             that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onError()();
        };
    }-*/;

    private native void doSend(String message) throws IOException /*-{
        if (this._ws) {
            console.log("WebSocket sending:"+message);
           
            this._ws.send(message);
        } else {
            this.@org.gemsjax.client.communication.WebSocketCommunicationConnection::generateSendError(Ljava/lang/String;)(message);
        }
    }-*/;

    private native void doClose() /*-{
        console.log("WebSocket closing");
        this._ws.close();
        console.log("WebSocket closed");
    }-*/;




	@Override
	public void connect() throws IOException {
		doConnect(getRemoteAddress());
		
	}




	@Override
	public void deregisterInputChannel(InputChannel c) {
		inputChannels.remove(c);
		
		for (Map.Entry<MessageType<?>, Set<InputChannel>> e : inputChannelMap.entrySet())
			e.getValue().remove(c);
	}




	@Override
	public int getPort() {
		return port;
	}




	@Override
	public String getRemoteAddress() {
		return (useSsl?"wss://":"ws://")+serverURL + ":"+(useSsl?sslPort:port)+webSocketServletURL;
	}




	@Override
	public boolean isClosed() {
		return !isConnected;
	}




	@Override
	public boolean isConnected() {
		return isConnected;
	}




	@Override
	public boolean isKeepAlive() {
		return keepAlive;
	}




	@Override
	public void registerInputChannel(InputChannel c) {
		inputChannels.add(c);
	}




	@Override
	public void setKeepAlive(boolean keepAlive) {
		if (keepAlive)
		{	if (!this.keepAlive)
			{
				if (keepAliveTimer != null) 
					keepAliveTimer.cancel();
				
				keepAliveTimer = new KeepAliveTimer();
				keepAliveTimer.scheduleRepeating(keepAliveTimer.timeout);
			}
		}
		else
			if(this.keepAlive)
			{
				if (keepAliveTimer != null)
					keepAliveTimer.cancel();
			}
		
		this.keepAlive = keepAlive;
	}




	@Override
	public void close() throws IOException {
		doClose();
	}




	@Override
	public void send(Message message) throws IOException {
		
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
		
		doSend(toSend);
	}




	@Override
	public boolean isSupported() {
		return doIsSupportedCheck();
	}




	@Override
	public void addCloseListener(ClosedListener listener) {
		closedListeners.add(listener);
	}




	@Override
	public void addEstablishedListener(EstablishedListener listener) {
		establishedListeners.add(listener);
	}




	@Override
	public void removeCloseListener(ClosedListener listener) {
		closedListeners.remove(listener);
	}




	@Override
	public void removeEstablishedListener(EstablishedListener listener) {
		establishedListeners.remove(listener);
	}




	@Override
	public void addErrorListener(ErrorListener listener) {
		errorListeners.add(listener);
	}




	@Override
	public void removeErrorListener(ErrorListener listener) {
		errorListeners.remove(listener);
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

}


