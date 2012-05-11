package org.gemsjax.test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.gemsjax.client.communication.channel.CollaborationChannel;
import org.gemsjax.client.module.CollaborationModule;
import org.gemsjax.client.module.handler.CollaborationModuleHandler;
import org.gemsjax.client.user.RegisteredUserImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;
import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;
import org.gemsjax.shared.metamodel.impl.MetaFactory;
import org.gemsjax.shared.user.User;
import org.gemsjax.shared.user.UserOnlineState;




class FakeCommunicationConnection implements CommunicationConnection{

	public Set<VectorClockProcess> inputChannelDeliveryHelper;
	private Map<MessageType<?>, Set<InputChannel>> inputChannelMap;
	
	public FakeCommunicationConnection(){
		inputChannelMap = new LinkedHashMap<MessageType<?>, Set<InputChannel>>();
		inputChannelDeliveryHelper = new LinkedHashSet<VectorClockProcess>();
	}
	
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connect() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getRemoteAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setKeepAlive(boolean keepAlive) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isKeepAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void send(Message message) throws IOException {
		deliverMessage(message);
	}
	

	@Override
	public void registerInputChannel(InputChannel c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerInputChannel(InputChannel c, MessageType<?> type) {
		
		if (!inputChannelMap.containsKey(type)){ // key is not already there
			Set<InputChannel> channels =  new LinkedHashSet<InputChannel>();
			
			channels.add(c);
			
			inputChannelMap.put(type, channels);
		}
		else
		{	// Key is already present, so append the channel to the list
			Set<InputChannel> channels = inputChannelMap.get(type);
			channels.add(c);
		}
	}

	@Override
	public void deregisterInputChannel(InputChannel c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCloseListener(ClosedListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCloseListener(ClosedListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEstablishedListener(EstablishedListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEstablishedListener(EstablishedListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addErrorListener(ErrorListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeErrorListener(ErrorListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	private void deliverMessage(Message m){
		
		if (m instanceof TransactionMessage)
		{	
			Transaction t = ((TransactionMessage) m).getTransaction();
			for (VectorClockProcess p : inputChannelDeliveryHelper){
				if (p.userId == t.getUserId())
					continue;
				
				p.channel.onMessageRecieved(m);
				
			}
		}
		else
		{
			// TODO when needed
		}
		
	}
	
	
}






class VectorClockProcess extends Thread implements CollaborationModuleHandler{
	
	public CollaborationChannel channel;
	public CollaborationModule module;
	public int userId;
	public int collaborateableId;
	
	public VectorClockProcess(int userId, int collaborateableId, FakeCommunicationConnection connection)
	{
		this.userId = userId;
		User u = new RegisteredUserImpl(userId, "displayedName", UserOnlineState.ONLINE);
		this.collaborateableId = collaborateableId;
		
		connection.inputChannelDeliveryHelper.add(this);
		Collaborateable c = MetaFactory.createMetaModel(1, "name");
		
		channel = new CollaborationChannel(connection, c.getId());
		module = new CollaborationModule(u, c.getId(), channel);
		module.addCollaborationModuleHandler(this);
	}
	
	@Override
	public void run(){
		System.out.println("RUN");
		while(true)
		
		try {
			module.sendAndCommitTransaction(new LinkedList<Command>());
			this.sleep(3000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onCollaborateableUpdated() {
		System.out.println("User "+userId+" "+ module);
	}
	
}




public class VectorClockTest {
	
	public static void main(String args[]){
		
		FakeCommunicationConnection connection = new FakeCommunicationConnection();
		
		Set<VectorClockProcess> processes = new LinkedHashSet<VectorClockProcess>();
		
		for (int i = 1; i<=3; i++){
			processes.add(new VectorClockProcess(i,1, connection));
		}
		
		
		for(VectorClockProcess p : processes){
			p.start();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

}
