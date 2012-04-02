package org.gemsjax.server.communication.channel;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.gemsjax.server.communication.parser.CollaboratableFileMessageParser;
import org.gemsjax.server.communication.parser.SearchMessageParser;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileErrorMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;
import org.xml.sax.SAXException;

public class CollaborateableFileChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private String regex;
	
	public CollaborateableFileChannel(CommunicationConnection connection)
	{
		this.connection = connection;
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

	@Override
	public void onMessageReceived(InputMessage arg0) {
		CollaboratableFileMessageParser parser = new CollaboratableFileMessageParser();
		
		try {
			ReferenceableCollaborateableFileMessage msg = parser.parse(arg0.getText());
			
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
