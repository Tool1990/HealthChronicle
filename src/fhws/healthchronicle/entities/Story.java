package fhws.healthchronicle.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Story
{
	@Id
	@GeneratedValue
	private long id;
	private String title;
	@OneToOne
	//@JoinColumn(name="platformUser", nullable=false)
	private PlatformUser platformUser;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public PlatformUser getPlatformUser()
	{
		return platformUser;
	}

	public void setPlatformUser(PlatformUser platformUser)
	{
		this.platformUser = platformUser;
	}
}
