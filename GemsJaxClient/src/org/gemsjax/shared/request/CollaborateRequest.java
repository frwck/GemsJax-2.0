package org.gemsjax.shared.request;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * This {@link Request} is used to invite another {@link RegisteredUser} to work collaborative on a {@link Collaborateable}.
 * @author Hannes Dorfmann
 *
 */
public interface CollaborateRequest extends Request{
	
	/**
	 * Get the Collaborateable on which the user can work collaborative if he accept.
	 * @return
	 */
	public Collaborateable getCollaborateable();
	
	public void setCollaborateable(Collaborateable collaborateable);

}
