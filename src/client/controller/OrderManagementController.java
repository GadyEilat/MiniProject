package client.controller;
import java.io.IOException;
import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.Order;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PageRange;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
/**
 * Order Management window tells you the details of your existing order.
 * @author Gady
 *
 */
public class OrderManagementController extends AbstractScenes {
	public Order ord = new Order(null,null,null,null,null,null,null,null,null,null,null,null);
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
    /**
     * tells us if the order belongs to a subscriber (comes back from chat client)
     * @param ans the answer (should be true)
     */
    public void isSubscriber(Boolean ans) {
    	dblAmount = Double.valueOf(ord.getNumOfVisitors());
    	if (ans) {
    		pricePerPerson = price*0.85*0.80;
        	price= pricePerPerson*dblAmount;
    	}
    	priceTxt.setText(String.format("Price: %.2f", price));
    		
    }
    /**
     * tells us if the order belongs to a guide (comes back from chat client)
     * @param ans should be true
     */
    public void isGuide(Boolean ans) { //not including "Tashlum Merosh", not sure how to handle that.
    	dblAmount = Double.valueOf(ord.getNumOfVisitors());
    	if (ans) {
    		pricePerPerson = price*0.75;
        	price= pricePerPerson*(dblAmount-1);
    	}
    	priceTxt.setText(String.format("Price: %.2f", price));
    		
    }
    /**
     * tells us if the order belongs to a regular traveler (comes back from chat client)
     * @param ans should be true
     */
    public void isRegular(Boolean ans) {
    	dblAmount = Double.valueOf(ord.getNumOfVisitors());
    	if (ans) {
    		pricePerPerson = price*0.85;
    		price=pricePerPerson*dblAmount;
    	}
    	priceTxt.setText(String.format("Price: %.2f", price));
    		
    }
    
    /**
     * happens when you click on cancel button - pops up a new small cancel order window
     * @param event when cancel button clicked
     * @throws IOException 
     */
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
/**
 * move to change order details window where the traveler can change he's order details
 * @param event if the change order details button was clicked
 */
    @FXML
    void ChangeOrderDetails(ActionEvent event) {
    	switchScenes("/client/boundaries/Change Order Details.fxml", "Change Order Details");
    }
    /**
     * function for handling with prints.
     * @param myWindow the current displayed window
     */
    public static void printCurrWindow(Window myWindow) {
    	print(myWindow, myWindow.getScene().getRoot().snapshot(null,null));
    }
    /**
     * goes to the function above for printing the window. and happens when you click on print button
     * @param event if you click on print button
     */
    @FXML
    void PrintDetails(ActionEvent event) {
    	printCurrWindow(printDetailsbtn.getScene().getWindow());
    }
    /**
     * function for printing our current window, called from above.
     * @param myWindow the current window
     * @param screenshot
     */
    private static void print(Window myWindow, WritableImage screenshot) { 
    	PrinterJob job = PrinterJob.createPrinterJob();
    	if (job!=null) {
    		job.getJobSettings().setPageRanges(new PageRange(1,1));
    		if (!job.showPageSetupDialog(myWindow)|| !job.showPrintDialog(myWindow)) {
    			return;
    		}
    		final PageLayout pageLayout = job.getJobSettings().getPageLayout();
    		final double sizeX = pageLayout.getPrintableWidth() / screenshot.getWidth();
    		final double sizeY = pageLayout.getPrintableHeight() / screenshot.getHeight();
    		final double size = Math.min(sizeX, sizeY);
    		final ImageView print_node = new ImageView(screenshot);
    		print_node.getTransforms().add(new Scale(size,size));
    		job.printPage(print_node);
    		job.endJob();
    	}
    }

    /**
     * button exit, for going back to Existing order window.
     * @param event if the button was clicked
     */
    @FXML
    void Exit(ActionEvent event) {
    	ChatClient.order = new Order();
    	switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    }
    /**
     * initialize the window
     */
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
