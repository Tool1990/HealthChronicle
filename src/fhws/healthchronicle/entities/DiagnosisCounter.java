package fhws.healthchronicle.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class DiagnosisCounter implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String diagnosisText;
	
	@OneToOne
	private SymptomCounter symptomCounter;
	private Integer counter;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDiagnosisText()
	{
		return diagnosisText;
	}

	public void setDiagnosisText(String diagnosisText)
	{
		this.diagnosisText = diagnosisText;
	}

	public Integer getCounter()
	{
		return counter;
	}

	public void setCounter(Integer counter)
	{
		this.counter = counter;
	}

	public SymptomCounter getSymptomCounter()
	{
		return symptomCounter;
	}

	public void setSymptomCounter(SymptomCounter symptomCounter)
	{
		this.symptomCounter = symptomCounter;
	}
}
