package fhws.healthchronicle.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import fhws.healthchronicle.entities.Diagnosis;
import fhws.healthchronicle.entities.DiagnosisCounter;
import fhws.healthchronicle.entities.Event;
import fhws.healthchronicle.entities.Protection;
import fhws.healthchronicle.entities.Story;
import fhws.healthchronicle.entities.Symptom;
import fhws.healthchronicle.entities.SymptomCounter;

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
			story.setTitle(symptomEvent.getSymptomText());
			story.setCured(false);
			story.setPlatformUser(session.getPlatformUser());

			persistObject(story);

			session.setActiveStory(story);
			
			System.out.println("New story created");
		}

		event = formEvent();
		persistObject(event);
		incrementCounter();

		session.getActiveStory().addEvent(event);

		return "show-events?faces-redirect=true";
	}
	
	public Event formEvent()
	{
		switch (event.getType())
		{
			case SYMPTOM:
				symptomEvent.setStory(session.getActiveStory());
				symptomEvent.setDate(event.getDate());
				symptomEvent.setType(event.getType());
				return symptomEvent;
			case DIAGNOSIS:
				diagnosisEvent.setStory(session.getActiveStory());
				diagnosisEvent.setDate(event.getDate());
				diagnosisEvent.setType(event.getType());
				return diagnosisEvent;
			case PROTECTION:
				protectionEvent.setStory(session.getActiveStory());
				protectionEvent.setDate(event.getDate());
				protectionEvent.setType(event.getType());
				return protectionEvent;
			default:
				return event;
		}
	}

	public void persistObject(Object o)
	{
		EntityManager em = session.getEm();
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
	}

	public void incrementCounter()
	{
		switch (event.getType())
		{
			case SYMPTOM:
				incrementSymptomCounter();				
				return;

			case DIAGNOSIS:
				incrementDiagnosisCounter();				
				return;

			case PROTECTION:

				return;

			default:
				return;
		}
	}
	
	public void incrementSymptomCounter()
	{
		EntityManager em = session.getEm();
		
		String sql = "SELECT sc FROM SymptomCounter sc WHERE sc.symptomText = :symptomText";
		Query query = em.createQuery(sql);
		query.setParameter("symptomText", symptomEvent.getSymptomText());
		SymptomCounter symptomCounter;
		
		if (query.getResultList().size() > 0)
		{
			Long id = ((SymptomCounter) query.getResultList().get(0)).getId();
			symptomCounter = em.find(SymptomCounter.class, id);
			
			em.getTransaction().begin();
			symptomCounter.setCounter(symptomCounter.getCounter()+1);
			em.getTransaction().commit();
		}
		else
		{
			symptomCounter = new SymptomCounter();
			symptomCounter.setSymptomText(symptomEvent.getSymptomText());
			symptomCounter.setCounter(1);
			
			persistObject(symptomCounter);
		}
	}
	
	public void incrementDiagnosisCounter()
	{
		EntityManager em = session.getEm();
		Symptom firstSymptom = (Symptom) session.getActiveStory().getEvents().get(0);
		
		String DiagnosisCounterSql = "SELECT dc FROM DiagnosisCounter dc WHERE dc.diagnosisText = :diagnosisText AND dc.symptomCounter.symptomText = :symptomText";
		Query diagnosisCounterQuery = em.createQuery(DiagnosisCounterSql);
		diagnosisCounterQuery.setParameter("diagnosisText", diagnosisEvent.getDiagnosisText());
		diagnosisCounterQuery.setParameter("symptomText", firstSymptom.getSymptomText());
		List<DiagnosisCounter>diagnosisCounterList = diagnosisCounterQuery.getResultList();
		DiagnosisCounter diagnosisCounter;
		
		String SymptomCounterSql = "SELECT sc FROM SymptomCounter sc WHERE sc.symptomText = :symptomText";
		Query SymptomCounterQuery = em.createQuery(SymptomCounterSql);
		SymptomCounterQuery.setParameter("symptomText", firstSymptom.getSymptomText());
		Long symptomCounterId = ((SymptomCounter) SymptomCounterQuery.getResultList().get(0)).getId();
		SymptomCounter symptomCounter = em.find(SymptomCounter.class, symptomCounterId);
		
		if (diagnosisCounterList.size() > 0)
		{
			Long diagnosisCounterId = diagnosisCounterList.get(0).getId();
			diagnosisCounter = em.find(DiagnosisCounter.class, diagnosisCounterId);
			
			em.getTransaction().begin();
			diagnosisCounter.setCounter(diagnosisCounter.getCounter()+1);
			em.getTransaction().commit();
		}
		else
		{
			diagnosisCounter = new DiagnosisCounter();
			diagnosisCounter.setDiagnosisText(diagnosisEvent.getDiagnosisText());
			diagnosisCounter.setCounter(1);
			diagnosisCounter.setSymptomCounter(symptomCounter);
			
			persistObject(diagnosisCounter);
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
