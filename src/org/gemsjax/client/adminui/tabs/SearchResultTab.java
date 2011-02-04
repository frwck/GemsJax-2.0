package org.gemsjax.client.adminui.tabs;

import org.gemsjax.client.adminui.widgets.BigMenuButton;
import org.gemsjax.client.adminui.widgets.Title;
import org.gemsjax.client.adminui.widgets.VerticalBigMenuButtonBar;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VStack;

public class SearchResultTab extends TwoColumnLayoutTab{

	private VStack content; 
	private BigMenuButton allResultsButton, othersMenuButton, metaModelmenuButton, experimentMenuButton, notificationsMenuButton;
	
	public SearchResultTab(String t) {
		super(t);
		
		VerticalBigMenuButtonBar bigMenuButtonBar = new VerticalBigMenuButtonBar(200,10,60);
		bigMenuButtonBar.setMargin(5);
		
		content = new VStack();
		content.setWidth("*");
		
		
		Title title =new Title("Search result");
		title.setWidth100();
		content.addMember(new Title("Title"));
		
		// Buttons
		allResultsButton = new BigMenuButton("All Results", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				simulateContentChange("Search Result: All Results", "Here will be the search resuld for All Results displayed", allResultsButton);
			}
		});
		
		
		

		allResultsButton.setActive(true);
		
		bigMenuButtonBar.addMember(allResultsButton);
		metaModelmenuButton = new BigMenuButton("Meta-Models", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				simulateContentChange("Search Result: Meta-Midels", "Here will be the search resuld for Meta-Model displayed", metaModelmenuButton);
			}
		});

		bigMenuButtonBar.addMember(metaModelmenuButton);
		
		experimentMenuButton = new BigMenuButton("Experiments", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				simulateContentChange("Search Result: Experiments", "Here will be the search resuld for Experiment displayed", experimentMenuButton);
			}
		});

		bigMenuButtonBar.addMember(experimentMenuButton);
		
		
		notificationsMenuButton = new BigMenuButton("Notifications", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				simulateContentChange("Search Result: Notifications", "Here will be the search resuld for Notifications displayed", notificationsMenuButton);
			}
		});
		
		bigMenuButtonBar.addMember(notificationsMenuButton);
		
		othersMenuButton = new BigMenuButton("Others", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				simulateContentChange("Search Result: Others", "Here will be the search resuld for Others displayed", othersMenuButton);
			}
		});
		bigMenuButtonBar.addMember(othersMenuButton);
		
		
		
		//bigMenuButtonBar.setMargin(5);
		//content.setMargin(5);
		

		this.setLeftColumn(bigMenuButtonBar, false);
		this.setRightColumn(content, true);
		
		
		

	}
	
	
	private void simulateContentChange(String title, String text, BigMenuButton button)
	{
		
		if (!button.isActive())
		{
			VStack stack = new VStack();
			stack.setMembersMargin(20);
			stack.setWidth("*");
			
			
			stack.addMember(new Title(title));
			stack.addMember(new Label(text));
			stack.setMargin(5);
			
			
			this.setRightColumn(stack, true);
			
			button.setActive(true);
		}
	}

}
