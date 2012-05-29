package org.gemsjax.client.admin.view.implementation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.tabs.TwoColumnLayout;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.admin.view.grids.MetaClassDetailView;
import org.gemsjax.client.admin.view.grids.MetaConnectionDetailView;
import org.gemsjax.client.admin.widgets.BigMenuButton;
import org.gemsjax.client.admin.widgets.VerticalBigMenuButtonBar;
import org.gemsjax.client.canvas.Anchor;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.canvas.CreateMetaInheritanceHandler;
import org.gemsjax.client.canvas.CreateMetaRelationHandler;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.MetaModelCanvas;
import org.gemsjax.client.canvas.MetaModelCanvas.EditingMode;
import org.gemsjax.client.canvas.handler.metamodel.CreateMetaClassHandler;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModelElement;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.events.CloseClickHandler;



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
	private BigMenuButton mouseButton, newClassButton, newRelationButton, newInheritanceButton, newContainmentButton, replayModeButton;
	private MetaModelCanvas canvas;
	
	private TwoColumnLayout layout;
	
	private UserLanguage language;
	private VLayout detailsView;
	
	private VLayout metaDetailPlaceHolder;
	
	private ListGrid collaboratorsList;
	
	private List<MetaBaseType> metaBaseTypes;
	
	private MetaClassDetailView metaClassDetailView;
	private MetaConnectionDetailView metaConnectionDetailView;
	
	private Set<MetaModelView.MetaAttributeManipulationListener> attributeManipulationListeners;
	private Set<MetaClassPropertiesListener> classPropertiesListeners;
	private Set<MetaConnectionPropertiesListener> connectionPropertiesListeners;
	
	private Button replayBackButton;
	private Button replayForwardButton;
	private Label replayInteractionDetails;
	private HLayout replayModeBar;
	
	
	public MetaModelViewImpl(String title, UserLanguage language, boolean enableReplayMode) throws CanvasSupportException 
	{
		super(title, language);
		attributeManipulationListeners = new LinkedHashSet<MetaModelView.MetaAttributeManipulationListener>();
		classPropertiesListeners = new LinkedHashSet<MetaModelView.MetaClassPropertiesListener>();
		connectionPropertiesListeners = new LinkedHashSet<MetaModelView.MetaConnectionPropertiesListener>();
		
		this.language = language;
		
		layout = new TwoColumnLayout();
		layout.setWidth100();
		layout.setHeight100();
		
		generateToolStrip(language, enableReplayMode);
		
		generateReplayModeStaff();
		
		metaDetailPlaceHolder = new VLayout();
		metaDetailPlaceHolder.setWidth100();
		metaDetailPlaceHolder.setHeight("*");
		
		
		detailsView = new VLayout();
		detailsView.setWidth(150);
		detailsView.setHeight100();
		
		detailsView.addMember(metaDetailPlaceHolder);
		
		canvas = new MetaModelCanvas();
		
		collaboratorsList = new ListGrid();
		collaboratorsList.setTitle("Collaborators");
		collaboratorsList.setCanEdit(false);
		collaboratorsList.setHeight(200);
		collaboratorsList.setWidth100();
		ListGridField collaboratorField = new ListGridField("displayedName", "Collaborators");
		collaboratorField.setWidth("100%");
		collaboratorsList.setFields(collaboratorField);
		collaboratorsList.setData(new CollaboratorListRecord[]{});
		
		detailsView.addMember(collaboratorsList);
		
		TwoColumnLayout canvasDetailContainer = new TwoColumnLayout();
		canvasDetailContainer.setWidth100();
		canvasDetailContainer.setHeight100();
		canvasDetailContainer.setLeftColumn(canvas, true);
		canvasDetailContainer.setRightColumn(detailsView, true);
		canvasDetailContainer.setMembersMargin(0);
		
		layout.setRightColumn(canvasDetailContainer, true);
		layout.setMembersMargin(0);
		
		
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
	
	private void generateToolStrip(UserLanguage language, boolean replayMode)
	{
		
		VerticalBigMenuButtonBar toolbar = new VerticalBigMenuButtonBar(100,10);
	
		//toolbar.setMargin(5);
		toolbar.setMembersMargin(10);
		
		mouseButton = new BigMenuButton(language.MetaModelToolbarUseMouse(),"/images/icons/mouse_black.png"); 
		newClassButton = new BigMenuButton(language.MetaModelToolbarNewMetaClass(),"/images/icons/class.png"); 
		newRelationButton = new BigMenuButton(language.MetaModelToolbarRelation(),"/images/icons/relation.png"); 
		newInheritanceButton = new BigMenuButton(language.MetaModelToolbarInheritance(),"/images/icons/inheritance.png"); 	
		newContainmentButton = new BigMenuButton(language.MetaModelToolbarContainment(),"/images/icons/containment.png"); 	
		replayModeButton =new BigMenuButton("Replay","/images/icons/replay.png");
		
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
		
		if (replayMode)
			toolbar.addMember(replayModeButton);
		
		
		
	    layout.setLeftColumn(toolbar, true);
	    
	    
	}

	
	private void generateReplayModeStaff(){
		int bottomPositionSpacer = 100;
		replayBackButton = new Button("<");
		replayBackButton.setWidth(15);
		replayBackButton.setHeight100();
		replayForwardButton = new Button(">");
		replayForwardButton.setWidth(15);
		replayForwardButton.setHeight100();
		replayInteractionDetails = new Label();
		replayInteractionDetails.setWidth("*");
		replayInteractionDetails.setHeight100();
		replayModeBar = new HLayout();
		replayModeBar.setWidth(Window.getClientWidth()/2);
		replayModeBar.setHeight(25);
		replayModeBar.setPageLeft((Window.getClientWidth()-replayModeBar.getWidth())/2);
		replayModeBar.setPageTop((Window.getClientHeight()-replayModeBar.getHeight()) -bottomPositionSpacer);
		replayModeBar.setStyleName("ReplayModeBar");
		replayModeBar.setMembersMargin(5);
		
		replayModeBar.addMember(replayBackButton);
		replayModeBar.addMember(replayForwardButton);
		replayModeBar.addMember(replayInteractionDetails);
		
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
		
		// Hide  ReplayModeBar if already displayed
		 if (canvas.getEditingMode() == EditingMode.REPLAY_MODE && mode != canvas.getEditingMode())
			 hideReplayModeBar();
		 
		 
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
			NotificationManager.getInstance().showTipNotification(new TipNotification("Select the source MetaClass", null , 2000, NotificationPosition.BOTTOM_CENTERED) );
			
			break;
			case CREATE_INHERITANCE: 
				newInheritanceButton.setActive(true);
				NotificationManager.getInstance().showTipNotification(new TipNotification("Select the class which should be extended", null, 2000, NotificationPosition.BOTTOM_CENTERED));
				break;						
			case READ_ONLY: break;// TODO: what to do when it has been set to READ_ONLY
			case REPLAY_MODE:
				replayModeButton.setActive(true);
				showReplayModeBar();
				break;
			default: Window.alert("Error: the mode is set to "+mode); break;
		}
		
		
	}
	
	
	
	private void showReplayModeBar(){
		replayModeBar.animateShow(AnimationEffect.WIPE);
	}
	
	public void hideReplayModeBar(){
		replayModeBar.animateHide(AnimationEffect.WIPE);
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

	@Override
	public void setMetaClassDetail(MetaClass metaClass) {
		clearDetailPlaceHolder();
		
		if (metaClassDetailView == null){
			metaClassDetailView = new MetaClassDetailView(metaBaseTypes);
			metaClassDetailView.addMetaAttributeManipulationListeners(attributeManipulationListeners);
			
			for (MetaClassPropertiesListener l : classPropertiesListeners )
				metaClassDetailView.addMetaClassPropertiesListener(l);
			
		}
		
		metaClassDetailView.setMetaClass(metaClass);
		metaDetailPlaceHolder.addMember(metaClassDetailView);
		
	}

	@Override
	public void setMetaConnectionDetail(MetaConnection metaConnection) {
		clearDetailPlaceHolder();
	
		if (metaConnectionDetailView == null){
			metaConnectionDetailView = new MetaConnectionDetailView(metaBaseTypes);
			metaConnectionDetailView.addMetaAttributeManipulationListeners(attributeManipulationListeners);
			
			for (MetaConnectionPropertiesListener l : connectionPropertiesListeners )
				metaConnectionDetailView.addMetaConnectionPropertiesListener(l);
			
		}
		
		metaConnectionDetailView.setMetaConnection(metaConnection);
		metaDetailPlaceHolder.addMember(metaConnectionDetailView);
		
	}
	
	private void clearDetailPlaceHolder(){
		metaDetailPlaceHolder.removeMembers(metaDetailPlaceHolder.getMembers());
	}
	

	@Override
	public void setMetaBaseTypes(List<MetaBaseType> types) {
		this.metaBaseTypes = types;
	}

	@Override
	public void addCreateMetaClassHandler(CreateMetaClassHandler h) {
		canvas.addCreateMetaClassHandler(h);
	}

	@Override
	public void removeCreateMetaClassHandler(CreateMetaClassHandler h) {
		canvas.removeCreateMetaClassHandler(h);
	}

	@Override
	public void showNameAlreadyInUseError(String name) {
			NotificationManager.getInstance().showTipNotification(new TipNotification("Name already in use", "The name \""+name+"\" is already user by a MetaModel-Element. Please choose a diffrent one", 2000, NotificationPosition.BOTTOM_CENTERED), AnimationEffect.FADE);
	}

	@Override
	public void addMetaAttributeManipulationListener(
			MetaAttributeManipulationListener l) {
		attributeManipulationListeners.add(l);
		
		if (metaClassDetailView!=null)
			metaClassDetailView.addMetaAttributeManipulationListener(l);
		
	}

	@Override
	public void removeMetaAttributeManipulationListener(
			MetaAttributeManipulationListener l) {
		
		attributeManipulationListeners.remove(l);
		
		if (metaClassDetailView!=null)
			metaClassDetailView.removeMetaAttributeManipulationListener(l);
	}

	@Override
	public void addMetaClassPropertiesListener(MetaClassPropertiesListener l) {
		classPropertiesListeners.add(l);
		if (metaClassDetailView!=null)
			metaClassDetailView.addMetaClassPropertiesListener(l);
		
	}

	@Override
	public void removeMetaClassPropertiesListener(MetaClassPropertiesListener l) {
		classPropertiesListeners.remove(l);
		if (metaClassDetailView!=null)
			metaClassDetailView.removeMetaClassPropertiesListener(l);
		
	}

	@Override
	public void showSendError(Exception e) {
		NotificationManager.getInstance().showTipNotification(new TipNotification("Connection error", "Your last interaction coud'nt be sent to the server. Please retry ", 2000, NotificationPosition.CENTER));
	}

	@Override
	public void addCloseClickHandler(CloseClickHandler h) {
		TabEnviroment.getInstance().addCloseClickHandler(h);
	}

	@Override
	public void removeCloseClickHandler(CloseClickHandler h) {
		// TODO how to implement/ which method to call?
		
	}

	@Override
	public void addCreateMetaRelationHandler(CreateMetaRelationHandler h) {
		canvas.addCreateMetaRelationHandler(h);
	}

	@Override
	public void removeCreateMetaRelationHandler(CreateMetaRelationHandler h) {
		canvas.removeCreateMetaRelationHandler(h);
	}
	
	
	@Override
	public void addMetaConnectionPropertiesListener(MetaConnectionPropertiesListener l){
		connectionPropertiesListeners.add(l);
	}
	
	@Override
	public void removeConnectionPropertiesListener(MetaConnectionPropertiesListener l){
		connectionPropertiesListeners.add(l);
	}
	
	
	public void clearDetailView(){
		metaDetailPlaceHolder.removeMembers(metaDetailPlaceHolder.getMembers());
	}

	@Override
	public void addCreateMetaInheritanceHandler(CreateMetaInheritanceHandler h) {
		canvas.addCreateMetaInheritanceHandler(h);	
	}

	@Override
	public void removeCreateMetaInheritanceHandler(
			CreateMetaInheritanceHandler h) {
		canvas.removeCreateMetaInheritanceHandler(h);
	}

	@Override
	public void showMetaInheritanceAlreadyExists(MetaClass clazz,
			MetaClass superClass) {
		
		NotificationManager.getInstance().showTipNotification(new TipNotification("Inheritance already exists", clazz.getName()+" already inherits from "+superClass.getName(), 2000, NotificationPosition.CENTER));
		
	}

	@Override
	public HasClickHandlers getReplayModeButton() {
		return replayModeButton;
	}

	@Override
	public Button getReplayModeBackButton() {
		return replayBackButton;
	}

	@Override
	public Button getReplayModeForwardButton() {
		return replayForwardButton;
	}

	@Override
	public void setReplayModeInteractionDetails(String details) {
		replayInteractionDetails.setContents(details);
		
	}
	
	@Override
	public EditingMode getCanvasEditingMode(){
		return canvas.getEditingMode();
	}
	
}
