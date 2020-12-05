// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.awt.DisplayMode;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import client.logic.Visitor;
import ocsf.server.*;
import server.Controller.ServerController;
import server.database.mysqlConnection;

public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	ArrayList<Object> arrOfVisitors = null;
	String visitor = null;
	public static int flag = 0;
	ServerController sc = new ServerController();
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
		// ServerController.instance.displayMsg("Message received: " + msg.toString() +
		// " from " + client);
		if (msg instanceof String) {

			arrOfVisitors = mysqlConnection.getDB(msg);
			if (arrOfVisitors != null) {
				try {
					client.sendToClient(arrOfVisitors);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// ServerController.instance.displayMsg("Message received: " + msg.toString() +
			// " from " + client);

			// this.sendToAllClients(arrOfVisitors);
		}
		if (msg instanceof Visitor) {
			boolean ans = mysqlConnection.updateDB(msg);
			if (ans)
				System.out.println("Email updated");
			else
				System.out.println("Email could not be updated");
		}
		if (flag == 0) { // in the first connection, display ip, host and status.
			System.out.println("Client IP: " + client.getInetAddress().getHostAddress());
			//sc.displayMsg("Client IP: " + client.getInetAddress().getHostAddress());
			System.out.println("Hostname: " + client.getInetAddress().getHostName());
			//sc.displayMsg("Hostname: " + client.getInetAddress().getHostName());
			if (client.isAlive()) {
				System.out.println("Client Status: Connected");
				//sc.displayMsg("Client Status: Connected");
			} else {
				System.out.println("Client Status: Disconnected");
				//sc.displayMsg("Client Status: Disconnected");
			}
			flag = 1;
		}
		System.out.println("Message received from : " + client);

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
		// ServerController.instance.displayMsg("Server has stopped listening for
		// connections.");
	}

}
//End of EchoServer class
