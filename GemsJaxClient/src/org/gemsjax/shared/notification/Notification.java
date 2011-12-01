package org.gemsjax.shared.notification;

import java.util.Date;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;

/**
 * This is the base interface for {@link Notification}s.
 * A {@link Notification} is a simple message, generated from the system to inform the User about something.
 * 
 * @author Hannes Dorfmann
 *
 */
public interface Notification {

	/**
	 * The code number,  which indicates, that an {@link Experiment} was deleted for which 
	 * the user, who has received this {@link Notification}, was an administrator.
	 * 
	 * The optional message text will contain the original {@link Experiment} name ({@link #getCodeNumber()})
	 */
	public static int EXPERIMENT_DELETED = 1;
	
	/**
	 * The code number, which indicates, that a {@link Collaborateable} was deleted on which 
	 * the user, who has received this {@link Notification}, worked collaboratively.
	 * 
	 * The optional message text will contain the original name of the deleted {@link Collaborateable}. ({@link #getCodeNumber()})
	 */
	public static int COLLABORATEABLE_DELETED = 2;
	
	
	
	
	public long getId();
	public Date getDate();
	public void setDate(Date date);
	
	/**
	 * A optional text message. The content of this optional message depends on the type of this {@link Notification} (
	 * @return
	 */
	public String getOptionalMessage();
	public void setOptionalMessage(String message);
	
	public void setRead(boolean read);
	public boolean isRead();
	
	/**
	 * Get the code number.
	 * For every code number there is a text message in the current users language available.
	 * So this code number is submitted to the client, and the client will map this code number to a text in his current language and display it on screen
	 * @return
	 */
	public int getCodeNumber();
	public void setCodeNumber(int codeNumber);
	
}
