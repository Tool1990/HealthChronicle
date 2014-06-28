package fhws.healthchronicle.entities;

import javax.persistence.Entity;

@Entity
public class Diagnosis extends Event
{
	private static final long serialVersionUID = 1L;

	private String doctor;

	public String getDoctor()
	{
		System.out.println("getdoc: " + doctor);
		return doctor;
	}

	public void setDoctor(String doctor)
	{
		System.out.println("kjkkK: " + doctor);
		this.doctor = doctor;
	}
}
