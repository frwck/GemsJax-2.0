package org.gemsjax.client.admin.view;

import java.util.Set;

import org.gemsjax.client.admin.widgets.StepByStepWizard.WizardHandler;
import org.gemsjax.shared.communication.message.experiment.ExperimentGroupDTO;
import org.gemsjax.shared.communication.message.friend.Friend;

/**
 * This is the View inteface to create a new Experiment
 * @author Hannes Dorfmann
 *
 */
public interface CreateExperimentView {
	
	/**
	 * Get the name of the Experiment
	 * @return
	 */
	public String getExperimentName();
	
	public String getDescription();
	
	public Set<Friend> getSelectedFriends();
	
	public Set<ExperimentGroupDTO> getExperimentGroups();
	
	
	public void showIt();
	public void closeIt();
	
	
	public void addWizardHandler(WizardHandler h);
	public void removeWizardHandler(WizardHandler h);

	public void showException(Exception e);

	public void showSuccessful();

}
