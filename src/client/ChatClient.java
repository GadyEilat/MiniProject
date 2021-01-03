
package client;

import ocsf.client.*;
import server.database.mysqlConnection;
import common.ChatIF;
import common.DataTransfer;
import common.TypeOfMessageReturn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import client.controller.ExistingOrderController;
import client.controller.ManagerController;
import client.controller.MyOrdersGuideController;
import client.controller.OrderManagementController;
import client.controller.ChangeOrderDetailsController;
import client.controller.SubscriptionEntryController;
import client.controller.ClientGUIController;
import client.controller.TourGuideLoginController;
import client.controller.TourGuideNewOrderController;
import client.controller.TravelerNewOrderController;
import client.controller.WorkerLogin;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.maxVis;
import client.logic.Worker;
import client.logic.Order;
import client.logic.Subscriber;
import java.io.*;
import common.TypeOfMessageReturn;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {

	public static Visitor visitor = new Visitor(null, null, null, null, null);
	public static Order order = new Order(null, null, null, null, null, null, null, null);
	public static TourGuide tourguide = new TourGuide(null, null, null, null, null);
	public static TourGuideOrder tourguideorder = new TourGuideOrder(null, null, null, null, null, null, null, null);
    public static Worker worker;
    public static Subscriber subscriber;
	public static ObservableList<TourGuideOrder> oblist = FXCollections.observableArrayList();
    public static maxVis visMax= new maxVis(null, null, null, 0, 0, null, 0);

	ChatIF clientUI;
	public boolean waitForConnection = false;

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
	}

	public void handleMessageFromServer(Object msg) {
		DataTransfer returnData = (DataTransfer) msg;
		Object object = returnData.getObject();
		switch (returnData.getTypeOfMessageReturn()) {
		case LOGIN_FAILED:
			if(object instanceof Worker) {
				WorkerLogin.instance.logInAnswerFailed();
			}
			if (object instanceof Subscriber) {
				SubscriptionEntryController.instance.subscriberNotFound();
			}
			if(object instanceof TourGuide) {
				TourGuideLoginController.instance.notFound();
			}
				
			
			break;
		case LOGIN_SUCCESSFUL:
			if(object instanceof Worker) {
				worker = (Worker)object;
				WorkerLogin.instance.checkLogInAnswer(worker);
			}
			if (object instanceof Subscriber) {
				subscriber = (Subscriber)object;
				SubscriptionEntryController.instance.subscriberFound();
			}		
			
			break;
		
		case DELETE_ORDER_FAILED:
			//not suppose to happen.
			break;
		case DELETE_ORDER_SUCCESS:
			
			break;
			
		case UPDATE_FAILED:
			if (object instanceof Order) {
				ChangeOrderDetailsController.instance.notUpdated();
			}
			break;
		case UPDATE_SUCCESS:
			if (object instanceof Order) {
			ChangeOrderDetailsController.instance.updated();
			}
			break;
			
		case RETURN_ORDER_FAILED:
			System.out.println("Couldn't recieve details from DB");
			break;
		case RETURN_ORDER:
			if (object instanceof Order) {
				if (object != null) {
					System.out.println("--> handleMessageFromServer");
					waitForConnection = false;
					Order recievedOrd = (Order) object;
					String check = ExistingOrderController.instance.order.getOrderNumber();
					if (check.equals(recievedOrd.getOrderNumber())) {
						ExistingOrderController.instance.order = recievedOrd; // update the instance of the order in "existing"
																		// to be not null...
						ExistingOrderController.instance.isFound();
					} else {
						ExistingOrderController.instance.notFound();
					}
				}
			}
			break;
		case NEWORDER_SUCCESS:
			if (object instanceof Order) {
				if (object != null) {
					System.out.println("--> handleMessageFromServer");
					waitForConnection = false;
					order = (Order) object;
					TravelerNewOrderController.instance.TravelerOrder = order;
					TravelerNewOrderController.instance.isFound();
				} 
				else {
					TravelerNewOrderController.instance.notFound();
				}
			}
			break;
		case NEWORDER_FAILED:
			break;
		case TOUR_DETAILS:
			if (object instanceof TourGuide) {
				System.out.println("--> handleMessageFromServer");
				// System.out.println("--> HELLLLOOOOO");
				//waitForConnection = false;
				this.tourguide=(TourGuide)object;
					TourGuideLoginController.instance.isFound();

				
			}
			break;

		case TOUR_MYORDERS:
			if (object instanceof TourGuideOrder) {
				System.out.println("--> handleMessageFromServer");
				waitForConnection = false;
				// ObservableList <TourGuideOrder> oblistt=FXCollections.observableArrayList();
				// oblist=(ObservableList <TourGuideOrder>)msg;
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
			break;
			
			
			
			
		case REQUESTINFO_SUCCESS:
			if (object instanceof Subscriber) {
				subscriber = (Subscriber)object;
			}
			break;
		default:
			break;
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