package org.gemsjax.client.desktopenviroment;

import com.smartgwt.client.widgets.Canvas;

public class ContentContainer extends Canvas{
	
	
	private HomeOverview homeOverview;
	
	public ContentContainer()
	{
		super();
		this.homeOverview = new HomeOverview();
		
		this.setWidth100();
		this.setHeight("*");
		
		this.addChild(new GWindow("Test", 500 ,500));
		this.addChild(homeOverview);
		this.draw();
	}

}
