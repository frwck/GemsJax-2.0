package org.gemsjax.client.tests;

public class AssertException extends RuntimeException{
	
	private String name;
	private boolean expected, got;
	
	
	public AssertException(String name, boolean expected, boolean got){
		super();
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean getExpected(){
		return expected;
	}
	
	public boolean got(){
		return got;
	}

}
