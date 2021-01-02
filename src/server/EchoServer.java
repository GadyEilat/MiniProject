// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.awt.DisplayMode;
import java.io.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.WaitingList;
import client.logic.maxVis;
import common.DataTransfer;
import common.TypeOfMessageReturn;
import common.logic.Worker;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import ocsf.server.*;
import server.Controller.ServerController;
import server.database.mysqlConnection;

public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	ArrayList<Object> arrOfVisitors = null;
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
		DataTransfer data = (DataTransfer)msg;
		Object object = data.getObject();
		DataTransfer returnData;
		switch (data.getTypeOfMessage()) {
		case REQUESTINFO:
			
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
					ServerController.instance.displayMsg("Order details updated");
				else
					ServerController.instance.displayMsg("Order details could not be updated");
			}
			break;
			
		case TOURGUIDEWAITINGLIST:
			if (object instanceof WaitingList) {
				boolean ans2 = mysqlConnection.updateWaitingListTour(object);
				if (ans2)
					ServerController.instance.displayMsg("Waitinglist details updated");
				else
					ServerController.instance.displayMsg("Waitinglist details could not be updated");
			}
			
			
			break;
			
		case CHECKMAXVIS:
			if(object instanceof TourGuideOrder)
			{
			    Object visMax= new maxVis(null, null, null, 0 ,0, null, 0);
			    maxVis t= new maxVis(null, null, null, 0, 0, null, 0);
			    t.setDate(((TourGuideOrder) object).getDate());
			    t.setPark(((TourGuideOrder) object).getParkName());
			    t.setVisitorsInOrder(((TourGuideOrder) object).getNumOfVisitors());
			    t.setTime(((TourGuideOrder) object).getTime());
			    
			    
			      visMax=mysqlConnection.checkMaxVisitors(t);
			      
			      visMax=(Object)visMax;
			      
			  	if (visMax != null) {
					try {
						returnData = new DataTransfer(TypeOfMessageReturn.TOUR_MAXVISCHECK, visMax);
						client.sendToClient(returnData);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}         
			}
			
			break;
			
		case UPDATEINFO:
			
			break;
		case LOGIN_REQUEST:
			if(object instanceof Worker) {
				
			}
			break;
		case LOGOUT:
			
			break;
		default:
			break;
		
//	
			
		}
//		if (msg instanceof String) {
//
//			arrOfVisitors = mysqlConnection.getDB(msg);
//			if (arrOfVisitors != null) {
//				try {
//					client.sendToClient(arrOfVisitors);
//					
//				}
//				catch (IOException e) {
//					e.printStackTrace();
//				}
//
//			}
//		}
//	
		
		
		
		
		if(msg instanceof maxVis)
		{
		    Object visMax= new maxVis(null, null, null, 0, 0, null, 0);
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
