package test.regex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.gemsjax.shared.RegExFactory;
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
		String filter = RegExFactory.startWithTagSubTag("sys", "reg");
		
		assertTrue("<sys><reg>".matches(filter));
		assertTrue("<sys> <reg>".matches(filter));
		assertTrue("<sys><reg/>".matches(filter));
		assertTrue("<sys><reg bla=\"foo\">".matches(filter));
		assertTrue("  <sys> <reg />".matches(filter));
		assertTrue("   <sys> <reg bla=\"foo\" bla=\"foo2\" ></reg> </sys>".matches(filter));
		
	}

}
