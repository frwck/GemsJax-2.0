package org.gemsjax.client.admin.adminui;

import org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Side;
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
	
	private static TabEnviroment instance = new TabEnviroment();

	private TabEnviroment(){
		super();
		
		this.setAlign(Alignment.CENTER);
		
		// Styling
		this.setWidth(AdminApplicationViewImpl.contentWidth);
		
		this.setTabBarPosition(Side.TOP);
		
		this.draw();
	}

	
	public static TabEnviroment getInstance()
	{
		return instance;
	}
	

}
