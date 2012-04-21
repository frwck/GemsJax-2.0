package test.communication;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.gemsjax.server.communication.parser.ExperimentMessageParser;
import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsMessages;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ExperimentMessageParserTest {
	@Test
	public void testGetAllExperimentsMessages() throws ParserConfigurationException, SAXException, IOException {
		String referenceId = "id";
		GetAllExperimentsMessages msg = new GetAllExperimentsMessages(referenceId);

		System.out.println(msg.toXml());

		ExperimentMessageParser parser = new ExperimentMessageParser();
		GetAllExperimentsMessages out = (GetAllExperimentsMessages) parser.parse(msg.toXml());

		assertEquals(msg.toXml(), out.toXml());
	}
}
