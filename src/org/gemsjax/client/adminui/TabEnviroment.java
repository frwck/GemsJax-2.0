package org.gemsjax.client.adminui;

import org.gemsjax.client.adminui.tabs.TwoColumnLayoutTab;
import org.gemsjax.client.adminui.tabs.LoadingTab;
import org.gemsjax.client.adminui.tabs.SearchResultTab;
import org.gemsjax.client.desktopenviroment.DropDownMenuButton;
import org.gemsjax.client.desktopenviroment.DropDownMenuButton.DropDownMenu;
import org.gemsjax.client.presenter.CreateExperimentPresenter;
import org.gemsjax.client.view.implementation.CreateExperimentViewImpl;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * This is the TabEnviroment which displayes the {@link Tab}.<br /> 
 * This is a <b>Singleton</b> so use {@link #getInstance()}
 * 
 * @author Hannes Dorfmann
 *
 */
public class TabEnviroment extends TabSet{
	
	private static TabEnviroment instance;

	private DropDownMenuButton logoMenu;
	private int iii = 0;
	
	
	private TabEnviroment(){
		super();
		
		this.setAlign(Alignment.CENTER);
		
		// Styling
		this.setWidth(AdminApplicationUI.contentWidth);
		
		this.setTabBarPosition(Side.TOP);
		
		this.draw();
	}
	
	/**
	 * Get the {@link TabEnviroment} instance (Singleton)
	 * @return
	 */
	public static TabEnviroment getInstance()
	{
		if (instance == null)
		{
			instance = new TabEnviroment();
			demo();
		}
		return instance;
	}
	
	
	// TODO remove DEMO TABS
	private static void demo()
	{
		final LoadingTab loadingTab = new LoadingTab("Loading Tab Test");
		loadingTab.setContent(new Label("Test"));
		// TODO remove demo timer	
		new Timer()
		{
			public void run()
			{
				
				
					if (instance.iii%2==0)
						loadingTab.showContent();
					else
						loadingTab.showLoading();
					
					instance.iii++;
			}
		}.scheduleRepeating(2000);
		
		instance.addTab(new SearchResultTab("Search Result"));
		instance.addTab(loadingTab);
		instance.addTab(new CreateExperimentViewImpl("New Experiment"));
		instance.addTab(new Tab("Tab3"));
		instance.addTab(new Tab("Tab4"));
		instance.addTab(new Tab("Tab5"));
		instance.addTab(new Tab("Tab6"));
		

	}
	
	
	/**
	 * Generates the MainMenu
	 */
	private void generateMainMenu()
	{
		logoMenu.setWidth(115);
		logoMenu.setHeight(30);
		logoMenu.setHoverClass("logoHover");
		logoMenu.setPadding(5);
		
		DropDownMenu menu = logoMenu.getDropDownMenu();
		menu.addStyleName("dropDownMainMenu");
		menu.setPosition(5, 30);
		
		
		for (int i =0; i<5;i++)
		{
			Label label = new Label("<a href=\"#\">Label "+i+"</a>");
			label.setHeight(20);
			menu.addMenuItem(label);
		};
			
		
		menu.draw();
		logoMenu.draw();
	
		
	}

}
