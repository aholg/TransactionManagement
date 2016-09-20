package org.holgersson.Transaction_Management.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Status {
	@XmlElement
	private String status;

	public Status() {

	}

	public Status(String status) {
		this.status=status;
	}

	public String getStatus(){
		return this.status;
	}

}
