package fhws.healthchronicle.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQuery(name="getStories", query="SELECT s FROM Story s WHERE s.platformUser.id = :userId")
public class Story implements Serializable
{
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	
	//@JoinColumn(name="platformUser", nullable=false)
	@ManyToOne
	private PlatformUser platformUser;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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
