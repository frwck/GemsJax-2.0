package org.gemsjax.client.desktopenviroment;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.desktopenviroment.DropDownMenuButton.DropDownMenu;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;


/**
 * The TopBar is the GUI Component that shows the MainMenu ({@link #logoMenu}), the {@link HomeButton}, and the {@link WindowBar}.
 * Normally there exists only one TopBar. So this is implemented as a Singleton. 
 * @author Hannes Dorfmann
 *
 */
public class TopBar extends HLayout {

	
	private class HomeButton extends Layout implements MouseOverHandler, MouseOutHandler, ClickHandler
	{
		private String notHoverClass;
		private int test = 1;
		
		public HomeButton()
		{
			this.setWidth(28);
			this.setHeight(28);
			this.setPadding(1);
			
			Img icon = new Img("/images/icons/home.png");
			icon.setAlign(Alignment.CENTER);  
			icon.setLayoutAlign(VerticalAlignment.CENTER);
			icon.setCursor(Cursor.HAND);
			icon.setWidth(26);
			icon.setHeight(26);
			
			this.setAlign(Alignment.CENTER);  
			this.setLayoutAlign(VerticalAlignment.CENTER);
			
			notHoverClass = this.getStyleName();
			
			this.addMember(icon);
			
			this.addMouseOverHandler(this);
			this.addMouseOutHandler(this);
			this.addClickHandler(this);
		}

		@Override
		public void onClick(ClickEvent event) {
			//TODO on click on HOME Button
			
			windowBar.addWindowTask(new WindowTask("Task "+test));
			test++;
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			this.setStyleName(notHoverClass);
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			this.setStyleName("homeIconHover");
		}
		
		
	}
	
	
	
	private WindowBar windowBar;
	private DropDownMenuButton logoMenu;
	private HomeButton homeIcon;
	private List<WindowTask> windowTaskList;
	
	public TopBar()
	{
		super();
		windowTaskList = new LinkedList<WindowTask>();
		
		// Styling
		this.addStyleName("TopBar");
		this.setHeight(30);
		this.setWidth100();
		this.setMembersMargin(5);
	    this.setLayoutMargin(0);
	    this.setLayoutRightMargin(5);
	    this.setLayoutLeftMargin(5);
	   
	    // Logo and main Menu
	   Img logo = new Img("/images/logo.png");
	   logo.setAlign(Alignment.CENTER);  
       logo.setLayoutAlign(VerticalAlignment.CENTER);
       
	   logoMenu = new DropDownMenuButton(logo); 
	   generateMainMenu();
	   
	   // HomeIcon
	   this.homeIcon = new HomeButton();
	   
	   // WindowBar
	   this.windowBar = new WindowBar();
	   this.windowBar.setWidth("*");
	   
	   this.addMember(logoMenu);
	   this.addMember(this.homeIcon);
	   this.addMember(windowBar);
	   
	}
	

	
	
	/**
	 * Get the WindowBar
	 * @return
	 */
	public WindowBar getWindowBar()
	{
		return windowBar;
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
