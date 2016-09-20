package org.holgersson.Transaction_Management.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sum {
	@XmlElement private double sum;
	
	public Sum(){
		
	}
	
	public Sum(double sum){
		this.sum=sum;
	}
	
	public void add(double value){
		sum+=value;
	}
	public double getSum(){
		return sum;
	}
}
