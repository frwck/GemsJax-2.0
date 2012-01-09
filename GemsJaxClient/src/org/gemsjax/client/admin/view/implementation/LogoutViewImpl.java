package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.view.LogoutView;
import org.gemsjax.shared.communication.message.system.LogoutMessage.LogoutReason;

import com.smartgwt.client.util.SC;

public class LogoutViewImpl implements LogoutView{

	private UserLanguage language;
	
	public LogoutViewImpl(UserLanguage language)
	{
		this.language = language;
	}
	
	@Override
	public void show(LogoutReason reason) {
		
		String msg="Logout";
		switch (reason)
		{
			case CLIENT_USER_LOGOUT: msg = language.LogoutReasonClientUser(); break;
			case SERVER_OTHER_CONNECTION: msg = language.LogoutReasonServerOtherConnection(); break;
		}
		
		SC.warn(msg);
	}

}
