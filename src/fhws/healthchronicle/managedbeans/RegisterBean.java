package fhws.healthchronicle.managedbeans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.Date;

import fhws.healthchronicle.entities.Symptom;
import fhws.healthchronicle.entities.User;
import fhws.healthchronicle.utilitybeans.DateUtility;
@ManagedBean(name = "registerBean")
@RequestScoped
public class RegisterBean {

	// Constants
	private final String PERSISTENCE_UNIT_NAME = "common-entities";

	// Constructor
	public RegisterBean() {
		System.out.println("RegisterBean started");
	}

	/*@PostConstruct 
	public void setupVariables(){
			long year = User.getYear();
			long month = User.getMonth();
			long day = User.getDay();
			
			String datestring = DateUtility.numberToString(day)+"."+DateUtility.numberToString(month)+"."+DateUtility.numberToString(year);
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			try {
				 
				date = formatter.parse(datestring);
				System.out.println(date);
				System.out.println(formatter.format(date));
		 
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}*/
	
	// Variable - OUTER
	private String name = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Variable - OUTER
	private String password = null;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	// Geschlecht
	private String sex = null;
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
			this.sex = sex;
	}
	
	//Alter wie aus der eingabe?
	
	private String gewicht = null;
	
	public String getGewicht(){
		return gewicht; 
	}
	
	public void setGewicht(String gewicht){
		this.gewicht = gewicht;
	}
	
	//Größe
	private String groesse = null;
	
	public String getGroesse(){
		return groesse;
		
	}

	public void setGroesse(String groesse){
		this.groesse = groesse;
	}
	private Date date;
	public Date getDate(){
		return date;
	}
	public void setDate(Date date){
		this.date = date;
	}
	// Buttons logic
	public String registerUser() {
		if (name.length() > 0 && password.length() > 0) {
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT x FROM User x WHERE x.name ='"
					+ name + "'");

			List<User> usersList = (List<User>) q.getResultList();

			if (usersList.size() > 1) {
				// Error: Should throw an Exception, because there cannot be
				// many users with same name
				System.out
						.println("Oooops! Too many Users with same name found!");
			} else if (usersList.size() == 1) {
				// One User found, cannot register another with same name
				System.out.println("Oooops! Such user already exists");
			} else {
				// No User found, now check if password is correct and if, then
				// set session data

				User user = new User();
				user.setName(name);
				user.setPassword(password);
				user.setRole("user");
				user.setGewicht(gewicht);
				user.setGroesse(groesse);
				user.setSex(sex);
				//user.setAlter("alter");
				
				//User geburt = new User();
				long day = DateUtility.calculateDay(date);
				user.setDay(day);
				long month = DateUtility.calculateMonth(date);
				user.setMonth(month);
				long year = DateUtility.calculateYear(date);
				user.setYear(year);

				em.getTransaction().begin();
				em.persist(user);
				//em.persist(geburt);
				em.getTransaction().commit();

				return "registration-success.xhtml?faces-redirect=true";
			}

		}

		return null;
	}

	@PreDestroy
	public void cry() {
		// System.out.println("RegisterBean is about to be destroyed");
	}
}
