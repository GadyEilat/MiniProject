package client.controller;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.EmailDetails;
import client.logic.Order;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PageRange;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ChangeOrderDetailsController extends AbstractScenes{
	public Order ord = new Order(null,null,null,null,null,null,null,null,null,null);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	public int wasCanceled = 0; //a flag for telling if you canceled the order.
	Double price;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private Text msgFromController;
    
    @FXML
    private Text priceTxt;
    
    @FXML
    private Text helloTxt;
    
    @FXML
    private URL location;

    @FXML
    private TextField orderNumberTxt;

    @FXML
    private Button applyBtn;

    @FXML
    private Button btnLogout;
    
    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private ComboBox<String> amountOfVisitorsComboBox;
    
    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> parkComboBox;

    @FXML
    private ImageView backBtn;

    @FXML
    private Button orderManagementBtn;

    @FXML
    private Button changeOrderDetailsBtn;

    @FXML
    private Button printDetailsBtn;

    @FXML
    private Button cancelOrderBtn;

    public static ChangeOrderDetailsController instance;
    ObservableList<String> list;
    ObservableList<String> list2;
    ObservableList<String> list3;
    
    public void isGuide() {
    	Double dblAmount = Double.valueOf(amountOfVisitorsComboBox.getSelectionModel().getSelectedItem());
    	Double pricePerPerson = OrderManagementController.instance.pricePerPerson;
    	price= pricePerPerson*(dblAmount-1); //the only difference from the next method...
    	priceTxt.setText(String.format("Price: %.2f", price));
    	ord.setTotalPrice(String.valueOf(price));
    	DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, ord);
		ClientUI.chat.accept(data);
    }
    public void isOther() {
    	Double dblAmount = Double.valueOf(amountOfVisitorsComboBox.getSelectionModel().getSelectedItem());
    	Double pricePerPerson = OrderManagementController.instance.pricePerPerson;
    	price=pricePerPerson*dblAmount;
    	priceTxt.setText(String.format("Price: %.2f", price));
    	ord.setTotalPrice(String.valueOf(price));
    	DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO_REQUEST, ord);
		ClientUI.chat.accept(data);
    }
    
    
    public void updated()
    {
    	msgFromController.setFill(Color.GREEN);
		msgFromController.setText("Updated Successfully");
    	DataTransfer data = new DataTransfer(TypeOfMessage.CHECK_KIND, ord.getID());
		ClientUI.chat.accept(data);
    	priceTxt.setText(String.format("Price: %.2f", price));
    	
    	//sending a mail
    	String toSend = "You Successfully updated your order details " + ord.getNameOnOrder() + ". <br>The new order details are:<br>Order Number: " +
    	ord.getOrderNumber() + "<br>Park: " + ord.getParkName() + "<br>Date: " + ord.getDate()+ "<br>Time: " + ord.getHour() + "<br>Amount of visitors: " +
    	ord.getNumOfVisitors();
    	EmailDetails details= new EmailDetails(ord.getEmail(),"GoNature Updated Order",toSend);
    	DataTransfer maildata = new DataTransfer(TypeOfMessage.SENDMAIL, details);
		ClientUI.chat.accept(maildata);
    }
    
    public void notUpdated()
    {
    	msgFromController.setFill(Color.RED);
		msgFromController.setText("Couldn't update");
    }
    
    @FXML
    void Apply(ActionEvent event) {
    	ord.setNumOfVisitors(amountOfVisitorsComboBox.getSelectionModel().getSelectedItem());
    	String save = OrderManagementController.instance.ord.getDate();
		ord.setDate(datePicker.getValue().toString());
		if (java.time.LocalDate.now().isAfter(datePicker.getValue())) {
			msgFromController.setFill(Color.RED);
			msgFromController.setText("Invalid Date");
			ord.setDate(save);
			datePicker.setValue(LOCAL_DATE(save));
		} else {
			ord.setNumOfVisitors(amountOfVisitorsComboBox.getSelectionModel().getSelectedItem());
			ord.setHour(timeComboBox.getSelectionModel().getSelectedItem());
			ord.setParkName(parkComboBox.getSelectionModel().getSelectedItem());
			DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, ord);
			ClientUI.chat.accept(data);
		}
	}

    @FXML
    void CancelOrder(ActionEvent event) throws IOException{
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
    void OrderManagement(ActionEvent event) { //same as last button, but in different position
    	switchScenes("/client/boundaries/Order Management.fxml", "Order Management");
    }

    public static void printCurrWindow(Window myWindow) {
    	print(myWindow, myWindow.getScene().getRoot().snapshot(null,null));
    }
    
    @FXML
    void PrintDetails(ActionEvent event) {
    	printCurrWindow(printDetailsBtn.getScene().getWindow());
    }
    
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
    
    @FXML
    void Exit(ActionEvent event) {
    	ChatClient.order = new Order();
    	switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    }
    
    private void setParkComboBox() {
    	ArrayList<String> al = new ArrayList<String>();	
		al.add("Park1");
		al.add("Park2");
		al.add("Park3");
		list = FXCollections.observableArrayList(al);
		parkComboBox.setItems(list);
    }
    
    private void setTimeComboBox() {
    	ArrayList<String> al2 = new ArrayList<String>();
    	al2.add("8:00");
    	al2.add("9:00");
    	al2.add("10:00");
    	al2.add("11:00");
    	al2.add("12:00");
    	al2.add("13:00");
    	al2.add("14:00");
    	al2.add("15:00");
    	list2=FXCollections.observableArrayList(al2);
    	timeComboBox.setItems(list2);
    	
    }
    
    private void setAmountOfVisitorsComboBox() {
    	ArrayList<String> al3 = new ArrayList<String>();
    	al3.add("1");
    	al3.add("2");
    	al3.add("3");
    	al3.add("4");
    	al3.add("5");
    	al3.add("6");
    	al3.add("7");
    	al3.add("8");
    	al3.add("9");
    	al3.add("10");
    	al3.add("11");
    	al3.add("12");
    	al3.add("13");
    	al3.add("14");
    	al3.add("15");
    	list3=FXCollections.observableArrayList(al3);
    	amountOfVisitorsComboBox.setItems(list3);
    }
    
    public static final LocalDate LOCAL_DATE (String dateString){ //method for dealing with dates.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    public void initialize(URL location, ResourceBundle resources) {
    	instance=this;
    	ord= OrderManagementController.instance.ord;
    	setParkComboBox(); // call a func above.
    	parkComboBox.getSelectionModel().select(ord.getParkName());
    	orderNumberTxt.setText(ord.getOrderNumber());
    	setAmountOfVisitorsComboBox(); //call func above
    	amountOfVisitorsComboBox.getSelectionModel().select(ord.getNumOfVisitors()); 
    	helloTxt.setText("Hello " + ord.getNameOnOrder());
    	setTimeComboBox(); // call func above.
    	timeComboBox.getSelectionModel().select(ord.getHour());
    	priceTxt.setText(String.format("Price: %.2f", OrderManagementController.instance.price));
    	ord.setTotalPrice(priceTxt.getText());
    	try {
            datePicker.setValue(LOCAL_DATE(ord.getDate()));
        } catch (NullPointerException e) {}
    }
}
