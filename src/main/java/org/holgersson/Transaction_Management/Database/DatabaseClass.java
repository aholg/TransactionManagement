package org.holgersson.Transaction_Management.Database;

import java.util.HashMap;
import java.util.Map;

import org.holgersson.Transaction_Management.model.Transaction;


public class DatabaseClass {

	private static Map<Long, Transaction> transactions = new HashMap<>();
	

	
	public static Map<Long, Transaction> getTransactions() {
		return transactions;
	}
	
	

	
	
	
}
