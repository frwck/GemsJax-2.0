package test.communication;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.gemsjax.server.communication.parser.SearchMessageParser;
import org.gemsjax.shared.communication.message.search.GlobalSearchMessage;
import org.gemsjax.shared.communication.message.search.SearchRegisteredUserMessage;
import org.junit.Test;
import org.xml.sax.SAXException;

public class SystemMessageParserTest {

	
	@Test
	public void globalSearchTest() throws SAXException, IOException
	{
		String refId ="ref1";
		String search ="serarch for this";
		
		SearchMessageParser parser = new SearchMessageParser();
		
		GlobalSearchMessage m = new GlobalSearchMessage(refId, search);
		
		GlobalSearchMessage pm = (GlobalSearchMessage) parser.parse(m.toXml());
		
		assertEquals(m.getReferenceId(), pm.getReferenceId());
		assertEquals(m.getSearchString(), pm.getSearchString());
		
	}
	
	
	
	@Test
	public void searchRegisteredUserTest() throws SAXException, IOException
	{
		String refId ="ref1";
		String search ="serarch for this";
		
		SearchMessageParser parser = new SearchMessageParser();
		
		SearchRegisteredUserMessage m = new SearchRegisteredUserMessage(refId, search);
		
		SearchRegisteredUserMessage pm = (SearchRegisteredUserMessage) parser.parse(m.toXml());
		
		assertEquals(m.getReferenceId(), pm.getReferenceId());
		assertEquals(m.getSearchString(), pm.getSearchString());
		
	}
	
	

}
