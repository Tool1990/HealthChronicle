package fhws.healthchronicle.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role
{
	@Id
	@GeneratedValue
	private int id;
	private String rolename;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getRolename()
	{
		return rolename;
	}
	public void setRolename(String rolename)
	{
		this.rolename = rolename;
	}
}
