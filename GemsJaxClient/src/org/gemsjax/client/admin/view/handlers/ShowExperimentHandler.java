package org.gemsjax.client.admin.view.handlers;

/**
 * With this handler any view can tell this handler, that 
 * the user wants to display a Experiment right now
 * @author Hannes Dorfmann
 *
 */
public interface ShowExperimentHandler {
	/**
	 * With this handler any view can tell this handler, that 
 * the user wants to display a Experiment right now
	 * @param experimentId 
	 */
	public void onShowExperimentRequired(int experimentId);

}
