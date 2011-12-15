package org.gemsjax.server.communication.channel;

import java.io.IOException;

import org.gemsjax.server.communication.parser.SystemMessageParser;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.LoginMessage;
import org.gemsjax.shared.communication.message.system.SystemMessage;
import org.xml.sax.SAXException;

/**
 * This channel listen to incoming system messages as xml with a beginning  &lt sys &gt xml tag
 * @author Hannes Dorfmann
 *
 */
public class UserAuthenticationChannel implements InputChannel, OutputChannel{

	
	private CommunicationConnection communicationConnection;
	private String filterRegEx;
	
	private UserDAO userDAO;
	private ExperimentDAO experimentDAO;
		
	public UserAuthenticationChannel(CommunicationConnection connection)
	{
		this.communicationConnection = connection;
		filterRegEx = RegExFactory.startWithTag("sys");
		userDAO = new HibernateUserDAO();
		experimentDAO = new HibernateExperimentDAO();
	}
		
		@Override
	public String getFilterRegEx() {
		return filterRegEx;
	}

	@Override
	public void onMessageReceived(String msg) {
		
		SystemMessageParser parser = new SystemMessageParser();
		
		try {
			SystemMessage m = parser.parse(msg);
			
			
			if (m instanceof LoginMessage)
			{
				
			}
			
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void send(Message message) throws IOException {
		communicationConnection.send(message.toString());
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
		
	
}
