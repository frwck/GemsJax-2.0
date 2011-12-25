package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.view.RegistrationView;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class RegistrationViewImpl extends Window implements RegistrationView {

	
	

	
	public RegistrationViewImpl(UserLanguage language)
	{
		
	}
	
	
	private void generateForm(UserLanguage language)
	{
		
		/*
		 * 
		 * final DynamicForm form = new DynamicForm();  
        form.setWidth(250);  
  
        TextItem username = new TextItem();  
        username.setName("username");  
        username.setTitle("Username");  
        username.setRequired(true);  
        username.setDefaultValue("bob");  
  
        TextItem email = new TextItem();  
        email.setName("email");  
        email.setTitle("Email");  
        email.setRequired(true);  
        email.setDefaultValue("bob@isomorphic.com");  
        Something like this email.setValidator();
  
        MatchesFieldValidator validator = new MatchesFieldValidator();  
        validator.setOtherField("password2");  
        validator.setErrorMessage("Passwords do not match");  
          
        PasswordItem password = new PasswordItem();  
        password.setName("password");  
        password.setTitle("Password");  
        password.setRequired(true);  
        password.setValidators(validator);  
  
        PasswordItem password2 = new PasswordItem();  
        password2.setName("password2");  
        password2.setTitle("Password again");  
        password2.setRequired(true);  
  
        final ButtonItem createAccount = new ButtonItem();  
        createAccount.setName("createAccount");  
        createAccount.setTitle("Create Account");  
        createAccount.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                form.validate();  
            }  
        });  
          
        form.setFields(username, email, password, password2, createAccount);  
  
        form.draw();  
    }
    
		 */
		
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPasswordRepeated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasClickHandlers getRegisterButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showErrorMessage(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	

}
