package fhws.healthchronicle.entities;

import javax.persistence.Entity;

@Entity
public class Symptom extends Entry{

	//What symptom
	private String term;
	public String getTerm(){
		return term;
	}
	public void setTerm(String term){
		this.term = term;
	}
	
	//Variable - OUTER
	private int intensity;
	public int getIntensity(){
		return intensity;
	}
	public void setIntensity(int intensity){
		this.intensity = intensity;
	}

}
