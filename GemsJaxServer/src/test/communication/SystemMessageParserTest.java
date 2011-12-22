package test.communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.gemsjax.server.communication.parser.SystemMessageParser;
import org.gemsjax.shared.communication.message.system.LoginMessage;
import org.gemsjax.shared.communication.message.system.LogoutMessage;
import org.gemsjax.shared.communication.message.system.LogoutMessage.LogoutReason;

import org.junit.Test;
import org.xml.sax.SAXException;

public class SystemMessageParserTest {

	
	@Test
	public void loginMessage() throws SAXException, IOException
	{
		String username = "username";
		String password = "password";
		boolean experimentLogin = true;
		
		LoginMessage lm = new LoginMessage(username, password, experimentLogin);
			
		SystemMessageParser parser = new SystemMessageParser();
		
		LoginMessage m = (LoginMessage) parser.parse(lm.toXml());
		
		
		assertEquals(m.getUsername(), username);
		assertEquals(m.getPassword(), password);
		assertTrue(m.isExperimentLogin() == experimentLogin);
		
		assertTrue(m.equals(lm));
	}
	
	
	
	
	@Test
	public void logoutMessage() throws SAXException, IOException
	{
		LogoutReason reason = LogoutReason.CLIENT_USER_LOGOUT;
		
		LogoutMessage lm = new LogoutMessage(reason);
			
		SystemMessageParser parser = new SystemMessageParser();
		
		LogoutMessage m = (LogoutMessage) parser.parse(lm.toXml());
		
		assertTrue (m.getLogoutReason() == reason);
		assertTrue(lm.getLogoutReason() == m.getLogoutReason());
		assertTrue(m.equals(lm));
	}
	
	

}
