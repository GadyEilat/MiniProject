// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import client.ClientUI;
import client.logic.Order;
import client.logic.ParkInfo;
import client.logic.Subscriber;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.Worker;
import client.logic.maxVis;
import common.DataTransfer;
import common.TypeOfMessage;
import common.TypeOfMessageReturn;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import ocsf.server.*;
import server.Controller.ServerController;
import server.database.mysqlConnection;

public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	ArrayList<Object> arrOfAnswer = null;
	Order order = new Order(null, null, null, null, null, null, null, null);
	String visitor = null;
	String TourID;
	public static int flag = 0;
	/**
	 * The default port to listen on.
	 */
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */

	public EchoServer(int port) {
		super(port);
		mysqlConnection.connectToDB();
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		ServerController.instance.displayMsg("Message received : " + msg + "\nfrom : " + client);
		DataTransfer data = (DataTransfer) msg;
		Object object = data.getObject();
		DataTransfer returnData;
		switch (data.getTypeOfMessage()) {
		case NEW_ORDER:
			if (object instanceof Order) {
				object = mysqlConnection.newDBOrder(object);
				if (object != null) {
					ServerController.instance.displayMsg("New Order created");
					returnData = new DataTransfer(TypeOfMessageReturn.NEWORDER_SUCCESS, object);
				} else {
					ServerController.instance.displayMsg("New Order could not be created");
					returnData = new DataTransfer(TypeOfMessageReturn.NEWORDER_FAILED, null);
				}
				try {
					client.sendToClient(returnData);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;

		case GET_ORDER:
			if (object instanceof Order) {
				order = mysqlConnection.getDBOrder(object);
				if (order != null) {
					ServerController.instance.displayMsg("Got Existing Order Details");
					returnData = new DataTransfer(TypeOfMessageReturn.RETURN_ORDER, order);
				} else {
					ServerController.instance.displayMsg("Couldn't recieve existing order details");
					returnData = new DataTransfer(TypeOfMessageReturn.RETURN_ORDER_FAILED, null);
				}
				try {
					client.sendToClient(returnData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
			
			
			
		case REQUESTINFO_HISTORY:///////////////////////////////////////////////////////////////////////////////////////
		
			if(object instanceof Subscriber){
				Subscriber subscriber = (Subscriber)object;
				ObservableList<Object> ans3 = mysqlConnection.getHistorySubOrders(subscriber.getId());
				//DataTransfer data = new DataTransfer(TypeOfMessage.SUCCSESS, ans3);

				if (ans3 != null) {
					for (int i = 0; i < ans3.size(); i++) {
						try {
							returnData = new DataTransfer(TypeOfMessageReturn.HISTORY_ORDERS,ans3.get(i));
							client.sendToClient(returnData);
							//client.sendToClient(ans3);
						}

						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
						
			break;
			
			
		case REQUESTINFO:
			if (object instanceof Subscriber) {
				Subscriber subscriber = (Subscriber) object;
				String checkSubExist = "SELECT * FROM gonature.subscriber WHERE subscriberNumber ='"+subscriber.getSubscriberNumber()+"';";
				arrOfAnswer = mysqlConnection.getDB(checkSubExist);
				
				if (!arrOfAnswer.isEmpty()) {
					subscriber.setId((String) arrOfAnswer.get(0));
					subscriber.setFname((String) arrOfAnswer.get(1));
					subscriber.setLname((String) arrOfAnswer.get(2));
					subscriber.setEmail((String) arrOfAnswer.get(3));
					subscriber.setteln((String) arrOfAnswer.get(4));
					subscriber.setAmountOfFamilyMember((String) arrOfAnswer.get(5));
					subscriber.setCreditCard((String) arrOfAnswer.get(6));
					subscriber.setSubscriberNumber((String) arrOfAnswer.get(7));	

					returnData = new DataTransfer(TypeOfMessageReturn.REQUESTINFO_SUCCESS, subscriber);
					try {
						client.sendToClient(returnData);

					} catch (IOException e) {
						e.printStackTrace();
					}
				} else
					ServerController.instance.displayMsg("subscriber REQUESTINFO details failed");
											
			}

			break;
		case UPDATEINFO:
			if (object instanceof Subscriber) {
				Subscriber subscriber = (Subscriber) object;
				String upDateSub = "UPDATE `gonature`.`subscriber` SET `ID` = '"+subscriber.getId()+"', `FirstName` = '"+subscriber.getFname()+"', "
						+ "`LastName` = '"+subscriber.getLname()+"', `Email` = '"+subscriber.getEmail()+"', `Telephone` = '"+subscriber.getTeln()+"', "
						+ "`AmountOfFamilyMembers` = '"+subscriber.getAmountOfFamilyMember()+"', `CreditCard` = '"+subscriber.getCreditCard()+"' WHERE "
								+ "(`ID` = '"+subscriber.getId()+"') and " + "(`subscriberNumber` = '"+subscriber.getSubscriberNumber()+"');";
				boolean Answer = mysqlConnection.updateDB(upDateSub);
				if(Answer) {
					ServerController.instance.displayMsg("subscriber UPDATEINFO details updated");

				} else {
					ServerController.instance.displayMsg("subscriber UPDATEINFO details failed");
				}
				
			}

			break;
		case LOGIN_REQUEST:
			if (object instanceof Worker) {
				Worker worker = (Worker) object;
				Worker RoleAndPark = null;
				ParkInfo parkInfo;
				String checkUserAndPassword = "SELECT Role, Park, name FROM gonature.worker WHERE UserName = '" + worker.getUserName()
						+ "' AND Password = '" + worker.getPassword() + "';";
				arrOfAnswer = mysqlConnection.getDB(checkUserAndPassword);
				if (!arrOfAnswer.isEmpty()) {
					String scene;
					String role = (String) arrOfAnswer.get(0);
					String park = (String) arrOfAnswer.get(1);
					String workerName = (String) arrOfAnswer.get(2);
					arrOfAnswer = mysqlConnection
							.getDB("SELECT * FROM gonature.manageparks WHERE numberOfPark = '" + park + "'");
					if (role.equals("Manager")) {
						scene = "/client/boundaries/manager.fxml";
						role = "Manager";
						parkInfo = new ParkInfo((String) arrOfAnswer.get(0), (String) arrOfAnswer.get(1),
								(String) arrOfAnswer.get(2), (String) arrOfAnswer.get(3), (String) arrOfAnswer.get(4));
						RoleAndPark = new Worker(null, null, role, parkInfo, workerName, scene);

					} else if (role.equals("Department Manager")) {
						scene = "/client/boundaries/mainDepartmantManager.fxml";
						role = "Departmant Manager";
						parkInfo = new ParkInfo((String) arrOfAnswer.get(0), (String) arrOfAnswer.get(1),
								(String) arrOfAnswer.get(2), (String) arrOfAnswer.get(3), null);
						RoleAndPark = new Worker(null, null, role, parkInfo, workerName, scene);

					} else if (role.equals("Service")) {
						scene = "/client/boundaries/FamilySubscriptionRegistration.fxml";
						role = "Service Representative";
						parkInfo = new ParkInfo((String) arrOfAnswer.get(0), null, null, null, null);
						RoleAndPark = new Worker(null, null, role, parkInfo, workerName, scene);

					} else
						System.out.println("Error");

					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_SUCCESSFUL, RoleAndPark);
				} else
					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED, new Worker());
				try {
					client.sendToClient(returnData);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			
			if (object instanceof Subscriber) {
				Subscriber subscriber = (Subscriber) object;
				String checkSubExist = "SELECT * FROM gonature.subscriber WHERE subscriberNumber ='"+subscriber.getSubscriberNumber()+"' "
						+ "OR ID = '"+subscriber.getId()+"'; ";
				arrOfAnswer = mysqlConnection.getDB(checkSubExist);
				
				if (!arrOfAnswer.isEmpty()) {
					subscriber.setId((String) arrOfAnswer.get(0));
					subscriber.setFname((String) arrOfAnswer.get(1));
					subscriber.setLname((String) arrOfAnswer.get(2));
					subscriber.setEmail((String) arrOfAnswer.get(3));
					subscriber.setteln((String) arrOfAnswer.get(4));
					subscriber.setAmountOfFamilyMember((String) arrOfAnswer.get(5));
					subscriber.setCreditCard((String) arrOfAnswer.get(6));
					subscriber.setSubscriberNumber((String) arrOfAnswer.get(7));	

					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_SUCCESSFUL, subscriber);
				} else
					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED, new Subscriber());
				try {
					client.sendToClient(returnData);

				} catch (IOException e) {
					e.printStackTrace();
				}
							
			}
			
			
			break;
		case LOGOUT:

			break;
		case UPDATEINFO_REQUEST:
			if (object instanceof ParkInfo) {
				ParkInfo parkInfo = (ParkInfo) object;
				String UpdateQuery = "UPDATE gonature.manageparkstoapprove SET Approve = '0', maxVisitors = '"
						+ parkInfo.getMaxVisitors() + "'," + " gapOfVisitors = '" + parkInfo.getGapOfVisitors() + "',"
						+ " maxHourToVisit = '" + parkInfo.getMaxHourToVisit() + "' WHERE numberOfPark = '"
						+ parkInfo.getNumberOfPark() + "';";
				boolean ans = mysqlConnection.updateDB(UpdateQuery);
				if (ans)
					ServerController.instance.displayMsg("parkInfo UPDATEINFO_REQUEST details updated");
				else
					ServerController.instance.displayMsg("parkInfo UPDATEINFO_REQUEST details could not be updated");
			}

		case TOURGUIDENEWORDER:
			if (object instanceof TourGuideOrder) {
				boolean ans2 = mysqlConnection.updateDBOrders(object);
				if (ans2)
					ServerController.instance.displayMsg("TourGuide details updated");
				else
					ServerController.instance.displayMsg("TourGuide details could not be updated");
			}
			break;

		case CHECKMAXVIS:
			if (object instanceof maxVis) {
				Object visMax = new maxVis(null, null, null);
				visMax = mysqlConnection.checkMaxVisitors("2020-12-31");

				visMax = (Object) visMax;

				if (visMax != null) {
					try {
						client.sendToClient(visMax);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			break;

		default:
			break;
		}

		if (msg instanceof String) {

			arrOfAnswer = mysqlConnection.getDB(msg);
			if (arrOfAnswer != null) {
				try {
					client.sendToClient(arrOfAnswer);

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		if (flag == 0) { // in the first connection, display ip, host and status.
			ServerController.instance.displayMsg("Client IP: " + client.getInetAddress().getHostAddress());
			ServerController.instance.displayMsg("Hostname: " + client.getInetAddress().getHostName());
			if (client.isAlive()) {
				ServerController.instance.displayMsg("Client Status: Connected");
			} else {
				ServerController.instance.displayMsg("Client Status: Disconnected");
			}
			flag = 1;
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		ServerController.instance.displayMsg("Server has start listening for connections at port : " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		ServerController.instance.displayMsg("Server has stopped listening for connections.");
	}

}
//End of EchoServer class
