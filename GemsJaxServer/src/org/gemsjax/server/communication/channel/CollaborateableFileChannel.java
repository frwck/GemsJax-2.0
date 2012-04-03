package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.CollaborateableFileChannelHandler;
import org.gemsjax.server.communication.parser.CollaboratableFileMessageParser;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileErrorMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.GetAllCollaborateablesMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.NewCollaborateableFileMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.UpdateCollaborateableFileMessage;
import org.xml.sax.SAXException;

public class CollaborateableFileChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private String regex;
	private OnlineUser onlineUser;
	private Set<CollaborateableFileChannelHandler> handlers;
	
	public CollaborateableFileChannel(CommunicationConnection connection, OnlineUser onlineUser)
	{
		this.onlineUser = onlineUser;
		this.connection = connection;
		handlers = new LinkedHashSet<CollaborateableFileChannelHandler>();
		this.connection.registerInputChannel(this);
		this.regex = RegExFactory.startWithTag(ReferenceableCollaborateableFileMessage.TAG);
		
	}

	@Override
	public void send(Message arg0) throws IOException {
		connection.send(arg0);
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		if (msg.matches(regex))
			return true;
		else
			return false;
	}
	
	public void addCollaborateableFileChannelHandler(CollaborateableFileChannelHandler h){
		handlers.add(h);
	}
	
	public void removeCollaborateableFileChannelHandler(CollaborateableFileChannelHandler h){
		handlers.remove(h);
	}
	

	@Override
	public void onMessageReceived(InputMessage arg0) {
		CollaboratableFileMessageParser parser = new CollaboratableFileMessageParser();
		
		try {
			
			ReferenceableCollaborateableFileMessage msg = parser.parse(arg0.getText());
			
			if (msg instanceof GetAllCollaborateablesMessage)
				for (CollaborateableFileChannelHandler h: handlers)
					h.onGetAllCollaborateableFiles(onlineUser);
			else
			if (msg instanceof NewCollaborateableFileMessage){
				NewCollaborateableFileMessage m = (NewCollaborateableFileMessage)msg;
				for (CollaborateableFileChannelHandler h: handlers)
					h.onCreateNewCollaborateableFile(onlineUser, m.getName(), m.getKeywords(), m.getType(), m.isPublic(), m.getAdministratorIds(), m.getCollaboratorIds());
			}
			else
			if (msg instanceof UpdateCollaborateableFileMessage){
				UpdateCollaborateableFileMessage m = (UpdateCollaborateableFileMessage) msg;
				for (CollaborateableFileChannelHandler h: handlers)
					h.onUpdateCollaborateableFile(onlineUser, m.getCollaborateableId(), m.getName(), m.getKeywords(), m.isPublic(), m.getAddAdminIds(), m.getRemoveAdminIds(), m.getAddCollaboratorIds(), m.getRemoveCollaboratorIds());
			}
			
			
			
		} catch (SAXException e) {
			try {
				send (new CollaborateableFileErrorMessage(parser.getCurrentReferenceId(), CollaborateableFileError.PARSING));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			
			try {
				send (new CollaborateableFileErrorMessage(parser.getCurrentReferenceId(), CollaborateableFileError.PARSING));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
}
