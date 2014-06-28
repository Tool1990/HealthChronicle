package fhws.healthchronicle.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "getStories", query = "SELECT s FROM Story s WHERE s.platformUser.id = :userId")
public class Story implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String title;

	@ManyToOne
	private PlatformUser platformUser;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "story")
	private List<Event> events = new ArrayList<Event>();

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

	public List<Event> getEvents()
	{
		return events;
	}

	public void setEvents(List<Event> events)
	{
		this.events = events;
	}
	
	public void addEvent(Event event)
	{
		this.events.add(event);
	}
}
