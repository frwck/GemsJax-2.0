package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;

public class NewExperimentRegistrationMessage extends SystemMessage implements Serializable{

	private String verificationCode;
	private String password;
	private String displayedName;
	
	
	public NewExperimentRegistrationMessage(){}
	
	
	
	public NewExperimentRegistrationMessage(String verificationCode,
			String password, String displayedName) {
		super();
		this.verificationCode = verificationCode;
		this.password = password;
		this.displayedName = displayedName;
	}



	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(Archive a) throws Exception {
		verificationCode = a.serialize("verificationCode", verificationCode).value;
		password = a.serialize("password", password).value;
		displayedName = a.serialize("displayedName", displayedName).value;
	}



	public String getVerificationCode() {
		return verificationCode;
	}



	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getDisplayedName() {
		return displayedName;
	}



	public void setDisplayedName(String displayedName) {
		this.displayedName = displayedName;
	}

}
