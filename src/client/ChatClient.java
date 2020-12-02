
package client;

import ocsf.client.*;
import common.ChatIF;
import client.logic.Visitor;

import java.io.*;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {

	public static ArrayList<Visitor> list = new ArrayList<>();

	ChatIF clientUI;
	public boolean waitForConnection = false;

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;

	}

	public void handleMessageFromServer(Object msg) { //This function gets the msg from the server and prints the table
		System.out.println("handleMessageFromServer");
		System.out.println(msg.toString());
		waitForConnection = false;
		String msgO = msg.toString();
		String[] splitFirstTime, splitSecondTime;
		Visitor Visitor;
		if (msgO.isEmpty())
			System.out.println("handleMessageFromServer: msg is empty");
		splitFirstTime = msgO.split(",");
		for (int i = 0; i < splitFirstTime.length; i++) {
			splitSecondTime = splitFirstTime[i].split("\\s");
			Visitor = new Visitor(splitSecondTime[0], splitSecondTime[1], splitSecondTime[2], splitSecondTime[3], splitSecondTime[4]);
			list.add(Visitor);
		}
		System.out.println(list);
	}

	/* public void handleMessageFromClientUI(String message)  
	  {
	    try
	    {
	    	sendToServer(message);
	    }
	    catch(IOException e)
	    {
	      clientUI.display
	        ("Could not send message to server.  Terminating client.");
	      quit();
	    }
	  }*/
	
	
	
	
	
	
	
	
	
	
	public void handleMessageFromClientUI(String message) {
		try {

			if (message.equals("exit")) {
				System.out.println("--> Update the connection");

				try {
					openConnection();
					sendToServer(message);
				} catch (Exception e) {
					System.out.println("--> the server is shutdown");
				}
				quit();
			}
			System.out.println("--> handleMessageFromClientUI --> message: " + message);

			openConnection();
			waitForConnection = true;
			sendToServer(message);
			System.out.println("--> handleMessageFromClientUI --> sendToServer(message)");
			while (waitForConnection) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
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
