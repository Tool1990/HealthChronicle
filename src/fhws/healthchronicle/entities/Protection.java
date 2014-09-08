package fhws.healthchronicle.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Protection extends Event
{
	private static final long serialVersionUID = 1L;
	
	private String protectionText;
	private Float quantity;

	@Enumerated(EnumType.STRING)
	private QuantityUnit quantityUnit = QuantityUnit.GRAM;

	public enum QuantityUnit
	{
		GRAM("quantity_unit_gram"), TIMES("quantity_unit_times"), PIECES("quantity_unit_pieces");

		private String label;

		private QuantityUnit(String label)
		{
			this.label = label;
		}

		public String getLabel()
		{
			return label;
		}
	}
	
	public QuantityUnit[] getQuantityUnits()
	{
		return QuantityUnit.values();
	}

	@Enumerated(EnumType.STRING)
	private IntervalUnit intervalUnit = IntervalUnit.HOURS;

	public enum IntervalUnit
	{
		HOURS("interval_unit_hours"), DAYS("interval_unit_days"), WEEKS("interval_unit_weeks");

		private String label;

		private IntervalUnit(String label)
		{
			this.label = label;
		}

		public String getLabel()
		{
			return label;
		}
	}
	
	public IntervalUnit[] getIntervalUnits()
	{
		return IntervalUnit.values();
	}

	private Integer period;

	@Enumerated(EnumType.STRING)
	private PeriodUnit periodUnit = PeriodUnit.DAYS;

	public enum PeriodUnit
	{
		DAYS("period_unit_days"), WEEKS("period_unit_weeks"), MONTHS("period_unit_months"), YEARS("period_unit_years");

		private String label;

		private PeriodUnit(String label)
		{
			this.label = label;
		}

		public String getLabel()
		{
			return label;
		}
	}
	
	public PeriodUnit[] getPeriodUnits()
	{
		return PeriodUnit.values();
	}

	public Float getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Float quantity)
	{
		this.quantity = quantity;
	}

	public QuantityUnit getQuantityUnit()
	{
		return quantityUnit;
	}

	public void setQuantityUnit(QuantityUnit quantityUnit)
	{
		this.quantityUnit = quantityUnit;
	}

	public IntervalUnit getIntervalUnit()
	{
		return intervalUnit;
	}

	public void setIntervalUnit(IntervalUnit intervalUnit)
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

	public PeriodUnit getPeriodUnit()
	{
		return periodUnit;
	}

	public void setPeriodUnit(PeriodUnit periodUnit)
	{
		this.periodUnit = periodUnit;
	}

	public String getProtectionText()
	{
		return protectionText;
	}

	public void setProtectionText(String protectionText)
	{
		this.protectionText = protectionText;
	}
}
