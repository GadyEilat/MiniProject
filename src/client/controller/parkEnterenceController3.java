
package client.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.ParkStatus;
import client.logic.Worker;
import client.logic.casualOrder;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


/** Description of parkEnterenceController3 
• *
• * @author Elad Kobi
• * 
• * 
• */

public class parkEnterenceController3 extends AbstractScenes {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
	public static parkEnterenceController3 instance;
	ParkStatus status;

	    @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private Text getNumPpl2;

	    @FXML
	    private TextField txtOrderNumber;

	    @FXML
	    private Text errormsg;

	    @FXML
	    private Button LogOutBtn;

	    @FXML
	    private Text txtWorkerName;

	    @FXML
	    void ExistenceOrder(ActionEvent event) {
	    	switchScenes("/client/boundaries/WorkerParkEnternece2.fxml", "Enternece");
	    }

	    
	    /** Description of LogOut 
	    • *
	    • * @param LogOut button logs out to the main menu.
	    • */
	    
	    
	    
	    @FXML
	    void LogOutButton(ActionEvent event) {
	    	DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
			ClientUI.chat.accept(data);
			ChatClient.worker = new Worker(null, null, null, null, null, null);
			ChatClient.connected = false;

	    	switchScenes("/client/boundaries/main.fxml", "Enternece");
	    }

	    @FXML
	    void NewOrder(ActionEvent event) {
	    	switchScenes("/client/boundaries/WorkerParkEnternece.fxml", "Enternece");
	    	
	    }
	    /** Description of completeOrder2 
	    • *@param Button that checks if first the order number is in the park
	    • * 
	    • */
	    @FXML
	    void completeOrder2(ActionEvent event) {
           status.setAmount(txtOrderNumber.getText());
           DataTransfer data = new DataTransfer(TypeOfMessage.GETCASUALORDER,status);
	    	ClientUI.chat.accept(data);
	    }
	    /** Description of finishExitPark 
	    • *@param order finish the exit park process
	    • * 
	    • */
	   public void finishExitPark(casualOrder order) {
	    	String check=order.getOrderNumber();
	    	if(check == null)
	    		errormsg.setText("Error with order number");
	    	else {
	    		DataTransfer data = new DataTransfer(TypeOfMessage.PARKEXITSTATUS,order);
		    	ClientUI.chat.accept(data);
		    	errormsg.setText("Thank you for visit, see you again soon!");
	    	}
	    }
	   
	   public void errorOrder() {
		   errormsg.setText("Error with order number");
	   }

	    /** Description of initialize 
	    • *@see https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html
	 
	    • */
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    	instance = this;
	    	status = new ParkStatus(dtf.format(now), ChatClient.worker.getPark().getNumberOfPark(), null, null, null);
 	
	    }
	}


