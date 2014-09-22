package fhws.healthchronicle.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import fhws.healthchronicle.entities.DiagnosisCounter;
import fhws.healthchronicle.entities.Event;
import fhws.healthchronicle.entities.Symptom;
import fhws.healthchronicle.helper.SymptomDiagnosisStatistic;

@ManagedBean
@RequestScoped
public class StatisticBean
{
	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean session;

	private SymptomDiagnosisStatistic symptomDiagnosisStatistic;

	public void calculateSymptomDiagnosisStatistic()
	{
		symptomDiagnosisStatistic = new SymptomDiagnosisStatistic();

		Event firstEvent = session.getActiveStory().getEvents().get(0);
		
		if (firstEvent.getType() != Event.EventType.SYMPTOM)
		{
			symptomDiagnosisStatistic = null;
			return;
		}
			
		Symptom firstSymptom = (Symptom) firstEvent;
		
		String firstsymptomText = firstSymptom.getSymptomText();

		String sql = "SELECT dc FROM DiagnosisCounter dc WHERE dc.symptomCounter.symptomText = :symptomText ORDER BY dc.counter DESC";
		// "SELECT d.diagnosisText, COUNT(d.diagnosisText) FROM Diagnosis d WHERE d.story.id IN (SELECT s.story.id FROM Symptom s WHERE LOWER(symptomtext) LIKE LOWER(:symptomText)) GROUP BY d.diagnosisText ORDER BY COUNT(d.diagnosisText) DESC"

		EntityManager em = session.getEm();
		Query query = em.createQuery(sql);
		query.setParameter("symptomText", firstsymptomText);

		List<DiagnosisCounter> diagnosisCounterList = query.getResultList();

		if (diagnosisCounterList.size() > 0)
		{
			DiagnosisCounter diagnosisCounter = (DiagnosisCounter) diagnosisCounterList.get(0);

			symptomDiagnosisStatistic.setSymptomText(diagnosisCounter.getSymptomCounter().getSymptomText());
			symptomDiagnosisStatistic.setDiagnosisText(diagnosisCounter.getDiagnosisText());
			symptomDiagnosisStatistic.setPercentage(diagnosisCounter.getCounter() * 100 / diagnosisCounter.getSymptomCounter().getCounter());
		}
	}

	public SessionBean getSession()
	{
		return session;
	}

	public SymptomDiagnosisStatistic getSymptomDiagnosisStatistic()
	{
		return symptomDiagnosisStatistic;
	}

	public void setSymptomDiagnosisStatistic(SymptomDiagnosisStatistic symptomDiagnosisStatistic)
	{
		this.symptomDiagnosisStatistic = symptomDiagnosisStatistic;
	}

	public void setSession(SessionBean session)
	{
		this.session = session;
	}
}