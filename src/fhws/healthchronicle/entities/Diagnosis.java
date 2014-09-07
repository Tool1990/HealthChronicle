package fhws.healthchronicle.entities;

import javax.persistence.Entity;

@Entity
public class Diagnosis extends Event
{
	private static final long serialVersionUID = 1L;

	private String classification;
	private String diagnosisText;
	
	private String doctor;

	public String getDoctor()
	{
		return doctor;
	}

	public void setDoctor(String doctor)
	{
		this.doctor = doctor;
	}

	public String getClassification()
	{
		return classification;
	}

	public void setClassification(String classification)
	{
		this.classification = classification;
	}

	public String getDiagnosisText()
	{
		return diagnosisText;
	}

	public void setDiagnosisText(String diagnosisText)
	{
		this.diagnosisText = diagnosisText;
	}
}
