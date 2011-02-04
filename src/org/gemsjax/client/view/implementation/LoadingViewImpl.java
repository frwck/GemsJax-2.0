package org.gemsjax.client.view.implementation;

import org.gemsjax.client.desktopenviroment.TopBar;
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

public class LoadingViewImpl extends VLayout implements LoadingView {
	
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

		this.setStyleName("loadingViewBackground");
		this.setWidth100();
		this.setHeight100();
		
		
		Label spacerTop = new Label();
		Label spacerBottom = new Label();
		
		spacerTop.setWidth("*");
		spacerBottom.setWidth("*");
		
		// Label
		this.loadingLabel = new Label();
		this.loadingLabel.setAlign(Alignment.CENTER);
		this.loadingLabel.setStyleName("loadingLabel");
		setLoadingMessage("start loading");
		
		// VStack
		VStack loadingContent = new VStack();
		Label imgLabel = new Label();
		imgLabel.setContents("<img src=\""+logoURL+"\" width=\"300\" /> <br /><br /> "+
									" <img src=\""+loadingURL+"\" width=\"32\" /> <br /> <br />");
		imgLabel.setAlign(Alignment.CENTER);
		loadingContent.addMember(imgLabel);
		loadingContent.addMember(loadingLabel);
		
		this.setMembersMargin(10);
		this.addMember(spacerTop);
		this.addMember(loadingContent);
		this.addMember(spacerBottom);
		
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
