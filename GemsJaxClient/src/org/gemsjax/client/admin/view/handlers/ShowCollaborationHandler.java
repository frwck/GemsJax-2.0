package org.gemsjax.client.admin.view.handlers;

import org.gemsjax.shared.collaboration.Collaborateable;

/**
 * With this handler any view can communicate with this handler, to say
 * that a certain collaborateable should be displayed
 * @author Hannes Dorfmann
 *
 */
public interface ShowCollaborationHandler {
	
	/**
	 * With this handler any view can communicate with this handler, to say
 * that a certain {@link Collaborateable} should be displayed
	 * @param collaboration
	 */
	public void onShowCollaborationRequired(int collaboration);

}
