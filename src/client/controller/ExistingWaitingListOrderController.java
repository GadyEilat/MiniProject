package client.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.Order;
import client.logic.WaitingList;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * ExistingWaitingListOrderController Class: The window relates to an existing order that is in the waiting list
 * he gets here by entering his order number in the previous window. In this window he can check his
 * order details and approve his arrival if a place became available for him.
 * The controller expands the AbstractScenes class that replaces the scenes within the main stage.
 * @author Gady
 *
 */
public class ExistingWaitingListOrderController extends AbstractScenes{
	
	public WaitingList orderWL = new WaitingList(null,null ,null ,null ,null ,null ,null ,null ,null ,null ,null, null);
	int myFlag=0; //flag for telling if an order had already become approved.
	
	@FXML
    private TextField orderNumberTxt;

    @FXML
    private TextField timeTxt;

    @FXML
    private TextField dateTxt;

    @FXML
    private TextField parkTxt;

    @FXML
    private TextField amountOfVisitorsTxt;

    @FXML
    private Button approveBtn;

    @FXML
    private Text approveText;

    @FXML
    private Text helloTxt;

    @FXML
    private Button btnLogout;


    public static ExistingWaitingListOrderController instance;
    
    /**
     * try to approve the arrival, will only work if you received a mail for approval
     * not more than a hour ago.
     * @param event
     */
    @FXML
    void Approve(ActionEvent event) {
		if (!(orderWL.getNeedsToApprove().equals("NeedsTo")) && myFlag==0) { //if it ain't true. (false/null)
			approveText.setFill(Color.RED);
			approveText.setText("You can't approve your order now!");
		}
		else if (orderWL.getNeedsToApprove().equals("NeedsTo")){ 
			approveText.setFill(Color.DARKGREEN);
			approveText.setText("Order was approved!");
			myFlag=1;
			orderWL.setNeedsToApprove("Approved");
			DataTransfer data = new DataTransfer(TypeOfMessage.APPROVED_WL, orderWL);
			ClientUI.chat.accept(data);
		}

    }
    /**
     * method for translating String to LocalDate
     * @param dateString
     * @return
     */
    public static final LocalDate LOCAL_DATE (String dateString){ //method for dealing with dates.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    /**
     * Exit the current stage and go back to Existing order window.
     * @param event
     */
    @FXML
    void Exit(ActionEvent event) {
    	ChatClient.order = new Order();
    	switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    }
    /**
     * initialize the window.
     */
    public void initialize(URL location, ResourceBundle resources) {
    	instance=this;
    	orderWL=ExistingOrderController.instance.orderWL;
    	parkTxt.setText(orderWL.getParkName());
    	orderNumberTxt.setText(orderWL.getOrderNumber());
    	amountOfVisitorsTxt.setText(orderWL.getNumOfVisitors());
    	helloTxt.setText("Hello " + orderWL.getNameOnOrder());
    	timeTxt.setText(orderWL.getTime());
    	dateTxt.setText(orderWL.getDate());
    }

}
