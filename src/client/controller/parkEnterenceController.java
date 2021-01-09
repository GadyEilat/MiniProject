package client.controller;


import client.ChatClient;
import client.ClientUI;
import client.logic.Order;
import client.logic.ParkStatus;
import client.logic.Worker;
import client.logic.casualOrder;
import common.DataTransfer;
import common.TypeOfMessage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

/**
 * parkEnterenceController class
 * @author Elad
 *This controller is responsible for the screen displaying the entering to the park on a casual visit.
 *The controller expands the AbstractScenes class that replaces the scenes within the main stage.
 *It is possible to log out of the main menu. 
 *It is possible to go to the screen where you can check an order details.
 */



public class parkEnterenceController extends AbstractScenes {
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDateTime now = LocalDateTime.now();
	    //wait.setTimeOfEnterence(dtf.format(now));
	    public String discountDay=null;
	    double casualPrice= 30;
	    public String maxPark;

	    ParkStatus status;
	    @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TextField txtName;

	    @FXML
	    private TextField txtNumOfVis;

	    @FXML
	    private Text getNumPpl;

	    @FXML
	    private Button btnLogout;
	    
	    @FXML
	    private ComboBox<String> selectT;
	    
	    @FXML
	    private Text txtWorkerName;
	   
	    ObservableList<String> list;
	  
	    @FXML
	    void parkExitB(ActionEvent event) {
	    	switchScenes("/client/boundaries/WorkerParkEnternece3.fxml", "Enternece");
	    }
	    
	    
	    
	    /** Description of resetStatus
	     * @param event Button that resets the park vistors number
	     * on the park 
	    */
	    
	    @FXML
	    void resetStatus(ActionEvent event) {
	    	DataTransfer reset = new DataTransfer(TypeOfMessage.RESETPARKSTATUS,status.getPark());
	    	switchScenes("/client/boundaries/WorkerParkEnternece.fxml", "Enternece");
	    	ClientUI.chat.accept(reset);
	    }
	    
	    @FXML
	    void ExistenceOrder(ActionEvent event) {
	    	switchScenes("/client/boundaries/WorkerParkEnternece2.fxml", "Enternece");
	    }
		
	    
	    
	    public static parkEnterenceController instance;
	    
	    

	    @FXML
	    void LogOutButton(ActionEvent event) {
	    	DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
			ClientUI.chat.accept(data);
			ChatClient.worker = new Worker(null, null, null, null, null, null);
			ChatClient.connected = false;
	    	switchScenes("/client/boundaries/main.fxml", "Enternece");
	    }
	    /** Description of completeOrder 
	     * @param event Button complete the casual visit at the park
	     * The details are inserted into the order entity and
	     * after that all the details are been sent to the data base.
	     */
	    @FXML
	    void completeOrder(ActionEvent event) {
	    	DateTimeFormatter drf = DateTimeFormatter.ofPattern("HH:mm:ss");
  		    LocalDateTime noww = LocalDateTime.now();
	    	ParkStatus status1=new ParkStatus(null,null,null,null, null);
	    	status1.setPark(status.getPark());
	    	status1.setAmount((txtNumOfVis.getText()));
	    	status1.setDate(status.getDate());
	    	casualOrder order=new casualOrder(null,null,null,null,null,null, null,null);
	    	order.setPark(status.getPark());
	    	order.setDate(status.getDate());
	    	order.setTime(drf.format(noww));
	    	order.setOrderKind(selectT.getValue());
	    	order.setNumOfVis(txtNumOfVis.getText());
	    	double price=checkFinalPrice(order.getOrderKind(), discountDay, status1.getAmount());
	    	order.setPayment(String.valueOf(String.format("%.2f", price)));
	    	order.setOrderNumber(generateRandomChars("123456789", 5));
	
	    	DataTransfer data = new DataTransfer(TypeOfMessage.PARKENTERSENDSTATUS,status1);
	    	ClientUI.chat.accept(data);
	    	DataTransfer data2 = new DataTransfer(TypeOfMessage.CASUALVISITUPDATE,order);
	    	ClientUI.chat.accept(data2);
	    	
	    	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setHeaderText(null);
        	alert.setContentText("New visit created succsesfully. Price ="+price);
        	alert.show();
	  
	    }
	    
	    
	   private void typeOfT() {
	    	ArrayList<String> al = new ArrayList<String>();	
			al.add("Regular");
			al.add("Subscriber");
			al.add("Tourguide");
			
			list = FXCollections.observableArrayList(al);
			selectT.setItems(list);
	    }
	    
	    
	    private void alertMessage(){
	    	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setHeaderText(null);
        	alert.setContentText("Fields missing");
        	alert.show();	
	    }
	    /** Description of insertData 
	     *
	     * @param t Gets the number of visitors in park from data base.
	     * @param b gets the total number of visitors that allowed in park.
	     * The method will print the total number of visitors in the park.
	     */
	    public void insertData(String t, String b) {
	    	getNumPpl.setText(t+"/"+b);
	    	maxPark=b;
	    	//parkEnterenceController2.instance.insertData(t);
	    	
	    }
	    
	    /** Description of insertData 
	    • *
	    • * @param t Gets the discount of the park for the day.
	    • * 
	    • * 
	    • */
	    public void getDiscountDay(String t) {
	    	discountDay=t;	
	    }
	    
	    /** Description of insertData 
	     *
	     * @param candidateChars Chars for the order number.
	     * @param length Length of the order number.
	     * @return Returns an order number
	     */
		public static String generateRandomChars(String candidateChars, int length) {
		    StringBuilder sb = new StringBuilder();
		    Random random = new Random();
		    for (int i = 0; i < length; i++) {
		        sb.append(candidateChars.charAt(random.nextInt(candidateChars
		                .length())));
		    }

		    return sb.toString();
		}
		
		  /** Description of insertData
		   * This method will return the price of the casual visit. 
	     *@param numofppl number of people in the order.
	     * @param type Type of visitor.
	     * @param dis Daily discount if there is one.
	     * @return Returns an order number
	    */
		public double checkFinalPrice(String type, String dis, String numofppl)
		{
			if(type=="Regular") {
				
				if(dis!=null) {	
					Double dd=100-(Double.valueOf(dis));
					return ((Double.valueOf(numofppl)*casualPrice)*(dd/100));
				}
				else 
					return (Integer.valueOf(numofppl)*casualPrice);
			}
			
			if(type=="Subscriber") {
				Double subPrice=casualPrice*0.80;
				if(dis!=null) {
					Double dd=100-(Double.valueOf(dis));
					return (Double.valueOf(numofppl)*subPrice*(dd/100));
				}
				else 
					return (Integer.valueOf(numofppl)*subPrice);
			}
			
			if(type=="Tourguide") {
				Double tourPrice=casualPrice*0.90;
				if(dis!=null) {
					Double dd=100-(Double.valueOf(dis));
					return (Double.valueOf(numofppl)*tourPrice*(dd/100));
				}
				else 
					return (Integer.valueOf(numofppl)*tourPrice);
			}
		
			return 0;
		}
	    
		  /** Description of initialize 
	    • *@see https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html
	     * The worker details are been saved.
	     * The combo box is beeing initialized.
	     * The name of the worker is printed on screen.
	     */
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    	instance = this;
	    	status = new ParkStatus(dtf.format(now), ChatClient.worker.getPark().getNumberOfPark(), null, null, null);
	    	DataTransfer data = new DataTransfer(TypeOfMessage.PARTKENTERGETSTATUS,status);
	    	ClientUI.chat.accept(data);
	    	typeOfT();
	    	txtWorkerName.setText("Hello"+" "+ChatClient.worker.getWorkerName());
	    	//DataTransfer data5 = new DataTransfer(TypeOfMessage.CheckDiscounts,status);
	    	//ClientUI.chat.accept(data5);
		}
	}


