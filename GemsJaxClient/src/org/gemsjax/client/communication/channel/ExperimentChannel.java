package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.handler.ExperimentChannelHandler;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.experiment.ExperimentErrorMessage;
import org.gemsjax.shared.communication.message.experiment.ExperimentMessage;
import org.gemsjax.shared.communication.message.experiment.ExperimentSuccessfulMessage;
import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsAnswerMessage;

public class ExperimentChannel implements InputChannel, OutputChannel {
	
	private CommunicationConnection connection;
	private Set<ExperimentChannelHandler> handlers;
	
	public ExperimentChannel(CommunicationConnection connection){
		this.connection = connection;
		this.connection.registerInputChannel(this, ExperimentMessage.TYPE);
		handlers = new LinkedHashSet<ExperimentChannelHandler>();
	}
	
	
	public void addExperilentChannelHandler(ExperimentChannelHandler h){
		handlers.add(h);
	}
	
	public void removeExperilentChannelHandler(ExperimentChannelHandler h){
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

		
		if (msg instanceof GetAllExperimentsAnswerMessage)
			for(ExperimentChannelHandler h : handlers)
				h.onGetAllAnswer(((GetAllExperimentsAnswerMessage)msg).getReferenceId(), ((GetAllExperimentsAnswerMessage) msg).getExperiments());
		else
		if (msg instanceof ExperimentSuccessfulMessage)
			for(ExperimentChannelHandler h : handlers)
				h.onSuccessfulMessage(((ExperimentSuccessfulMessage)msg).getReferenceId());
		else
		if (msg instanceof ExperimentErrorMessage)
			for(ExperimentChannelHandler h : handlers)
				h.onErrorMessage(((ExperimentErrorMessage)msg).getReferenceId(), ((ExperimentErrorMessage) msg).getExperimentError());
		
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
