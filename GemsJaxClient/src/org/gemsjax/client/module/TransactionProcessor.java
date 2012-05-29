package org.gemsjax.client.module;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.gemsjax.client.user.RegisteredUserImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.collaboration.TransactionImpl;
import org.gemsjax.shared.user.User;
import org.gemsjax.shared.user.UserOnlineState;


class TransactionComparator implements Comparator<Transaction>{

	private int localUserId;
	
	public TransactionComparator(int localUserId){
		this.localUserId = localUserId;
	}
	
	/**
	 * Compares Transaction f with Transaction s.
	 * @return f < s return -1   f>s return +1    f = s return 0
	 */
	@Override
	public int compare(Transaction f, Transaction s) {
		
		if (f.getVectorClockEnrty(localUserId)<s.getVectorClockEnrty(localUserId))
			return -1;
		else
		if (f.getVectorClockEnrty(localUserId)>s.getVectorClockEnrty(localUserId))
			return 1;
		else // f[localUserId] == s [localUserId]
		{
			TreeMap<Integer, Long> sorted = new TreeMap<Integer, Long>(f.getVectorClock());
			
			for (Entry<Integer, Long> e: sorted.entrySet()){
				
				if (e.getValue()<s.getVectorClockEnrty(e.getKey()))
					return -1;
				
				if (e.getValue()>s.getVectorClockEnrty(e.getKey()))
				return +1;
			}
			
			
		}
		
		
		return 0;
	}
	
}





public class TransactionProcessor {
	
	private List<Transaction> history;
	private User user;
	private Collaborateable collaborateable;
	private Transaction currentReplayTransaction;
	private int currentReplayTransactionIndex;
	
	private boolean replayMode;
	
	public TransactionProcessor(User user, Collaborateable collaborateable){
		history = new LinkedList<Transaction>();
		this.user = user;
		this.collaborateable = collaborateable;
		replayMode = false;
		currentReplayTransactionIndex = -1;
	}
	
	
	/**
	 * Executes a Transaction and puts it in the correct position of the History
	 * @param t
	 */
	public void executeTransaction(Transaction t){
		
		TransactionComparator comp = new TransactionComparator(user.getId());
		
		if (history.isEmpty()){

			history.add(t);
			try {
				if (!replayMode)
					t.commit();
			} catch (ManipulationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			// Serach for the correct position, start from the end
			
			int insertedIndex = -1;
			for (int i = history.size()-1; i>=0; i--){
				Transaction current = history.get(i);
				int lt = comp.compare(t, current);
				if (lt>=0){
					history.add(i+1, t);
					insertedIndex= i+1;
					break;
				}
				else
				{
					try {
						if (!replayMode)
							current.rollback();
					} catch (ManipulationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (i == 0){
						history.add(0,t);
						insertedIndex = 0;
					}
				}
				
			}
			
			
			for (int i = insertedIndex;i<history.size();i++){
				Transaction current = history.get(i);
				try {
					if (!replayMode)
						current.commit();
				} catch (ManipulationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
		
		// If its in replay mode, then adjust currentReplayTransactionIndex
		if (replayMode)
			adjustIndexOfCurrentTransaction();
		
		
	}
	
	
	
	private void setCurrentReplayTransactionToLast(){
		
		if (!history.isEmpty()){
			currentReplayTransaction = history.get(history.size()-1);
			currentReplayTransactionIndex = history.size()-1;
		}
		else{
			currentReplayTransaction = null;
			currentReplayTransactionIndex = -1;
		}
	}
	
	
	public int getHistorySize(){
		return history.size();
	}
	
	
	
	private int adjustIndexOfCurrentTransaction(){
		for (int i = 0; i<history.size(); i++)
			if (history.get(i) == currentReplayTransaction)
				return i;
		
		return -1;
	}
	
	
	public int getCurrentReplayTransactionIndex(){
		return currentReplayTransactionIndex;
	}
	
	public Transaction getCurrentReplayTransaction(){
		return currentReplayTransaction;
	}


	public boolean isReplayMode() {
		return replayMode;
	}


	/**
	 * If argument is true, then the replay mode will be activated
	 * @param replayMode
	 */
	public void setReplayMode(boolean replayMode) {
		this.replayMode = replayMode;
		
		if (replayMode)
			setCurrentReplayTransactionToLast();
		else
		{
			// TODO support it
			leaveReplayStepToLast();
		}
			
		
	}
	
	
	
	public void replayModeStepBack(){
		
		if (replayMode && currentReplayTransactionIndex>=0){
			try {
				currentReplayTransaction.rollback();
			} catch (ManipulationException e) {
				e.printStackTrace();
			}
			currentReplayTransactionIndex--;
			if (currentReplayTransactionIndex<0)
				currentReplayTransaction = null;
			else
				currentReplayTransaction = history.get(currentReplayTransactionIndex);
		}
		
	}
	
	
	
	public void replayModeStepForward(){
		if (replayMode && currentReplayTransactionIndex<=history.size()-1){
					
			currentReplayTransactionIndex++;
			currentReplayTransaction = history.get(currentReplayTransactionIndex);
			
					try {
						currentReplayTransaction.commit();
					} catch (ManipulationException e) {
						e.printStackTrace();
					}
		}
	}
	
	
	private void leaveReplayStepToLast(){
		
		if (!history.isEmpty())
			while(currentReplayTransactionIndex<history.size()-1){
				
				currentReplayTransactionIndex++;
				currentReplayTransaction = history.get(currentReplayTransactionIndex);
				
						try {
							currentReplayTransaction.commit();
						} catch (ManipulationException e) {
							e.printStackTrace();
						}
			}
	}
	
	
	
	
	/*
	public static void main(String args[]){
		
		TransactionProcessor tp = new TransactionProcessor(new RegisteredUserImpl(1, "displayedName", UserOnlineState.OFFLINE ));
		
		Transaction t1 = new TransactionImpl();
		t1.setVectorClockEntry(1, 2);
		t1.setVectorClockEntry(2, 2);
		t1.setVectorClockEntry(3, 2);
		
		
		Transaction t2 = new TransactionImpl();
		t2.setVectorClockEntry(1, 2);
		t2.setVectorClockEntry(2, 1);
		t2.setVectorClockEntry(3, 1);
		
		Transaction t3 = new TransactionImpl();
		t3.setVectorClockEntry(1, 2);
		t3.setVectorClockEntry(2, 1);
		t3.setVectorClockEntry(3, 2);
		
		
		tp.executeTransaction(t1);
		tp.executeTransaction(t2);
		tp.executeTransaction(t3);
		
		
	}
	
	*/

}
