package fhws.healthchronicle.managedbeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.primefaces.event.CloseEvent;

import fhws.healthchronicle.entities.Action;
import fhws.healthchronicle.entities.Diagnosis;
import fhws.healthchronicle.entities.Entry;
import fhws.healthchronicle.entities.Status;
import fhws.healthchronicle.entities.Story;
import fhws.healthchronicle.entities.Symptom;
import fhws.healthchronicle.utilitybeans.DateUtility;

@ManagedBean(name = "chronicleBean")
@SessionScoped
public class ChronicleBean {
	// Injecting sessionBean
	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean session;
	public SessionBean getSession() {
		return session;
	}
	public void setSession(SessionBean session) {
		this.session = session;
	}
	
	// Constants - INNER
	private final String PERSISTENCE_UNIT_NAME = "common-entities";
	
	private int toggle=1;


	// Constructor
	public ChronicleBean() {
		resetVisibility();
		listOfStoriesVisible=true;
	}

	//Logic
	public void createStory(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Story toBeCreated = new Story();
			
			String title = "Diagnose unbekannt...";
			toBeCreated.setTitle(title);
			
			String state = "running";
			toBeCreated.setState(state);		
		
			String story_teller = session.getCanSeeChronicleOf();
			toBeCreated.setStory_teller(story_teller);
		
			long year = DateUtility.calculateYear();		
			toBeCreated.setF_year(year);
			toBeCreated.setT_year(year);
			
			long month = DateUtility.calculateMonth();		
			toBeCreated.setF_month(month);
			toBeCreated.setT_month(month);
			long day = DateUtility.calculateDay();		
			toBeCreated.setF_day(day);
			toBeCreated.setT_day(day);
			long hour = DateUtility.calculateHour();		
			toBeCreated.setF_hour(hour);
			toBeCreated.setT_hour(hour);
			
			long minute = DateUtility.calculateMinute();		
			toBeCreated.setF_minute(minute);
			toBeCreated.setT_minute(minute);
			
			long second = DateUtility.calculateSecond();		
			toBeCreated.setF_second(second);
			toBeCreated.setT_second(second);
		
		em.persist(toBeCreated);
		em.getTransaction().commit();
		select(toBeCreated);
		addOnStoryCreateMessage("Geschichte erstellt");
	}
	

	
	//Variable - OUTER
	public List<Story> getAllStories(){
		List<Story> allStories = new ArrayList<Story>();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		Query q1 = em.createQuery("SELECT x FROM Story x WHERE x.story_teller='"+session.getUsersName()+"' and x.state='running'");
		List<Story> runningStories = (List<Story>) q1.getResultList();
		Collections.sort(runningStories);
		allStories.addAll(runningStories);
		
		Query q2 = em.createQuery("SELECT x FROM Story x WHERE x.story_teller='"+session.getUsersName()+"' and x.state='closed'");
		List<Story> closedStories = (List<Story>) q2.getResultList();
		Collections.sort(closedStories);
		allStories.addAll(closedStories);
		
		return allStories;
	}
	
	//Logic
	public String renderDateFrom(Story story){
		long f_y=story.getF_year();
		long f_m=story.getF_month();
		long f_d=story.getF_day();
		long t_y=story.getT_year();
		long t_m=story.getT_month();
		long t_d=story.getT_day();		
		
		String result_last = numberToString(t_d)+ "." + numberToString(t_m) + "." + numberToString(t_y);
		
		String result_first = null;
		
		if(f_y == t_y){
			if(f_m == t_m){
				if(f_d == t_d){
					
				}
				else{
					result_first=numberToString(f_d)+".";
				}
			}
			else{
				result_first=numberToString(f_d)+"."+numberToString(f_m)+".";
			}
		}
		else{
			result_first=numberToString(f_d)+"."+numberToString(f_m)+"."+numberToString(f_y);
		}
		
		if(result_first == null){
			return result_last;
		}
		else{
			return result_first + " - " + result_last;
		}
	}

	//Logic
	public String renderDateFromSelectedStory(){
		return (selectedStory == null)? "No Date":renderDateFrom(selectedStory);
	}
	//Logic
	public String renderTitleFrom(Story story){
		String title = story.getTitle();
		return title;
	}
	//Logic
	public String renderTitleFromSelectedStory(){
		return (selectedStory == null)? "No Title":renderTitleFrom(selectedStory);
	}
	
	//Logic - INNER
	public String numberToString(long number){
		String string="";
		if(number<10){
			string="0";
		}
		string= string + String.valueOf(number);
		return string;
	}
	
	//Variable - OUTER
	private Story selectedStory;
	public Story getSelectedStory(){
		return (selectedStory!=null)?selectedStory:new Story();
	}
	public String select(Story story){
		selectedStory=story;
		showSelectedStory();
		System.out.println("Story "+story+" selected: status="+story.getState());
		return null;
	}
	public String deselectStory(){
		selectedStory=null;
		showListOfStories();
		System.out.println("Story deselected");
		return null;
	}
	
	//Variable - OUTER
	public boolean isStorySelected(){
		return (selectedStory!=null)?true:false;
	}
	
	//Logic - INNER
	public void resetVisibility(){
		listOfStoriesVisible=false;
		selectedStoryVisible=false;
		optionsVisible=false;
		diagnosisFormVisible=false;
		symptomFormVisible=false;
		actionFormVisible=false;
	}
	
	//Variable - OUTER
	private boolean actionFormVisible;
	public boolean isActionFormVisible(){
		return actionFormVisible;
	}
	public String showActionForm(){
		resetVisibility();
		actionFormVisible=true;
		return null;
	}
	
	//Variable - OUTER
	private boolean symptomFormVisible;
	public boolean isSymptomFormVisible(){
		return symptomFormVisible;
	}
	public String showSymptomForm(){
		resetVisibility();
		symptomFormVisible=true;
		return null;
	}
	
	//Variable - OUTER
	private boolean diagnosisFormVisible;
	public boolean isDiagnosisFormVisible(){
		return diagnosisFormVisible;
	}
	public String showDiagnosisForm(){
		resetVisibility();
		diagnosisFormVisible=true;
		return null;
	}
	
	//Variable - OUTER
	private boolean optionsVisible;
	public boolean isOptionsVisible(){
		return optionsVisible;
	}
	public void showOptions(){
		resetVisibility();
		optionsVisible=true;
	}
	
	//Variable - OUTER
	private boolean listOfStoriesVisible;
	public boolean isListOfStoriesVisible(){
		return listOfStoriesVisible;
	}
	public String showListOfStories(){
		resetVisibility();
		listOfStoriesVisible=true;
		return null;
	}
	
	//Variable - OUTER
	private boolean selectedStoryVisible;
	public boolean isSelectedStoryVisible(){
		return selectedStoryVisible;
	}
	public String showSelectedStory(){
		resetVisibility();
		selectedStoryVisible=true;
		return null;
	}
	
	//Variable - OUTER
	public List<Entry> getAllEntries(){
		List<Entry> allEntries = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT x FROM Entry x WHERE x.belongs_to_story = "+getSelectedStory().getId()+"");
		allEntries = (List<Entry>) q.getResultList();
		Collections.sort(allEntries);
		return allEntries;
	}
	
	//Variable - OUTER
	private Entry selectedEntry;
	public String select(Entry selection){
		selectedEntry=selection;
		return null;
	}
	public Entry getSelectedEntry(){
		return selectedEntry;
	}
	
	//Logic 
	public String deselectEntry(){
		selectedEntry=null;
		return null;
	}
	
	public void handleClose(CloseEvent event) {  
        deselectStory();
        try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("chronicle.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	
	public void handleCloseOptions(CloseEvent event) {  
		showSelectedStory();
        try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("chronicle.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    } 
	
	public void addOnStoryCreateMessage(String summary) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }
	
	public void deleteStory(Story story) {  
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		List<Entry> allEntries = null;		
		Query q = em.createQuery("SELECT x FROM Entry x WHERE x.belongs_to_story = "+story.getId()+"");
		allEntries = (List<Entry>) q.getResultList();
		
		for(Entry entry : allEntries){
			em.getTransaction().begin();
			Entry toDelete = em.merge(entry);
    		em.remove(toDelete);
    		em.getTransaction().commit();
		}
		
		em.getTransaction().begin();
		Story toDelete = em.merge(story);
		em.remove(toDelete);
		em.getTransaction().commit();
		
        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Geschichte \"" + story.getTitle() + "\" gelöscht", "");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public String getColorForStory(Story story){
		if(toggle==1){
			toggle=2;
			return (story.getState().equals("running"))?"#ffd8d1" : "#d0e3b0";
		}
		else{
			toggle=1;
			return (story.getState().equals("running"))?"#ffe5e0" : "#dbeac4 ";
		}
		
	}
	
	public boolean isSelectedStoryEmpty(){
		List<Entry> allEntries = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT x FROM Entry x WHERE x.belongs_to_story = "+getSelectedStory().getId()+"");
		allEntries = (List<Entry>) q.getResultList();
		return allEntries.isEmpty();
	}
	
	public boolean isSelectedStoryRunning(){
		return (getSelectedStory().getState().equals("running"));
	}
	
	public void closeStory(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		Story toUpdate = em.merge(selectedStory);
		toUpdate.setState("closed");
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		Status status = new Status();
		status.setStatus("Gesund");
		status.setBelongs_to_story(selectedStory.getId());
		long day = DateUtility.calculateDay();
		status.setDay(day);
		long month = DateUtility.calculateMonth();
		status.setMonth(month);
		long year = DateUtility.calculateYear();
		status.setYear(year);
		long hour = 99L;
		status.setHour(hour);
		long minute = 99L;
		status.setMinute(minute);
		long second = 99L;
		status.setSecond(second);
		em.persist(status);
		em.getTransaction().commit();
		
		
        deselectStory();
        try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("chronicle.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void openStory(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Story toUpdate = em.merge(selectedStory);
		toUpdate.setState("running");
		em.getTransaction().commit();
		
		Query q = em.createQuery("SELECT x FROM Status x WHERE x.belongs_to_story = "+getSelectedStory().getId()+"");
		List<Status> statuss = (List<Status>) q.getResultList();
		
		for(Status entry : statuss){
			em.getTransaction().begin();
			Status toDelete = em.merge(entry);
    		em.remove(toDelete);
    		em.getTransaction().commit();
		}
		
        deselectStory();
        try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("chronicle.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void deleteEntry(Entry entry) {  
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		Entry toDelete = em.merge(entry);
		em.remove(toDelete);
		em.getTransaction().commit();
		
		selectedEntry=null;
		
		updateSelectedStory();
		
        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eintrag gelöscht", "");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void editEntry(Entry entry) {  
		System.out.println("select "+entry.getClass().getName());
		select(entry);
		if(selectedEntry.getClass().equals(Symptom.class)){
			showSymptomForm();
		}
		else if (selectedEntry.getClass().equals(Diagnosis.class)){
			showDiagnosisForm();
		}
		else if (selectedEntry.getClass().equals(Action.class)){
			showActionForm();
		}
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("chronicle.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void updateSelectedStory(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		List<Entry> allEntries = getAllEntries();
		int lastindex = allEntries.size()-1;
		selectedStory.setF_year(allEntries.get(lastindex).getYear());
		selectedStory.setF_month(allEntries.get(lastindex).getMonth());
		selectedStory.setF_day(allEntries.get(lastindex).getDay());
		
		selectedStory.setT_year(allEntries.get(0).getYear());
		selectedStory.setT_month(allEntries.get(0).getMonth());
		selectedStory.setT_day(allEntries.get(0).getDay());
		
		em.getTransaction().begin();		
		Story toBeUpdated = em.merge(selectedStory);				
		em.persist(toBeUpdated);
		em.getTransaction().commit();
	}
	
	public void showStatistics(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("statistics.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}
