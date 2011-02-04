package org.gemsjax.client.desktopenviroment;


import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * The major UI class building the main structure of the UI ({@link TopBar} and {@link ContentContainer}).
 * <br />
 * <b>This class is realized as a <u>Singelton</u>.</b> So you must use {@link #getInstance()} to access the singelton instance
 * @author Hannes
 *
 */
public class DesktopEnviromentUI {

	/**
	 * The singelton instance
	 */
	private static DesktopEnviromentUI instance;
	
	
	private ContentContainer contentContainer;
	private TopBar topBar;
	
	
	private DesktopEnviromentUI()
	{
		
		contentContainer = new ContentContainer();
		topBar = new TopBar();
		
		
		/** Bulid the UI structure */
		RootPanel rootPanel = RootPanel.get();
		
		
		VLayout uiLayout = new VLayout();
		uiLayout.setWidth100();
		uiLayout.setHeight100();
		
		uiLayout.draw();
		
		
		uiLayout.addMember(topBar);
		uiLayout.addMember(contentContainer);
		
		rootPanel.add(uiLayout);
		
	}
	
	
	
	/**
	 * Get the singleton instance
	 * @return
	 */
	public static DesktopEnviromentUI getInstance()
	{
		if (instance == null) 
			instance = new DesktopEnviromentUI();
		
		return instance;
	}
	
	
	/**
	 * Get the {@link TopBar}
	 * @return
	 */
	public TopBar getTopBar()
	{
		return topBar;
	}
	
	
}
