package fhws.healthchronicle.entities;

import javax.persistence.Entity;

@Entity
public class Status extends Entry{
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
