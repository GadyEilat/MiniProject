package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import client.logic.TourGuide;
import client.logic.Visitor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
/** Description of TourChangeDetailsController 
• * This controller is the main menu of the TourGuide.
• * The user have few options here:
• * Change his details, view his order or make a new order.
• * @author Elad Kobi
• */
public class TourGuideMenuController extends AbstractScenes  {
	TourGuide tourguide;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LogOutBtn;

    @FXML
    private Text tourName;
    @FXML
    private Button updateDetalisGuideBtn;

    @FXML
    private Button NewOrderBtn;

    @FXML
    private Button myOrdersBtn;
    @FXML
    private Button changeOrdersGuide;
    
    
    /** Description of loadGuideMenuChange 
     *@param tourguide this function gets an entity on a TourGuide
     *  and prints his details to the screen.
     */
    public void loadGuide(TourGuide tourguidee) {
    	this.tourguide = tourguidee;
        this.tourName.setText("Hello"+" "+tourguide.getFname());
    }
    /** Description of LogOutButton 
     *@param event log out button.
     */
    @FXML
    void LogOutButton(ActionEvent event) {
    	
    	switchScenes("/client/boundaries/TourGuideLogin.fxml", "Login");
    }
    /** Description of NewOrderButton 
     *@param event move to New Order screen.
     */
    @FXML
    void NewOrderButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideNewOrder.fxml", "New Order");
    }
    /** Description of TourGuideMyOrders 
     *@param event button to move to TourGuideMyOrders.
     */
    @FXML
    void myOrdersButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMyOrders.fxml", "My Orders");
    }
    /** Description of updateDetalisGuideButton 
     *@param event button to move to TourGuideMyOrders.
     */
    @FXML
    void updateDetalisGuideButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideChangeDetails.fxml", "Change Details");
         
    }    

    /** Description of initialize 
     *@see https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html
     * loadGuide method is called.
     */

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	loadGuide(ChatClient.tourguide);

	}
    
    
    

}

