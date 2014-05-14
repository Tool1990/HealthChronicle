package fhws.healthchronicle.entities;

import javax.persistence.Entity;

@Entity
public class Action extends Entry
{

	// Variable - OUTER
	private String	action;

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	// Variable - OUTER
	private int	period;

	public int getPeriod()
	{
		return period;
	}

	public void setPeriod(int period)
	{
		this.period = period;
	}

	// Variable - OUTER
	private String	amount;

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}
}
