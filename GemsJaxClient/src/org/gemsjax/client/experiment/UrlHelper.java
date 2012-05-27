package org.gemsjax.client.experiment;

import com.google.gwt.user.client.Window;

public class UrlHelper {
	
	public static String getVerificationCode(){
		
		String url = Window.Location.getParameter("veri");
		
		return url;
		
		/*
		if (!url.matches(ServletPaths.SERVER_URL+ServletPaths.EXPERIMENT+"/.*"))
			return null;
		
		
		int index = url.lastIndexOf("/");
		if (index==-1)
			return null;
		
		return url.substring(index+1);
		
		*/
	}

}
