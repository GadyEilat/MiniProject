
package client;

import ocsf.client.*;
import server.database.mysqlConnection;
import common.ChatIF;
import common.DataTransfer;
import common.TypeOfMessageReturn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import client.controller.ExistingOrderController;
import client.controller.FamilySubscriptionHistoryController;
import client.controller.ManagerController;
import client.controller.ManagerDiscountController;
import client.controller.MyOrdersGuideController;
import client.controller.OrderManagementController;
import client.controller.ServiceRepresentativeController;
import client.controller.ChangeOrderDetailsController;
import client.controller.SubscriptionEntryController;
import client.controller.ClientGUIController;
import client.controller.DepartmantManagerApproveController;
import client.controller.TourGuideLoginController;
import client.controller.TourGuideNewOrderController;
import client.controller.TravelerNewOrderController;
import client.controller.WorkerLogin;
import client.controller.parkEnterenceController;
import client.controller.parkEnterenceController2;
import client.controller.subscriberNewOrderController;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.maxVis;
import client.logic.Worker;
import client.logic.Order;
import client.logic.ParkInfo;
import client.logic.ParkStatus;
import client.logic.Subscriber;
import java.io.*;
import common.TypeOfMessageReturn;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {

	public static Visitor visitor = new Visitor(null, null, null, null, null);
	public static Order order = new Order(null, null, null, null, null, null, null, null);
	public static TourGuide tourguide = new TourGuide(null, null, null, null, null);
	public static TourGuideOrder tourguideorder = new TourGuideOrder(null, null, null, null, null, null, null, null,
			null, null);
	public static Worker worker;
	public static ParkInfo parkInfo;
	public static Subscriber subscriber;
	public static ObservableList<TourGuideOrder> oblist = FXCollections.observableArrayList();
	public static maxVis visMax = new maxVis(null, null, null, 0, 0, null, 0);
	public static String datesToShow[][];
	public static String datesToApprveShow[][];
	public static boolean connected = false;
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
			if (object instanceof Worker) {
				WorkerLogin.instance.logInAnswerFailed();
			}
			
			/**
			 * If the object is a subscription type, it prints a message that a subscription has not been found.
			 */
			
			if (object instanceof Subscriber) {
				SubscriptionEntryController.instance.subscriberNotFound();
			}
			if (object instanceof TourGuide) {
				TourGuideLoginController.instance.notFound();
			}

			break;
			
		case LOGIN_FAILED_CONNECTED:
			if (object instanceof Worker) {
				WorkerLogin.instance.alreadyConnected();
			}
			break;
			
		case LOGIN_SUCCESSFUL:
			if (object instanceof Worker) {
				worker = (Worker) object;
				connected = true;
				WorkerLogin.instance.checkLogInAnswer(worker);
			}
			
			/**
			 * If the object is a subscription type, it saves a family subscription data.
			 * Open a subscription screen
			 */
			
			if (object instanceof Subscriber) {
				subscriber = (Subscriber) object;
				SubscriptionEntryController.instance.subscriberFound();
			}

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
			
			/**
			 * If the object is a subscription type, it saves a family subscription data.
			 * Opens a PopOut window for the service representative with the subscription number.
			 */
			
			if (object instanceof Subscriber) {
				subscriber = (Subscriber) object;
				try {
					ServiceRepresentativeController.instance.showNumOfSubPopOut();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (object instanceof TourGuide) {
				try {
					ServiceRepresentativeController.instance.showNumOfSubPopOut();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
						ExistingOrderController.instance.order = recievedOrd; // update the instance of the order in
																				// "existing"
						// to be not null...
						ExistingOrderController.instance.isFound();
					} else {
						ExistingOrderController.instance.notFound();
					}
				}
			}
			break;

		case IS_SUBSCRIBER:
			if (object instanceof Boolean) { // came from OrderManagementController
				Boolean isIt = (Boolean) object;
				if (isIt == true) {
					OrderManagementController.instance.isSubscriber(true);
				}
			}
			if (object instanceof Integer) { // came from ChangeOrderDetails
				ChangeOrderDetailsController.instance.isOther();
			}

			break;

		case IS_GUIDE:
			if (object instanceof Boolean) {
				Boolean isIt = (Boolean) object;
				if (isIt == true) {
					OrderManagementController.instance.isGuide(true);
				}
			}
			if (object instanceof Integer) { // came from ChangeOrderDetails
				ChangeOrderDetailsController.instance.isGuide();
			}
			break;

		case IS_REGULAR:
			if (object instanceof Boolean) {
				Boolean isIt = (Boolean) object;
				if (isIt == true) {
					OrderManagementController.instance.isRegular(true);
				}
			}
			if (object instanceof Integer) { // came from ChangeOrderDetails
				ChangeOrderDetailsController.instance.isOther();
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
				} else {
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
				// waitForConnection = false;
				this.tourguide = (TourGuide) object;
				TourGuideLoginController.instance.isFound();

			}
			break;

		case HISTORY_ORDERS:
			if (object instanceof Order) {
				System.out.println("--> handleMessageFromServer");
				waitForConnection = false;

				System.out.print(object.toString());
				FamilySubscriptionHistoryController.instance.getLine((Order) object);
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
				visMax = (maxVis) object;
				System.out.print(visMax.toString());
				TourGuideNewOrderController.instance.checkDate2(visMax);
				// TourGuideNewOrderController.instance.checkDate(null, visMax);
			}
			break;

		case NEW_ORDERMAXVISCHECK:
			if (object instanceof maxVis) {
				visMax = (maxVis) object;
				System.out.print(visMax.toString());
				TravelerNewOrderController.instance.checkDate2(visMax);
			}
			break;
			
		case SUB_NEW_ORDER_SUCCESS:
			if (object instanceof Order) {
				order = (Order) object;
				subscriberNewOrderController.instance.thereIsPlaceForVisitors();
			}
			break;
			
		case SUB_NEW_ORDER_FAILED:
//			if (object instanceof Order) {
				order = (Order) object;
//				subscriberNewOrderController.instance.TravelerOrder = order;
				subscriberNewOrderController.instance.thereIsNoPlaceForVisitors();
//			}			
			break;
			
		case PARK_STATUS:
			if (object instanceof ParkStatus) {
				String t = null;
				ParkStatus status = (ParkStatus) object;
				t = status.getDiscount();
				parkEnterenceController.instance.insertData(status.getAmount(), status.getMaxAmount());
				parkEnterenceController.instance.getDiscountDay(t);
				parkEnterenceController2.instance.insertData(status.getAmount(), status.getMaxAmount());
				parkEnterenceController2.instance.getDiscountDay(t);
			}
			break;

		case PARKENTERRETURNORDER:
			if (object instanceof Order) {
				Order order = (Order) object;
				parkEnterenceController2.instance.orderDetails(order);

			}

			break;
			
		case PARK_DISCOUNT:
			if (object instanceof String) {
				String t = null;
				t = (String) object;
				parkEnterenceController.instance.getDiscountDay(t);
				parkEnterenceController2.instance.getDiscountDay(t);

			}
			break;

		case REQUESTINFO_SUCCESS:
			
			/**
			 * If the object is a subscription type, it saves a family subscription data.
			 */
			
			if (object instanceof Subscriber) {
				subscriber = (Subscriber) object;
			}
			if (object instanceof ParkInfo) {
				parkInfo = (ParkInfo) object;
				if (parkInfo.getRole().equals("Manager")) {
					datesToShow = parkInfo.getDiscountDates();
					ManagerDiscountController.instance.updateDatePicker();
				} else if (parkInfo.getRole().equals("Department Manager")) {
					datesToApprveShow = parkInfo.getDiscountDates();
					DepartmantManagerApproveController.instance.loadData();
				}
			}
			break;
			
		case REQUESTINFO_FAILED:
			if (object instanceof ParkInfo) {
				parkInfo = (ParkInfo) object;
				if (parkInfo.getRole().equals("Manager")) {
					DepartmantManagerApproveController.instance.notFond();
				} else if (parkInfo.getRole().equals("Department Manager")) {
					ManagerDiscountController.instance.notFond();
				}
			}

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
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Connection Failed");
			alert.setHeaderText("Cannot connect to server");
			alert.setContentText("");
			alert.showAndWait();
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