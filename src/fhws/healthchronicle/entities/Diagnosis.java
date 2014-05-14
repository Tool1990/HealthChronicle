package fhws.healthchronicle.entities;

import javax.persistence.Entity;

@Entity
public class Diagnosis extends Entry{

	//Variable - Diagnosistext
	private String diagnosis;
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	//Variable - optional location
	private String loc;
	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	//Variable - who did diagnosis
	private String author;
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	//Variable - where
	private String where_was_it_done;
	public String getWhere_was_it_done() {
		return where_was_it_done;
	}

	public void setWhere_was_it_done(String where_was_it_done) {
		this.where_was_it_done = where_was_it_done;
	}
	
}
