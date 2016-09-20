package org.holgersson.Transaction_Management.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.holgersson.Transaction_Management.Database.DatabaseClass;


/**
 * Contains business logic for transaction operations.
 * 
 * @author Anton
 *
 */
public class TransactionDao {
	/**
	 * Retrieves all saved transactions.
	 * 
	 * @return A list containing all transactions.
	 */
	private static List<Transaction> transactionList;
	private Map<Long,Transaction>transactions=DatabaseClass.getTransactions();
	public TransactionDao(){
		transactions.put(1L, new Transaction(10.0, "car", 1));
		transactions.put(2L, new Transaction(200.0, "car", 2));
	}
	public List<Transaction> getAllTransactions() {
		

//			if (transactionList==null) {
//			
//				Transaction transaction = new Transaction(10.0, "car", 1);
//				transactionList = new ArrayList<Transaction>();
//				transactionList.add(transaction);
//			//	saveTransactionList(transactionList);
//			} 
		return new ArrayList<Transaction>(transactions.values());
	}

	/**
	 * Retrieves a transaction with given transaction id.
	 * 
	 * @param transaction_id
	 *            Transaction id for transaction to retrieve.
	 * @return A transaction object or null if not found.
	 */
	public Transaction getTransaction(long transaction_id) {
		List<Transaction> transactionList = getAllTransactions();

		for (Transaction transaction : transactionList) {
			if (transaction.getTransactionId() == transaction_id) {
				return transaction;
			}
		}
		return null;
	}

	/**
	 * Retrieves a list of transaction identifiers with the same type given by
	 * input.
	 * 
	 * @param type
	 *            A string containing the type of transactions to be returned.
	 * @return A json list of all transaction ids that share the same type.
	 */
	public List<Long> getTypes(String type) {
		List<Transaction> transactionList = getAllTransactions();
		List<Long> typesList = new ArrayList<>();
		for (Transaction transaction : transactionList) {
			if (type.equals(transaction.getType())) {
				typesList.add((transaction.getTransactionId()));
			}
		}
		return typesList;
	}

	/**
	 * Adds a new transaction.
	 * 
	 * @param pTransaction
	 *            Transaction to add.
	 * @return Response depending on result.
	 */
	public Response addTransaction(Transaction pTransaction,UriInfo uriInfo) {
		transactionList=new ArrayList<Transaction>(transactions.values());
		
		boolean transactionExists = false;
		for (Transaction transaction : transactionList) {
			if (transaction.getTransactionId() == pTransaction.getTransactionId()) {
				transactionExists = true;
				transactions.remove(transaction.getTransactionId());
				break;
			}
		}
		URI uri=uriInfo.getAbsolutePathBuilder().build();
		
		transactions.put(pTransaction.getTransactionId(), pTransaction);
		//saveTransactionList(transactionList);
		if (!transactionExists) {

			return Response.created(uri).entity(pTransaction).build();
		} else {
			return Response.ok(uri).entity(pTransaction).build();
		}

	}

	/**
	 * A sum of all transactions that are transitively linked by their parent_id
	 * to $transaction_id.
	 * 
	 * @param transaction_id
	 *            Transaction id to use for search.
	 * @return A sum object containing sum of the matching transactions.
	 */
	public Sum getSum(long transaction_id) {
		List<Transaction> transactionList = getAllTransactions();
		Transaction pTransaction = getTransaction(transaction_id);
		Sum sum = new Sum();

		if (pTransaction != null) {
			sum.add(pTransaction.getAmount());
			for (Transaction transaction : transactionList) {
				if (transaction.getParent_id() != null && transaction.getParent_id().longValue() == transaction_id) {
					sum.add(transaction.getAmount());

				}
			}

		}
		return sum;
	}


}
