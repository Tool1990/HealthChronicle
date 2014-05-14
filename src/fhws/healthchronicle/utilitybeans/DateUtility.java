package fhws.healthchronicle.utilitybeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
	public static String numberToString(long number){
		String string="";
		if(number<10){
			string="0";
		}
		string= string + String.valueOf(number);
		return string;
	}
	//Logic
	public static long calculateYear(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateYear(Date date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateMonth(){
		DateFormat dateFormat = new SimpleDateFormat("MM");
		Date date = new Date();
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateMonth(Date date){
		DateFormat dateFormat = new SimpleDateFormat("MM");
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
		
	//Logic
	public static long calculateDay(){
		DateFormat dateFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateDay(Date date){
		DateFormat dateFormat = new SimpleDateFormat("dd");
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateHour(){
		DateFormat dateFormat = new SimpleDateFormat("HH");
		Date date = new Date();
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateHour(Date date){
		DateFormat dateFormat = new SimpleDateFormat("HH");
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateMinute(){
		DateFormat dateFormat = new SimpleDateFormat("mm");
		Date date = new Date();
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateMinute(Date date){
		DateFormat dateFormat = new SimpleDateFormat("mm");
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateSecond(){
		DateFormat dateFormat = new SimpleDateFormat("ss");
		Date date = new Date();
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
	
	//Logic
	public static long calculateSecond(Date date){
		DateFormat dateFormat = new SimpleDateFormat("ss");
		String date_as_string = dateFormat.format(date);
		long date_as_long = Long.parseLong(date_as_string);
		return date_as_long;
	}
}
