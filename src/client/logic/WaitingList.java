package client.logic;

import java.io.Serializable;

public class WaitingList extends Order implements Serializable {

	private static final long serialVersionUID = 1L;

	public WaitingList(String parkName, String hour, String date, String email, String orderNumber, String numOfVisitors) {
		super(parkName, hour, date, email, orderNumber, numOfVisitors, numOfVisitors);
	}
}
