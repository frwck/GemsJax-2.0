package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.collaboration.Transaction;

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

	public static final String TAG = "tx";
	public static final String ATTRIBUTE_ID="id";
	public static final String SUBTAG_VECTOR_CLOCK = "vc";
	public static final String SUBSUBTAG_VECTOR_CLOCK_ENTRY="val";
	public static final String SUBTAG_COMMANDS = "commands";
	
	private Transaction transaction;
	
	public TransactionMessage(int collaborateableId, Transaction transaction) {
		super(collaborateableId);
		
		this.transaction = transaction;
		
	}

	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

}
