package org.gemsjax.server.module;

import java.util.List;
import java.util.Map.Entry;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.collaboration.command.Command;

public class TransactionProcessor {
	
	
	
	public TransactionProcessor(){
		
	}
	
	
	
	public void executeTransaction(Transaction tx) throws ManipulationException{
		
		synchronized(tx.getCollaborateable()){
			
			mergeMaxVectorClocks(tx, tx.getCollaborateable());
			
			for (Command c: tx.getCommands())
				c.execute();
		
		}
		
		
	}
	
	
	public void executeTransactions(List<Transaction> txs) throws ManipulationException{
		
		for (Transaction t : txs)
			executeTransaction(t);
		
	}
	
	
	
	
	private void  mergeMaxVectorClocks(Transaction tx, Collaborateable collaborateable){
		
		synchronized(collaborateable){
				// Adjust Vector Clock
				for (Entry<Integer, Long> e : tx.getVectorClock().entrySet()){
					long receivedVal = tx.getVectorClockEnrty(e.getKey());
					long currentVal = getVectorClockValue(e.getKey(), collaborateable);
					
					if (receivedVal>currentVal)
						collaborateable.getVectorClock().put(e.getKey(), receivedVal);
				}
			}
		}
		
		
		private long getVectorClockValue(int userId, Collaborateable collaborateable){
			Long value = collaborateable.getVectorClock().get(userId);
			if ( value==null)
				return 0;
			else
				return value;
			
	}
		
	

}
