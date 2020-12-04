// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import client.logic.Visitor;
import ocsf.server.*;
import server.Controller.ServerController;
import server.database.mysqlConnection;

public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	ArrayList<String> arrOfVisitors = new ArrayList<String>();
	String visitor = null;
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
		//ServerController.instance.displayMsg("Message received: " + msg.toString() + " from " + client);
		if (msg instanceof String) {
			
			visitor = (String)mysqlConnection.getDB(msg);

			// ServerController.instance.displayMsg("Message received: " + msg.toString() +
			// " from " + client);
			System.out.println("Message received from : " + client);
			
			try {
				client.sendToClient(visitor);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//this.sendToAllClients(arrOfVisitors);
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
//		try {
//		ServerController.instance.displayMsg("Server has start listening for connections at port : " + getPort());
//		}catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("svsdvsdvsv");
//			e.printStackTrace();
//		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		//ServerController.instance.displayMsg("Server has stopped listening for connections.");
	}

}
//End of EchoServer class
