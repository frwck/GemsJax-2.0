package org.gemsjax.client.admin.view;

<<<<<<< HEAD
public interface MetaModelView {

=======
import org.gemsjax.client.editor.MetaModelCanvas;
import org.gemsjax.client.editor.MetaModelCanvas.EditingMode;

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
	
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248
}
