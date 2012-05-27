package org.gemsjax.client.experiment.view;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public interface ExperimentLoginView {
	
	
	public String getPassword();
	public HasClickHandlers getLoginButton();
	public void showPasswordIncorrectMessage();
	public void showError(Throwable t);

}
