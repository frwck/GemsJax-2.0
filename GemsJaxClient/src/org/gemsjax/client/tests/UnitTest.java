package org.gemsjax.client.tests;

import com.smartgwt.client.widgets.layout.VStack;
/**
 * This is a simple Unit Test that can be run.
 * @author Hannes Dorfmann
 *
 */
public abstract class UnitTest  extends VStack{
	
	private int testsRunned;
	private int testsOk;
	private int testsFailed;
	
	public UnitTest(){
		setWidth100();
		testsRunned = 0;
		testsFailed = 0;
		testsFailed = 0;
	}
	
	/**
	 * This method will be called, to run a {@link UnitTest}.
	 * This is the method, where you can check correctness by using
	 * {@link #assertTrue(String, boolean)}, {@link #assertFalse(String, boolean)},
	 * {@link #assertEqual(String, Object, Object)} and {@link #assertNotEqual(String, Object, Object)}.
	 */
	public abstract void test();

	public void clearTestResults(){
		this.removeMembers(this.getMembers());
		testsRunned = 0;
		testsFailed = 0;
		testsFailed = 0;
	}
	
	
	/**
	 * 
	 * @param name The name is used, to trace which test was successfull or has been failed. So the name should be unique
	 * @param booleanExpression
	 */
	public void assertTrue(String name, boolean booleanExpression){
		testsRunned++;
		if (booleanExpression){
			this.addMember(new CorrectTestLabel(name));
			testsOk++;
		}
		else{
			this.addMember(new FailTestLabel(name));
			testsFailed++;
			throw new AssertException(name, true, false);
		}
	}
	
	
	public void assertFalse(String name, boolean booleanExpression){
		testsRunned++;
		if (!booleanExpression){
			this.addMember(new CorrectTestLabel(name));
			testsOk++;
		}
		else{
			this.addMember(new FailTestLabel(name));
			testsFailed++;
			throw new AssertException(name, false, true);
		}
	}
	
	/**
	 * Check if Object a equals Object b by calling a.equals(b)
	 * @param name
	 * @param a
	 * @param b
	 */
	public void assertEqual(String name, Object a, Object b){
		testsRunned++;
		if (a.equals(b))
		{
			this.addMember(new CorrectTestLabel(name));
			testsOk++;
		}
		
		else {
			this.addMember(new FailTestLabel(name));
			testsFailed++;
			throw new AssertException(name, true, false);
		}
	}
	
	
	/**
	 * Check if Object a not equals Object b by calling a.equals(b)
	 * @param name
	 * @param a
	 * @param b
	 */
	public void assertNotEqual(String name, Object a, Object b){
		testsRunned++;
		if (!a.equals(b)){
			testsOk++;
			this.addMember(new CorrectTestLabel(name));
		}
		else {
			this.addMember(new FailTestLabel(name));
			testsFailed++;
			throw new AssertException(name, false, true);
		}
	}
	
	
	public int getTestCount(){
		return testsRunned;
	}
	
	public int getFailCount(){
		return testsFailed;
	}
	
	
	public int getSuccessfulCount(){
		return testsOk;
	}
	
	
	public void logConsole(String msg){
		TestRunner.logConsoleMessage(msg);
	}
	
}
