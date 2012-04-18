package org.gemsjax.client.admin.view.implementation;

import java.util.LinkedHashMap;
import java.util.Set;

import net.sourceforge.htmlunit.corejs.javascript.Token.CommentType;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.view.CreateMetaModelView;
import org.gemsjax.client.admin.widgets.FriendChooserList;
import org.gemsjax.client.admin.widgets.ModalDialog;
import org.gemsjax.client.admin.widgets.OptionButton;
import org.gemsjax.client.admin.widgets.StepByStepWizard;
import org.gemsjax.client.admin.widgets.StepByStepWizard.WizardHandler;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Collaborateable.Permission;
import org.gemsjax.shared.communication.CommunicationConstants;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.friend.Friend;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;


public class CreateMetaModelViewImpl extends ModalDialog implements CreateMetaModelView{

	private StepByStepWizard wizard;
	private UserLanguage lang;
	
	private TextItem nameField;
	private TextAreaItem description;
//	private SelectItem permissionField;
	private FriendChooserList collaborateorChooserList;
	private FriendsModule friendsModule;
	
//	private LinkedHashMap<Integer, String> permissionValues;
	
	
	
	public CreateMetaModelViewImpl(UserLanguage lang, FriendsModule friendsModule){
		this.lang = lang;
		this.friendsModule = friendsModule;
		this.wizard = new StepByStepWizard();
		
		VStack content = new VStack();
		HLayout header = new HLayout();
		header.setMembersMargin(20);
		
		
		OptionButton close = new OptionButton(lang.CreateMetaModelCloseButton());
		close.setWidth(55);
		close.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				collaborateorChooserList.deregisterFriendsModule();
				animateHide(AnimationEffect.FADE);
				destroy();
			}
		});
		
		Title t = new Title(lang.CreateMetaModelTitle());
		t.setWidth("*");
		
		header.addMember(t);
		header.addMember(close);
		header.setWidth100();
		header.setHeight(30);
		
		content.addMember(header);
		content.addMember(wizard.getDisplayableContent());
		content.setWidth(500);
		content.setHeight(400);
		content.setMargin(5);
		Label nameSection = new Label("Step 1: Name & Desciption");
		Label collaboratorSection = new Label("Step 2: Collaborators");
		
		nameSection.setWidth(200);
		nameSection.setHeight(20);
		collaboratorSection.setWidth(200);
		collaboratorSection.setHeight(20);
		wizard.setMarginBetweenSectionAndContent(30);
		wizard.setStepSectionMargin(5);
		wizard.addStepSection(nameSection, createFirstStep());
		wizard.addStepSection(collaboratorSection, createSecondStep());
//		wizard.setHeight(400);
		wizard.setWidth(510);
		wizard.startFromBegin();
		
		
		this.addItem(content);
		this.setWidth(510);
		this.setHeight(450);
		this.centerInPage();
	}
	
	
	
	private Canvas createFirstStep(){
		DynamicForm form = new DynamicForm();
		nameField = new TextItem();  
		nameField.setName("itemName");  
		nameField.setTitle(lang.CreateMetaModelName());  
        nameField.setWidth("100%");
        
        /*
        permissionField = new SelectItem();
        permissionField.setName("permission");
        permissionValues = new LinkedHashMap<Integer, String>();
        permissionValues.put(CommunicationConstants.CollaborateablePermission.PRIVATE, lang.CollaborateablePermissionPrivate() );
        permissionValues.put(CommunicationConstants.CollaborateablePermission.READ_ONLY, lang.CollaborateablePermissionReadOnly());
        permissionValues.put(CommunicationConstants.CollaborateablePermission.COPYABLE, lang.CollaborateablePermissionCopyable());
        
        permissionField.setValueMap(permissionValues);
        permissionField.setDefaultValue(lang.CollaborateablePermissionPrivate());
        
        permissionField.setTooltip(lang.CollaborateablePermissionHint());
        
        
        permissionField.setTitle(lang.CreateMetaModelPermission());
        permissionField.setWidth("100%");
        
        */
		
        description = new TextAreaItem();  
        description.setName("description");  
        description.setTitle(lang.CreateMetaModelDescription()); 
        description.setWidth("100%");
        
        
        
        form.setItems(nameField,  description);
		form.setWidth100();
		
		VStack stack = new VStack();
		stack.setWidth100();
		stack.addMember(new Label(lang.CreateMetaModelNameDescriptionIntro()));
		stack.addMember(form);
		
		OptionButton next = new OptionButton(lang.CreateMetaModelNext());
		next.setWidth(60);
		next.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (FieldVerifier.isEmpty(getName()) || !FieldVerifier.isValidCollaborateableName(getName()))
					SC.say(lang.CreateMetaModelErrorName());
				else
					wizard.next();
			}
		});
		next.setAlign(Alignment.RIGHT);
		stack.addMember(next);
		
		return stack;
	}
	
	
	
	private Canvas createSecondStep(){
		VStack stack = new VStack();
		stack.setWidth100();
		collaborateorChooserList = new FriendChooserList(lang, friendsModule);
		stack.addMember(new Label(lang.CreateMetaModelCollaboratorIntro()));
		stack.addMember(collaborateorChooserList);
		
		OptionButton next = new OptionButton(lang.CreateMetaModelFinish());
		next.setWidth(60);
		next.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wizard.next();
			}
		});
		
		next.setAlign(Alignment.RIGHT);
		stack.addMember(next);
		return stack;
	}
	
	
	
	
	@Override
	public String getName() {
		return nameField.getValueAsString();
	}

	@Override
	public Set<Friend> getCollaborators() {
		return collaborateorChooserList.getSelectedFriends();
	}


	@Override
	public String getDescription() {
		return description.getValueAsString();
	}



	@Override
	public void addWizardHandler(WizardHandler h) {
		wizard.addWizardHandler(h);
	}



	@Override
	public void removeWizardHandler(WizardHandler h) {
		wizard.removeWizardHandler(h);
	}



	@Override
	public void onSuccessfulCreated() {
		SC.say(lang.CreateMetaModelSuccessful());
		this.hide();
	}
	
	public void onErrorOccurred(CollaborateableFileError error){
		//TODO show a more specific error message
		SC.say(lang.CreateMetaModelCreationError());
	}



	@Override
	public Permission getPermission() {
		return Permission.PRIVATE;
	}

}
