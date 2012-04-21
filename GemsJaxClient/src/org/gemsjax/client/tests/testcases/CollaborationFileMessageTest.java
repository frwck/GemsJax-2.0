package org.gemsjax.client.tests.testcases;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.parser.CollaborateableFileMessageParser;
import org.gemsjax.client.metamodel.MetaModelImpl;
import org.gemsjax.client.tests.UnitTest;
import org.gemsjax.client.util.Console;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;
import org.gemsjax.shared.communication.message.collaborateablefile.GetAllCollaborateablesAnswerMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;

public class CollaborationFileMessageTest extends UnitTest {

	private CollaborateableFileMessageParser parser;
	
	@Override
	public void test() {
		
		parser = new CollaborateableFileMessageParser();
	

		logConsole("Simple console message");
		testGetAll();
	
	}
	
	
	private void testGetAll(){
		
		// Sample Message object
		String referenceId ="ref-id";
		CollaborateableType type = CollaborateableType.METAMODEL;
		Set<Collaborateable> result = new LinkedHashSet<Collaborateable>();
		MetaModelImpl metaModel = new MetaModelImpl(1, "MetaModel1");
		result.add(metaModel);
		
		GetAllCollaborateablesAnswerMessage m = new GetAllCollaborateablesAnswerMessage(referenceId, type, result);
		logConsole(m.toXml());
		ReferenceableCollaborateableFileMessage pm = parser.parseMessage(m.toXml());
		
		GetAllCollaborateablesAnswerMessage parsedMessage = (GetAllCollaborateablesAnswerMessage) pm;
		
		
		
		assertEqual("reference Id", m.getReferenceId(), parsedMessage.getReferenceId());
		assertTrue("CollaborateableType", m.getType() == parsedMessage.getType());
		
		Set<Collaborateable> parsedResult = parsedMessage.getResult();
		for (Collaborateable c: parsedResult)
		{
			assertTrue("Meta Model id ", c.getId() == metaModel.getId());
			assertEqual("MetaModel name", c.getName(), metaModel.getName());
		}
		
		
	}

}
