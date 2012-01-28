package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.gemsjax.server.communication.channel.handler.LogoutChannelHandler;
import org.gemsjax.server.communication.parser.SystemMessageParser;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.server.module.OnlineUserManager;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.LogoutMessage;
import org.gemsjax.shared.communication.message.system.SystemMessage;
import org.xml.sax.SAXException;

public class LogoutChannel implements InputChannel, OutputChannel {
	
	private CommunicationConnection connection;
	private String regexFilter;
	private SystemMessageParser parser;
	
	private Set<LogoutChannelHandler> handlers;
	private HttpSession session;
	
	public LogoutChannel(CommunicationConnection connection, HttpSession session)
	{
		this.connection = connection;
		this.parser = new SystemMessageParser();
		this.regexFilter = RegExFactory.startWithTagSubTag(SystemMessage.TAG, LogoutMessage.TAG);
		handlers = new LinkedHashSet<LogoutChannelHandler>();
		this.session = session;
	}
	

	public void addLogoutChannelHandler(LogoutChannelHandler h)
	{
		handlers.add(h);
	}
	
	
	public void removeLogoutChannelHandler(LogoutChannelHandler h)
	{
		handlers.remove(h);
	}
	
	
	@Override
	public void send(Message arg0) throws IOException {
		connection.send(arg0);
	}

	@Override
	public boolean isMatchingFilter(String arg0) {
		return arg0.matches(regexFilter);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		
		try {
			SystemMessage m = parser.parse(msg.getText());
			
			if (m instanceof LogoutMessage)
			{
				OnlineUser u = OnlineUserManager.getInstance().getOnlineUser(session);
						
				if (u!=null){
					for (LogoutChannelHandler h: handlers)
						h.onLogoutReceived(u);
				}
				else
				{
					// TODO What to do, if could not find the user by the session
				}
			}
			
		} catch (SAXException e) {
			// TODO What to do?
			e.printStackTrace();
		} catch (IOException e) {
			// TODO What to do?
			e.printStackTrace();
		}
		
	}

}
