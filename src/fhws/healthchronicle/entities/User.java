package fhws.healthchronicle.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REGISTERED_USER")
public class User{
	
	@Id @GeneratedValue
	private long id;
	
	private String name;	
	private String password;
	private String role;
	private String gewicht;
	private String groesse;
	private String sex;
	private long year;
	private long month;
	private long day;
	//private String alter;
	
	//Variable	
	
	public long getYear() {
		return year;
	}
	public void setYear(long year) {
		this.year = year;
	}
	//Variable
	public long getMonth() {
		return month;
	}
	public void setMonth(long month) {
		this.month = month;
	}
	//Variable
	public long getDay() {
		return day;
	}
	public void setDay(long day) {
		this.day = day;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean isAdmin() {
		return role.equals("admin");
	}
	public void setAdmin(boolean isAdmin) {
		this.role = (isAdmin)?"admin":"user";
	}
	
	//Geschlecht
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
			this.sex = sex;
	}
	
	//alter
	/*public String getAlter() {
		return alter;
	}
	
	public void setAlter(String alter) {
		this.alter = alter;
	} */
	
	//gewicht
	public String getGewicht() {
		return gewicht;
	}
	public void setGewicht(String gewicht) {
		this.gewicht = gewicht;
	}
	//groesse
	public String getGroesse() {
		return groesse;
	}
	public void setGroesse(String groesse) {
		this.groesse = groesse;
	}
}
