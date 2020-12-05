
package client;

import ocsf.client.*;
import common.ChatIF;
import client.controller.ClientGUIController;
import client.controller.DataGuiController;
import client.logic.Visitor;

import java.io.*;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {

	public static Visitor visitor = new Visitor(null, null, null, null, null);

	ChatIF clientUI;
	public boolean waitForConnection = false;

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); 			// Call the superclass constructor
		this.clientUI = clientUI;

	}

	public void handleMessageFromServer(Object msg) {

		System.out.println("--> handleMessageFromServer");

		waitForConnection = false;
		ArrayList<String> st;
		st = (ArrayList<String>)msg;
		if(st.isEmpty())
			ClientGUIController.instance.notFound();
		else {
			//TODO: change to c'tor
			visitor.setFname(st.get(0));
			visitor.setLname(st.get(1));
			visitor.setId(st.get(2));
			visitor.setEmail(st.get(3));
			visitor.setteln(st.get(4));
			ClientGUIController.instance.isFound();

			}
	}


	public void handleMessageFromClientUI(Object msg) {
		try {
			openConnection();
			waitForConnection = true;
			sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
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