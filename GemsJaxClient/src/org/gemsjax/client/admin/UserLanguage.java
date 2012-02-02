package org.gemsjax.client.admin;

import org.gemsjax.shared.FieldVerifier;

public interface UserLanguage extends com.google.gwt.i18n.client.Constants{
	
	  // Login
	  @DefaultStringValue("Login")
	  String Login();
	  
	  @DefaultStringValue("Logout")
	  String Logout(); 
	  
	  @DefaultStringValue("Logout successful")
	  String LogoutReasonClientUser();
	  
	  @DefaultStringValue("An unexpected error has occurred. Please reload.")
	  String UnexpectedErrorOccurred();
	  
	  @DefaultStringValue("You were loged out by the server, because the server has accepted another connection with your user authentication data")
	  String LogoutReasonServerOtherConnection();
	  
	  
	  @DefaultStringValue("Username")
	  String Username();  
	  
	  @DefaultStringValue("Password")
	  String Password();
	  
	  
	  @DefaultStringValue("Sign In")
	  String LoginTitle();  
	  
	  @DefaultStringValue("Forget password?")
	  String ForgetPassword();

	  @DefaultStringValue("New User")
	  String NewUser();  
	  
	  @DefaultStringValue("Login. Please wait.")
	  String WaitWhileLoginIn();
	  
	  @DefaultStringValue("A connection error has occurred. Please retry.")
	  String LoginSentFail();
	  
	  @DefaultStringValue("Login failed. Username or password is not correct.")
	  String LoginFailed();
	  
	  /**
	   * If you try to login, but the password field was empty
	   * @return
	   */
	  @DefaultStringValue("The password field can not be empty. You must enter a password.")
	  String PasswordIsEmptyMessage();
	  
	  /**
	   * If you try to login, but the username field is not valid 
	   * @see FieldVerifier#isValidUsername(String)
	   * @return
	   */
	  @DefaultStringValue("The Input is not a valid username.")
	  String IsNotValidUsernameMessage();
	  
	  // registered user menu
	  @DefaultStringValue("settings")
	  String SettingsMenuItem();

	  @DefaultStringValue("experiments")
	  String ExperimentsMenuItem();    
	  
	  @DefaultStringValue("notifications")
	  String NotificationsMenuItem();
	  
	  @DefaultStringValue("meta-models")
	  String MetaModelsMenuItem();
	  
	  
	  
	  // Experiments

	  @DefaultStringValue("Create Experiment")
	  String CreateExperiment();
	  
	  @DefaultStringValue("Description")
	  String ExperimenteDescritption();  
	  
	  //Meta-Model
	  @DefaultStringValue("Use Mouse")
	  String MetaModelToolbarUseMouse();
	  
	  @DefaultStringValue("Click on the canvas to create there a new Meta-Class")
	  String MetaModelToolbarNewMetaClassTip();
	
	  @DefaultStringValue("Meta-Class")
	  String MetaModelToolbarNewMetaClass();
	  
	  
	  @DefaultStringValue("Select the class which should derive")
	  String MetaModelToolbarInheritanceTip1();
	  
	  @DefaultStringValue("Select the super class")
	  String MetaModelToolbarInheritanceTip2();
	  
	  @DefaultStringValue("Inheritance")
	  String MetaModelToolbarInheritance();
	  
	  @DefaultStringValue("Select the class, where the relation starts")
	  String MetaModelToolbarRelationTip1();
	  
	  
	  @DefaultStringValue("Select the class, where the relation ends")
	  String MetaModelToolbarRelationTip2();
	  
	  @DefaultStringValue("Relation")
	  String MetaModelToolbarRelation();
	  
	  @DefaultStringValue("Use the mouse and the toolbar on the left to interact with the canvas")
	  String MetaModelEditorWelcomeTip();
	  
	  @DefaultStringValue("It's not allowed to place this anchor here")
	  String MetaModelAnchorPlaceNotAllowedTitle();
	  

	  @DefaultStringValue("The anchor must be placed in the corresponding area")
	  String MetaModelAnchorPlaceNotAllowedText();
	  
	  
	  // Registration
	  
	  @DefaultStringValue("Sign Up")
	  String RegistrationTitle();
	  
	  
	  @DefaultStringValue("Passwords do not match")
	  String RegistrationPasswordMismatch();
	  
	  @DefaultStringValue("The username is not valid. A Username must have at least 3 characters and cosnsist of this kind of characters: A-Z a-z 0-9")
	  String RegistrationInvalidUsername();
	  
	  @DefaultStringValue("The e-mail address is not valid")
	  String RegistrationInvalidEmail();

	  @DefaultStringValue("Username")
	  String RegistrationUsername();
	  
	  @DefaultStringValue("Password")
	  String RegistrationPassword();
	  
	  @DefaultStringValue("Password again")
	  String RegistrationPasswordRepeated();
	  
	  @DefaultStringValue("E-Mail")
	  String RegistrationEmail();
	  
	  @DefaultStringValue("Create account")
	  String RegistrationSubmit();
	  
	  
	  @DefaultStringValue("Registration failed ")
	  String RegistrationTitleFail();
	  
	  @DefaultStringValue("Registration successful ")
	  String RegistrationTitleSuccessful();
	  
	  @DefaultStringValue("Registration was successful")
	  String RegistrationSuccessful();
	  
	  
	  @DefaultStringValue("The submitted e-mail address is already used by another user")
	  String RegistrationFailEmail();
	  
	  
	  
	  @DefaultStringValue("The submitted username is already used by another user")
	  String RegistrationFailUsername();
	
	  
	  @DefaultStringValue("An unexcected error has occurred. Please retry.")
	  String RegistrationUnexpectedError();
	  
	  
	  
	  
	  @DefaultStringValue("Users")
	  String GlobalSearchUsersMenuTitle();
	
	  @DefaultStringValue("Friends")
	  String GlobalSearchFriendsMenuTitle();
	  
	  
	  @DefaultStringValue("MetaModls")
	  String GlobalSearchMetaModelsMenuTitle();
	  
	  
	  @DefaultStringValue("Models")
	  String GlobalSearchModelsMenuTitle();
	  
	  
	  @DefaultStringValue("Experiments")
	  String GlobalSearchExperimentsMenuTitle();
	  
	  
	  
}
