package org.holgersson.Transaction_Management.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class Transaction {



	private double amount;

	private String type;

	private Long parent_id;

	private long transactionId;
	
	private List<Link> links=new ArrayList<>();
	
	public Transaction() {
	}

/*	public Transaction(double amount, String type, long transaction_id, Long parent_id) {
		this.amount = amount;
		this.type = type;
		this.transactionId = transaction_id;
		this.parent_id = parent_id;
	}
*/
	public Transaction(double amount, String type, long transaction_id) {
		this.amount = amount;
		this.type = type;
		this.transactionId = transaction_id;
		this.parent_id = null;
	}

	@XmlTransient
	public long getTransactionId() {
		return transactionId;
	}

	
	
	public void setTransaction_id(long transaction_id) {
		this.transactionId = transaction_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getParent_id() {
		return parent_id;
	}


	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;

	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(String url, String rel){
		Link link=new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}

/*
	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", type=" + type + ", parent_id=" + parent_id + "]";
	}
*/
}
