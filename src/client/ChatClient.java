
package client;

import ocsf.client.*;
import common.ChatIF;
import client.controller.ClientGUIController;
import client.controller.DataGuiController;
import client.logic.Visitor;

import java.io.*;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {

	public static ArrayList<Visitor> list = new ArrayList<>();
	public static Visitor visitor = new Visitor(null, null, null, null, null);

	ChatIF clientUI;
	public boolean waitForConnection = false;

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;

	}

	public void handleMessageFromServer(Object msg) { // This function gets the msg from the server and prints the table

		System.out.println("--> handleMessageFromServer");

		waitForConnection = false;
		ArrayList<String> st;
		st = (ArrayList<String>)msg;
		if(st.isEmpty())
			ClientGUIController.instance.notFound();
		else {
			visitor.setFname(st.get(0));
			visitor.setLname(st.get(1));
			visitor.setId(st.get(2));
			visitor.setEmail(st.get(3));
			visitor.setteln(st.get(4));
			ClientGUIController.instance.isFound(true);
			}
			
//		try {
//			ClientGUIController.instance.answerForID(st);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();

		}
		//String[] result = st.split("\\s");
//		vis.setId(st.get(0));
//		vis.setFname(st.get(1));
//		vis.setLname(st.get(2));
//		vis.setEmail(st.get(3));
//		vis.setteln(st.get(4));
//		System.out.println(vis);
		
//	}

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
