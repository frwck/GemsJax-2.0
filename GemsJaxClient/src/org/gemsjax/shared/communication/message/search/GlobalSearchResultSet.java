package org.gemsjax.shared.communication.message.search;

import java.util.Set;

import org.gemsjax.shared.communication.message.friend.Friend;

/**
 * This is the result set of a {@link GlobalSearchMessage}
 * @author Hannes Dorfmann
 *
 */
public class GlobalSearchResultSet {
	
	private Set<UserResult> userResults;
	private Set<Friend> friendResults;
	private Set<CollaborationResult> metaModelResults;
	private Set<CollaborationResult> modelResults;
	private Set<ExperimentResult> experimentResults;
	private Set<CollaborationResult> usersMetaModelsResult;
	private Set<CollaborationResult> usersModelsResult;
	
	/**
	 * 
	 * @param userResults The result set with all users (exclusive friends), which are matching the search criteria
	 * @param friendResults The result of your friends, which are matching the search criteria
	 * @param metaModelResults The result set of all public meta models (exclusive the user owned/collaborated ones), which are matching the search criteria
	 * @param modelResults The result set of all public meta models (exclusive the user owned/collaborated ones), which are matching the search criteria
	 * @param usersMetaModelsResult The result set of the users owned or in collaboration MetaModels, which are matching the search criteria
	 * @param usersModelsResult The result set of the users owned or in collaboration MetaModels, which are matching the search criteria
	 * @param experimentResults The result of all experiments that are owned or co-administrated by the user
	 */
	public GlobalSearchResultSet(Set<UserResult> userResults, Set<Friend> friendResults,
			Set<CollaborationResult> metaModelResults, Set<CollaborationResult> modelResults,
			Set<CollaborationResult> usersMetaModelsResult, Set<CollaborationResult> usersModelsResult, Set<ExperimentResult> experimentResults)
	{
		this.userResults = userResults;
		this.friendResults = friendResults;
		this.metaModelResults = metaModelResults;
		this.usersMetaModelsResult = usersMetaModelsResult;
		this.usersModelsResult = usersModelsResult;
		this.modelResults = modelResults;
		this.experimentResults = experimentResults;
		
	}

	/**
	 * The result set with all users (exclusive friends), which are matching the search criteria
	 * @return
	 */
	public Set<UserResult> getUserResults() {
		return userResults;
	}

	/**
	 * The result of your friends, which are matching the search criteria
	 * @return
	 */
	public Set<Friend> getFriendResults() {
		return friendResults;
	}


	/**
	 * The result set of all public meta models (exclusive the user owned/collaborated ones), which are matching the search criteria
	 * @return
	 */
	public Set<CollaborationResult> getMetaModelResults() {
		return metaModelResults;
	}

/**
 * The result set of all public meta models (exclusive the user owned/collaborated ones), which are matching the search criteria
 * @return
 */
	public Set<CollaborationResult> getModelResults() {
		return modelResults;
	}


	/**
	 * The result of all experiments that are owned or co-administrated by the user
	 * @return
	 */
	public Set<ExperimentResult> getExperimentResults() {
		return experimentResults;
	}


	/**
	 * The result set of the users owned or in collaboration MetaModels, which are matching the search criteria
	 * @return
	 */
	public Set<CollaborationResult> getUsersMetaModelsResults() {
		return usersMetaModelsResult;
	}

	/**
	 * The result set of the users owned or in collaboration MetaModels, which are matching the search criteria
	 * @return
	 */
	public Set<CollaborationResult> getUsersModelResults() {
		return usersModelsResult;
	}

}
