package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.view.CreateExperimentView;
import org.gemsjax.client.admin.widgets.StepByStepWizard;
import org.gemsjax.client.admin.widgets.Title;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * This is the implementeation for the {@link CreateExperimentView}
 * @author Hannes Dorfmann
 *
 */
public class CreateExperimentViewImpl extends LoadingTab implements CreateExperimentView{

	private StepByStepWizard wizard;
	private Title title;
	
	public CreateExperimentViewImpl(UserLanguage language)
	{
		super(language.CreateExperiment(), language);
		wizard = new StepByStepWizard();
		
		
		buildWizzard();
		
		wizard.startFromBegin();
		
		
		this.title = new Title(language.CreateExperiment());
		
		VStack contentStack = new VStack();
		contentStack.setWidth100();
		contentStack.setHeight100();
		contentStack.addMember(this.title);
		contentStack.addMember(wizard.getDisplayableContent());
		
		
		
		this.setContent(contentStack);
		
		showContent();
		
		
		
	}
	
	
	private void buildWizzard()
	{
		
	}
	
	
	public void demo()
	{
		Button n1 = new Button("next1");
		n1.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wizard.next();
			}
		});
		
		Button n2 = new Button("next2");
		n2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wizard.next();
			}
		});
		
		
		Button n3 = new Button("next3");
		n3.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wizard.next();
			}
		});
		
		
		Button n4 = new Button("next4");
		n4.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wizard.next();
			}
		});
		
		
		
		Button n5 = new Button("next5");
		n5.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wizard.next();
			}
		});
		
		
		wizard.addStepSection(new Label("Step 1"), n1, n2);
		wizard.addStepSection(new Label("Step 2"), n3);
		wizard.addStepSection(new Label("Step 3"), n4);
		wizard.addStepSection(new Label("Step 4"), n5);
		
		
		
	}

	@Override
	public String getExperimentName() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
