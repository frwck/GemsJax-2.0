package org.gemsjax.client.admin.adminui;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;


/**
 * This is the Header of the Admin GUI.
 * @author Hannes Dorfmann
 *
 */
public class Header extends HLayout{
	
	
	
	
	
	 /**
	 * The url path to the logo
	 */
	private static String logoUrl =  "/images/logo_dark_200.png";
	
	/**
	 * The height of this Header
	 */
	public static String HEIGHT = "55px";

	private Img logo;
	private UserBox userBox;
	
	private UserLanguage language;
	private SearchField searchField;
	
	public Header(UserLanguage language)
	{
		// Setup this HStack 
		super();
		
		this.language = language;
		
		this.setWidth(AdminApplicationViewImpl.contentWidth);
		this.setHeight(Header.HEIGHT);
		this.setMembersMargin(0);
		this.setAlign(Alignment.CENTER);
		
		// Set the logo 
		logo = new Img(logoUrl);
		logo.setWidth(200);
		logo.setHeight(40);
		logo.setMargin(5);
		
		
		Canvas spacer = new Canvas();
		spacer.setWidth("*");
		
		//UserBox
		this.userBox = new UserBox(language);
		searchField = new SearchField();
		
		DynamicForm searchForm = new DynamicForm();
		searchForm.setFields(searchField);
		searchForm.setMargin(10);
		
		
		
		// Add Members
		this.addMember(logo);
		this.addMember(spacer);
		this.addMember(searchForm);
		this.addMember(userBox);
		
		
		
				
	}

	
	public void setNotificationRequestCount(long count)
	{
		this.userBox.setNotificationRequestCount(count);
	}

	

	public HasClickHandlers getDashBoardMenuItem()
	{
		return userBox.getDashBoardMenuItem();
	}
	
	
	public HasClickHandlers getNotificationsMenuItem()
	{
		return userBox.getNotificationsMenuItem();
	}
	
	
	public HasClickHandlers getMetaModelsItem()
	{
		return userBox.getMetaModelsItem();
	}
	
	
	public HasClickHandlers getExpetimetsItem()
	{
		return userBox.getExpetimetsItem();
	}
	
	public HasClickHandlers getSettingsItem()
	{
		return userBox.getSettingsItem();
	}
	
	
	public HasClickHandlers getLogoutItem()
	{
		return userBox.getLogoutItem();
	}
	
	
	public SearchField getSearchField()
	{
		return searchField;
	}
	
	
	
}
