package org.gemsjax.client.adminui;

import org.gemsjax.client.adminui.tabs.TwoColumnLayoutTab;
import org.gemsjax.client.adminui.tabs.LoadingTab;
import org.gemsjax.client.adminui.tabs.SearchResultTab;
import org.gemsjax.client.desktopenviroment.DropDownMenuButton;
import org.gemsjax.client.desktopenviroment.DropDownMenuButton.DropDownMenu;

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
	
	private TabEnviroment(){
		super();
		
		this.setAlign(Alignment.CENTER);
		
		// Styling
		this.setWidth(AdminApplicationUI.contentWidth);
		
		
		this.addTab(new SearchResultTab("Search Result"));
		
		this.addTab(new LoadingTab("Loading Animation Tab"));
		
		this.addTab(new Tab("Tab3"));
		this.addTab(new Tab("Tab4"));
		this.addTab(new Tab("Tab5"));
		this.addTab(new Tab("Tab6"));
		this.addTab(new Tab("Tab7"));
		this.addTab(new Tab("Tab8"));
		this.addTab(new Tab("Tab9"));
		this.addTab(new Tab("Tab10"));
		this.addTab(new Tab("Tab11"));
		this.addTab(new Tab("Tab12"));
		this.addTab(new Tab("Tab13"));
		this.addTab(new Tab("Tab14"));
		this.addTab(new Tab("Tab15"));
		this.addTab(new Tab("Tab16"));
		this.addTab(new Tab("Tab18"));
		this.addTab(new Tab("Tab19"));
		this.addTab(new Tab("Tab20"));
		this.addTab(new Tab("Tab21"));

		
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
			instance = new TabEnviroment();
		
		return instance;
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
