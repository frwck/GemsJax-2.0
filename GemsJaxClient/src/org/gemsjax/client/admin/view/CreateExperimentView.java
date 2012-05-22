package org.gemsjax.client.admin.view;

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
	
	public void showIt();
	
	public void closeIt();
	

}
