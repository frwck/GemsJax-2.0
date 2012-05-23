package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.ExperimentChannelHandler;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.experiment.CreateExperimentMessage;
import org.gemsjax.shared.communication.message.experiment.ExperimentMessage;
import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsAnswerMessage;
import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsMessage;
import org.gemsjax.shared.user.User;

public class ExperimentChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private OnlineUser user;
	private Set<ExperimentChannelHandler> handlers;
	
	public ExperimentChannel(CommunicationConnection connection, OnlineUser user){
		this.connection = connection;
		this.user = user;
		this.handlers = new LinkedHashSet<ExperimentChannelHandler>();
		this.connection.registerInputChannel(this, ExperimentMessage.TYPE);
	}
	
	
	public void addExperimentChannelHandler(ExperimentChannelHandler h){
		handlers.add(h);
	}
	
	public void removeExperimentChannelHandler(ExperimentChannelHandler h){
		handlers.remove(h);
	}
	
	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageRecieved(Message msg) {
		
		if (msg instanceof GetAllExperimentsMessage)
			for (ExperimentChannelHandler h : handlers)
				h.onGetAllExperiments(((GetAllExperimentsMessage) msg).getReferenceId(), user);
		else
		if (msg instanceof CreateExperimentMessage)
			for (ExperimentChannelHandler h : handlers)
				h.onCreateExperiment((CreateExperimentMessage) msg, user);
		
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
