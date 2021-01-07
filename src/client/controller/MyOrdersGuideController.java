package client.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MyOrdersGuideController extends AbstractScenes {
	
	/** Description of MyOrdersGuideController 
	• *
	• * @author Elad Kobi
	• */
	
	
	
	TourGuide tourguide;
    @FXML
    private ResourceBundle resources;
    @FXML
    private Text TourNameMy;
    @FXML
    private URL location;

    @FXML
    private Button LogOutBtn;

    @FXML
    private Button updateDetalisGuideBtn;

    @FXML
    private Button NewOrderBtn;

    @FXML
    private Button myOrdersBtn;
    
    @FXML
    private Button backButton;
    
    @FXML
    private TableView<TourGuideOrder> TableOrders;

    @FXML
    private TableColumn<TourGuideOrder, String> col_orderNum;

    @FXML
    private TableColumn<TourGuideOrder, String> col_Date;

    @FXML
    private TableColumn<TourGuideOrder, String> col_Park;

    public static MyOrdersGuideController instance;
    
ObservableList <TourGuideOrder> oblist=FXCollections.observableArrayList();
    
/** Description of MyOrdersGuideController 
• *
• * @param tourguideM this function gets an entity of tourguide and prints his details on the screen.
• */
    
    public void loadGuide(TourGuide tourguideM) {
    	this.tourguide = tourguideM;
         this.TourNameMy.setText(tourguideM.getFname());
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
    	
    }

    @FXML
    void updateDetalisGuideButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideChangeDetails.fxml", "GoNature Enter");
    }

    
    @FXML
    void backButtonMyOrders(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "GoNature Enter");
    }
    
    /** Description of initialize 
    • *@see https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html
    • * prints the table.
    • */
    
    @Override
   	public void initialize(URL location, ResourceBundle resources) {
    	instance = this;
       	loadGuide(ChatClient.tourguide);
       	col_Park.setCellValueFactory(new PropertyValueFactory<TourGuideOrder, String>("parkName"));
      	col_orderNum.setCellValueFactory(new PropertyValueFactory<TourGuideOrder, String>("orderNumber"));
      	col_Date.setCellValueFactory(new PropertyValueFactory<TourGuideOrder, String>("Date"));
        //Load dummy data
       //	TableOrders.setItems(getOrder());
      	String guideName=ChatClient.tourguide.getFname();
      	
      	
       	Integer xd=5;
       	DataTransfer data = new DataTransfer(TypeOfMessage.TOURGETORDERS,xd);
       	ClientUI.chat.accept(data);
       	TableOrders.setItems(oblist);
   	}
    /** Description of initialize 
    • *@param TourGuideOrder adds an order to the Observeable list for printing 
    • * prints the table.
    • */

    public void getLine(TourGuideOrder t) {
    	
    	oblist.add(new TourGuideOrder(t.getParkName(), t.getTime(), t.getDate(), t.getNumOfVisitors(), t.getEmail(), t.getOrderNumber(), t.getNameOnOrder(), null, null,null));
    }
    
    
    
    
	private ObservableList<TourGuideOrder> getOrder() {
		ObservableList <TourGuideOrder> oblist=FXCollections.observableArrayList();
          return oblist;
	}
    
    
    
    
    
    

}

