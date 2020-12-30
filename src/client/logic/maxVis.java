package client.logic;

import java.io.Serializable;

public class maxVis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Date;
	private String visitorsInOrder;
	private String MaxVisitors;
	
	
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "maxVis [Date=" + Date + ", visitorsInOrder=" + visitorsInOrder + ", MaxVisitors=" + MaxVisitors + "]";
	}
	public maxVis(String date, String visitorsInOrder, String maxVisitors) {
		//super();
		this.Date = date;
		this.visitorsInOrder = visitorsInOrder;
		this.MaxVisitors = maxVisitors;
	}
	public String getDate() {
		return Date;
	}
	public String getVisitorsInOrder() {
		return visitorsInOrder;
	}
	public String getMaxVisitors() {
		return MaxVisitors;
	}
	public void setDate(String date) {
		Date = date;
	}
	public void setVisitorsInOrder(String visitorsInOrder) {
		this.visitorsInOrder = visitorsInOrder;
	}
	public void setMaxVisitors(String maxVisitors) {
		MaxVisitors = maxVisitors;
	}
	

}
