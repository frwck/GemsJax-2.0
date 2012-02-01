package org.gemsjax.client.module.handler;

import java.util.Set;

import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.UserResult;

/**
 * 
 * @author Hannes Dorfmann
 *
 */
public interface GlobalSearchModuleHandler {
	
	public void onSearchResultReceived(String referenceId, Set<UserResult> userResults, Set<CollaborationResult> collaborationResutl, Set<ExperimentResult> experimentResult);

}
