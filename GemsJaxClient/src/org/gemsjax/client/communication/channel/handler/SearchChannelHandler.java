package org.gemsjax.client.communication.channel.handler;

import java.util.Set;

import org.gemsjax.client.communication.channel.SearchChannel;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.SearchResultErrorMessage;
import org.gemsjax.shared.communication.message.search.UserResult;

/**
 * The observer / handler for {@link SearchChannel}s
 * @author Hannes Dorfmann
 *
 */
public interface SearchChannelHandler {
	
	public void onSearchResultReceived(String referenceId, Set<UserResult> userResults, Set<CollaborationResult> collaborationResults, Set<ExperimentResult> experimentResults);

	
	/**
	 * Called if the client has received a {@link SearchResultErrorMessage} from the server as result of a search query message
	 * @param referenceId
	 * @param error
	 */
	public void onSearchResultError(String referenceId, SearchError error);
	
	/**
	 * Called, if an unexpected error occurs in the observed {@link SearchChannel}, like {@link CommunicationConnection} errors
	 * or parsing the server result error
	 * @param The referenceId (if could be determined)
	 * @param The {@link Throwable}
	 */
	public void onUnexpectedError(String referenceId, Throwable t);
}
