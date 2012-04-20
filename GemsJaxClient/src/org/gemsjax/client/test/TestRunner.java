package org.gemsjax.client.test;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VStack;

public class TestRunner extends Window {
	
	private VStack testResult;
	
	
	public TestRunner(){
		this.setTitle("Unit Tests");
		this.setWidth100();
		this.setHeight100();
		
		
		testResult = new VStack();
		testResult.addMember(new Label("<h1>Tests:</h1>"));
		
		this.addItem(testResult);
		this.bringToFront();
	}

}
