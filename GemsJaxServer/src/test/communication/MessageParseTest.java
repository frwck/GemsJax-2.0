package test.communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.gemsjax.server.communication.parser.LoginMessageParser;
import org.gemsjax.shared.communication.message.LoginMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.mchange.util.AssertException;

public class MessageParseTest {

	/*
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}	

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	*/
	
	@Test
	public void loginMessage() throws SAXException, IOException
	{
		String username = "username";
		String password = "password";
		boolean experimentLogin = true;
		
		LoginMessage lm = new LoginMessage(username, password, experimentLogin);
			
		LoginMessageParser parser = new LoginMessageParser();
		
		LoginMessage m = parser.parse(lm.toXml());
		
		
		assertEquals(m.getUsername(), username);
		assertEquals(m.getPassword(), password);
		assertEquals(m.isExperimentLogin(), experimentLogin);
		
		assertTrue(m.equals(lm));
	}

}
