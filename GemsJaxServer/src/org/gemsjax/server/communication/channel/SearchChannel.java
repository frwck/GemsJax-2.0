package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.gemsjax.server.communication.HttpCommunicationConnection;
import org.gemsjax.server.communication.parser.SearchMessageParser;
import org.gemsjax.server.module.Authenticator;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.server.module.OnlineUserManager;
import org.gemsjax.server.module.SearchExecutor;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.GlobalSearchMessage;
import org.gemsjax.shared.communication.message.search.GlobalSearchResultMessage;
import org.gemsjax.shared.communication.message.search.ReferenceableSearchMessage;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.SearchResultErrorMessage;
import org.gemsjax.shared.communication.message.search.UserResult;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.user.RegisteredUser;
import org.xml.sax.SAXException;

public class SearchChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private String regex;
	private SearchMessageParser parser;
	private HttpSession session;
	private OnlineUser onlineUser;
	
	public SearchChannel(CommunicationConnection connection, HttpSession session)
	{
		this(connection);
		this.session = session;
	}
	
	
	public SearchChannel(CommunicationConnection connection)
	{
		this.connection = connection;
		connection.registerInputChannel(this);
		regex = RegExFactory.startWithTag(ReferenceableSearchMessage.TAG);
		parser = new SearchMessageParser();
		
	}
	
	public SearchChannel(CommunicationConnection connection, OnlineUser ou)
	{
		this.onlineUser = ou;
		this.connection = connection;
		connection.registerInputChannel(this);
		regex = RegExFactory.startWithTag(ReferenceableSearchMessage.TAG);
		parser = new SearchMessageParser();
		
	}
	
	@Override
	public void send(Message m) throws IOException {
		connection.send(m);
	}

	
	
	private void setHttpResponseStatusCode(int httpStatusCode)
	{
		if (connection instanceof HttpCommunicationConnection)
			((HttpCommunicationConnection)connection).setResponseStatusCode(httpStatusCode);
	}
	
	
	@Override
	public boolean isMatchingFilter(String msg) {
		return msg.matches(regex);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		
		// 	OnlineUser ou = Authenticator.isAuthenticated(session); // No longer in use, because
		OnlineUser ou = onlineUser;
		
		if (ou !=null && ou.getUser() instanceof RegisteredUser)
		{
			try {
				ReferenceableSearchMessage m = parser.parse(msg.getText());
				
				if (m instanceof GlobalSearchMessage)
				{
					SearchExecutor se = new SearchExecutor((RegisteredUser) ou.getUser());
					String searchString = ((GlobalSearchMessage) m).getSearchString();
					
					Set<RegisteredUser> users = se.getUsers(searchString);
					Set<Collaborateable> collaborateables = se.getCollaborateables(searchString);
					Set<Experiment> experiments = se.getExperiments(searchString);
					
					setHttpResponseStatusCode(200);
					send(generateResult(m.getReferenceId(), (RegisteredUser) ou.getUser(), users, collaborateables, experiments));
					
				}
				
				
				
			} catch (SAXException e) {
				setHttpResponseStatusCode(400);
				try {
					send(new SearchResultErrorMessage(parser.getCurrentReferenceId(), SearchError.PARSING, e.getMessage()));
				} catch (IOException e1) {
					// TODO What to do if could not sent
					e1.printStackTrace();
				}
			} catch (IOException e) {
				setHttpResponseStatusCode(400);
				try {
					send(new SearchResultErrorMessage(parser.getCurrentReferenceId(), SearchError.PARSING, e.getMessage()));
				} catch (IOException e1) {
					// TODO What to do if could not sent
					e1.printStackTrace();
				}
				
			}
		
		}
		else
		{
			// Not authenticated
			setHttpResponseStatusCode(200);
			try {
				send(new SearchResultErrorMessage(null, SearchError.AUTHENTICATION, "Not authenticated"));
			} catch (IOException e) {
				// TODO what to do if answer could not be sent
				e.printStackTrace();
			}
		}
		
	}
	

	
	private GlobalSearchResultMessage generateResult( String refId, RegisteredUser requester, Set<RegisteredUser> users, Set<Collaborateable> collaborateables, Set<Experiment> experiments)
	{
		Set<UserResult> ur = new LinkedHashSet<UserResult>();
		for (RegisteredUser u : users)
			ur.add(new UserResult(u.getId(), u.getUsername(), u.getDisplayedName(), u.getProfilePicture()));
		
		
		Set<CollaborationResult> cr = new LinkedHashSet<CollaborationResult>();
		for (Collaborateable c: collaborateables)
		{
			boolean pub = c.getPublicPermission()!=Collaborateable.NO_PERMISSION;
			CollaborateableType t ;
			if (c instanceof MetaModel)
				t = CollaborateableType.METAMODEL;
			else
				t = CollaborateableType.MODEL;
			
			cr.add(new CollaborationResult(c.getId(), c.getName(), c.getOwner().getDisplayedName(), pub, t));
		}	
		
		
		Set<ExperimentResult> er = new LinkedHashSet<ExperimentResult>();
		for (Experiment e: experiments){
			boolean coAdmin = e.getAdministrators().contains(requester);
			er.add(new ExperimentResult(e.getId(), e.getName(), e.getOwner().getDisplayedName(), coAdmin));
			
		}
		
		
		return new GlobalSearchResultMessage(refId, ur, cr, er);
			
	}

}
