package org.gemsjax.client.admin.adminui;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.presenter.CreateExperimentPresenter;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.tabs.SearchResultTab;
import org.gemsjax.client.admin.tabs.TwoColumnLayoutTab;
import org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl;
import org.gemsjax.client.admin.view.implementation.CreateExperimentViewImpl;

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

	
	private UserLanguage language;
	
	private int iii = 0;
	
	
	public TabEnviroment(UserLanguage language){
		super();
		
		//TODO Implement TabEnviroment as pure Singleton when demo has been removed
		//TODO remove hack
		instance = this;
		
		this.language = language;
		this.setAlign(Alignment.CENTER);
		
		// Styling
		this.setWidth(AdminApplicationViewImpl.contentWidth);
		
		this.setTabBarPosition(Side.TOP);
		
		this.draw();
		demo();
	}

	
	public static TabEnviroment getInstance()
	{
		return instance;
	}
	
	// TODO remove DEMO TABS
	private void demo()
	{
		final LoadingTab loadingTab = new LoadingTab("Loading Tab Test", language);
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
		
		instance.addTab(new SearchResultTab("Search Result", language));
		instance.addTab(loadingTab);
		instance.addTab(new Tab("Tab3"));
		instance.addTab(new Tab("Tab4"));
		instance.addTab(new Tab("Tab5"));
		instance.addTab(new Tab("Tab6"));
		

	}

}
