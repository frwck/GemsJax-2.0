package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;

import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.admin.tabs.TwoColumnLayoutTab;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.admin.widgets.BigMenuButton;
import org.gemsjax.client.admin.widgets.VerticalBigMenuButtonBar;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.editor.MetaModelCanvas;
import org.gemsjax.client.editor.MetaModelCanvas.EditingMode;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.HasClickHandlers;

/**>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248
 * This is the implementation for the Meta-Model editing Tab.
 * <b>To add this Tab to the {@link TabEnviroment} call TabEnviroment.getInstance().addTab(); 
 * outside of the constructor, for example in the corresponding presenter</b>
 * 
 * @author Hannes Dorfmann
 *
 */
public class MetaModelViewImpl extends TwoColumnLayoutTab implements MetaModelView{

	private TipNotification tipNotification;
	private BigMenuButton mouseButton, newClassButton, newRelationButton, newInheritanceButton;
	private MetaModelCanvas canvas;
	
	private UserLanguage language;
	
	public MetaModelViewImpl(String title, UserLanguage language) throws CanvasSupportException 
	{
		super(title, language);
		
		this.language = language;
		
		generateToolStrip(language);
		
		canvas = new MetaModelCanvas();
		this.setRightColumn(canvas, true);
		
		// TODO set it to the corresponding MetaModel settings (check for READ_ONLY)
		setCanvasEditingMode(EditingMode.NORMAL);
		
		canvas.initCanvasSize();
		canvas.redrawCanvas();
		
		this.getLayout().setOverflow(Overflow.HIDDEN);

		canvas.setOverflow(Overflow.SCROLL);

		tipNotification= new TipNotification("Welcome",language.MetaModelEditorWelcomeTip(), 200, 78 , 3000, NotificationPosition.BOTTOM_CENTERED); 
		tipNotification.animateShow(AnimationEffect.FADE);
	}
	
	private void generateToolStrip(UserLanguage language)
	{
		
		VerticalBigMenuButtonBar toolbar = new VerticalBigMenuButtonBar(120,10);
	
		//toolbar.setMargin(5);
		toolbar.setMembersMargin(10);
		
		mouseButton = new BigMenuButton(language.MetaModelToolbarUseMouse(),"/images/icons/mouse_black.png"); 
		newClassButton = new BigMenuButton(language.MetaModelToolbarNewMetaClass(),"/images/icons/class.png"); 
		newRelationButton = new BigMenuButton(language.MetaModelToolbarRelation(),"/images/icons/relation.png"); 
		newInheritanceButton = new BigMenuButton(language.MetaModelToolbarInheritance(),"/images/icons/inheritance.png"); 	
		
		
		mouseButton.setHeight(100);
		newClassButton.setHeight(100);
		newRelationButton.setHeight(100);
		newInheritanceButton.setHeight(100);
		
		
		
		toolbar.addMember(mouseButton);
		toolbar.addMember(newClassButton);
		toolbar.addMember(newRelationButton);
		toolbar.addMember(newInheritanceButton);
		
		
	    this.setLeftColumn(toolbar, true);
	    
	    
	}

	@Override
	public HasClickHandlers getNewMetaClassButton() {
		return newClassButton;
	}

	@Override
	public HasClickHandlers getUseMouseButton() {
		return mouseButton;
	}

	@Override
	public HasClickHandlers getNewRelationshipButton() {
		return newRelationButton;
	}

	@Override
	public HasClickHandlers getNewInheritanceButton() {
		return newInheritanceButton;
	}

	@Override
	public void setCanvasEditingMode(EditingMode mode) {
		
		 // Remove the previous displayed TipNotification
		 canvas.setEditingMode(mode); 
		 
		// if (tipNotification.isVisible())
		//	 tipNotification.hide();
		 
		switch (mode) {
			case NORMAL:  			mouseButton.setActive(true);break;
			case CREATE_CLASS:		newClassButton.setActive(true); 
				tipNotification = new TipNotification(language.MetaModelToolbarNewMetaClassTip(), null ,200,78, 3000, NotificationPosition.BOTTOM_CENTERED);
				tipNotification.animateShow(AnimationEffect.FADE);
			break;
			case CREATE_RELATION:	newRelationButton.setActive(true); break;
			case CREATE_INHERITANCE: newInheritanceButton.setActive(true);
			case READ_ONLY: break;// TODO: what to do when it has been set to READ_ONLY
			default: Window.alert("Error: the mode is set to "+mode); break;
		}
		
		
	}
	

}
