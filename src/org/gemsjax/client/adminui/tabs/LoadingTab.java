package org.gemsjax.client.adminui.tabs;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;


public class LoadingTab extends Tab{

	private static final String loadingImageURL ="/images/loading.gif";
	
	public Canvas content;
	public VLayout loadingCanvas;
	private boolean showingLoading;
	
	private static int i =0;
	
	public LoadingTab(String title)
	{
		super(title);
		
		content= new Label("Test");
		this.setPane(content);
		
		showingLoading = false;
		loadingCanvas = new VLayout();
		loadingCanvas.setWidth100();
		loadingCanvas.setHeight100();
		
				
		Img loadingImage = new Img(loadingImageURL);
		loadingImage.setValign(VerticalAlignment.CENTER);
		loadingImage.setAlign(Alignment.CENTER);
		loadingImage.setWidth(32);
		loadingImage.setHeight(32);
		
		HLayout helpLoadingLayout = new HLayout();
		helpLoadingLayout.setWidth100();
		
		
		Canvas topSpacer = new Canvas();
		topSpacer.setHeight("*");
		
		Canvas bottomSpacer = new Canvas();
		bottomSpacer.setHeight("*");
		

		Canvas leftSpacer = new Canvas();
		Canvas rightSpacer = new Canvas();
		
		leftSpacer.setWidth("*");
		rightSpacer.setWidth("*");
		
		helpLoadingLayout.addMember(leftSpacer);
		helpLoadingLayout.addMember(loadingImage);
		helpLoadingLayout.addMember(rightSpacer);
		
		
		loadingCanvas.addMember(topSpacer);
		loadingCanvas.addMember(helpLoadingLayout);
		loadingCanvas.addMember(bottomSpacer);
		
		
		
		
		loadingImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showContent();	
			}
		});
		
		
		
		content.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showLoading();
			}
		});
		
		
		new Timer()
		{
			public void run()
			{
				
				
					if (i%2==0)
						showContent();
					else
						showLoading();
					
				i++;
			}
		}.scheduleRepeating(2000);
		
		
	}
	
	
	public void showLoading()
	{
		if (!showingLoading)
			this.content = this.getPane();
		
		this.getTabSet().updateTab(this, loadingCanvas);
		showingLoading= true;
		
	}
	
	public void showContent()
	{
		if (content != null) 
			this.getTabSet().updateTab(this, content);
		
		showingLoading = false;
		
		
	}
	
	
}
