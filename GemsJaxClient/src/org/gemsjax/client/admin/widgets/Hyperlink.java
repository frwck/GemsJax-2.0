package org.gemsjax.client.admin.widgets;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;

public class Hyperlink extends Label{
	
	private static String baseStyle = "widget-hyperlink-normal"; 
	private static String mouseOverStyle = "widget-hyperlink-mouseover";
	
	public Hyperlink(String txt){
		super(txt);
		this.setBaseStyle(baseStyle);
		
		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				setBaseStyle(baseStyle);
			}
		});
		
		this.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				setBaseStyle(mouseOverStyle);
			}
		});
		
		this.setHeight(16);
		this.setMargin(0);
		this.setPadding(0);
		
	}

}
