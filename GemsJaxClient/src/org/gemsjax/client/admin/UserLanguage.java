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
	  
	  
	  @DefaultStringValue("models")
	  String ModelsMenuItem();
	  
	  
	  
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
	  
	  @DefaultStringValue("Containment")
	  String MetaModelToolbarContainment();
	  
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
	  
	  
	  @DefaultStringValue("MetaModels")
	  String GlobalSearchMetaModelsMenuTitle();
	  
	  
	  @DefaultStringValue("My MetaModels")
	  String GlobalSearchMyMetaModelsTitle();
	  
	  
	  @DefaultStringValue("Public MetaModels")
	  String GlobalSearchPublicMetaModelsTitle();
	  
	  
	  @DefaultStringValue("My Models")
	  String GlobalSearchMyModelsTitle();
	  
	  
	  @DefaultStringValue("Public Models")
	  String GlobalSearchPublicModelsTitle();
	  
	  @DefaultStringValue("Models")
	  String GlobalSearchModelsMenuTitle();
	  
	  
	  @DefaultStringValue("Experiments")
	  String GlobalSearchExperimentsMenuTitle();
	  
	  @DefaultStringValue("Nothing found")
	  String GlobalSearchNoResult();
	  

	  @DefaultStringValue("Send friendship request")
	  String GlobalSearchNewFriendshipRequest();
	  
	  @DefaultStringValue("Unfriend")
	  String GlobalSearchUnfriend();
	  
	  
	  @DefaultStringValue("Show")
	  String GlobalSearchShow();
	  
	  
	  @DefaultStringValue("An unexpected error has been occurred. Please retry.")
	  String GlobalSearchUnexpectedErrorMessage();
	  
	  
	  @DefaultStringValue("The search can not be executed, because you are not authenticated!")
	  String GlobalSearchErrorNotAuthenticated();
	  
	  @DefaultStringValue("An unexpected parse error has been ocurred. Please retry.")
	  String GlobalSearchErrorParsing();
	  
	  
	  @DefaultStringValue("An unexpected database error has been ocurred. Please retry.")
	  String GlobalSearchErrorDatabase();
	  
	  @DefaultStringValue("No Result received yet")
	  String GlobalSearchNoResultReceived();
	  
	  
	  // Requests
	  
	  @DefaultStringValue("Accept")
	  String RequestAccept();
	  
	  @DefaultStringValue("Reject")
	  String RequestReject();
	  
	  @DefaultStringValue("Friendships")
	  String RequestFriendshipTitle();
	  
	  @DefaultStringValue("Experiments")
	  String RequestExperimentTitle();
	  
	  @DefaultStringValue("Collabrations")
	  String RequestCollaborationTitle();
	  
	  @DefaultStringValue("Received")
	  String RequestDateLabel();
	  
	  @DefaultStringValue("From")
	  String RequestFromLabel();
	  
	  @DefaultStringValue("Message")
	  String RequestMessageLabel();
	  

	  @DefaultStringValue("An error has occrred. Please retry")
	  String RequestError();
	  
	  @DefaultStringValue("Options")
	  String RequestOptionsLabel();
	  
	  @DefaultStringValue("You have been invited to administrate ")
	  String RequestExperimentInvitedMessage();
	  
	  
	  @DefaultStringValue("You have been invited to work on ")
	  String RequestCollaborationInvitedMessage();
	  
	  
	  @DefaultStringValue(" wants to be befriended with you")
	  String RequestFriendshipInvitedMessage();
	  
	  
	  // Notification
	  @DefaultStringValue("Notifications")
	  String NotificationTitle();
	  
	  @DefaultStringValue("Received")
	  String NotificationDateLabel();
	  
	  @DefaultStringValue("Delete")
	  String NotificationDelete();
	  
	  @DefaultStringValue("Mark as read")
	  String NotificationMarkAsRead();
	  
	  @DefaultStringValue("Topic")
	  String NotificationTopicLabel();
	  
	  @DefaultStringValue("Could not mark the notification as read")
	  String NotificationMarkAsReadError();
	  
	  @DefaultStringValue("Could not delete the notification")
	  String NotificationDeleteError();
	  
	  
	  @DefaultStringValue("You are no longer befriended with ")
	  String NotificationQuickFriendshipMessage();
	  
	  @DefaultStringValue("The Experiment ")
	  String NotificationQuickExperimentDeletedMessagePart1();
	  
	  @DefaultStringValue(" has been deleted and is no longer available")
	  String NotificationQuickExperimentDeletedMessagePart2();

	  @DefaultStringValue("The Collaborateable ")
	  String NotificationQuickCollaborationDeletedMessagePart1();
	  
	  @DefaultStringValue(" has been deleted and is no longer available")
	  String NotificationQuickCollaborationDeletedMessagePart2();
	  
	  @DefaultStringValue(" has accepted ")
	  String NotificationAccepted();
	  
	  @DefaultStringValue(" has rejected ")
	  String NotificationRejected();
	  
	  @DefaultStringValue(" your experiment invitation to ")
	  String NotificationExperimentInvitation();
	  
	  @DefaultStringValue(" your friendship request")
	  String NotificationFriendshipInvitation();
	  
	  @DefaultStringValue(" your collaboration invitation to ")
	  String NotificationCollaborationInvitation();
	  
	  
	  @DefaultStringValue("Click here to retry")
	  String NotificationErrorRestartLoading();
	  
	  @DefaultStringValue("Could not load your Notifications or Requests")
	  String NotificationErrorLoading();
	  
	  @DefaultStringValue("Notification Center")
	  String NotificationCenterTitle();
	  
	   
	  @DefaultStringValue("A new friendship request has been send")
	  String ManagageFriendshipRequestHasBeenSend();
	  
	  @DefaultStringValue("You are already befriended with")
	  String ManageFriendshipAlreadyBefriended();
	  
	  @DefaultStringValue("An error has occurred. Please retry.")
	  String ManageFriendshipParsingError();
	  
	  @DefaultStringValue("An database error has occurred. Please retry.")
	  String ManageFriendshipDatabaseError();
	  
	  @DefaultStringValue("A friendship request has already been send, but is unanswered yet.")
	  String ManageFriendshipAlreadyRequestedError();
	  
	  @DefaultStringValue("Something went wrong. The friend could not be found")
	  String ManageFriendshipFriendIdError();
	  
	  @DefaultStringValue("An unexpected error has occurred. Please retry.")
	  String ManageFriendshipUnexpectedError();
	  
	  
	  @DefaultStringValue("Already friends")
	  String ManageFriendshipAlreadyFriendsError();
	  
	  
	  @DefaultStringValue("Friendship request sent to ")
	  String ManageFriendshipRequestSuccessful();
	  
	  
	  @DefaultStringValue("Friendship with")
	  String ManageCancelFriendshipSuccessful1();
	  
	  
	  @DefaultStringValue(" has been canceled")
	  String ManageCancelFriendshipSuccessful2();
	  
	  @DefaultStringValue("Friendship with ")
	  String ManageCancelFriendshipError1();
	 
	  @DefaultStringValue(" could not be canceled.")
	  String ManageCancelFriendshipError2();
	  
	  
	  @DefaultStringValue("Friendship request could not be sent to ")
	  String ManageFriendshipReqeustError();
	  
	  @DefaultStringValue("This wizard will lead you step by step through the creation progress. As first step, you have to fill in a name and a optional description of the new meta model.")
	  String CreateMetaModelNameDescriptionIntro();
	  

	  @DefaultStringValue("As next step select your friends that should be invited to work with you on this meta model.")
	  String CreateMetaModelCollaboratorIntro();
	  
	  @DefaultStringValue("finish")
	  String CreateMetaModelFinish();
	  
	  @DefaultStringValue("Description")
	  String CreateMetaModelDescription();
	 
	  @DefaultStringValue("New meta model")
	  String CreateMetaModelTitle();
	  
	  @DefaultStringValue("Name")
	  String CreateMetaModelName();
	  
	  @DefaultStringValue("Permission")
	  String CreateMetaModelPermission();
	  
	  @DefaultStringValue("close")
	  String CreateMetaModelCloseButton();
	  
	  @DefaultStringValue("continue")
	  String CreateMetaModelNext();
	  
	  @DefaultStringValue("Meta model has been created successfully")
	  String CreateMetaModelSuccessful();
	  
	  @DefaultStringValue("An error has occurred. Please retry.")
	  String CreateMetaModelCreationError();
	  
	  @DefaultStringValue("A valid name must be set")
	  String CreateMetaModelErrorName();
	  
	  @DefaultStringValue("Private")
	  String CollaborateablePermissionPrivate();
	  
	  @DefaultStringValue("Public - Read Only")
	  String CollaborateablePermissionReadOnly();
	  
	  
	  @DefaultStringValue("Public - Copyable")
	  String CollaborateablePermissionCopyable();
	  
	  
	  @DefaultStringValue("<b>Private:</b> This file is only visible and accessible to you and all collaborators<br />"+
			  			  "<b>Public - Read Only:</b> This file is visible to everybody, but can only be modified by you and the collaborators"+
			  			  "<b>Public - Copyable:</b> This file is visible to everybody and everybody can make a copy of this file")
	  String CollaborateablePermissionHint();
	  
	  
	  
	  
	  @DefaultStringValue("My meta models")
	  String AllMetaModelsTitle();
	  
	  @DefaultStringValue("No meta model found")
	  String AllMetaModelsEmpty();
	   
	  @DefaultStringValue("New")
	  String AllMetaModelsNew();
	  
	  @DefaultStringValue("Refresh")
	  String AllMetaModelsRefresh();
	  
	  
	  
	  @DefaultStringValue("Friends name")
	  String FriendChooserListEnterName();
	  
	  @DefaultStringValue("Please choose")
	  String FriendChooserAmbigousDailogTitle();
	  
	  @DefaultStringValue("The inserted name is ambiguous. Please choose:")
	  String FriendChooserAmbigousDailogIntro();
	  
	  @DefaultStringValue("No one found")
	  String FriendChooserNoOneFound();
	  
	  @DefaultStringValue("Friend is already in list")
	  String FriendChooserAlreadyInList();
	  
	  
	  
	  
	  
}
