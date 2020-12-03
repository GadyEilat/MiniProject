package server;

import javafx.application.Application;
import javafx.stage.Stage;
import server.Controller.*;
import server.EchoServer;


public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;

	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub				  		
		ServerController server = new ServerController(); // create server frame
		server.start(primaryStage);
	}
	
	public static void runServer(int p)
	{
		 int port = 0; //Port to listen on

	        try
	        {
	        	
//	        	port = Integer.parseInt(p); //Set port to 5555
	        	port = p;	// set port to 5555 default or other number
	        }
	        catch(Throwable t)
	        {
	        	ServerController.instance.displayMsg("ERROR - Could not connect!");
	        }
	        
	        
	        try 
	        {
	        	
	        	EchoServer sv = new EchoServer(port);
	        	sv.listen(); //Start listening for connections
	        	System.out.println("Server listening for connections on port " + port);
	        } 
	        catch (Exception ex) 
	        {
	        	ServerController.instance.displayMsg("ERROR - Could not listen for clients!");
	        }
	}
	

}
