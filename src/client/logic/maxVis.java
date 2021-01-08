package client.logic;

import java.io.Serializable;
/** Description of casualOrder 
• *
• * @author Elad Kobi
• * 
• * 
• */
public class maxVis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Date;
	private String visitorsInOrder;
	private String Park;
	private int Allowed1;
	private int Allowed2;
	private String Time;
	private int maxTime;
	

	
	
	
	
	
	
	
	/** Description of toString 
	• * toString for the entity
	• */
	@Override
	public String toString() {
		return "maxVis [Date=" + Date + ", visitorsInOrder=" + visitorsInOrder + ", MaxVisitors=" + Park + "]";
	}
	
	/** Description of casualOrder 
	• * A constructor, a maxVis entity uses for cheking if a date is avilable to visit the park.
	• */
	public maxVis(String date, String visitorsInOrder, String park, int allowed1, int allowed2, String time, int maxtime) {
		//super();
		this.Date = date;
		this.visitorsInOrder = visitorsInOrder;
		this.Park = park;
		this.Allowed1= allowed1;
		this.Allowed2= allowed2;
		this.Time= time;
		this.maxTime=maxtime;
	}
	public int getAllowed1() {
		return Allowed1;
	}
	public int getAllowed2() {
		return Allowed2;
	}
	public void setAllowed1(int allowed1) {
		Allowed1 = allowed1;
	}
	public void setAllowed2(int allowed2) {
		Allowed2 = allowed2;
	}
	public String getDate() {
		return Date;
	}
	public String getVisitorsInOrder() {
		return visitorsInOrder;
	}
	public String getPark() {
		return Park;
	}
	public void setDate(String date) {
		Date = date;
	}
	public void setVisitorsInOrder(String visitorsInOrder) {
		this.visitorsInOrder = visitorsInOrder;
	}
	public void setPark(String park) {
		Park = park;
	}
	
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public int getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}
	
	
	
	

}
