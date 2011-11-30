package org.gemsjax.shared.request;

import java.util.Date;

import org.gemsjax.shared.user.RegisteredUser;

/**
 * This is the base interface for {@link Request}s.
 * A {@link Request} can be sent by another {@link RegisteredUser} user to make a request to work on something, like Experiment
 * @author Hannes Dorfmann
 *
 */
public interface Request {

	/**
	 * This enum represents the state of a {@link Request}
	 * @author Hannes Dorfmann
	 *
	 */
	public enum RequestState
	{
		/**
		 * Means: A {@link Request} is new and the receiver ({@link Request#getReceiver()}) has not answered yet
		 */
		NEW,
		
		/**
		 * Means: The receiver ({@link Request#getReceiver()}) has answered in a positive manner.
		 */
		ACCEPT,
		
		/**
		 * Means: The receiver ({@link Request#getReceiver()}) has answered in a negative manner.
		 */
		REJECT
	}
	
	/**
	 * The unique id
	 * @return
	 */
	public long getId();
	
	
	/**
	 * The date, when the server has stored this request in his database
	 * @return
	 */
	public Date getDate();
	public void setDate(Date date);
	
	/**
	 * The user, who must answere on this Request
	 * @return
	 */
	public RegisteredUser getReceiver();
	
	/**
	 * The user, who has created this {@link Request}
	 * @return
	 */
	public RegisteredUser getSender();
	public void setReceiver(RegisteredUser receiver);
	public void setSender(RegisteredUser sender);
	
	/**
	 * Get the {@link RequestState}
	 * @return
	 */
	public RequestState getRequestState();
	
	public void getRequestState(RequestState state);
	
}
