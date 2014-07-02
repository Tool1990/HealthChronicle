package fhws.healthchronicle.entities;

import javax.persistence.Entity;

@Entity
public class Symptom extends Event
{
	private static final long serialVersionUID = 1L;

	private Integer intensity;

	public Integer getIntensity()
	{
		return intensity;
	}

	public void setIntensity(Integer intensity)
	{
		this.intensity = intensity;
	}
}
