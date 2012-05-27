package org.gemsjax.client.experiment.view;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public interface ExperimentRegistrationView {
	
	public String getDisplayName();
	public String getPassword();
	public String getPasswordRepeated();
	public HasClickHandlers getSubmitButton();
	
	
	public void showPasswordMissmatch();
	public void showUnexpectedError();
	
	public void showDisplayedNameAlreadyUsed();
	public void showDisplayNameNotValid();
	public void showSuccessful();

}
