package org.gemsjax.client.admin.view;

import org.gemsjax.client.admin.widgets.StepByStepWizard.WizardHandler;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.friend.Friend;

public interface CreateMetaModelView {
	
	public void show();
	public void hide();
	
	public String getName();
	
	public Friend getCollaborators();
	
	public String getDescription();

	public void addWizardHandler(WizardHandler h);
	public void removeWizardHandler(WizardHandler h);
	
	public void onSuccessfulCreated();
	
	public void onErrorOccurred(CollaborateableFileError error);
	
}
