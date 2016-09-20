package org.holgersson.Transaction_Management.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.holgersson.Transaction_Management.model.Status;
import org.holgersson.Transaction_Management.model.Sum;
import org.holgersson.Transaction_Management.model.Transaction;
import org.holgersson.Transaction_Management.model.TransactionDao;

/**
 * @author Anton
 * 
 * 
 *         Handles incoming http requests for transactions and matches them with
 *         correct method.
 */

@Path("/TransactionService")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionService {
	TransactionDao transactionDao = new TransactionDao();

	@GET
	public List<Transaction> getTransactions() {

		return transactionDao.getAllTransactions();
	}

	/**
	 * Retrieves a transaction with given transaction id.
	 * 
	 * @param transaction_id
	 *            Transaction id for transaction to retrieve.
	 * @return A transaction object or null if not found.
	 */
	@GET
	@Path("/transaction/{transaction_id}")

	public Response getTransaction(@PathParam("transaction_id") long transaction_id, @Context UriInfo uriInfo) {
		Transaction transaction = transactionDao.getTransaction(transaction_id);
		transaction.addLink(getUriForSelf(uriInfo, transaction), "self");
		return Response.ok(transaction).build();
	}

	/**
	 * Retrieves a list of transaction identifiers with the same type given by
	 * input.
	 * 
	 * @param type
	 *            A string containing the type of transactions to be returned.
	 * @return A json list of all transaction ids that share the same type.
	 */
	@GET
	@Path("/types/{type}")
	public Response getTypes(@PathParam("type") String type) {
		List<Long> result = transactionDao.getTypes(type);
		// GenericEntity<List<String>>entity=new
		// GenericEntity<List<String>>(result){};
		return Response.ok(result).build();

	}

	/**
	 * Adds a new transaction.
	 * 
	 * @param transaction_id
	 *            Transaction identifier to use for the new transaction.
	 * @param transactionBody
	 *            Transaction object containing fields with values: amount,
	 *            type, parent id.
	 * @return Response object containing entity and reponse code.
	 * @throws IOException
	 */
	@PUT
	@Path("/transaction/{transaction_id}")

	public Response addTransaction(@PathParam("transaction_id") long transaction_id, Transaction transactionBody,@Context UriInfo uriInfo)
			throws IOException {
		transactionBody.setTransaction_id(transaction_id);

		Response response = transactionDao.addTransaction(transactionBody,uriInfo);
		
		return response;
	}

	/**
	 * A sum of all transactions that are transitively linked by their parent_id
	 * to $transaction_id.
	 * 
	 * @param transaction_id
	 *            Transaction id to use for search.
	 * @return A sum object containing sum of the matching transactions.
	 */
	@GET
	@Path("/sum/{transaction_id}")

	public Response getSum(@PathParam("transaction_id") long transaction_id) {

		Sum sum = transactionDao.getSum(transaction_id);

		return Response.ok(sum).build();
	}

	private String getUriForSelf(UriInfo uriInfo, Transaction transaction) {
		String uri = uriInfo.getBaseUriBuilder().path(TransactionService.class).path("/transaction")
				.path(Long.toString(transaction.getTransactionId())).build().toString();
		return uri;
	}

}
