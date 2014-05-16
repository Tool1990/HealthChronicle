package fhws.healthchronicle.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Story
{
	@Id
	@GeneratedValue
	private long			id;
	private String			title;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stories")
	private PlatformUser	user;

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
		return user;
	}

	public void setPlatformUser(PlatformUser user)
	{
		this.user = user;
	}
}
