package org.gemsjax.client.tests;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

public class TestRunner extends Window  implements ClickHandler{
	
	private VLayout container;
	private UnitTest unitTest;
	private Button runButton;
	private Label resultOverview;
	private static TextAreaItem console;
	private static DynamicForm consoleForm;
	private HStack header;
	
	public TestRunner(UnitTest unitTest){
		this.setTitle("Unit Tests");
		this.setWidth100();
		this.setHeight100();
		
		this.unitTest = unitTest;
		
		runButton = new Button("start unit tests");
		runButton.addClickHandler(this);
		container = new VLayout();
		
		
		resultOverview = new Label();
		resultOverview.setHeight(20);
		resultOverview.setWidth100();
		
		
		header = new HStack();
		header.setHeight(20);
		header.addMember(runButton);
		header.addMember(resultOverview);
		
		buildConsole();
		
		unitTest.setHeight("*");
		unitTest.setOverflow(Overflow.SCROLL);
		
		
		container.addMember(header);
		container.addMember(unitTest);
		container.addMember(consoleForm);
		container.setWidth100();
		container.setHeight100();
		
		this.addItem(container);
		this.draw();
		this.bringToFront();
		
	}
	
	
	private void setResultOverview(){
		resultOverview.setContents("Tests runned: "+unitTest.getTestCount()+" Successful: "+unitTest.getSuccessfulCount()+" Failed: "+unitTest.getFailCount());
	}


	public void runUnitTest(){
		container.removeMembers(container.getMembers());
		container.addMember(header);
		container.addMember(unitTest);
		container.addMember(consoleForm);
		unitTest.clearTestResults();
		
		try{
			unitTest.test();
		}
		catch (AssertException e){
			Label exLabel = new Label("The test \""+e.getName()+"\" has failed. Expected "+e.getExpected()+" but test has evaluated to "+e.got());
			exLabel.setWidth(100);
			exLabel.setHeight(20);
			unitTest.addMember(exLabel);
		}
		catch(Throwable t){
			Label exLabel = new Label(t.getClass().getName()+": " + t.getMessage() +"<br />"+t.getLocalizedMessage()+"<br />"+toString());
			exLabel.setWidth(100);
			unitTest.addMember(exLabel);
		}
		finally{
			setResultOverview();
		}
		
	}

	
	@Override
	public void onClick(ClickEvent event) {
		runUnitTest();
	}
	
	
	private void buildConsole(){
		consoleForm = new DynamicForm();
	    console = new TextAreaItem();
	    console.setShowTitle(false);
	    consoleForm.setFields(console);
	    console.setWidth(800);
	    console.setHeight("100%");
	    consoleForm.setWidth(800);
	    consoleForm.setHeight("20%");
	}

	
	public static void logConsoleMessage(String msg){
		console.setValue(console.getValueAsString()+"\n"+msg);
	}
}
