package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.view.CreateExperimentView;
import org.gemsjax.client.admin.widgets.FriendChooserList;
import org.gemsjax.client.admin.widgets.OptionButton;
import org.gemsjax.client.admin.widgets.StepByStepWizard;
import org.gemsjax.client.admin.widgets.StyledModalDialog;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.client.module.FriendsModule;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
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
	private static int backgroundCounter =0;
	
	
	public ExperimentGroupPanel(){
		
		backgroundCounter ++;
		setBackgroundColor(backgroundCounter%2==0?"E0E0E0":"C2C2C2");
		
		
		 RegExpValidator emailValidator = new RegExpValidator();  
	     emailValidator.setErrorMessage("Invalid email address"); 
	     emailValidator.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");  
	        
		
		RegExpValidator dateValidator = new RegExpValidator();
		dateValidator.setErrorMessage("Invalid date format: yyyy.mm.dd hh:mm");  
		dateValidator.setExpression("^[2-9][0-9][0-9][0-9]\\.[0-9][0-9]\\.[0-9][0-9] [0-9][0-9]:[0-9[0-9]$");  
	    
		name = new TextItem("name","Groupname");
		startDate = new TextItem("startDate", "Start date");
		startDate.setValidators(dateValidator);
		startDate.setValidateOnExit(true);
		
		endDate = new TextItem("endDate", "End date");
		endDate.setValidators(dateValidator);
		endDate.setValidateOnExit(true);
		
		addEmailAddress = new TextItem("addEmailAdress","Add E-Mailaddress");
		
		
		DynamicForm form = new DynamicForm();
		form.setFields(name, startDate, endDate, addEmailAddress);
		
		emailAdresses = new ListGrid();
		
		this.addMember(form);
		
		
		this.addMember(emailAdresses);
		
		this.setWidth100();
		this.setHeight100();
		
		this.setOverflow(Overflow.SCROLL);
		
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
		
		this.setShowCancelButton(false);
		this.setShowOkButton(false);
		this.setContent(contentStack);
		this.setWidth("70%");
		this.setHeight("70%");
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
				wizard.next();
			}
		});
		
		nextButton.setWidth(80);
		nextButton.setHeight(30);
		
		bottomLayout.addMember(bottomPlaceHolder);
		bottomLayout.addMember(nextButton);
		
		description = new RichTextEditor();
		description.setWidth100();
		description.setHeight(300);
		description.setOverflow(Overflow.SCROLL);
		
		
		mainLayout.addMember(description);
		mainLayout.addMember(bottomLayout);
		
		wizard.addStepSection(new Label("Description"), mainLayout);
	}
	
	private void generateNameAndAdminsStep(){
		
		VStack stack = new VStack();
		stack.setWidth100();
		stack.setHeight100();
		
		Label placeHolder = new Label();
		placeHolder.setWidth100();
		placeHolder.setHeight("*");
		
		DynamicForm nameForm = new DynamicForm();
		nameForm.setWidth100();
		name = new TextItem();
		
		
		
		HLayout bottomLayout = new HLayout();
		bottomLayout.setWidth100();
		bottomLayout.setHeight(25);
		Label bottomPlaceHolder = new Label();
		bottomPlaceHolder.setWidth("*");
		bottomPlaceHolder.setHeight100();
		
		OptionButton nextButton = new OptionButton("next", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				wizard.next();
			}
		});
		
		nextButton.setWidth(80);
		nextButton.setHeight(30);
		
		bottomLayout.addMember(bottomPlaceHolder);
		bottomLayout.addMember(nextButton);
		
		
		stack.addMember(adminsList);
		stack.addMember(nameForm);
		stack.addMember(placeHolder);
		stack.addMember(bottomLayout);
		
		wizard.addStepSection(new Label("Name & Admins"), stack);
	}
	
	public void generateGroups(){
	
	}


	@Override
	public String getExperimentName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void showIt() {
		this.animateShow(AnimationEffect.FADE);
	}


	@Override
	public void closeIt() {
		this.animateHide(AnimationEffect.FADE);
	}
	

}
