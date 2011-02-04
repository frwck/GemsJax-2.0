package org.gemsjax.client.adminui;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;


/**
 * This is the Header of the Admin GUI. Its implemented as Singleton, so use {@link #getInstance()}
 * @author Hannes Dorfmann
 *
 */
public class Header extends HLayout{
	
	
	private class SearchField extends TextItem
	{
		
		public SearchField()
		{
			super();
			this.setWidth( 200 );
			final PickerIcon searchIcon = new PickerIcon( PickerIcon.SEARCH );
			this.setIcons(searchIcon);
			this.setTitle("");
			this.setTitleStyle("header-searchfield-title");
		}
	}
	
	

	/**
	 * Singleton instance
	 */
	private static Header instance;
	/**
	 * The url path to the logo
	 */
	private static String logoUrl =  "/images/logo_dark_200.png";
	
	/**
	 * The height of this Header
	 */
	public static String headerHeight = "55px";

	private Img logo;
	private UserBox userBox;
	
	private Header()
	{
		// Setup this HStack 
		super();
		this.setWidth(AdminApplicationUI.contentWidth);
		this.setHeight(Header.headerHeight);
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
		this.userBox = UserBox.getInstance();
		
		DynamicForm searchForm = new DynamicForm();
		searchForm.setFields(new SearchField());
		searchForm.setMargin(10);
		
		
		// Add Members
		this.addMember(logo);
		this.addMember(spacer);
		this.addMember(searchForm);
		this.addMember(userBox);
		
		
		
				
	}
	
	
	
	
	/**
	 * Access to the singleton instance
	 * @return
	 */
	public static Header getInstance()
	{
		if (instance==null)
			instance = new Header();
		
		return instance;
	}
	
	
	
}
