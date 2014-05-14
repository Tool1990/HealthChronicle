package fhws.healthchronicle.managedbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import fhws.healthchronicle.entities.Story;
import fhws.healthchronicle.entities.Symptom;

@ManagedBean(name = "statisticsBean")
@RequestScoped
public class StatisticsBean {
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
	
	private CartesianChartModel categoryModel;  
	public CartesianChartModel getCategoryModel() {  
        return categoryModel;  
    } 	
	private ChartSeries symptoms; 
	
	private int totalSymptoms = 0;
	public int getTotalSymptoms(){
		return totalSymptoms;
	}
	
	private List<Symptom> symptomList = new ArrayList<Symptom>();
	private List<Story> storiesList;
	private HashMap<String, Integer> statisticalData = new HashMap<String, Integer>();
	
	public StatisticsBean(){
		createCategoryModel(); 
	}
	
	@PostConstruct 
	public void prepareData(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
					
		Query q = em.createQuery("SELECT x FROM Story x WHERE x.title ='"+ chronicle.getSelectedStory().getTitle() +"'");
		storiesList = (List<Story>) q.getResultList();
	
		for(Story story : storiesList){
			q = em.createQuery("SELECT x FROM Symptom x WHERE x.belongs_to_story ='"+ story.getId() +"'");
			symptomList.addAll((List<Symptom>) q.getResultList());	
		}
	
		for(Symptom symptom : symptomList){
			if(statisticalData.containsKey(symptom.getTerm())){
				int i = statisticalData.get(symptom.getTerm());
				i = i+1;
				statisticalData.put(symptom.getTerm(), i);
				totalSymptoms++;
			}
		else{
			statisticalData.put(symptom.getTerm(), 1);
			totalSymptoms++;
		}
		}
		for(int j=0; j<11;j++){
			selectAndSaveHighest(categoryModel, statisticalData);
		}
		categoryModel.addSeries(symptoms);
	}
	
	private void selectAndSaveHighest(CartesianChartModel categoryModel, HashMap<String, Integer> statisticalData){
		if(statisticalData.size()>0){
			Set<String> keyset = statisticalData.keySet();
			
			String currentKey = "";
			Integer highestValue = -1;
			for(String key : keyset){
				if(statisticalData.get(key)>highestValue){
					currentKey=key;
					highestValue=statisticalData.get(key);
				}
			}
			
			symptoms.set(currentKey, highestValue);
			statisticalData.remove(currentKey);
		}
	}
		
	private void createCategoryModel() {  
		
		categoryModel = new CartesianChartModel();    
		symptoms = new ChartSeries();
        symptoms.setLabel("Symptome");  
    } 
}
