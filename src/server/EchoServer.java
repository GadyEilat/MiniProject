// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.awt.DisplayMode;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import client.logic.Visitor;
import common.DataTransfer;
import common.logic.Worker;
import javafx.application.Platform;
import ocsf.server.*;
import server.Controller.ServerController;
import server.database.mysqlConnection;

public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	ArrayList<Object> arrOfVisitors = null;
	String visitor = null;
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
		switch (data.getTypeOfMessage()) {
		case REQUESTINFO:
			
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
		}
		
		
		
		
		
		
		
		
		
		if (msg instanceof String) {

			arrOfVisitors = mysqlConnection.getDB(msg);
			if (arrOfVisitors != null) {
				try {
					client.sendToClient(arrOfVisitors);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		if (msg instanceof Visitor) {
			boolean ans = mysqlConnection.updateDB(msg);
			if (ans)
				ServerController.instance.displayMsg("Email updated");
			else
				ServerController.instance.displayMsg("Email could not be updated");
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
