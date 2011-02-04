package org.gemsjax.client.adminui;


import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * This is the class for the GUI. This is a Singleton, so use {@link #getInstance()}.
 * @author Hannes Dorfmann
 *
 */
public class AdminApplicationUI {
	
	/**
	 * The width of the "visible" content. This is the width of the {@link Header}, {@link Footer} and the {@link TabEnviroment}
	 */
	public static String contentWidth="90%";
	
	/**
	 * The singleton instance
	 */
	private static AdminApplicationUI instance;
	
	
	
	
	private AdminApplicationUI()
	{
		createUI();
	}
	
	
	private void createUI()
	{
		// Set the 
		VLayout uiLayout = new VLayout();
		uiLayout.setWidth100();
		uiLayout.setHeight100();
		uiLayout.setMargin(0);
		
		
		// Header
		Canvas spacerHeaderLeft = new Canvas();
		Canvas spacerHeaderRight = new Canvas();
	
		HLayout headerCenteringLayout = new HLayout();
		headerCenteringLayout.setWidth100();
		headerCenteringLayout.setHeight(Header.headerHeight);
		spacerHeaderLeft.setWidth("*");
		spacerHeaderRight.setWidth("*");
		
		headerCenteringLayout.addMember(spacerHeaderLeft);
		headerCenteringLayout.addMember(Header.getInstance());
		headerCenteringLayout.addMember(spacerHeaderRight);
		
		
		
		// TabEnviroment
		Canvas spacerTabLeft = new Canvas();
		Canvas spacerTabRight = new Canvas();
	
		HLayout tabCenteringLayout = new HLayout();
		tabCenteringLayout.setWidth100();
		tabCenteringLayout.setHeight("*");
		spacerTabLeft.setWidth("*");
		spacerTabRight.setWidth("*");
		
		tabCenteringLayout.addMember(spacerTabLeft);
		tabCenteringLayout.addMember(TabEnviroment.getInstance());
		tabCenteringLayout.addMember(spacerTabRight);
		
		
		
		
		// Add parts
		uiLayout.addMember(headerCenteringLayout);
		uiLayout.addMember(tabCenteringLayout);
		uiLayout.addMember(Footer.getInstance());
		
		uiLayout.draw();
		
	}
	
	
	
	
	/**
	 * Access the singleton via this method.
	 * @return
	 */
	public static AdminApplicationUI getInstance()
	{
		if (instance == null)
			instance = new AdminApplicationUI();
		
		return instance;
	}

}
