package fhws.healthchronicle.entities;

import javax.persistence.Entity;

@Entity
public class Symptom extends Event
{
	private static final long serialVersionUID = 1L;

	private String location;
	private Integer intensity;

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public Integer getIntensity()
	{
		return intensity;
	}

	public void setIntensity(Integer intensity)
	{
		this.intensity = intensity;
	}
}
