package org.gemsjax.client.view.implementation;

import org.gemsjax.client.view.LoadingView;

import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class LoadingViewImpl extends Window implements LoadingView {
	
	/**
	 * The url to the logo image
	 */
	private static final String loadingURL="/images/loading_grey.gif";
	
	/**
	 * The url to the loading animation gif
	 */
	private static final String logoURL="/images/loading-logo.png";
	
	
	private Label loadingLabel;
	
	
	public LoadingViewImpl()
	{
		super();

		/*
		this.setStyleName("loadingViewBackground");
		 
		this.setWidth100();
		this.setHeight100();
		*/
		
		this.setWidth(100);
		this.setHeight(100);
		
		
		this.setBackgroundColor("none");
		
		
		// Label
		this.loadingLabel = new Label();
		this.loadingLabel.setAlign(Alignment.CENTER);
		this.loadingLabel.setStyleName("loadingLabel");
		setLoadingMessage("start loading");
		loadingLabel.setValign(VerticalAlignment.TOP);
		loadingLabel.setAlign(Alignment.CENTER);
		
		// Loading Animation Img
		Img loadingImg = new Img("/images/loadingsnake.gif");
		loadingImg.setWidth(45);
		loadingImg.setHeight(45);
		loadingImg.setAlign(Alignment.CENTER);
		loadingImg.setValign(VerticalAlignment.BOTTOM);
		
	
		Label loadingAnimation = new Label("<img src=\""+"/images/loadingsnake.gif"+"\" width=\"50\" />");
		loadingAnimation.setAlign(Alignment.CENTER);
		loadingAnimation.setValign(VerticalAlignment.BOTTOM);
		//loadingAnimation.setHeight100();
		
		
		// VStack
		VStack loadingContent = new VStack();
		loadingContent.setHeight100();
		loadingContent.setWidth100();
		loadingContent.setAlign(Alignment.CENTER);
		loadingContent.setMembersMargin(20);
		
		loadingContent.addMember(loadingAnimation);
		loadingContent.addMember(loadingLabel);
	
		
		
		this.setStyleName("loadingWindow");
		this.addItem(loadingContent);
		
		this.setIsModal(true);
		this.setWidth100();
		this.setHeight100();
		this.setOpacity(100);
		this.setModalMaskOpacity(50);
		this.setShowModalMask(true);
		this.setModalMaskStyle("loadingViewBackground");
		this.setBorder("none");
		this.setShowHeader(false);
		this.setShowStatusBar(false);
		this.setShowTitle(false);
		this.setShowCloseButton(false);
		this.setCanDragResize(false);
		this.setCanDragReposition(false);
		this.setEdgeSize(0);
		this.setEdgeOpacity(0);
		
		
		
		
		this.draw();
		this.bringToFront();
		this.centerInPage();
		
		
		
	}
	


	@Override
	public void hideIt() {
		this.hide();
	}



	@Override
	public void setLoadingMessage(String text) {
		this.loadingLabel.setContents(text);
		
	}

}
