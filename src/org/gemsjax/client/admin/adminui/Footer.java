package org.gemsjax.client.admin.adminui;

import com.ibm.icu.util.Calendar;
import com.smartgwt.client.types.BkgndRepeat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;

/**
 * The Footer, implemented as Singleton, so use {@link #getInstance()}
 * @author Hannes Dorfmann
 *
 */
public class Footer  extends HLayout{

	private static String footerHeight = "40px";
	
	/**
	 * The singleton instance
	 */
	private static Footer instance;
	
	private Footer()
	{
		super();
		this.setWidth100();
		this.setHeight(footerHeight);
		//this.setBackgroundImage("/images/footer_background.png");
		//this.setBackgroundRepeat(BkgndRepeat.REPEAT_X);
		this.setStyleName("footer");
		
		// spacers
		Canvas spacerLeft = new Canvas();
		spacerLeft.setWidth("*");
		
		Canvas spacerRight = new Canvas();
		spacerRight.setWidth("*");
		
		
		//TODO add FOOTER content
		HStack footerContent = new HStack();
	
		footerContent.setMembersMargin(5);
		
		Label copyright = new Label("<div style=\"font: normal normal normal 13.34px/normal helvetica, arial, freesans, clean, sans-serif;\">Copyright &copy; 2011 GemsJax</div>");
		copyright.setWidth(200);
		
		footerContent.addMember(copyright);
		footerContent.addMember(new Label("<a href=\"#\">about</a>"));
		footerContent.addMember(new Label("<a href=\"#\">contact</a>"));
		footerContent.addMember(new Label("<a href=\"#\">privacy</a>"));
		footerContent.addMember(new Label("<a href=\"#\">terms of service</a>"));
		
		
		
		this.addMember(spacerLeft);
		this.addMember(footerContent);
		this.addMember(spacerRight);
	}
	
	
	
	
	/**
	 * Access to the singleton instance
	 * @return
	 */
	public static Footer getInstance()
	{
		if (instance==null)
			instance = new Footer();
		
		return instance;
	}
	
	
	
}