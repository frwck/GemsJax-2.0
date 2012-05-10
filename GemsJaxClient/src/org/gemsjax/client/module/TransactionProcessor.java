package org.gemsjax.client.module;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.gemsjax.client.user.RegisteredUserImpl;
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
	
	public TransactionProcessor(User user){
		history = new LinkedList<Transaction>();
		this.user = user;
		
	}
	
	
	/**
	 * Executes a Transaction and puts it in the correct position of the History
	 * @param t
	 */
	public void executeTransaction(Transaction t){
		
		TransactionComparator comp = new TransactionComparator(user.getId());
		
		if (history.isEmpty()){

			history.add(t);
			t.commit();
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
					current.rollback();
					if (i == 0){
						history.add(0,t);
						insertedIndex = 0;
					}
				}
				
			}
			
			
			for (int i = insertedIndex;i<history.size();i++){
				Transaction current = history.get(i);
				current.commit();
			}
			
		}
		
	}
	
	
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
	
	

}
