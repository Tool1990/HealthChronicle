package fhws.healthchronicle.entities;

import javax.persistence.Entity;

@Entity
public class Protection extends Event
{
	private static final long serialVersionUID = 1L;

	private Float quantity;
	private String quantityUnit;
	private Integer interval;
	private String intervalUnit;
	private Integer period;
	private String periodUnit;

	public Float getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Float quantity)
	{
		this.quantity = quantity;
	}

	public String getQuantityUnit()
	{
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit)
	{
		this.quantityUnit = quantityUnit;
	}

	public Integer getInterval()
	{
		return interval;
	}

	public void setInterval(Integer interval)
	{
		this.interval = interval;
	}

	public String getIntervalUnit()
	{
		return intervalUnit;
	}

	public void setIntervalUnit(String intervalUnit)
	{
		this.intervalUnit = intervalUnit;
	}

	public Integer getPeriod()
	{
		return period;
	}

	public void setPeriod(Integer period)
	{
		this.period = period;
	}

	public String getPeriodUnit()
	{
		return periodUnit;
	}

	public void setPeriodUnit(String periodUnit)
	{
		this.periodUnit = periodUnit;
	}
}
