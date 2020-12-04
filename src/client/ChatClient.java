
package client;

import ocsf.client.*;
import common.ChatIF;
import client.controller.DataGuiController;
import client.logic.Visitor;

import java.io.*;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {

	public static ArrayList<Visitor> list = new ArrayList<>();
	public static Visitor vis = new Visitor(null, null, null, null, null);

	ChatIF clientUI;
	public boolean waitForConnection = false;

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;

	}

	public void handleMessageFromServer(Object msg) { // This function gets the msg from the server and prints the table
//		vis = (ArrayList<String>) msg;
//		waitForConnection = false;
//		String row;
//		String[] split;
//		Visitor Visitor;
//		for (int i = 0; i < vis.size(); i++) {
//			row = vis.get(i);
//			split = row.split(",");
//			Visitor = new Visitor(split[0], split[1], split[2], split[3], split[4]);
//			DataGuiController.addItem(Visitor);
//		}
		if((String)msg == null) {
			vis.setId(null);
		}
		else {
		System.out.println("--> handleMessageFromServer");

		waitForConnection = false;
		String st;
		st = (String)msg;
		String[] result = st.split("\\s");
		vis.setId(result[0]);
		vis.setFname(result[1]);
		vis.setLname(result[2]);
		vis.setEmail(result[3]);
		vis.setteln(result[4]);
		}
	}

	/*
	 * public void handleMessageFromClientUI(String message) { try {
	 * sendToServer(message); } catch(IOException e) { clientUI.display
	 * ("Could not send message to server.  Terminating client."); quit(); } }
	 */

	public void handleMessageFromClientUI(Object msg) {

		try {
			openConnection();
			waitForConnection = true;
			sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			quit();
		}
	}
//		try {
//
//			if (message.equals("exit")) {
//				System.out.println("--> Update the connection");
//
//				try {
//					openConnection();
//					sendToServer(message);
//				} catch (Exception e) {
//					System.out.println("--> the server is shutdown");
//				}
//				quit();
//			}
//			System.out.println("--> handleMessageFromClientUI --> message: " + message);
//
//			openConnection();
//			waitForConnection = true;
//			sendToServer(message);
//			System.out.println("--> handleMessageFromClientUI --> sendToServer(message)");
//			while (waitForConnection) {
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			closeConnection();
//		} catch (IOException e) {
//			e.printStackTrace();
//			clientUI.display("Could not send message to server: Terminating client." + e);
//			quit();
//		}

	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
