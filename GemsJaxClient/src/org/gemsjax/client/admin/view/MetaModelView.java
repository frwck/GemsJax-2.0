package org.gemsjax.client.admin.view;


import java.util.List;

import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.notification.Notification;
import org.gemsjax.client.canvas.Anchor;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.MetaModelCanvas;
import org.gemsjax.client.canvas.MetaModelCanvas.EditingMode;
import org.gemsjax.client.canvas.handler.metamodel.CreateMetaClassHandler;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModelElement;

import com.smartgwt.client.widgets.events.HasClickHandlers;


public interface MetaModelView extends CollaborateableView{
	
	
	public interface MetaAttributeManipulationListener{
		
		public void onMetaAttributeManipulated(MetaAttributeManipulationEvent event);
		
		public class MetaAttributeManipulationEvent{
			public enum ManipulationType{
				NEW,
				MODIFY,
				DELETE
			}
			
			private ManipulationType type;
			private MetaAttribute attribute;
			private String name;
			private MetaBaseType baseType;
			private MetaClass metaClass;
			
			public MetaAttributeManipulationEvent(ManipulationType type, MetaClass metaClass){
				this.type = type;
				this.metaClass = metaClass;
			}
			
			public MetaClass getMetaClass(){
				return metaClass;
			}

			public MetaAttribute getMetaAttribute() {
				return attribute;
			}

			public void setAttribute(MetaAttribute attribute) {
				this.attribute = attribute;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public MetaBaseType getBaseType() {
				return baseType;
			}

			public void setBaseType(MetaBaseType baseType) {
				this.baseType = baseType;
			}

			public ManipulationType getType() {
				return type;
			}
			
		}
		
		
		
	}
	
	
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
	
	
	public HasClickHandlers getNewContainmentButton();
	
	
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
	
	/**
	 * Get the corresponding {@link Drawable} to the {@link MetaModelElement} element. 
	 * @param element
	 * @return The corresponding {@link Drawable} to the element o or null, if no {@link Drawable}, which displays the element, is currently on the canvas.
	 */
	public Drawable getDrawableOf(MetaModelElement element);
	
	/**
	 * Redraw the canvas
	 */
	public void redrawMetaModelCanvas();
	
	/**
	 * The View should display something (for example a  {@link Notification}) to inform the user that the {@link Anchor} can not be placed at the desired position
	 * @param a
	 */
	public void showAnchorPlaceNotAllowed(Anchor a);
	
	
	public void showLoading();
	
	public void showContent();

	/**
	 * Remove all Drawables from the Canvas
	 */
	public void clearDrawables();
	
	
	public void addCollaborator(Collaborator c);
	
	public void removeCollaborator(Collaborator c);
	
	
	public void setMetaClassDetail(MetaClass metaClass);
	
	public void setMetaConnectionDetail(MetaConnection metaConnection);
	
	public void setMetaBaseTypes(List<MetaBaseType> types);
	
	
	public void showNameAlreadyInUseError(String name);	
	
	public void addCreateMetaClassHandler(CreateMetaClassHandler h);
	public void removeCreateMetaClassHandler(CreateMetaClassHandler h);
	
	
	public void addMetaAttributeManipulationListener(MetaAttributeManipulationListener l);
	public void removeMetaAttributeManipulationListener(MetaAttributeManipulationListener l);
	
}
