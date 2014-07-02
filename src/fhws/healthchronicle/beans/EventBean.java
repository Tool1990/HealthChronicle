package fhws.healthchronicle.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import fhws.healthchronicle.entities.Diagnosis;
import fhws.healthchronicle.entities.Event;
import fhws.healthchronicle.entities.Protection;
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

	public String eventText(Event e)
	{
		switch (e.getType())
		{
			case "symptom":
				symptomEvent = (Symptom) e;
				return symptomEvent.getDescription() + ": " + getPainText(symptomEvent.getIntensity());
			case "diagnosis":
				diagnosisEvent = (Diagnosis) e;
				return diagnosisEvent.getDescription() + " wurde  von " + diagnosisEvent.getDoctor() + " diagnostiziert";
			case "protection":
				protectionEvent = (Protection) e;
				return protectionEvent.getDescription() + ": " + protectionEvent.getQuantity() + " " + getQuantityUnitText(protectionEvent.getQuantityUnit()) + " " + getIntervalUnitText(protectionEvent.getIntervalUnit()) + " für " + protectionEvent.getPeriod() + " " + getPeriodUnitText(protectionEvent.getPeriodUnit());
			default:
				throw new IllegalArgumentException();
		}
	}

	public String getPeriodUnitText(String unit)
	{
		switch (unit)
		{
			case "days":
				return "Tage";
			case "weeks":
				return "Wochen";
			case "month":
				return "Monate";
			case "years":
				return "Jahre";
			default:
				throw new IllegalArgumentException();
		}
	}

	public String getQuantityUnitText(String unit)
	{
		switch (unit)
		{
			case "gram":
				return "Gramm";
			case "times":
				return "mal";
			case "pieces":
				return "Stück";
			default:
				throw new IllegalArgumentException();
		}
	}

	public String getIntervalUnitText(String unit)
	{
		switch (unit)
		{
			case "hours":
				return "stündlich";
			case "days":
				return "täglich";
			case "weeks":
				return "wöchentlich";
			default:
				throw new IllegalArgumentException();
		}
	}

	public String getPainText(Integer intensity)
	{
		switch (intensity)
		{
			case 0:
				return "Kein Schmerz";
			case 2:
				return "Schmerz kaum spührbar";
			case 3:
				return "Unangenehmer Schmerz";
			case 4:
				return "Intensiver Schmerz";
			case 5:
				return "Unerträglicher Schmerz";
			default:
				throw new IllegalArgumentException();
		}
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
