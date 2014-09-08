package fhws.healthchronicle.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Symptom extends Event
{
	private static final long serialVersionUID = 1L;
	
	private String symptomText;

	@Enumerated(EnumType.ORDINAL)
	private Intensity intensity = Intensity.INTENSITY_0;

	public enum Intensity
	{
		INTENSITY_0("intensity_0"), INTENSITY_1("intensity_1"), INTENSITY_2("intensity_2"), INTENSITY_3("intensity_3"), INTENSITY_4("intensity_4"), INTENSITY_5("intensity_5");

		private String label;

		private Intensity(String label)
		{
			this.label = label;
		}

		public String getLabel()
		{
			return label;
		}
	}

	public Intensity[] getIntensities()
	{
		return Intensity.values();
	}

	public Intensity getIntensity()
	{
		return intensity;
	}

	public void setIntensity(Intensity intensity)
	{
		this.intensity = intensity;
	}
	
	public String getSymptomText()
	{
		return symptomText;
	}

	public void setSymptomText(String symptomText)
	{
		this.symptomText = symptomText;
	}
}
