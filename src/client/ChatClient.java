
package client;

import ocsf.client.*;
import common.ChatIF;
import common.DataTransfer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import client.controller.ClientGUIController;
import client.controller.DataGuiController;
import client.controller.ExistingOrderController;
import client.controller.MyOrdersGuideController;
import client.controller.OrderManagementController;
import client.controller.TourGuideLoginController;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.Order;
import java.io.*;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {

	public static Visitor visitor = new Visitor(null, null, null, null, null);
	public static Order order = new Order(null,null,null,null,null,null, null);
    public static TourGuide tourguide = new TourGuide(null, null, null, null, null);
    public static TourGuideOrder tourguideorder= new TourGuideOrder(null,null,null,null,null,null,null);
    public static ObservableList <TourGuideOrder> oblist=FXCollections.observableArrayList();
	ChatIF clientUI;
	public boolean waitForConnection = false;

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); 			// Call the superclass constructor
		this.clientUI = clientUI;
	}

	public void handleMessageFromServer(Object msg) {
		if (msg instanceof ArrayList<?>) {
			System.out.println("--> handleMessageFromServer");
			// System.out.println("--> HELLLLOOOOO");
			waitForConnection = false;
			ArrayList<String> st;
			st = (ArrayList<String>) msg;
			if (st.isEmpty())
				TourGuideLoginController.instance.notFound();
			else {
				// TODO: change to c'tor
				tourguide.setFname(st.get(0));
				tourguide.setLname(st.get(1));
				tourguide.setId(st.get(2));
				tourguide.setEmail(st.get(3));
				tourguide.setteln(st.get(4));
				TourGuideLoginController.instance.isFound();
			}
		}

		if (msg instanceof ObservableList<?>) {
			System.out.println("--> handleMessageFromServer");
			waitForConnection = false;
			// ObservableList <TourGuideOrder> oblistt=FXCollections.observableArrayList();
			oblist = (ObservableList<TourGuideOrder>) msg;
		}
		
		if (msg instanceof Order)
		{
			System.out.println("--> handleMessageFromServer");
			waitForConnection = false;
			Order recievedOrd = (Order)msg;
			String check = ExistingOrderController.order.getOrderNumber();
			if (check.equals(recievedOrd.getOrderNumber()))
			{
				ExistingOrderController.order=recievedOrd; //update the instance of the order in "existing" to be not null...
				ExistingOrderController.instance.isFound();
			}
			else
			{
				ExistingOrderController.instance.notFound();
			}
		}
	}

	public void handleMessageFromClientUI(Object msg) {
		try {
			openConnection();
			waitForConnection = true;
			sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
			quit();
		}
	}


	public void quit() {
		try {
			closeConnection();
			
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class