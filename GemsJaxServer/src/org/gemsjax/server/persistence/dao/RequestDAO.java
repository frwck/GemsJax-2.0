package org.gemsjax.server.persistence.dao;

import java.util.List;

import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyBefriendedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyExistsException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.request.AdministrateExperimentRequest;
import org.gemsjax.shared.request.CollaborateRequest;
import org.gemsjax.shared.request.FriendshipRequest;
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
	 * @throws {@link AlreadyAssignedException} thrown, if its not possible to create a {@link Request}, because the receiver({@link Request#getReceiver()}) is already working collaborateable on this collaborateable
	 * @throws AlreadyExistsException thrown, if there exists already a {@link CollaborateRequest} with the same sender, receiver and {@link Collaborateable}
	 */
	public abstract CollaborateRequest createCollaborateRequest(RegisteredUser sender, RegisteredUser receiver, Collaborateable c) throws DAOException, AlreadyAssignedException, AlreadyExistsException;
	
	/**
	 * Create a new {@link AdministrateExperimentRequest}
	 * @param sender
	 * @param receiver
	 * @param experiment
	 * @return
	 * @throws DAOException
	 * @throws {@link AlreadyAssignedException} thrown, if its not possible to create a {@link Request}, because the receiver({@link Request#getReceiver()}) is already working collaborateable on this collaborateable
	 * @throws AlreadyExistsException thrown, if there exists already a {@link CollaborateRequest} with the same sender, receiver and {@link Collaborateable}
	 */
	public abstract AdministrateExperimentRequest createAdministrateExperimentRequest(RegisteredUser sender, RegisteredUser receiver, Experiment experiment) throws DAOException, AlreadyAssignedException, AlreadyExistsException;
	
	public abstract FriendshipRequest createFriendshipRequest(RegisteredUser sender, RegisteredUser receiver) throws AlreadyBefriendedException, AlreadyExistsException, DAOException;
	
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
	
	/**
	 * Get a request by its unique id
	 * @param id
	 * @return
	 * @throws DAOException
	 * @throws NotFoundException
	 */
	public abstract Request getRequestById(int id) throws DAOException, NotFoundException;
	
	
	public long getRequestCount(RegisteredUser user) throws DAOException;
	
	
	public boolean isFriendshipRequestAlreadyExisting(RegisteredUser requester, RegisteredUser receiver) throws DAOException;
	
	public boolean isAdminExperimentRequestAlreadyExisting(RegisteredUser receiver, Experiment experiment) throws DAOException;
	
	public boolean isCollaborationRequestAlreadyExisting( RegisteredUser receiver, Collaborateable collaborateable) throws DAOException;
	
	
	
}
