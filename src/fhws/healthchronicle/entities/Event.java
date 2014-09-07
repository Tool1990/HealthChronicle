package fhws.healthchronicle.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "getEvents", query = "SELECT e FROM Event e WHERE e.story.id = :storyId")
public class Event implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private EventType type = EventType.SYMPTOM;

	public enum EventType
	{
		SYMPTOM("symptom"), DIAGNOSIS("diagnosis"), PROTECTION("protection");

		private String label;

		private EventType(String label)
		{
			this.label = label;
		}

		public String getLabel()
		{
			return label;
		}
	};

	public EventType[] getEventTypes()
	{
		return EventType.values();
	}

	private Date date = new Date();

	@ManyToOne
	private Story story;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public EventType getType()
	{
		return type;
	}

	public void setType(EventType type)
	{
		this.type = type;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Story getStory()
	{
		return story;
	}

	public void setStory(Story story)
	{
		this.story = story;
	}
}
