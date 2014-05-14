package fhws.healthchronicle.managedbeans;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import fhws.healthchronicle.entities.Symptom;
import fhws.healthchronicle.entities.SymptomCollection;
import fhws.healthchronicle.utilitybeans.DateUtility;

@ManagedBean(name="symptomBean")
@RequestScoped
public class SymptomBean {
	// Injecting chronicleBean
	@ManagedProperty(value = "#{chronicleBean}")
	private ChronicleBean chronicle;
	public ChronicleBean getChronicle() {
		return chronicle;
	}
	public void setChronicle(ChronicleBean chronicle) {
		this.chronicle = chronicle;
	}
	
	// Constants - INNER
	private final String PERSISTENCE_UNIT_NAME = "common-entities";
	
	private ArrayList<String> symptoms;
	
	private Map<String, Integer> intensities = new HashMap<String, Integer>(); 
	public Map<String, Integer> getIntensities() {  
        return intensities;  
    }  
		
	//Constructor
	public SymptomBean(){
		resetFields();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		List<SymptomCollection> allRows = null;		
		Query q = em.createQuery("SELECT x FROM SymptomCollection x");
		allRows = (List<SymptomCollection>) q.getResultList();
		
		symptoms = new ArrayList<String>();
		for(SymptomCollection row : allRows){
			symptoms.add(row.getSymptom());
		}
		
		intensities.put("kaum merkbar", 1);
		intensities.put("merkbar", 2);
		intensities.put("mittelmäßig", 3);
		intensities.put("stark", 4);
		intensities.put("unerträglich", 5);
	}
	
	@PostConstruct 
	public void setupVariables(){
		if(chronicle.getSelectedEntry()!=null && chronicle.getSelectedEntry().getClass().equals(Symptom.class)){
			term = ((Symptom)chronicle.getSelectedEntry()).getTerm();
			intensity = ((Symptom)chronicle.getSelectedEntry()).getIntensity();
			
			long year = ((Symptom)chronicle.getSelectedEntry()).getYear();
			long month = ((Symptom)chronicle.getSelectedEntry()).getMonth();
			long day = ((Symptom)chronicle.getSelectedEntry()).getDay();
			long hour = ((Symptom)chronicle.getSelectedEntry()).getHour();
			long minute = ((Symptom)chronicle.getSelectedEntry()).getMinute();
			
			String datestring = DateUtility.numberToString(day)+"."+DateUtility.numberToString(month)+"."+DateUtility.numberToString(year)+" "+
								DateUtility.numberToString(hour)+":"+DateUtility.numberToString(minute);
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
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
	private String term;
	public String getTerm(){
		return term;
	}
	public void setTerm(String term){
		this.term=term;
	}
	
	//Variable - OUTER
	private int intensity;
	public int getIntensity(){
		return intensity;
	}
	public void setIntensity(int intensity){
		this.intensity = intensity;
	}
	
	//Variable - OUTER
	private Date date;
	public Date getDate(){
		return date;
	}
	public void setDate(Date date){
		this.date = date;
	}
	
	//Logic INNER
	private void resetFields(){
		term = "";
		intensity=0;
	}
	
	//Logic
	public void deselectSymptom(){
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
	
	//Logic 
	public void save(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Symptom toBeSaved;
		if(chronicle.getSelectedEntry() != null){
			toBeSaved = em.merge((Symptom) chronicle.getSelectedEntry());
		}
		else{
			toBeSaved = new Symptom();
		}
		
		toBeSaved.setTerm(term);
		toBeSaved.setIntensity(intensity);
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
	        
	    for (String item : symptoms) {  
	    	if(item.contains(query)){
	    		results.add(item);
	    	}
	    } 	          
	    return results;  
	 }
	 
	 public void intensityChange() {  
	     return;  
	 }
}
