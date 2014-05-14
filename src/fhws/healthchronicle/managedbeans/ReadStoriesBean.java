package fhws.healthchronicle.managedbeans;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;

import fhws.healthchronicle.entities.Action;
import fhws.healthchronicle.entities.Diagnosis;
import fhws.healthchronicle.entities.Story;
import fhws.healthchronicle.entities.Symptom;
import fhws.healthchronicle.entities.User;

@ManagedBean(name="readStoriesBean")
@SessionScoped
public class ReadStoriesBean {
	
	public static String pAbout;
	public String getPAbout() {
		return pAbout;
	}
	public void setPAbout(String pAbout) {
		this.pAbout=pAbout;
	}
	
	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "common-entities";
    
	public static String about = null;
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about=about;
	}
	
	public static String[] outputListe;
	public String[] getOutputListe() {
		return outputListe;
	}
	public void setOutputListe(String[] outputListe) {
		this.outputListe=outputListe;
	}
	
	List<String> symListbox = null;
	public List<String> getSymListbox() {
		return symListbox; 
	}
	public void setSymListbox(List<String> symListbox) {
		this.symListbox=symListbox;
	}
	
	List<SelectItem> selectSymptom;
	public List<SelectItem> getSelectSymptom() {
		return selectSymptom; 
	}
	public void setSelectSymptom(List<SelectItem> selectSymptom) {
		this.selectSymptom=selectSymptom;
	}
	
	List<User> user;
	public List<User> getUser() {
		return user; 
	}
	public void setUser(List<User> user) {
		this.user=user;
	}
	
	List<Diagnosis> diagnosis;
	public List<Diagnosis> getDiagnosis() {
		return diagnosis; 
	}
	public void setDiagnosis(List<Diagnosis> diagnosis) {
		this.diagnosis=diagnosis;
	}
	
	List<Symptom> symptom;
	public List<Symptom> getSymptom() {
		return symptom; 
	}
	public void setSymptom(List<Symptom> symptom) {
		this.symptom=symptom;
	}
	
	List<Action> action;
	public List<Action> getAction() {
		return action; 
	}
	public void setAction(List<Action> action) {
		this.action=action;
	}
	
	List<String> behListbox = null;;
	public List<String> getBehListbox() {
		return behListbox; 
	}
	public void setBehListbox(List<String> behListbox) {
		this.behListbox=behListbox;
	}
	
	List<SelectItem> selectBehandlung;
	public List<SelectItem> getSelectBehandlung() {
		return selectBehandlung; 
	}
	public void setSelectBehandlung(List<SelectItem> selectBehandlung) {
		this.selectBehandlung=selectBehandlung;
	}
	
	List<Story> readStories;
	public List<Story> getReadStories() {
		return readStories; 
	}
	public void setReadStories(List<Story> readStories) {
		this.readStories=readStories;
	}
	
	public String sort = "DESC";
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public List<Long> dId;
	public List<Long> getDId() {
		return dId;
	}
	public void setDId(List<Long> dId) {
		this.dId = dId;
	}
	
	public String userBirthday;
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday=userBirthday;
	}
	
	public List<String> intensity;
	public List<String> getIntensity() {
		return intensity;
	}
	public void setIntensity(List<String> intensity) {
		this.intensity=intensity;
	}
	
	public List<String> periode;
	public List<String> getPeriode() {
		return periode;
	}
	public void setPeriode(List<String> periode) {
		this.periode=periode;
	}
	
	
	// Konstruktor
	public ReadStoriesBean() throws UnsupportedEncodingException {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		byte ptext[] = params.get("pAbout").getBytes("ISO-8859-1"); 
		this.about = new String(ptext, "UTF-8");
		System.out.println("this.about: "+this.about);
		
		this.symListbox = new ArrayList<String>();
		this.behListbox = new ArrayList<String>();
		this.getAllStories();
		this.listFill();
	}
		
		public void getAllStories() {
			this.readStories = new ArrayList<Story>();
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			//alle Geschichten ohne Filter
			Query q = em.createQuery("SELECT DISTINCT d.belongs_to_story FROM Diagnosis d WHERE (d.diagnosis LIKE '%"+this.about+"%') OR (d.diagnosis LIKE '%"+this.about.toUpperCase()+"%') OR (d.diagnosis LIKE '%"+this.about.toLowerCase()+"%') ORDER BY d.year "+this.sort+", d.month "+this.sort+", d.day "+this.sort+", d.hour "+this.sort+", d.minute "+this.sort+", d.second "+this.sort);
			this.dId = (List<Long>) q.getResultList();
			System.out.println("Size dId: "+this.dId);
			System.out.println("---");
			
			for(Long d:this.dId) {
				q = em.createQuery("SELECT s FROM Story s WHERE s.id = " + d);
				Story story = (Story) q.getSingleResult();
				this.readStories.add(story);
			}
			for(Story s:this.readStories) {
				switch (s.getState()) {
				case "closed":
					s.setState("geschlossen");
					break;
				case "running":
					s.setState("offen");
					break;
				}
			}
			
			//Filter nach Symptomen
			String strS = "";
			int hS;
			if (this.symListbox.size() > 0) {
				int count = 1;
				for(String s: this.symListbox) {
			        hS = Integer.parseInt(s);
			        System.out.println(this.selectSymptom.get(hS).getLabel());
			        if (count == 1)
		        		strS += " AND (s.term = '"+this.selectSymptom.get(hS).getLabel()+"'";
			        else
			        	strS += " OR s.term = '"+this.selectSymptom.get(hS).getLabel()+"'";
				    	count++;
				}
				strS += ")";
			
				for (int i=0;i<this.readStories.size();i++) {
					long id = this.readStories.get(i).getId();
		        	q = em.createQuery("SELECT s FROM Symptom s WHERE s.belongs_to_story='"+id+"' "+strS);
		        	List<Symptom> s = (List<Symptom>) q.getResultList();
		        	if (s.size() == 0)
		        		this.readStories.remove(i);
				}
			}
			
			//Filter nach Behandlung
			String strB = "";
			int hB;
			if (this.behListbox.size() > 0) {
				int count = 1;
				for(String b: this.behListbox) {
			        hB = Integer.parseInt(b);
			        if (count == 1)
		        		strB += " AND (a.action = '"+this.selectBehandlung.get(hB).getLabel()+"'";
			        else
			        	strB += " OR a.action = '"+this.selectBehandlung.get(hB).getLabel()+"'";
				    	count++;
				}
				strB += ")";
			
				for (int i=0;i<this.readStories.size();i++) {
					long id = this.readStories.get(i).getId();
		        	q = em.createQuery("SELECT a FROM Action a WHERE a.belongs_to_story='"+id+"' "+strB);
		        	List<Action> a = (List<Action>) q.getResultList();
		        	if (a.size() == 0)
		        		this.readStories.remove(i);
				}
			}
		}
		
		public void onTabChange(TabChangeEvent event) {
			String activeIndex = ((AccordionPanel) event.getComponent()).getActiveIndex();

			EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();
			
			try {
				int y = Integer.parseInt(activeIndex);
				
				long id = this.readStories.get(y).getId() ;
				String who = this.readStories.get(y).getStory_teller();
				
				Query q;
				q = em.createQuery("SELECT u FROM User u WHERE u.name='"+who+"' ");
				this.user = (List<User>) q.getResultList();
				if (this.user.get(0).getGewicht() != null)
					this.user.get(0).setGewicht(" Gewicht:"+this.user.get(0).getGewicht());
				
				if (this.user.get(0).getGroesse() != null)
					this.user.get(0).setGroesse(" Körpergröße:"+this.user.get(0).getGroesse());
				
				if (this.user.get(0).getSex() != null)
					this.user.get(0).setSex(" Geschlecht:"+this.user.get(0).getSex());
				
				this.userBirthday = null;
				if (this.user.get(0).getDay()!=0 & +this.user.get(0).getMonth()!=0 & this.user.get(0).getYear()!=0) {
				    GregorianCalendar today = new GregorianCalendar();
				    GregorianCalendar past = new GregorianCalendar((int) this.user.get(0).getYear(), (int) this.user.get(0).getMonth(), (int) this.user.get(0).getDay());
					long difference = today.getTimeInMillis() - past.getTimeInMillis();
					int years = (int)(difference / (1000 * 60 * 60 * 24) / 365);
					this.userBirthday = "Alter: "+String.valueOf(years);
				}
				
				q = em.createQuery("SELECT d FROM Diagnosis d WHERE d.belongs_to_story='"+id+"' ");
				this.diagnosis = (List<Diagnosis>) q.getResultList();
				
				q = em.createQuery("SELECT s FROM Symptom s WHERE s.belongs_to_story='"+id+"' ");
				this.symptom = (List<Symptom>) q.getResultList();
				this.intensity = new ArrayList<String>();
				for(Symptom s:this.symptom) {
					switch (s.getIntensity()) {
					case 1:
						this.intensity.add("kaum merkbar");
						break;
					case 2:
						this.intensity.add("merkbar");
						break;
					case 3:
						this.intensity.add("mittelmäßig");
						break;
					case 4:
						this.intensity.add("stark");
						break;
					case 5:
						this.intensity.add("unerträglich");
						break;
					default:
						this.intensity.add("k.A.");
						break;
					}
				}
				
				q = em.createQuery("SELECT a FROM Action a WHERE a.belongs_to_story='"+id+"' ");
				this.action = (List<Action>) q.getResultList();
				this.periode = new ArrayList<String>();
				for(Action a:this.action) {
					switch (a.getPeriod()) {
					case 1:
						this.periode.add("einmalig");
						break;
					case 2:
						this.periode.add("täglich");
						break;
					case 3:
						this.periode.add("wöchentlich");
						break;
					case 4:
						this.periode.add("monatlich");
						break;
					default:
						this.periode.add("k.A.");
						break;
					}
				}
			}
			catch (NumberFormatException e) {
			}
	    }
		
		public void listFill() {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();
			Query q;
			List<String> sym = null;
			List<String> act = null;
			
			for (int i=0;i<this.dId.size();i++) {
				long id = this.dId.get(i);

				//Symptomen List
				q = em.createQuery("SELECT DISTINCT s.term FROM Symptom s WHERE s.belongs_to_story='"+id+"' ");
				sym = (List<String>) q.getResultList();
				for(String s:sym) 
					if (!this.symListbox.contains(s))
						this.symListbox.add(s);
				
				//Behandlungen List
				q = em.createQuery("SELECT DISTINCT a.action FROM Action a WHERE a.belongs_to_story='"+id+"' ");
				act = (List<String>) q.getResultList();
				for(String a:act) 
					if (!this.behListbox.contains(a))
						this.behListbox.add(a);
			}	
			
			int m=0;
			//Symtomen List befüllen
			this.selectSymptom = new ArrayList<SelectItem>();
			for(String s : this.symListbox) {
				this.selectSymptom.add(new SelectItem(m,s));
				m++;
			}
			
			//Behandlungen List befüllen
			this.selectBehandlung = new ArrayList<SelectItem>();
			m=0;
			for(String b : this.behListbox) {
				this.selectBehandlung.add(new SelectItem(m,b));
				m++;
			}
		}
		
}
