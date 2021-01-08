package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import client.logic.EmailDetails;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TourGuideOrderSController extends AbstractScenes {
	
	/** Description of TourGuideOrderSController 
	• * This controller responsible to show the user his new order details.
	• * After that he can either move to main menu or see his orders.
	• * @author Elad Kobi
	• */
	
	
	
	TourGuide tourguide;
	TourGuideOrder tourguideorder;
	//TourGuideNewOrderController orderup= new TourGuideNewOrderController();
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button viewOrderTourBtn;

    @FXML
    private Button returnToMenuTourBTN;

    @FXML
    private Button LogOutBtn;

    @FXML
    private Button updateDetalisGuideBtn;

    @FXML
    private Button NewOrderBtn;

    @FXML
    private Button myOrdersBtn;
    
    @FXML
    private Text TourNo;
    
    @FXML
    private TextField dateOrderGuide;

    @FXML
    private TextField orderNumberGuide;

    @FXML
    private TextField guideNameOrderS;
    

    @FXML
    private TextField txtOrderNum;
    
//    public void recieve(){
//       // System.out.println("I recieved "+data);
//        this.dateOrderGuide.setText(orderup.tourguideorderr.getDate());
//        this.orderNumberGuide.setText(orderup.tourguideorderr.getOrderNumber());
//    }
    /** Description of loadGuide 
    • * This function shows the order details and sends a mail to the user about it.
    • * @param  tourguideO the tourguide who made the order.
    • * @param newOrderG the order that has been made.
    • * 
    • */
    
    public void loadGuide(TourGuide tourguideO, TourGuideOrder newOrederG) {
    	this.tourguide = tourguideO;
    	this.tourguideorder=newOrederG;
         this.TourNo.setText(tourguideO.getFname());
         this.guideNameOrderS.setText(tourguideO.getFname());
         this.dateOrderGuide.setText(newOrederG.getDate());
         this.txtOrderNum.setText(newOrederG.getOrderNumber());
         
         
     	if(newOrederG.getPrePaid()=="No") {
			double orderPayment=((Double.valueOf(newOrederG.getNumOfVisitors())-1)*22.5);
			String tourPayment=(String.format("%.2f", orderPayment));
			 this.orderNumberGuide.setText(tourPayment);
			}
			else {
			double orderPayment=(((Double.valueOf(newOrederG.getNumOfVisitors())-1)*22.5)*0.88);
			String tourPayment=(String.format("%.2f", orderPayment));
			 this.orderNumberGuide.setText(tourPayment);
			}
     	   
           String toSend = "You successfully created an order. " + newOrederG.getNameOnOrder()
			+ ".<br>The order details are:<br>Order Number: " + newOrederG.getOrderNumber() + "<br>Park: "
			+ newOrederG.getParkName() + "<br>Date: " + newOrederG.getDate() + "<br>Time: "
			+ newOrederG.getTime() + "<br>Amount of visitors: " + newOrederG.getNumOfVisitors();
	        EmailDetails details = new EmailDetails(newOrederG.getEmail(), "GoNature New Order", toSend);
	        DataTransfer maildata = new DataTransfer(TypeOfMessage.SENDMAIL, details);
	        ClientUI.chat.accept(maildata);

    }

    @FXML
    void LogOutButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideLogin.fxml", "Login");
    }

    @FXML
    void NewOrderButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideNewOrder.fxml", "New Order");
    }

    @FXML
    void myOrdersButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMyOrders.fxml", "My Orders");
    }

    @FXML
    void returnToMenuTourButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "Main Menu");
    }

    @FXML
    void updateDetalisGuideButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideChangeDetails.fxml", "Change Details");
    }

    @FXML
    void viewOrderTourButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMyOrders.fxml", "My Orders");
    }
    
    @FXML
    void changeOrder(ActionEvent event) {
    	switchScenes("/client/boundaries/Existing Order.fxml", "New Order");
    }
	  /** Description of initialize 
    • *@see https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html
    • */
    
    @Override
  		public void initialize(URL location, ResourceBundle resources) {
  	    	loadGuide(ChatClient.tourguide, TourGuideNewOrderController.tourguideorderr);
  	    	
    }
    
    
    
 
}
