package org.gemsjax.client.adminui.tabs;

import org.gemsjax.client.adminui.TabEnviroment;

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
import com.smartgwt.client.widgets.tab.TabSet;


public class LoadingTab extends Tab{

	private static final String loadingImageURL ="/images/loading.gif";
	
	public Canvas content;
	public VLayout loadingCanvas;
	private boolean showingLoading;
	
	public LoadingTab(String title)
	{
		super(title);
		
		content = new Canvas();
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
		
		
	}
	
	/**
	 * Show the loading animation and hide the normal content
	 */
	public void showLoading()
	{
		if (!showingLoading)
			this.content = this.getPane();
		
		this.getTabSet().updateTab(this, loadingCanvas);
		showingLoading= true;
		
	}
	
	/**
	 * Display the normal content and hide the loading animation
	 */
	public void showContent()
	{
		if (content != null) 
		{
			// TODO why TabSet is Null?
			TabSet ts=this.getTabSet();
			
			TabEnviroment.getInstance().updateTab(this, content);
		}
			
		
		showingLoading = false;
	
		
	}
	
	
	/**
	 * Set the normal content that is displayed, when not the loading animation ha
	 * @param content
	 */
	public void setContent(Canvas content)
	{
		this.content = content;
	}
	
		
}
