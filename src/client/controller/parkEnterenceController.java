package client.controller;


import client.ClientUI;
import client.logic.Order;
import client.logic.ParkStatus;
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





public class parkEnterenceController extends AbstractScenes {
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDateTime now = LocalDateTime.now();
	    //wait.setTimeOfEnterence(dtf.format(now));
	    public String discountDay=null;
	    double casualPrice= 30;

	ParkStatus status = new ParkStatus(dtf.format(now), "Park2", null, null, null);
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
	    private Button LogOutBtn;
	    
	    @FXML
	    private ComboBox<String> selectT;
	   
	    ObservableList<String> list;
	  
	    @FXML
	    void parkExitB(ActionEvent event) {

	    }
	    
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
	    	switchScenes("/client/boundaries/main.fxml", "Enternece");
	    	
  		   // wait.setTimeOfEnterence(dtf.format(now));
	    }

	    @FXML
	    void completeOrder(ActionEvent event) {
	    	DateTimeFormatter drf = DateTimeFormatter.ofPattern("HH:mm:ss");
  		    LocalDateTime noww = LocalDateTime.now();
	    	ParkStatus status1=new ParkStatus(null,null,null,null, null);
	    	status1.setPark(status.getPark());
	    	status1.setAmount((txtNumOfVis.getText()));
	    	status1.setDate(status.getDate());
	    	casualOrder order=new casualOrder(null,null,null,null,null,null, null);
	    	order.setPark(status.getPark());
	    	order.setDate(status.getDate());
	    	order.setTime(drf.format(noww));
	    	order.setOrderKind(selectT.getValue());
	    	double price=checkFinalPrice(order.getOrderKind(), discountDay, status1.getAmount());
	    	order.setPayment(String.valueOf(String.format("%.2f", price)));
	    	order.setOrderNumber(generateRandomChars("123456789", 5));
	
	    	DataTransfer data = new DataTransfer(TypeOfMessage.PARKENTERSENDSTATUS,status1);
	    	ClientUI.chat.accept(data);
	    	DataTransfer data2 = new DataTransfer(TypeOfMessage.CASUALVISITUPDATE,order);
	    	ClientUI.chat.accept(data2);
	    	
	    	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setHeaderText(null);
        	alert.setContentText("New visit created succsesfully");
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
	    
	    public void insertData(String t, String b) {
	    	getNumPpl.setText(t+"/"+b);
	    	//parkEnterenceController2.instance.insertData(t);
	    	
	    }
	    
	    public void getDiscountDay(String t) {
	    	discountDay=t;	
	    }
	    
		public static String generateRandomChars(String candidateChars, int length) {
		    StringBuilder sb = new StringBuilder();
		    Random random = new Random();
		    for (int i = 0; i < length; i++) {
		        sb.append(candidateChars.charAt(random.nextInt(candidateChars
		                .length())));
		    }

		    return sb.toString();
		}
		
		
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
	    
	    
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    	instance = this;
	    	DataTransfer data = new DataTransfer(TypeOfMessage.PARTKENTERGETSTATUS,status);
	    	ClientUI.chat.accept(data);
	    	typeOfT();
	    	//DataTransfer data5 = new DataTransfer(TypeOfMessage.CheckDiscounts,status);
	    	//ClientUI.chat.accept(data5);
		}
	}


