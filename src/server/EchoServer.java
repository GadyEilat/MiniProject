// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import client.ClientUI;
import client.logic.Order;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.Worker;
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
//		DataTransfer data = (DataTransfer)msg;
//		Object object = data.getObject();
//		DataTransfer returnData;
//		switch (data.getTypeOfMessage()) {
//		case REQUESTINFO:
//			
//			break;
//		case UPDATEINFO:
//			
//			break;
//		case LOGIN_REQUEST:
//			if(object instanceof Worker) {
//				Worker worker = (Worker)object;
//				String str = "SELECT Role , Park FROM gonature.worker WHERE UserName = "+worker.getUserName()+" AND Password = "+worker.getPassword()+";";
//				arrOfAnswer = mysqlConnection.getDB(str);
//				if (arrOfAnswer != null) {
//					
//					Worker RoleAndPark = new Worker(null,null,(String)arrOfAnswer.get(0) , (String)arrOfAnswer.get(1));
//					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_SUCCESSFUL,RoleAndPark);
//				}
//				else
//					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED,null);
//				try {
//					client.sendToClient(data);
//					
//				}
//				catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			break;
//		case LOGOUT:
//			
//			break;
//		default:
//			break;
//		}

		if (msg instanceof Order) {
			order = mysqlConnection.getDBOrder(msg);
			if (order != null) {
				try {
					client.sendToClient(order);

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
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

		if (msg instanceof TourGuide) {
			boolean ans = mysqlConnection.updateDB(msg);
			if (ans)
				ServerController.instance.displayMsg("TourGuide details updated");
			else
				ServerController.instance.displayMsg("TourGuide details could not be updated");
		}

		if (msg instanceof TourGuideOrder) {
			boolean ans2 = mysqlConnection.updateDBOrders(msg);
			if (ans2)
				ServerController.instance.displayMsg("TourGuide details updated");
			else
				ServerController.instance.displayMsg("TourGuide details could not be updated");
		}

		if (msg instanceof Integer) {
			ObservableList<Object> ans3 = mysqlConnection.getTourGuideOrders(TourID);
			// DataTransfer data = new DataTransfer(TypeOfMessage.SUCCSESS, ans3);

			if (ans3 != null) {
				for (int i = 0; i < ans3.size(); i++) {
					try {
						client.sendToClient(ans3.get(i));
						// client.sendToClient(ans3);
					}

					catch (IOException e) {
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

		// if (msg instanceof String) {

		// arrOfVisitors = mysqlConnection.getDB(msg);
		// if (arrOfVisitors != null) {
		// try {
		// client.sendToClient(arrOfVisitors);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

		// }
		// if (msg instanceof Visitor) {
		// boolean ans = mysqlConnection.updateDB(msg);
		// if (ans)
		// ServerController.instance.displayMsg("Email updated");
		// else
		// ServerController.instance.displayMsg("Email could not be updated");
		// }

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
