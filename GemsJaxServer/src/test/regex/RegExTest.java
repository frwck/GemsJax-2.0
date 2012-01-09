package test.regex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage.LoginAnswerStatus;
import org.gemsjax.shared.communication.message.system.LogoutMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.SystemMessage;
import org.junit.Test;



public class RegExTest {
	
	@Test
	public void startWithTag()
	{
		String filter = RegExFactory.startWithTag("sys");
		
		assertTrue("<sys>".matches(filter));
		assertTrue("<sys >".matches(filter));
		assertTrue("<sys />".matches(filter));
		assertTrue("<sys/>".matches(filter));
		assertTrue("<sys />".matches(filter));
		assertTrue("    <sys />".matches(filter));
		assertTrue("<sys     >".matches(filter));
		assertTrue("<sys bla=\"foo\">".matches(filter));
		assertTrue("<sys bla=\"foo\"    >".matches(filter));
		
		
		assertFalse("<sys".matches(filter));
		assertFalse("sys>".matches(filter));
	}
	
	@Test
	public void startWithTagSubTag()
	{
		String filter = RegExFactory.startWithTagSubTag(SystemMessage.TAG, RegistrationAnswerMessage.TAG);
		/*
		assertTrue("<sys><reg>".matches(filter));
		assertTrue("<sys> <reg>".matches(filter));
		assertTrue("<sys><reg/>".matches(filter));
		assertTrue("<sys><reg bla=\"foo\">".matches(filter));
		assertTrue("  <sys> <reg />".matches(filter));
		assertTrue("   <sys> <reg bla=\"foo\" bla=\"foo2\" ></reg> </sys>".matches(filter));
			*/
		
		String test = "<sys> <registration status=\"Status\"  fail-string=\"DesiredUsername | UserEmail\" /></sys>";
		assertTrue(test.matches(filter));

		
		filter = RegExFactory.startWithTagSubTag(SystemMessage.TAG, LoginAnswerMessage.TAG);
		LoginAnswerMessage failMessage = new LoginAnswerMessage(LoginAnswerStatus.FAIL);
		
		assertTrue(Pattern.matches(filter, failMessage.toXml()));
	}

	@Test
	public void orTest()
	{
		String regEx1 = RegExFactory.startWithTagSubTag(SystemMessage.TAG, LoginAnswerMessage.TAG);
		String regEx2 = RegExFactory.startWithTagSubTag(SystemMessage.TAG, LogoutMessage.TAG);
		
		String regEx = RegExFactory.createOr(regEx1, regEx2);
		
		System.out.println(regEx);
		
		LoginAnswerMessage failMessage = new LoginAnswerMessage(LoginAnswerStatus.FAIL);
		
		assertTrue(Pattern.matches(regEx, failMessage.toXml()));
	}
}
