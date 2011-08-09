package org.gemsjax.client.admin.view;


import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.MetaModelCanvas;
import org.gemsjax.client.canvas.MetaModelCanvas.EditingMode;

import com.smartgwt.client.widgets.events.HasClickHandlers;


public interface MetaModelView {
	
	/**
	 * Get the toolbar button for creating a new meta-class
	 * @return
	 */
	public HasClickHandlers getNewMetaClassButton();
	/**
	 * Get the "use mouse" toolbar button
	 * @return
	 */
	public HasClickHandlers getUseMouseButton();
	
	/**
	 * Get the toolbar button, for creating a new relationship bewteen two meta-classes
	 * @return
	 */
	public HasClickHandlers getNewRelationshipButton();
	
	/**
	 * Get the toolbar button, for creating a new inheritance between two meta-classes
	 * @return
	 */
	public HasClickHandlers getNewInheritanceButton();
	
	
	/**
	 * Set the {@link MetaModelCanvas} to the {@link EditingMode}. 
	 * This is used to say, how the {@link MetaModelCanvas} should behaver on user interactions like click, mouse move, etc.
	 * @param mode
	 * @see EditingMode
	 */
	public void setCanvasEditingMode(MetaModelCanvas.EditingMode mode);
	
	
	/**
	 * Remove the Drawable that displayes a MetaClass, Connection etc.
	 * @param drawable
	 * @throws DoubleLimitException 
	 */
	public void addDrawable(Drawable drawable) throws DoubleLimitException;
	
	/**
	 * Remove the Drawable that displayes a MetaClass, Connection etc.
	 * @param drawable
	 */
	public void removeDrawable(Drawable drawable);
	

}
