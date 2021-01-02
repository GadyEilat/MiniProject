
package client;

import ocsf.client.*;
import common.ChatIF;
import common.DataTransfer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import client.controller.ClientGUIController;
import client.controller.DataGuiController;
import client.controller.MyOrdersGuideController;
import client.controller.TourGuideLoginController;
import client.controller.TourGuideNewOrderController;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.maxVis;

import java.io.*;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {

	public static Visitor visitor = new Visitor(null, null, null, null, null);
    public static TourGuide tourguide = new TourGuide(null, null, null, null, null);
    public static TourGuideOrder tourguideorder= new TourGuideOrder(null,null,null,null,null,null,null, null);
    public static ObservableList <TourGuideOrder> oblist=FXCollections.observableArrayList();
    public static maxVis visMax= new maxVis(null, null, null, 0, 0, null, 0);
	ChatIF clientUI;
	public boolean waitForConnection = false;

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); 			// Call the superclass constructor
		this.clientUI = clientUI;

	}

	public void handleMessageFromServer(Object msg) {
		DataTransfer data = (DataTransfer)msg;
		Object object = data.getObject();
		DataTransfer returnData;
		switch (data.getTypeOfMessageReturn()) {
		case LOGIN_FAILED:
			
			break;
		case LOGIN_SUCCESSFUL:
			break;

		case TOUR_DETAILS:
			if(object instanceof ArrayList<?>) {
				System.out.println("--> handleMessageFromServer");
				//System.out.println("--> HELLLLOOOOO");
				waitForConnection = false;
				ArrayList<String> st;
				st = (ArrayList<String>)object;
				if(st.isEmpty())
					TourGuideLoginController.instance.notFound();
				else {
					//TODO: change to c'tor
					tourguide.setFname(st.get(0));
					tourguide.setLname(st.get(1));
					tourguide.setId(st.get(2));
					tourguide.setEmail(st.get(3));
					tourguide.setteln(st.get(4));
					TourGuideLoginController.instance.isFound();

					}
		}
			break;
			
		case TOUR_MYORDERS:
			if(object instanceof TourGuideOrder) {
				System.out.println("--> handleMessageFromServer");
				waitForConnection = false;
				//ObservableList <TourGuideOrder> oblistt=FXCollections.observableArrayList();
				//oblist=(ObservableList <TourGuideOrder>)msg;
				System.out.print(object.toString());
				MyOrdersGuideController.instance.getLine((TourGuideOrder) object);
			}
			
			break;
		case TOUR_MAXVISCHECK:
			if (object instanceof maxVis) {
				visMax=(maxVis)object;
				System.out.print(visMax.toString());
				TourGuideNewOrderController.instance.checkDate2(visMax);
				//TourGuideNewOrderController.instance.checkDate(null, visMax);
			}

			
			
			
		default:
			break;
		}
		
		




if (msg instanceof maxVis) {
	visMax=(maxVis)msg;
	System.out.print(visMax.toString());
	TourGuideNewOrderController.instance.checkDate2(visMax);
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