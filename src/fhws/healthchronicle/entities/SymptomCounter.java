package fhws.healthchronicle.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SymptomCounter implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String symptomText;
	private Integer counter;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSymptomText()
	{
		return symptomText;
	}

	public void setSymptomText(String symptomText)
	{
		this.symptomText = symptomText;
	}

	public Integer getCounter()
	{
		return counter;
	}

	public void setCounter(Integer counter)
	{
		this.counter = counter;
	}
}
