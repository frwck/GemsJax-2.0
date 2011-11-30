package org.gemsjax.server.persistence.dao;

import java.util.List;

import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.request.CollaborateRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;

public interface RequestDAO {
	
	/**
	 * Create a new {@link CollaborateRequest} to let a {@link RegisteredUser} work on a {@link Collaborateable}
	 * @param sender
	 * @param receiver
	 * @param c
	 * @return
	 * @throws DAOException 
	 */
	public abstract CollaborateRequest createCollaborateRequest(RegisteredUser sender, RegisteredUser receiver, Collaborateable c) throws DAOException;
	
	/**
	 * Get all {@link Request}s for a {@link RegisteredUser}.
	 * That means that the {@link RegisteredUser} is the receiver of a {@link Request}
	 * @param receiverUser
	 * @return
	 */
	public abstract List<Request> getAllRequestsFor(RegisteredUser receiverUser);
	
	/**
	 * Get all {@link Request}s that were created by the passed {@link RegisteredUser}
	 * @param senderUser The {@link RegisteredUser} who created a {@link Request}. That means, that the passed {@link RegisteredUser} is the sender of a {@link Request}.
	 * @return
	 */
	public abstract List<Request> getAllRequestsBy(RegisteredUser senderUser);

	/**
	 * Delete a {@link Request}
	 * @param request
	 */
	public abstract void deleteRequest(Request request) throws DAOException;
}
