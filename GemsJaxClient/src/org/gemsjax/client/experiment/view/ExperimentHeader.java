package org.gemsjax.client.experiment.view;

import java.util.Date;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.Header;
import org.gemsjax.client.admin.adminui.UserBox;
import org.gemsjax.client.admin.adminui.UserBox.UserBoxItem;
import org.gemsjax.client.admin.adminui.UserBox.UserBoxItemSeparator;
import org.gemsjax.client.admin.adminui.UserBox.UserBoxMenuItem;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class ExperimentHeader extends HLayout{
	
	private static String logoUrl =  "/images/logo_dark_200.png";
	private Img logo;
	private UserBox userBox;
	
	private UserLanguage language;
	
	
	
	public ExperimentHeader(UserLanguage language, String experimentName, String experimentGroupName, Date startDate, Date endDate, String userName){
		
		this.language = language;
		this.userBox = new UserBox(language);
		userBox.removeMembers(userBox.getMembers());
		
		userBox.addMember(userBox.getBorderLeft());
		userBox.addMember(userBox.new UserBoxItem("Hello "+userName));
		userBox.addMember(userBox.new UserBoxItemSeparator());
		userBox.addMember((UserBoxMenuItem)userBox.getLogoutItem());
		userBox.addMember(userBox.getBorderRight());
		
		
		
		
		this.setWidth100();
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
		
		String txt = experimentName+"<b> &raquo;</b> "+experimentGroupName+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"<b>Start:</b> "+DateTimeFormat.getMediumDateTimeFormat().format(startDate)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b>End:</b>"+DateTimeFormat.getMediumDateTimeFormat().format(endDate);
		
		Label expDetails = new Label(txt);
		expDetails.setPadding(10);
		expDetails.setHeight100();
		expDetails.setWidth("*");
		
		
		
		// Add Members
		this.addMember(logo);
		this.addMember(expDetails);
		this.addMember(spacer);
		this.addMember(userBox);
	}
	
	

}
