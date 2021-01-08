package client.controller;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.ChatClient;
import client.ClientUI;
import client.logic.TourGuide;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
public class TourChangeDetailsController extends AbstractScenes {
	
	/** Description of TourChangeDetailsController 
	• * This controller responsible Of change details screen.
	• * The user can change few details and send them to the data base.
	• * @author Elad Kobi
	• * 
	• * 
	• */
	
	
	
	TourGuide tourguide;
	
	   @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TextField ChangeName;

	    @FXML
	    private TextField changeLNameTour;

	    @FXML
	    private TextField changeEmailTour;

	    @FXML
	    private Button updateDetailsTourBtn;

	    @FXML
	    private TextField changePhoneTour;

	    @FXML
	    private Button LogOutBtn;

	    @FXML
	    private Text tourNameM;

	    @FXML
	    private Button updateDetalisGuideBtn;

	    @FXML
	    private Button NewOrderBtn;

	    @FXML
	    private Button myOrdersBtn;
	    
	    @FXML
	    private Button backButtonUpdateDetails;
    
    
    public void loadGuide(TourGuide tourguideM) {
    	this.tourguide = tourguideM;
         this.tourNameM.setText(tourguideM.getFname());
    }
    
    /** Description of loadGuideMenuChange 
    • *@param tourguide this function gets an entity on a TourGuide
    • *  and prints his details to the screen.
    • */
    
    public void loadGuideMenuChange(TourGuide tourguide) {
		this.tourguide = tourguide;
		this.ChangeName.setText(tourguide.getFname());
		this.changeLNameTour.setText(tourguide.getLname());
		this.changeEmailTour.setText(tourguide.getEmail());
		this.changePhoneTour.setText(tourguide.getTeln());
	}
    
    @FXML
    void backButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "Main Menu");
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

    /** Description of updateDetailsTourButton 
    • *@param event button thats gets the new details  
    • *  and updates the tourguide details on db
    • */
    
    
    @FXML
    void updateDetailsTourButton(ActionEvent event) {
    	String updatedName = (ChangeName.getText());
    	String updatedLName = (changeLNameTour.getText());
    	String updatedEmail = (changeEmailTour.getText());
    	String updatedPhone = (changePhoneTour.getText());
    	//String updatedID="4";
    	tourguide.setId(tourguide.getId());
    	tourguide.setFname(updatedName);
    	tourguide.setLname(updatedLName);
    	tourguide.setEmail(updatedEmail);
		tourguide.setteln(updatedPhone);
		boolean emailT=validate(updatedEmail);
        boolean idC=checkID(updatedPhone);
		
         if(emailT==false||idC==false) {
        	 Alert alert = new Alert(AlertType.INFORMATION);
         	alert.setHeaderText(null);
         	alert.setContentText("Wrong email or phone number");
         	alert.show();
         }
         else {
       	DataTransfer data = new DataTransfer(TypeOfMessage.TOURGUIDEDETAILS,tourguide);
		ClientUI.chat.accept(data);
		System.out.println("tourguide Updated Successfully");
         }
    }

    
    @FXML
    void changeOrder(ActionEvent event) {
    	switchScenes("/client/boundaries/Existing Order.fxml", "New Order");
    }
    
    @FXML
    void updateDetalisGuideButton(ActionEvent event) {

    }
    @FXML
    void changeEmailTourM(ActionEvent event) {

    }

    @FXML
    void changeLNameTourM(ActionEvent event) {

    }

    @FXML
    void changeNameTourG(ActionEvent event) {

    }

    @FXML
    void changePhoneTourM(ActionEvent event) {

    }
    
    
    /** Description of validate 
    • *@param emailrStr gets an email and  
    • * checking if it's a valid Email 
    • */
  
  	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
  			Pattern.CASE_INSENSITIVE);

  	public static boolean validate(String emailStr) {
  		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
  		return matcher.find();
  	}
    /** Description of alertMessage 
    • * prints an alert. 
    • */
  	private void alertMessage(){
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setHeaderText(null);
    	alert.setContentText("Fields missing");
    	alert.show();	
    }
  	
  	 /** Description of checkID 
    • *@param ID gets an ID and  
    • * checking if it's a valid ID 
    • */
  	
  	 public static boolean checkID(String ID) {
		   if(ID.length()==10 && ID.matches("[0-9]+"))
			   return true;
		   return false;
	   }
    
  	  /** Description of initialize 
	    • *@see https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html
	    • */
    
    @Override
   	public void initialize(URL location, ResourceBundle resources) {
       	loadGuide(ChatClient.tourguide);
       	loadGuideMenuChange(ChatClient.tourguide);

   	}

}




