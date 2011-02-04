package org.gemsjax.client.view;

import org.gemsjax.client.model.language.Language;

/**
 * Every view, which implement this can be change the Language dynamically 
 * @author Hannes Dorfmann
 *
 */
public interface LanguageChangeableView {

	/**
	 * Change the {@link Language} to the newLanguage
	 * @param newLanguage
	 */
	public void changeLanguage(Language newLanguage);
	
}
