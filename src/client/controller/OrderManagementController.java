package client.controller;
import java.net.URL;

import java.util.ResourceBundle;

import client.ClientUI;
import client.logic.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class OrderManagementController extends AbstractScenes {
	public Order ord = new Order(null,null,null,null,null,null,null);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField orderNumberTxt;

    @FXML
    private TextField amountOfVisitorsTxt;
    
    @FXML
    private Text helloTxt;
    
    @FXML
    private Button changeBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private TextField timeTxt;

    @FXML
    private TextField dateTxt;

    @FXML
    private TextField parkTxt;

    @FXML
    private ImageView existingOrderBackBtn;

    @FXML
    private Button orderManagementBtn;

    @FXML
    private Button changeOrderDetailsBtn;

    @FXML
    private Button printDetailsbtn;

    @FXML
    private Button cancelOrderBtn;

    public static OrderManagementController instance;
    
	@FXML
	void CancelOrder(ActionEvent event) {
		switchScenes("/client/boundaries/Cancel Confirmation.fxml", "Cancel Confirmation");
	}

    

    @FXML
    void ChangeOrderDetails(ActionEvent event) {
    	switchScenes("/client/boundaries/Change Order Details.fxml", "Change Order Details");
    }

    @FXML
    void PrintDetails(ActionEvent event) {
    	//fix
    }

    @FXML
    void Exit(ActionEvent event) {
    	switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    }
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    	instance=this;
    	ord=ExistingOrderController.instance.order;
//		OrderManagementController.instance.ord.setDate(recievedOrd.getDate());
//		OrderManagementController.instance.ord.setEmail(recievedOrd.getEmail());
//		OrderManagementController.instance.ord.setHour(recievedOrd.getHour());
//		OrderManagementController.instance.ord.setNameOnOrder(recievedOrd.getNameOnOrder());
//		OrderManagementController.instance.ord.setNumOfVisitors(recievedOrd.getNumOfVisitors());
//		OrderManagementController.instance.ord.setOrderNumber(recievedOrd.getOrderNumber());
//		OrderManagementController.instance.ord.setParkName(recievedOrd.getParkName());
    	parkTxt.setText(ord.getParkName());
    	orderNumberTxt.setText(ord.getOrderNumber());
    	amountOfVisitorsTxt.setText(ord.getNumOfVisitors());
    	helloTxt.setText("Hello " + ord.getNameOnOrder());
    	timeTxt.setText(ord.getHour());
    	dateTxt.setText(ord.getDate());
    }
}
