package org.gemsjax.client.admin.view.implementation;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.admin.view.CreateExperimentView;
import org.gemsjax.client.admin.widgets.FriendChooserList;
import org.gemsjax.client.admin.widgets.OptionButton;
import org.gemsjax.client.admin.widgets.StepByStepWizard;
import org.gemsjax.client.admin.widgets.StepByStepWizard.WizardHandler;
import org.gemsjax.client.admin.widgets.StyledModalDialog;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.communication.message.experiment.ExperimentGroupDTO;
import org.gemsjax.shared.communication.message.friend.Friend;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyUpEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyUpHandler;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;


class EMailRecord extends ListGridRecord{
	
	private String email;
	
	public EMailRecord(String email){
		setAttribute("email", email);
		this.email = email;
	}
	
	
	public String getEmail(){
		return getAttribute("email");
	}
	
	
}



class ExperimentGroupPanel extends VStack{
	
	private TextItem name;
	private TextItem startDate;
	private TextItem endDate;
	
	private TextItem addEmailAddress;
	private ListGrid emailAdresses;
	private OptionButton removeButton;
	private static int backgroundCounter =0;
	
	
	public ExperimentGroupPanel(final VStack parent){
		
		backgroundCounter ++;
		setBackgroundColor(backgroundCounter%2==0?"E0E0E0":"C2C2C2");
		
		
//		 RegExpValidator emailValidator = new RegExpValidator();  
//	     emailValidator.setErrorMessage("Invalid email address"); 
//	     emailValidator.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");  
//	        
//		
//		RegExpValidator dateValidator = new RegExpValidator();
//		dateValidator.setErrorMessage("Invalid date format: yyyy.mm.dd hh:mm");  
//		dateValidator.setExpression("^[2-9][0-9][0-9][0-9]\\.[0-9][0-9]\\.[0-9][0-9] [0-9][0-9]:[0-9[0-9]$");  
	    
		name = new TextItem("name","Groupname");
		startDate = new TextItem("startDate", "Start date");
		
		
		endDate = new TextItem("endDate", "End date");
		
		
		
		addEmailAddress = new TextItem("addEmailAdress","Add E-Mailaddress");
		addEmailAddress.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getKeyName().equals("Enter"))
					if (addEmail())
						addEmailAddress.clearValue();
			}
		});
		
		
		
		
		DynamicForm form = new DynamicForm();
		form.setFields(name, startDate, endDate, addEmailAddress);
		form.setTitleOrientation(TitleOrientation.TOP);
		form.setWidth100();
		
		
		
		emailAdresses = new ListGrid();
		ListGridField emailField = new ListGridField("email", "E-Mail");
		emailAdresses.setFields(emailField);
		emailAdresses.setCanRemoveRecords(true);
		
		
		
		HLayout bottomLayout = new HLayout();
		bottomLayout.setWidth100();
		bottomLayout.setHeight(25);
		Label bottomPlaceHolder = new Label();
		bottomPlaceHolder.setWidth("*");
		bottomPlaceHolder.setHeight100();
		
		removeButton = new OptionButton("remove group", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				parent.removeMember(ExperimentGroupPanel.this);
			}
			
		});
		
		removeButton.setWidth(150);
		removeButton.setHeight(30);
		

		bottomLayout.addMember(bottomPlaceHolder);
		bottomLayout.addMember(removeButton);
		
		
		this.addMember(form);
		this.addMember(emailAdresses);
		this.addMember(bottomLayout);
		
		this.setWidth100();
		this.setHeight100();
		
	}
	
	
	private boolean addEmail(){
		
		if (FieldVerifier.isValidEmail(addEmailAddress.getValueAsString()))
		{
			String email = addEmailAddress.getValueAsString();
			
			for (ListGridRecord r : emailAdresses.getRecords())
			{
				if (r.getAttribute("email").equals(email)){
					NotificationManager.getInstance().showTipNotification(new TipNotification("E-Mail already in list", null, 2000, NotificationPosition.CENTER));
					return false;
				}
			}
			
			
			RecordList l = emailAdresses.getDataAsRecordList();
			l.add(new EMailRecord(email));
			return true;
		}
		else{
			NotificationManager.getInstance().showTipNotification(new TipNotification("Invalid E-Mail", null, 2000, NotificationPosition.CENTER));
			return false;
		}
			
		
	}
	
	
	
	public boolean isValid(){
		
		if (FieldVerifier.isEmpty(getName()))
		{
			NotificationManager.getInstance().showTipNotification(new TipNotification("The name of a group is empty", null, 2000, NotificationPosition.CENTER));
			return false;
		}
		
		if (getStartDate()==null)
		{
			NotificationManager.getInstance().showTipNotification(new TipNotification("Invalid start date", "The start date of group \""+getName()+"\" is not valid. Format yyyy.mm.dd hh:mm", 2000, NotificationPosition.CENTER));
			return false;
		}
		
		if (getEndDate()==null)
		{
			NotificationManager.getInstance().showTipNotification(new TipNotification("Invalid end date", "The end date of group \""+getName()+"\" is not valid. Format yyyy.mm.dd hh:mm", 2000, NotificationPosition.CENTER));
			return false;
		}
		
		Date start = getStartDate();
		Date end = getEndDate();
		
		if (end.compareTo(start)<=0)
		{
			NotificationManager.getInstance().showTipNotification(new TipNotification("End date of group \""+getName()+"\" is before start date", null, 2000, NotificationPosition.CENTER));
			return false;
		}
		
		if (getEmailAdresses().isEmpty())
		{
			NotificationManager.getInstance().showTipNotification(new TipNotification("No E-Mail to invite", "Group \""+getName()+"\" has no e-mail address to invite. At least 1 is needed.", 2000, NotificationPosition.CENTER));
			return false;
		}
		
		
		return true;
	}
	
	
	public String getName(){
		return name.getValueAsString();
	}
	
	
	public Date getStartDate(){
		
		DateTimeFormat format = DateTimeFormat.getFormat("yyyy.MM.dd HH:mm");
		try{
			String input = startDate.getValueAsString();
			if (FieldVerifier.isEmpty(input))
				return null;
			
			Date d = format.parse(input);
			return d;
		}
		catch(IllegalArgumentException e)
		{
			return null;
		}

	}
	
	public Date getEndDate(){
		
		DateTimeFormat format = DateTimeFormat.getFormat("yyyy.MM.dd HH:mm");
		try{
			String input = endDate.getValueAsString();
			if (FieldVerifier.isEmpty(input))
				return null;
			
			Date d = format.parse(input);
			return d;
		}
		catch(IllegalArgumentException e)
		{
			return null;
		}

	}
	
	public Set<String> getEmailAdresses(){
		
		Set<String> mails = new LinkedHashSet<String>();
		
		
		for (ListGridRecord r : emailAdresses.getRecords())
		{
			mails.add(r.getAttribute("email"));
		}
		
		
		return mails;
		
	}
	
}





/**
 * This is the implementeation for the {@link CreateExperimentView}
 * @author Hannes Dorfmann
 *
 */
public class CreateExperimentViewImpl extends StyledModalDialog implements CreateExperimentView{

	private StepByStepWizard wizard;
	
	private TextItem name;
	private RichTextEditor description;
	private FriendChooserList adminsList;
	private VStack groupsContainer;
	
	
	public CreateExperimentViewImpl(UserLanguage language, FriendsModule friendModule)
	{
		super("Create Experiment");
		
		adminsList = new FriendChooserList(language, friendModule);
		
		wizard = new StepByStepWizard();
		
		
		buildWizzard();
		
		wizard.startFromBegin();
		
		
		
		VStack contentStack = new VStack();
		contentStack.setWidth100();
		contentStack.setHeight100();
		contentStack.addMember(wizard.getDisplayableContent());
		contentStack.setPadding(2);
		
		this.setShowCancelButton(false);
		this.setShowOkButton(false);
		this.setFooterVisible(false);
		this.setContent(contentStack);
		this.setWidth(600);
		this.setHeight(480);
		this.centerInPage();
		
	}
	
	
	private void buildWizzard()
	{
		generateNameAndAdminsStep();
		generateDescription();
		generateGroups();
	}
	
	
	private void generateDescription(){
		VLayout mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		
		HLayout bottomLayout = new HLayout();
		bottomLayout.setWidth100();
		bottomLayout.setHeight(25);
		Label bottomPlaceHolder = new Label();
		bottomPlaceHolder.setWidth("*");
		bottomPlaceHolder.setHeight100();
		
		OptionButton nextButton = new OptionButton("next", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (FieldVerifier.isNotEmpty(description.getValue())){
					wizard.next();
				}
				else
				{
					NotificationManager.getInstance().showTipNotification(new TipNotification("Insert a problem description", null, 2000, NotificationPosition.CENTER));
				}
			
			}
		});
		
		nextButton.setWidth(80);
		nextButton.setHeight(30);
		
		OptionButton backButton = new OptionButton("back", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wizard.previous();
			}
		});

		
		backButton.setWidth(80);
		backButton.setHeight(30);
		
		bottomLayout.addMember(backButton);
		bottomLayout.addMember(bottomPlaceHolder);
		bottomLayout.addMember(nextButton);
		
		description = new RichTextEditor();
		description.setWidth100();
		description.setHeight(300);
		description.setOverflow(Overflow.SCROLL);
		
		
		mainLayout.addMember(description);
		mainLayout.addMember(bottomLayout);
		
		wizard.addStepSection(new Label("2. Description"), mainLayout);
	}
	
	private void generateNameAndAdminsStep(){
		
		VStack stack = new VStack();
		stack.setWidth100();
		stack.setHeight100();
		
//		Label placeHolder = new Label();
//		placeHolder.setWidth100();
//		placeHolder.setHeight("*");
//		
		DynamicForm nameForm = new DynamicForm();
		nameForm.setWidth100();
		name = new TextItem("Name","Name");
		name.setWidth("100%");
		name.setHeight(23);
		nameForm.setHeight(25);
		nameForm.setFields(name);
		
		
		
		HLayout bottomLayout = new HLayout();
		bottomLayout.setWidth100();
		bottomLayout.setHeight(25);
		Label bottomPlaceHolder = new Label();
		bottomPlaceHolder.setWidth("*");
		bottomPlaceHolder.setHeight100();
		
		OptionButton nextButton = new OptionButton("next", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (FieldVerifier.isNotEmpty(name.getValueAsString())){
					wizard.next();
				}
				else
				{
					NotificationManager.getInstance().showTipNotification(new TipNotification("Insert a name", null, 2000, NotificationPosition.CENTER));
				}
			}
		});
		
		nextButton.setWidth(80);
		nextButton.setHeight(30);
		
		bottomLayout.addMember(bottomPlaceHolder);
		bottomLayout.addMember(nextButton);
		
		adminsList.setHeight(260);
		stack.addMember(nameForm);
		stack.addMember(adminsList);
//		stack.addMember(placeHolder);
		stack.addMember(bottomLayout);
		
		wizard.addStepSection(new Label("1. Name & Admins"), stack);
	}
	
	public void generateGroups(){
		
		VStack main = new VStack();
		main.setWidth100();
		
		groupsContainer = new VStack();
		groupsContainer.setWidth100();
		groupsContainer.setHeight(300);
		groupsContainer.setMembersMargin(10);
		groupsContainer.setOverflow(Overflow.SCROLL);
		
		// ADD a first group
		addGroup();
		
		OptionButton addGroup = new OptionButton("New Group", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addGroup();
				groupsContainer.scrollToBottom();
			}
		});
		
		addGroup.setWidth(150);
		addGroup.setHeight(25);
		
		
		HLayout bottomLayout = new HLayout();
		bottomLayout.setWidth100();
		bottomLayout.setHeight(25);
		Label bottomPlaceHolder = new Label();
		bottomPlaceHolder.setWidth("*");
		bottomPlaceHolder.setHeight100();
		
		OptionButton nextButton = new OptionButton("finish", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (validateGroups()){
					wizard.next();
				}
			
			}
		});
		
		nextButton.setWidth(80);
		nextButton.setHeight(30);
		
		OptionButton backButton = new OptionButton("back", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wizard.previous();
			}
		});

		
		backButton.setWidth(80);
		backButton.setHeight(30);
		
		bottomLayout.addMember(backButton);
		bottomLayout.addMember(bottomPlaceHolder);
		bottomLayout.addMember(nextButton);
		
		main.addMember(addGroup);
		main.addMember(groupsContainer);
		main.addMember(bottomLayout);
		
		wizard.addStepSection(new Label("3. Groups"), main);
	
	}


	private void addGroup(){
		
		ExperimentGroupPanel panel = new ExperimentGroupPanel(groupsContainer);
		panel.setWidth100();
		
		groupsContainer.addMember(panel);
		
	}
	
	
	@Override
	public String getExperimentName() {
		return name.getValueAsString();
	}


	@Override
	public void showIt() {
		this.animateShow(AnimationEffect.FADE);
	}


	@Override
	public void closeIt() {
		this.animateHide(AnimationEffect.FADE);
	}


	
	private boolean validateGroups(){
		
		int groupCount = 0;
		
		for (Canvas c : groupsContainer.getMembers())
		{
			if (c instanceof ExperimentGroupPanel){
				groupCount ++;
				ExperimentGroupPanel g = (ExperimentGroupPanel)c;
				if (!g.isValid())
					return false;
			}
		}
		
		
		if (groupCount == 0 ){
			NotificationManager.getInstance().showTipNotification(new TipNotification("At least one group is needed", null, 2000, NotificationPosition.CENTER));
			return false;
		}
			
		
		return true;
		
	}


	@Override
	public String getDescription() {
		return description.getValue();
	}


	@Override
	public Set<Friend> getSelectedFriends() {
		return adminsList.getSelectedFriends();
	}


	@Override
	public Set<ExperimentGroupDTO> getExperimentGroups() {
		
		Set<ExperimentGroupDTO> groups = new LinkedHashSet<ExperimentGroupDTO>();
		
		for (Canvas c: groupsContainer.getMembers())
		{
			if ( c instanceof ExperimentGroupPanel)
			{
				ExperimentGroupPanel p  = (ExperimentGroupPanel)c;
				ExperimentGroupDTO g = new ExperimentGroupDTO();
				g.setName(p.getName());
				g.setStartDate(p.getStartDate());
				g.setEndDate(p.getEndDate());
				g.setEmailToCreateInvitation(p.getEmailAdresses());
				groups.add(g);
			}
		}
		
		
		return groups;
	}
	
	
	public void addWizardHandler(WizardHandler h){
		wizard.addWizardHandler(h);
	}
	
	
	public void removeWizardHandler(WizardHandler h){
		wizard.removeWizardHandler(h);
	}
	
	@Override
	public void showException(Exception e){
		NotificationManager.getInstance().showTipNotification(new TipNotification("Unexpected error", "An unexpected error has occurred. Please retry.", 2000, NotificationPosition.CENTER));
		
	}


	@Override
	public void showSuccessful() {
		NotificationManager.getInstance().showTipNotification(new TipNotification("Successful", "Experiment has been created successful", 2000, NotificationPosition.CENTER));
		
	}
	

}
