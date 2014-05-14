package fhws.healthchronicle.managedbeans;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fhws.healthchronicle.entities.Diagnosis;
import fhws.healthchronicle.entities.DiagnosisCollection;
import fhws.healthchronicle.entities.Entry;
import fhws.healthchronicle.entities.Story;
import fhws.healthchronicle.entities.Symptom;
import fhws.healthchronicle.entities.SymptomCollection;
import fhws.healthchronicle.utilitybeans.DateUtility;

@ManagedBean(name="diagnosisBean")
@RequestScoped
public class DiagnosisBean {
	// Injecting chronicleBean
	@ManagedProperty(value = "#{chronicleBean}")
	private ChronicleBean chronicle;
	public ChronicleBean getChronicle() {
		return chronicle;
	}
	public void setChronicle(ChronicleBean chronicle) {
		this.chronicle = chronicle;
	}
	
	private ArrayList<String> diagnosa;
	
	// Constants - INNER
	private final String PERSISTENCE_UNIT_NAME = "common-entities";
	
	//Constructor
	public DiagnosisBean(){
		resetFields();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		List<DiagnosisCollection> allRows = null;		
		Query q = em.createQuery("SELECT x FROM DiagnosisCollection x");
		allRows = (List<DiagnosisCollection>) q.getResultList();
		
		diagnosa = new ArrayList<String>();
		for(DiagnosisCollection row : allRows){
			diagnosa.add(row.getDiagnosis());
		}
	}
	
	@PostConstruct 
	public void setupVariables(){
		if(chronicle.getSelectedEntry()!=null && chronicle.getSelectedEntry().getClass().equals(Diagnosis.class)){
			diagnosisText = ((Diagnosis)chronicle.getSelectedEntry()).getDiagnosis();
			author = ((Diagnosis)chronicle.getSelectedEntry()).getAuthor();
			
			long year = ((Diagnosis)chronicle.getSelectedEntry()).getYear();
			long month = ((Diagnosis)chronicle.getSelectedEntry()).getMonth();
			long day = ((Diagnosis)chronicle.getSelectedEntry()).getDay();
			
			String datestring = DateUtility.numberToString(day)+"."+DateUtility.numberToString(month)+"."+DateUtility.numberToString(year);
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			try {
				 
				date = formatter.parse(datestring);
				System.out.println(date);
				System.out.println(formatter.format(date));
		 
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getTitle(){
		return (chronicle.getSelectedEntry()==null)?"eintragen":"bearbeiten";
	}
	
	//Variable - OUTER
	private String diagnosisText;
	public String getDiagnosisText() {
		return diagnosisText;
	}
	public void setDiagnosisText(String diagnosisText) {
		this.diagnosisText = diagnosisText;
	}
	
	//Variable - OUTER
	private String locationOfProblem;
	public String getLocationOfProblem() {
		return locationOfProblem;
	}
	public void setLocationOfProblem(String locationOfProblem) {
		this.locationOfProblem = locationOfProblem;
	}
	
	//Variable - OUTER
	private String author;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	//Variable - OUTER
	private String where;
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	
	//Variable - OUTER
	private Date date;
	public Date getDate(){
		return date;
	}
	public void setDate(Date date){
		this.date = date;
	}

	//Variable - OUTER
	private Entry selectedDiagnosis;
	public String select(Entry diagnosis){
		selectedDiagnosis=diagnosis;
		return null;
	}
	
	public void deselectDiagnosis(){
		resetFields();
		
		if(chronicle.getSelectedEntry()==null){
			chronicle.deselectEntry();
			chronicle.showOptions();
		}
		else{
			chronicle.deselectEntry();
			chronicle.showSelectedStory();
		}
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("chronicle.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Logic - INNER
	private void resetFields(){
		diagnosisText="";
		locationOfProblem="";
		author="";
		where="";
	}
	
	//Logic 
	public void save(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Diagnosis toBeSaved;
		if(chronicle.getSelectedEntry() != null){
			toBeSaved = em.merge((Diagnosis) chronicle.getSelectedEntry());
		}
		else{
			toBeSaved = new Diagnosis();
		}
				
		toBeSaved.setDiagnosis(diagnosisText);
		toBeSaved.setAuthor(author);
		toBeSaved.setBelongs_to_story(chronicle.getSelectedStory().getId());
		long day = DateUtility.calculateDay(date);
		toBeSaved.setDay(day);
		long month = DateUtility.calculateMonth(date);
		toBeSaved.setMonth(month);
		long year = DateUtility.calculateYear(date);
		toBeSaved.setYear(year);
		long hour = DateUtility.calculateHour(date);
		toBeSaved.setHour(hour);
		long minute = DateUtility.calculateMinute(date);
		toBeSaved.setMinute(minute);
		long second = DateUtility.calculateSecond(date);
		toBeSaved.setSecond(second);
		
		em.persist(toBeSaved);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		Story toBeUpdated = em.merge(chronicle.getSelectedStory());
		toBeUpdated.setTitle(diagnosisText);
		em.persist(toBeUpdated);
		em.getTransaction().commit();
		
		chronicle.getSelectedStory().setTitle(diagnosisText);
		
		chronicle.updateSelectedStory();
		
		chronicle.showSelectedStory();
		
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("chronicle.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> complete(String query) {  
	    List<String> results = new ArrayList<String>();  
	        
	    for (String item : diagnosa) {  
	    	if(item.contains(query)){
	    		results.add(item);
	    	}
	    } 	          
	    return results;  
	 }

}
