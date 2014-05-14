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

import fhws.healthchronicle.entities.Action;
import fhws.healthchronicle.entities.ActionCollection;
import fhws.healthchronicle.entities.Diagnosis;
import fhws.healthchronicle.entities.Story;
import fhws.healthchronicle.entities.Symptom;
import fhws.healthchronicle.utilitybeans.DateUtility;

@ManagedBean(name="actionBean")
@RequestScoped
public class ActionBean {
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
	
	private ArrayList<String> actions;
	
	private Map<String, Integer> periods = new HashMap<String, Integer>(); 
	public Map<String, Integer> getPeriods() {  
        return periods;  
    }  
	
	//Constructor
	public ActionBean(){
		resetFields();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		List<ActionCollection> allRows = null;		
		Query q = em.createQuery("SELECT x FROM ActionCollection x");
		allRows = (List<ActionCollection>) q.getResultList();
		
		actions = new ArrayList<String>();
		for(ActionCollection row : allRows){
			actions.add(row.getAction());
		}
		
		periods.put("einmalig", 1);
		periods.put("täglich", 2);
		periods.put("wöchentlich", 3);
		periods.put("monatlich", 4);
	}
	
	@PostConstruct 
	public void setupVariables(){
		if(chronicle.getSelectedEntry()!=null && chronicle.getSelectedEntry().getClass().equals(Action.class)){
			action = ((Action)chronicle.getSelectedEntry()).getAction();
			period = ((Action)chronicle.getSelectedEntry()).getPeriod();
			amount = ((Action)chronicle.getSelectedEntry()).getAmount();
			
			long year = ((Action)chronicle.getSelectedEntry()).getYear();
			long month = ((Action)chronicle.getSelectedEntry()).getMonth();
			long day = ((Action)chronicle.getSelectedEntry()).getDay();
			long hour = ((Action)chronicle.getSelectedEntry()).getHour();
			long minute = ((Action)chronicle.getSelectedEntry()).getMinute();
			
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
	private Date date;
	public Date getDate(){
		return date;
	}
	public void setDate(Date date){
		this.date = date;
	}
	
	//Variable - OUTER
	private String action;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	//Variable - OUTER
	private int period;
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	
	//Variable - OUTER
	private String amount;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	//Logic inner
	private void resetFields(){
		action="";
		period=1;
		amount="";
	}
	
	//Logic
	public void deselectAction(){
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
		Action toBeSaved;
		if(chronicle.getSelectedEntry() != null){
			toBeSaved = em.merge((Action) chronicle.getSelectedEntry());
		}
		else{
			toBeSaved = new Action();
		}
				
		toBeSaved.setAction(action);
		toBeSaved.setPeriod(period);
		toBeSaved.setAmount(amount);
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
	
	public void periodChange() {  
	     return;  
	 }
	
	public List<String> complete(String query) {  
	    List<String> results = new ArrayList<String>();  
	        
	    for (String item : actions) {  
	    	if(item.contains(query)){
	    		results.add(item);
	    	}
	    } 	          
	    return results;  
	}
	
}
