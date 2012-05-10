package org.gemsjax.shared.communication.message.collaboration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.communication.serialisation.Archive;

/**
 * This kind of is sent from client to server and the server will forward this message to all other clients.
 * So both, client and server, need to have a parser for this kind of message.
 * 
 * 
 * <col on="integer">
 * 
 * 		<tx id="STRING">
 * 
 * 			<vc>
 * 				<val user="integer">integer</val>
 * 				<val user="integer">integer</val>
 * 				...
 * 			</vc>
 * 
 * 			<commands>
 * 				
 * 			</commands>
 * 
 * 		</tx>
 * 
 * </col>
 * 
 * 
 * @author Hannes Dorfmann
 *
 */
public class TransactionMessage extends CollaborationMessage {

	private Transaction transaction;
	
	
	
	
	public TransactionMessage(){
		
	}
	
	public TransactionMessage(Transaction transaction) {
		this.transaction = transaction;
	}

	
	
	
	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	
	@Override
	public void serialize(Archive a) throws Exception {
		transaction = a.serialize("transaction", transaction).value;
	}
	
	

}
