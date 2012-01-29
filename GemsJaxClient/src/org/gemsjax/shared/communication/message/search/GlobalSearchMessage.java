package org.gemsjax.shared.communication.message.search;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * Sent from client to server to do a global search. 
 * This kind of search, searches for the following data, that matches {@link #getSearchString()}:
 * <ul>
 * <li> {@link RegisteredUser}s <li>
 * <li> {@link Collaborateable}s <li>
 * <li> {@link Experiment}s </li>
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class GlobalSearchMessage  extends ReferenceableSearchMessage {
	
	private static final String TAG="global";

	private String serachString;
	
	public GlobalSearchMessage(String referenceId, String searchString) {
		super(referenceId);
		this.serachString = searchString;
	}

	@Override
	public String toXml() {
		return "<"+ReferenceableSearchMessage.TAG+" "+ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+">"+serachString+"</"+TAG+"></"+ReferenceableSearchMessage.TAG+">";
	}

	public String getSearchString()
	{
		return serachString;
	}
}
