package org.gemsjax.client.admin.view.implementation;


import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.Footer;
import org.gemsjax.client.admin.adminui.Header;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.admin.view.AdminUIView;
import org.gemsjax.client.admin.view.QuickSearchView;
import org.gemsjax.client.util.Console;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * This is the base class for the GUI. 
 * @author Hannes Dorfmann
 *
 */
public class AdminApplicationViewImpl implements AdminUIView, QuickSearchView{
	
	/**
	 * The width of the "visible" content. This is the width of the {@link Header}, {@link Footer} and the {@link TabEnviroment}. <br /><br />
	 * <b> Also the Notification width will be calculated according this value.
	 * <u> If you change this value from percent to another unity, like px, you also have to change the implementation of the private method {@link TipNotification} calculateWidthAndPosition()</u></b>
	 * 
	 */
	public static final String contentWidth="90%";
	
	
	
	private VLayout uiLayout;
	
	
	private UserLanguage language;
	
	private Header header;
	private TabEnviroment tabEnviroment;
	
	
	public AdminApplicationViewImpl(UserLanguage language)
	{
		this.language = language;
		createUI();
		
	}
	
	
	private void createUI()
	{
		// Set the 
		uiLayout = new VLayout();
		uiLayout.setWidth100();
		uiLayout.setHeight100();
		uiLayout.setMargin(0);
		
		header = new Header(language);
		
		// Header
		Canvas spacerHeaderLeft = new Canvas();
		Canvas spacerHeaderRight = new Canvas();
	
		HLayout headerCenteringLayout = new HLayout();
		headerCenteringLayout.setWidth100();
		headerCenteringLayout.setHeight(Header.headerHeight);
		spacerHeaderLeft.setWidth("*");
		spacerHeaderRight.setWidth("*");
		
		headerCenteringLayout.addMember(spacerHeaderLeft);
		headerCenteringLayout.addMember(header);
		headerCenteringLayout.addMember(spacerHeaderRight);
		
		
		
		// TabEnviroment
		tabEnviroment = TabEnviroment.getInstance();
	
		Canvas spacerTabLeft = new Canvas();
		Canvas spacerTabRight = new Canvas();
	
		HLayout tabCenteringLayout = new HLayout();
		tabCenteringLayout.setWidth100();
		tabCenteringLayout.setHeight("*");
		spacerTabLeft.setWidth("*");
		spacerTabRight.setWidth("*");
		
		tabCenteringLayout.addMember(spacerTabLeft);
		tabCenteringLayout.addMember(tabEnviroment);
		tabCenteringLayout.addMember(spacerTabRight);
		
		
		
		
		// Add parts
		uiLayout.addMember(headerCenteringLayout);
		uiLayout.addMember(tabCenteringLayout);
		uiLayout.addMember(Footer.getInstance());
		
		uiLayout.draw();
		uiLayout.hide();
		
	}
	


	@Override
	public HasClickHandlers getUserMenuExperiments() {
		return header.getDashBoardMenuItem();
	}


	@Override
	public HasClickHandlers getUserMenuMetaModels() {
		return header.getMetaModelsItem();
	}


	@Override
	public HasClickHandlers getUserMenuSettings() {
		return header.getSettingsItem();
	}


	@Override
	public HasClickHandlers getUserMenuLogout() {
		return header.getLogoutItem();
	}


	@Override
	public HasClickHandlers getUserMenuNotifications() {
		return header.getNotificationsMenuItem();
	}


	@Override
	public void show() {
		uiLayout.show();
	}


	@Override
	public void hide() {
		uiLayout.hide();
	}


	@Override
	public void addQuickSearchHandler(QuickSearchHanlder h) {
		header.getSearchField().getQuickSearchHandlers().add(h);
	}


	@Override
	public void removeQuickSearchHandler(QuickSearchHanlder h) {

		header.getSearchField().getQuickSearchHandlers().remove(h);
	}



}
