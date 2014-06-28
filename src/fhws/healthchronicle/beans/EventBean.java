package fhws.healthchronicle.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

import fhws.healthchronicle.entities.Diagnosis;
import fhws.healthchronicle.entities.Event;
import fhws.healthchronicle.entities.Protection;
import fhws.healthchronicle.entities.Symptom;

@RequestScoped
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
		System.out.println("new Event");
		event = new Event();
		symptomEvent = new Symptom();
		diagnosisEvent = new Diagnosis();
		protectionEvent = new Protection();
	}

	public String createEvent()
	{
		System.out.println("createEvent()");

		if (!session.isLoggedIn())
		{
			System.out.println("Not logged in");
			return "index";
		}

		event = formEvent();

		EntityManager em = session.getEm();
		em.getTransaction().begin();
		em.persist(event);
		em.getTransaction().commit();

		session.getActiveStory().addEvent(event);

		return "show-events?faces-redirect=true";
	}

	public Event formEvent()
	{

		switch (event.getType())
		{
			case "symptom":
				symptomEvent.setStory(session.getActiveStory());
				symptomEvent.setDescription(event.getDescription());
				symptomEvent.setDate(event.getDate());
				symptomEvent.setType(event.getType());
				return symptomEvent;
			case "diagnosis":
				diagnosisEvent.setStory(session.getActiveStory());
				diagnosisEvent.setDescription(event.getDescription());
				diagnosisEvent.setDate(event.getDate());
				diagnosisEvent.setType(event.getType());
				System.out.println(diagnosisEvent.getDoctor());
				return diagnosisEvent;
			case "protection":
				protectionEvent.setStory(session.getActiveStory());
				protectionEvent.setDescription(event.getDescription());
				protectionEvent.setDate(event.getDate());
				protectionEvent.setType(event.getType());
				return protectionEvent;
			default:
				return event;
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
