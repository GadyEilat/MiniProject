// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;


import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import client.logic.Order;
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

		ServerController.instance.displayMsg("Message received : "+ msg + "\nfrom : " + client);

		
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
		case REQUESTINFO:

			break;
		case UPDATEINFO:

			break;
		case LOGIN_REQUEST:
			if (object instanceof Worker) {
				Worker worker = (Worker) object;
				String str = "SELECT Role , Park FROM gonature.worker WHERE UserName = " + worker.getUserName()
						+ " AND Password = " + worker.getPassword() + ";";
				arrOfAnswer = mysqlConnection.getDB(str);
				if (arrOfAnswer != null) {

					Worker RoleAndPark = new Worker(null, null, (String) arrOfAnswer.get(0),
							(String) arrOfAnswer.get(1));
					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_SUCCESSFUL, RoleAndPark);
				} else
					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED, null);
				try {
					client.sendToClient(data);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case LOGOUT:

			break;
		case TOURGUIDELOGIN:
			if (object instanceof String) {

				arrOfVisitors = mysqlConnection.getDB(object);
				TourID=(String)arrOfVisitors.get(2);
				if (arrOfVisitors != null) {
					try {
						returnData = new DataTransfer(TypeOfMessageReturn.TOUR_DETAILS,arrOfVisitors);
						client.sendToClient(returnData);
						
					}
					catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
			break;
		case TOURGETORDERS:
			if(object instanceof Integer){
				ObservableList<Object> ans3 = mysqlConnection.getTourGuideOrders(TourID);
				//DataTransfer data = new DataTransfer(TypeOfMessage.SUCCSESS, ans3);

				if (ans3 != null) {
					for (int i = 0; i < ans3.size(); i++) {
						try {
							returnData = new DataTransfer(TypeOfMessageReturn.TOUR_MYORDERS,ans3.get(i));
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
		case TOURGUIDEDETAILS:
			if (object instanceof TourGuide) {
				TourGuide updGuide= (TourGuide)object;
				String updEmail=updGuide.getEmail();
				String upName=updGuide.getFname();
				String upLName= updGuide.getLname();
				String upNumber=updGuide.getTeln();
				
				String query ="UPDATE tourguides SET Name='"+upName+"', LastName='"+upLName+"', Email='"+updEmail+"', phoneNumber='"+upNumber+"' WHERE ID='"+updGuide.getId()+"'";
				
				boolean ans = mysqlConnection.updateDB(query);
				if (ans)
					ServerController.instance.displayMsg("TourGuide details updated");
				else
					ServerController.instance.displayMsg("TourGuide details could not be updated");
			}
			break;
			
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
			if(object instanceof maxVis)
			{
			    Object visMax= new maxVis(null, null, null);
			      visMax=mysqlConnection.checkMaxVisitors("2020-12-31");
			      
			      visMax=(Object)visMax;
			      
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
