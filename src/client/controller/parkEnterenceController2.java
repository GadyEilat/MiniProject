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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

/** Description of parkEnterenceController2 
• *
• * @author Elad Kobi
• * 
• * 
• */


public class parkEnterenceController2 extends AbstractScenes {
	String numOfvis;
	public String discountDay=null;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
	ParkStatus status;
	double casualPrice= 30;
	public casualOrder order= new casualOrder(null,null,null,null,null,null,null);
	 @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private Text getNumPpl2;

	    @FXML
	    private Text errormsg;
	    
	    @FXML
	    private TextField priceTxt;

	    @FXML
	    private TextField txtOrderNumber;

	    @FXML
	    private TextField txtDateOrder;

	    @FXML
	    private TextField txtOrderNumV;

	    @FXML
	    private Button LogOutBtn;
	    
	    @FXML
	    private TextField timeTxt;

	    @FXML
	    private TextField memType;
	    
	    @FXML
	    private TextField parkTxt;
	    
	    @FXML
	    private Text txtWorkerName;

	    public static parkEnterenceController2 instance;
	    
	    
	    
	    @FXML
	    void exitParkB(ActionEvent event) {

	    }
	    
	    @FXML
	    void ExistenceOrder(ActionEvent event) {

	    }

	    @FXML
	    void LogOutButton(ActionEvent event) {
	    	DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
			ClientUI.chat.accept(data);
			ChatClient.worker = new Worker(null, null, null, null, null, null);
			ChatClient.connected = false;

	    	switchScenes("/client/boundaries/main.fxml", "Enternece");
	    }
	    /** Description of completeOrder 
	    • *
	    • * @param event Button that checks the order details for the worker
	    • * 
	    • * 
	    • */
	    @FXML
	    void checkDetails(ActionEvent event) {
	        Order order=new Order(null,null,null,null,null,null,null,null);
	          order.setOrderNumber(txtOrderNumber.getText());
	          if (order.getOrderNumber().trim().isEmpty()) {
	  			errormsg.setText("Please enter an existing order number");
	  		}
	  		else { 
	  			DataTransfer data = new DataTransfer(TypeOfMessage.GETINFOPARKENTER,order);
	  			ClientUI.chat.accept(data);
	  		}
	    }

	    
	    /** Description of completeOrder 
	    • *
	    • * @param event Button that finish the visit order
	    • * 
	    • * 
	    • */
	    
	    
	    @FXML
	    void completeOrder2(ActionEvent event) {
	    	ParkStatus status1=new ParkStatus(null,null,null, null, null);
	    	status1.setPark(status.getPark());
	    	status1.setAmount(numOfvis);
	    	DataTransfer data = new DataTransfer(TypeOfMessage.PARKENTERSENDSTATUS,status1);
	    	ClientUI.chat.accept(data);
	    	
	    	errormsg.setText("New order visit succseed");
	    	finishOrder();
	    }
	    /** Description of insertData 
	    • *
	    • * @param t Gets the number of visitors in park from data base.
	    • * @param b gets the total number of visitors that allowed in park.
	    • * 
	    • */
	    public void insertData(String t, String b) {
	    	getNumPpl2.setText(t+"/"+b);
	    }
	    
	    /** Description of insertData 
	    • *
	    • * @param t an order entity that prints all the details
	    • * 
	    • * 
	    • */
	    
	    public void orderDetails(Order t) {
	    	txtDateOrder.setText(t.getDate());
	    	txtOrderNumV.setText(t.getNumOfVisitors());
	    	timeTxt.setText(t.getHour());
	    	numOfvis=t.getNumOfVisitors();
	    	memType.setText(t.getOrderKind());
	    	parkTxt.setText(t.getParkName());
	    	 if(t.getPrePaid().compareTo("No")==0) {
	             double price=checkFinalPrice(t.getOrderKind(), discountDay, t.getNumOfVisitors());
	             priceTxt.setText(String.valueOf(price));
	             }
	             if(t.getPrePaid().compareTo("Yes")==0) {
	             	priceTxt.clear();
	             }
	    	continueOrder(t);
	    	
	    }
	    
	    
	    public void finishOrder() {
	    	DataTransfer data2 = new DataTransfer(TypeOfMessage.CASUALVISITUPDATE,order);
	    	ClientUI.chat.accept(data2);
	    	order=new casualOrder(null,null,null,null,null,null,null);
	    }
	    /** Description of insertData 
	    • *
	    • * @param t an order entity that prints all the details
	    • * 
	    • * 
	    • */
	    public void continueOrder(Order t) {
	    	DateTimeFormatter drf = DateTimeFormatter.ofPattern("HH:mm:ss");
  		    LocalDateTime noww = LocalDateTime.now();
            order.setPark(t.getParkName());
            order.setDate(t.getDate());
            order.setTime(drf.format(noww));
            //order.setOrderKind(t.getKindOfVisitor);//fix
            order.setOrderNumber(t.getOrderNumber());
            order.setOrderKind(t.getOrderKind());
           
            //DataTransfer data2 = new DataTransfer(TypeOfMessage.CASUALVISITUPDATE,order);
	    	//ClientUI.chat.accept(data2);
	    }
	    
	    
	    
	    //רעיון למחר: לעשות פונקצייה שתרוץ באינטשלייז שתחזיר משתנה לקליינט שיחזיק הנחה יומית ואז אמשוך אותו ואחשב הנחה
	    /** Description of insertData 
	    • * @param t String from the data base that holds a discount if there is one. 
	    • */
	    public void getDiscountDay(String t) {
	    	discountDay=t;	
	    }
	    
//	    private void alertMessage(){
//	    	Alert alert = new Alert(AlertType.INFORMATION);
//        	alert.setHeaderText(null);
//        	alert.setContentText("Fields missing");
//        	alert.show();	
//	    }
		  /** Description of insertData 
	    • *@param numofppl number of people in the order.
	    • * @param type Type of visitor.
	    • * @param dis Daily discount if there is one.
	    • * @return Returns an order number
	    • */
		public double checkFinalPrice(String type, String dis, String numofppl)
		{
			if(type.compareTo("Regular")==0) {
				Double regularPrice=casualPrice*0.85;
				if(dis!=null) {
					Double dd=100-(Double.valueOf(dis));
					return ((Double.valueOf(numofppl)*regularPrice)*(dd/100));
				}
				else 
					return (Integer.valueOf(numofppl)*regularPrice);
			}
			
			if(type.compareTo("Subscriber")==0) {
				Double subPrice=casualPrice*0.65;
				if(dis!=null) {
					Double dd=100-(Double.valueOf(dis));
					return (Double.valueOf(numofppl)*subPrice*(dd/100));
				}
				else 
					return (Integer.valueOf(numofppl)*subPrice);
			}
			
			if(type.compareTo("TourGuide")==0) {
				Double tourPrice=casualPrice*0.75;
				if(dis!=null) {
					Double dd=100-(Double.valueOf(dis));
					return ((Double.valueOf(numofppl)-1)*tourPrice*(dd/100));
				}
				else 
					return (Integer.valueOf(numofppl)*tourPrice);
			}
		
			return 0;
		}
	    
	    
		  /** Description of initialize 
	    • *@see https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html
	    • * 
	    • * 
	    • * 
	    • */
	

	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    	instance = this;
	    	status = new ParkStatus(dtf.format(now), ChatClient.worker.getPark().getNumberOfPark(), null, null, null);
	    	DataTransfer data = new DataTransfer(TypeOfMessage.PARTKENTERGETSTATUS,status);
	    	ClientUI.chat.accept(data);
	    	txtWorkerName.setText("Hello"+" "+ChatClient.worker.getWorkerName());
	}
}

