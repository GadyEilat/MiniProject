package client.controller;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.Order;
import client.logic.TourGuideOrder;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderManagementController extends AbstractScenes {
	public Order ord = new Order(null,null,null,null,null,null,null,null);
	public int wasCanceled = 0;
	Double price=30.00, pricePerPerson, dblAmount;
	String strPrice=null;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text priceTxt;
    
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
    
    public void isSubscriber(Boolean ans) {
    	dblAmount = Double.valueOf(ord.getNumOfVisitors());
    	if (ans) {
    		pricePerPerson = price*0.85*0.80;
        	price= pricePerPerson*dblAmount;
    	}
    	priceTxt.setText(String.format("Price: %.2f", price));
    		
    }
    public void isGuide(Boolean ans) { //not including "Tashlum Merosh", not sure how to handle that.
    	dblAmount = Double.valueOf(ord.getNumOfVisitors());
    	if (ans) {
    		pricePerPerson = price*0.75;
        	price= pricePerPerson*dblAmount;
    	}
    	priceTxt.setText(String.format("Price: %.2f", price));
    		
    }
    public void isRegular(Boolean ans) {
    	dblAmount = Double.valueOf(ord.getNumOfVisitors());
    	if (ans) {
    		pricePerPerson = price*0.85;
    		price=pricePerPerson*dblAmount;
    	}
    	priceTxt.setText(String.format("Price: %.2f", price));
    		
    }
    
	@FXML
	void CancelOrder(ActionEvent event) throws IOException {
		Stage helpWindow = new Stage();
		FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/client/boundaries/Cancel Confirmation.fxml"));
		Parent current = fxmlLoad.load();
		helpWindow.initModality(Modality.APPLICATION_MODAL);
		helpWindow.setTitle("Cancel Confirmation");
		Scene scene = new Scene(current);
		helpWindow.setMinHeight(230);
		helpWindow.setMinWidth(350);
		helpWindow.setMaxHeight(230);
		helpWindow.setMaxWidth(350);
		helpWindow.setScene(scene);
		helpWindow.showAndWait();
	}

    @FXML
    void ChangeOrderDetails(ActionEvent event) {
    	switchScenes("/client/boundaries/Change Order Details.fxml", "Change Order Details");
    }

    @FXML
    void PrintDetails(ActionEvent event) {
//    	System.out.println("To Printer!");
//        PrinterJob job = PrinterJob.createPrinterJob();
//        if(job != null){
//        job.showPrintDialog(changeBtn.getScene().getWindow()); 
//        job.printPage(changeBtn.getParent());
//        job.endJob();
//        }
    }

    @FXML
    void Exit(ActionEvent event) {
    	ChatClient.order = new Order();
    	switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    }
    
    public void initialize(URL location, ResourceBundle resources) {
    	instance=this;
    	ord=ExistingOrderController.instance.order;
    	parkTxt.setText(ord.getParkName());
    	orderNumberTxt.setText(ord.getOrderNumber());
    	amountOfVisitorsTxt.setText(ord.getNumOfVisitors());
    	helloTxt.setText("Hello " + ord.getNameOnOrder());
    	timeTxt.setText(ord.getHour());
    	dateTxt.setText(ord.getDate());
    	DataTransfer data = new DataTransfer(TypeOfMessage.CHECK_KIND,ord);
		ClientUI.chat.accept(data);
    }
}
