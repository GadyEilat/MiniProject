package client.logic;

import java.io.Serializable;

public class ParkInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String numberOfPark;
	private String maxVisitors;
	private String gapOfVisitors;
	private String maxHourToVisit;
	private String numOfSub;

	public ParkInfo(String numberOfPark, String maxVisitors, String gapOfVisitors, String maxHourToVisit,
			String numOfSu) {
		this.numberOfPark = numberOfPark;
		this.maxVisitors = maxVisitors;
		this.gapOfVisitors = gapOfVisitors;
		this.maxHourToVisit = maxHourToVisit;
		this.numOfSub = numOfSu;

	}

	public String getMaxVisitors() {
		return maxVisitors;
	}

	public void setMaxVisitors(String maxVisitors) {
		this.maxVisitors = maxVisitors;
	}

	public String getGapOfVisitors() {
		return gapOfVisitors;
	}

	public String getNumberOfPark() {
		return numberOfPark;
	}

	public void setGapOfVisitors(String gapOfVisitors) {
		this.gapOfVisitors = gapOfVisitors;
	}

	public String getMaxHourToVisit() {
		return maxHourToVisit;
	}

	public void setMaxHourToVisit(String maxHourToVisit) {
		this.maxHourToVisit = maxHourToVisit;
	}

	public String getNumOfSub() {
		return numOfSub;
	}

	public void setNumOfSub(String numOfSub) {
		this.numOfSub = numOfSub;
	}

}
