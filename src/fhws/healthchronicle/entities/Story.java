package fhws.healthchronicle.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STORY")
public class Story  implements Comparable<Story> {
	
	//VARIABLE - OUTER
	@Id @GeneratedValue
	private long id;
	public long getId(){
		return id;
	}
	public void setId(long newId){
		this.id=newId;
	}
	
	//VARIABLE - OUTER
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	//VARIABLE - OUTER
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	//VARIABLE - OUTER
	private String story_teller;
	public String getStory_teller() {
		return story_teller;
	}
	public void setStory_teller(String story_teller) {
		this.story_teller = story_teller;
	}
	
	//From year
	private long f_year;
	public long getF_year() {
		return f_year;
	}
	public void setF_year(long f_year) {
		this.f_year = f_year;
	}

	//To year
	private long t_year;
	public long getT_year() {
		return t_year;
	}
	public void setT_year(long t_year) {
		this.t_year = t_year;
	}
	
	//From day
	private long f_day;
	public long getF_day() {
		return f_day;
	}
	public void setF_day(long f_day) {
		this.f_day = f_day;
	}
	
	//To day
	private long t_day;
	public long getT_day() {
		return t_day;
	}
	public void setT_day(long t_day) {
		this.t_day = t_day;
	}
	
	//From month
	private long f_month;
	public long getF_month() {
		return f_month;
	}
	public void setF_month(long f_month) {
		this.f_month = f_month;
	}
	
	//To month
	private long t_month;
	public long getT_month() {
		return t_month;
	}
	public void setT_month(long t_month) {
		this.t_month = t_month;
	}
	
	//From hour
	private long f_hour;
	public long getF_hour() {
		return f_hour;
	}
	public void setF_hour(long f_hour) {
		this.f_hour = f_hour;
	}
	
	//To hour
	private long t_hour;
	public long getT_hour() {
		return t_hour;
	}
	public void setT_hour(long t_hour) {
		this.t_hour = t_hour;
	}
	
	//From minute
	private long f_minute;
	public long getF_minute() {
		return f_minute;
	}
	public void setF_minute(long f_minute) {
		this.f_minute = f_minute;
	}
	
	//To minute
	private long t_minute;
	public long getT_minute() {
		return t_minute;
	}
	public void setT_minute(long t_minute) {
		this.t_minute = t_minute;
	}
	
	//From second
	private long f_second;
	public long getF_second() {
		return f_second;
	}
	public void setF_second(long f_second) {
		this.f_second = f_second;
	}
	
	//To second
	private long t_second;
	public long getT_second() {
		return t_second;
	}
	public void setT_second(long t_second) {
		this.t_second = t_second;
	}
	
	public int compareTo(Story story) {
		 
		int year = ((int)story.getT_year()-(int)this.t_year);
		if(year!=0){
			return year;
		}
		
		int month = ((int)story.getT_month()-(int)this.t_month);
		if(month!=0){
			return month;
		}
		
		int day = ((int)story.getT_day()-(int)this.t_day);
		if(day!=0){
			return day;
		}
		
		int hour = ((int)story.getT_hour()-(int)this.t_hour);
		if(hour!=0){
			return hour;
		}
		
		int minute = ((int)story.getT_minute()-(int)this.t_minute);
		if(minute!=0){
			return minute;
		}
		
		int second = ((int)story.getT_second()-(int)this.t_second);
		if(second!=0){
			return second;
		}
		
		return 0;		 
	}
	
}
