import java.io.*;
import java.util.Scanner;
import java.util.Calendar;
/**
*
*/
public class Event {
	
	int month;
	int day;
	int year;
	int hour;
	int minute;
	String description;
	long millis;
	
	public Event(int m, int d, int y, int h, int min, String desc){
		month = m;
		day = d;
		year = y;
		hour = h;
		minute = min;
		description = desc;
		millis = getMillis();
	}
	
	private long getMillis(){
		Calendar userCal = Calendar.getInstance();
		userCal.set(year, month-1, day, hour, minute);
		long userTimeValue = userCal.getTimeInMillis();
		
		return userTimeValue;
	}
	
	public int getMonth(){
		return month;
	}
	
	public void setMonth(int m){
		month = m;
	}
	
	public int getDay(){
		return day;
	}
	
	public void setDay(int d){
		day = d;
	}
	
	public int getYear(){
		return year;
	}
	
	public void setYear(int y){
		year = y;
	}
	
	public int getHour(){
		return hour;
	}
	
	public void setHour(int h){
		hour = h;
	}
	
	public int getMinute(){
		return minute;
	}
	
	public void setMinute(int min){
		minute = min;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void set(String desc){
		description = desc;
	}
	
	public long getKey(){
		return millis;
	}
	
	public String toString(){
		return "(" + month + "," + day + "," + year + "," + hour + "," + minute + "," + description + "," + millis + ")"; 
	}
	
	public static void main(String[] args) {
		Event newEvent = new Event(8, 28, 1998, 10, 30, "First Event");
		
		System.out.println(newEvent.getDay());
		System.out.println(newEvent.getMonth());
		System.out.println(newEvent.getYear());
		System.out.println(newEvent.getHour());
		System.out.println(newEvent.getMinute());
		System.out.println(newEvent.getDescription());
		System.out.println(newEvent.getKey());
	}
		
}
