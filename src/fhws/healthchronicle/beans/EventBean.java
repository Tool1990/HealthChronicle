package fhws.healthchronicle.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import fhws.healthchronicle.entities.Diagnosis;
import fhws.healthchronicle.entities.Event;
import fhws.healthchronicle.entities.Protection;
import fhws.healthchronicle.entities.Story;
import fhws.healthchronicle.entities.Symptom;

@ViewScoped
@ManagedBean
public class EventBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean session;

	private Event event;
	private Diagnosis diagnosisEvent;
	private Symptom symptomEvent;
	private Protection protectionEvent;

	public EventBean()
	{
		event = new Event();
		symptomEvent = new Symptom();
		diagnosisEvent = new Diagnosis();
		protectionEvent = new Protection();
	}

	public String createEvent()
	{
		if (!session.isLoggedIn())
		{
			System.out.println("Not logged in");
			return "index";
		}

		if (session.getActiveStory() == null)
		{
			Story story = new Story();
			story.setTitle(event.getDescription());
			story.setPlatformUser(session.getPlatformUser());

			EntityManager em = session.getEm();
			em.getTransaction().begin();
			em.persist(story);
			em.getTransaction().commit();

			session.setActiveStory(story);
			session.getPlatformUser().getStories().add(story);
			System.out.println("New story created");
		}

		event = formEvent();

		EntityManager em = session.getEm();
		em.getTransaction().begin();
		em.persist(event);
		em.getTransaction().commit();

		session.getActiveStory().addEvent(event);

		return "show-events?faces-redirect=true";
	}

	public Symptom castToSymptom(Event e)
	{
		return (Symptom) e;
	}

	public Diagnosis castToDiagnosis(Event e)
	{
		return (Diagnosis) e;
	}

	public Protection castToProtection(Event e)
	{
		return (Protection) e;
	}

	public Event formEvent()
	{
		switch (event.getType())
		{
			case SYMPTOM:
				symptomEvent.setStory(session.getActiveStory());
				symptomEvent.setDescription(event.getDescription());
				symptomEvent.setDate(event.getDate());
				symptomEvent.setType(event.getType());
				return symptomEvent;
			case DIAGNOSIS:
				diagnosisEvent.setStory(session.getActiveStory());
				diagnosisEvent.setDescription(event.getDescription());
				diagnosisEvent.setDate(event.getDate());
				diagnosisEvent.setType(event.getType());
				return diagnosisEvent;
			case PROTECTION:
				protectionEvent.setStory(session.getActiveStory());
				protectionEvent.setDescription(event.getDescription());
				protectionEvent.setDate(event.getDate());
				protectionEvent.setType(event.getType());
				return protectionEvent;
			default:
				return event;
		}
	}

	public String deleteEvent(Long eventId)
	{
		System.out.println("deleteEvent()");

		if (!session.isLoggedIn())
		{
			System.out.println("Not logged in");
			return "index?faces-redirect=true";
		}

		EntityManager em = session.getEm();

		try
		{
			em.getTransaction().begin();
			Event e = em.find(Event.class, eventId);
			em.remove(e);
			em.getTransaction().commit();

			deleteEventById(eventId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return "show-events?faces-redirect=true";
	}

	public void deleteEventById(Long eventId)
	{
		int index = 0;

		for (Event e : session.getActiveStory().getEvents())
		{
			if (e.getId() == eventId)
			{
				session.getActiveStory().getEvents().remove(index);
				return;
			}

			index++;
		}
	}

	public Symptom getSymptomEvent()
	{
		return symptomEvent;
	}

	public void setSymptomEvent(Symptom symptomEvent)
	{
		this.symptomEvent = symptomEvent;
	}

	public Diagnosis getDiagnosisEvent()
	{
		return diagnosisEvent;
	}

	public void setDiagnosisEvent(Diagnosis diagnosisEvent)
	{
		this.diagnosisEvent = diagnosisEvent;
	}

	public Protection getProtectionEvent()
	{
		return protectionEvent;
	}

	public void setProtectionEvent(Protection protectionEvent)
	{
		this.protectionEvent = protectionEvent;
	}

	public SessionBean getSession()
	{
		return session;
	}

	public void setSession(SessionBean session)
	{
		this.session = session;
	}

	public Event getEvent()
	{
		return event;
	}

	public void setEvent(Event event)
	{
		this.event = event;
	}
}
