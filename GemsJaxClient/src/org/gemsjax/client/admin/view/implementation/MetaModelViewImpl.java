package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.tabs.TwoColumnLayout;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.admin.widgets.BigMenuButton;
import org.gemsjax.client.admin.widgets.VerticalBigMenuButtonBar;
import org.gemsjax.client.canvas.Anchor;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.MetaModelCanvas;
import org.gemsjax.client.canvas.MetaModelCanvas.EditingMode;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.metamodel.MetaModelElement;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;



class CollaboratorListRecord extends ListGridRecord{
	
	private Collaborator collaborator;
	
	public CollaboratorListRecord(Collaborator collaborator){
		this.collaborator = collaborator;
		
		setAttribute("displayedName", collaborator.getDisplayedName());
		setAttribute("id", collaborator.getUserId());
	}
	
	
	public Collaborator getCollaborator(){
		return collaborator;
	}
	
	
}



/**
 * This is the implementation for the Meta-Model editing Tab.
 * <b>To add this Tab to the {@link TabEnviroment} call TabEnviroment.getInstance().addTab(); 
 * outside of the constructor, for example in the corresponding presenter</b>
 * 
 * @author Hannes Dorfmann
 *
 */
public class MetaModelViewImpl extends LoadingTab implements MetaModelView{

	private TipNotification tipNotification;
	private BigMenuButton mouseButton, newClassButton, newRelationButton, newInheritanceButton, newContainmentButton;
	private MetaModelCanvas canvas;
	
	private TwoColumnLayout layout;
	
	private UserLanguage language;
	private HLayout detailsView;
	
	final ListGrid collaboratorsList;
	
	
	public MetaModelViewImpl(String title, UserLanguage language) throws CanvasSupportException 
	{
		super(title, language);
		
		this.language = language;
		
		layout = new TwoColumnLayout();
		layout.setWidth100();
		layout.setHeight100();
		
		generateToolStrip(language);
		
		detailsView = new HLayout();
		detailsView.setWidth(150);
		detailsView.setHeight100();
		
		canvas = new MetaModelCanvas();
		
		collaboratorsList = new ListGrid();
		collaboratorsList.setTitle("Collaborators");
		collaboratorsList.setCanEdit(false);
		collaboratorsList.setHeight(200);
		collaboratorsList.setWidth100();
		ListGridField collaboratorField = new ListGridField("displayedName", "Collaborators");
		collaboratorsList.setFields(collaboratorField);
		collaboratorsList.setData(new CollaboratorListRecord[]{});
		detailsView.addMember(collaboratorsList);
		
		TwoColumnLayout canvasDetailContainer = new TwoColumnLayout();
		canvasDetailContainer.setWidth100();
		canvasDetailContainer.setHeight100();
		canvasDetailContainer.setLeftColumn(canvas, true);
		canvasDetailContainer.setRightColumn(detailsView, true);
		
		layout.setRightColumn(canvasDetailContainer, true);
		
		// TODO set it to the corresponding MetaModel settings (check for READ_ONLY)
		setCanvasEditingMode(EditingMode.NORMAL);
		

		canvas.setWidth("*");
		canvas.initCanvasSize();
		canvas.redrawCanvas();
		
		//this.getLayout().setOverflow(Overflow.HIDDEN);

		//canvas.setOverflow(Overflow.SCROLL);
		//layout.setOverflow(Overflow.HIDDEN);
		this.setContent(layout);
		this.showLoading();
		this.setCanClose(true);

	}
	
	private void generateToolStrip(UserLanguage language)
	{
		
		VerticalBigMenuButtonBar toolbar = new VerticalBigMenuButtonBar(100,10);
	
		//toolbar.setMargin(5);
		toolbar.setMembersMargin(10);
		
		mouseButton = new BigMenuButton(language.MetaModelToolbarUseMouse(),"/images/icons/mouse_black.png"); 
		newClassButton = new BigMenuButton(language.MetaModelToolbarNewMetaClass(),"/images/icons/class.png"); 
		newRelationButton = new BigMenuButton(language.MetaModelToolbarRelation(),"/images/icons/relation.png"); 
		newInheritanceButton = new BigMenuButton(language.MetaModelToolbarInheritance(),"/images/icons/inheritance.png"); 	
		newContainmentButton = new BigMenuButton(language.MetaModelToolbarContainment(),"/images/icons/containment.png"); 	
		
		
		mouseButton.setHeight(90);
		newClassButton.setHeight(90);
		newRelationButton.setHeight(90);
		newInheritanceButton.setHeight(90);
		newContainmentButton.setHeight(90);
		
		
		
		toolbar.addMember(mouseButton);
		toolbar.addMember(newClassButton);
		toolbar.addMember(newRelationButton);
		toolbar.addMember(newInheritanceButton);
		toolbar.addMember(newContainmentButton);
		
		
	    layout.setLeftColumn(toolbar, true);
	    
	    
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
			case NORMAL:  			
				mouseButton.setActive(true);break;
				
			case CREATE_CLASS:		
				newClassButton.setActive(true); 
				NotificationManager.getInstance().showTipNotification(new TipNotification(language.MetaModelToolbarNewMetaClassTip(), null , 3000, NotificationPosition.BOTTOM_CENTERED), AnimationEffect.FADE); 
				
			break;
			case CREATE_RELATION:	newRelationButton.setActive(true); 
			tipNotification = new TipNotification("This is a long Text example", "This is a example for very very long text, so bla bla bla bla normaly something usefull should be stay here and not this stupid bla bla bla bla bla text. There can be also a LOT OF UPER CASE CHARECTERS AND LOWER CASE CHARACTERS! This is a example for very very long text, so bla bla bla bla normaly something usefull should be stay here and not this stupid bla bla bla bla bla text. There can be also a LOT OF UPER CASE CHARECTERS AND LOWER CASE CHARACTERS! This is a example for very very long text, so bla bla bla bla normaly something usefull should be stay here and not this stupid bla bla bla bla bla text. There can be also a LOT OF UPER CASE CHARECTERS AND LOWER CASE CHARACTERS!" , 3000, NotificationPosition.BOTTOM_CENTERED); //language.MetaModelToolbarNewMetaClassTip()
			tipNotification.animateShow(AnimationEffect.FADE);
			break;
			case CREATE_INHERITANCE: newInheritanceButton.setActive(true);
			case READ_ONLY: break;// TODO: what to do when it has been set to READ_ONLY
			default: Window.alert("Error: the mode is set to "+mode); break;
		}
		
		
	}

	@Override
	public void addDrawable(Drawable drawable) throws DoubleLimitException {
		canvas.addDrawable(drawable);
		
	}

	@Override
	public void removeDrawable(Drawable drawable) {
		canvas.removeDrawable(drawable);
	}

	@Override
	public Drawable getDrawableOf(MetaModelElement element) {
		return canvas.getDrawableOf(element);
	}

	@Override
	public void redrawMetaModelCanvas() {
		canvas.redrawCanvas();
	}

	@Override
	public void showAnchorPlaceNotAllowed(Anchor a) {

		NotificationManager.getInstance().showTipNotification(new TipNotification(language.MetaModelAnchorPlaceNotAllowedTitle(), null , 2000, NotificationPosition.BOTTOM_CENTERED), AnimationEffect.FADE); 
		
	}

	@Override
	public void clearDrawables() {
		canvas.clearDrawables();
	}

	@Override
	public HasClickHandlers getNewContainmentButton() {
		return newContainmentButton;
	}

	@Override
	public void addCollaborator(Collaborator c) {
		for (ListGridRecord r : collaboratorsList.getRecords()){
			CollaboratorListRecord cr = (CollaboratorListRecord)r;
			if (cr.getCollaborator().getUserId()==c.getUserId())
				return;
					
		}
		
		collaboratorsList.addData(new CollaboratorListRecord(c));
		collaboratorsList.redraw();
	}

	@Override
	public void removeCollaborator(Collaborator c) {
		ListGridRecord found = null;
		for (ListGridRecord r : collaboratorsList.getRecords()){
			CollaboratorListRecord cr = (CollaboratorListRecord)r;
			if (cr.getCollaborator().getUserId()==c.getUserId()){
				found = r;
				break;
			}
					
		}
		if (found!=null)
			collaboratorsList.removeData(found);
		
		collaboratorsList.redraw();
	}
	
	
}
