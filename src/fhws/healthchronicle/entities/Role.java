package fhws.healthchronicle.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String rolename;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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
