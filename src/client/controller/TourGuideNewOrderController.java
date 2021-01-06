package client.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.WaitingList;
import client.logic.maxVis;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
public class TourGuideNewOrderController extends AbstractScenes {
	TourGuide tourguide;
	static TourGuideOrder tourguideorderr = new TourGuideOrder(null,null,null,null,null,null,null,null);
	private TourGuideOrderSController targetObj=null;
	public static TourGuideNewOrderController instance;
	static boolean thereIsSpot=false;
	
	    @FXML 
	    private ResourceBundle resources;
	    
	    @FXML
	    private TextField newOrderGetEmail;
	    
	    @FXML
	    private URL location;
	    
	    @FXML
	    private CheckBox isGroupOrder;
	    
	    @FXML
	    private Button continueToPayBtn;

	    @FXML
	    private ComboBox<String> chooseNumVisitorsBtn;

	    @FXML
	    private DatePicker chooseDayBtn;
	    
	    @FXML
	    private ComboBox<String> ChooseAnotherName;

	    @FXML
	    private SplitMenuButton chooseTimeBtn;

	    @FXML
	    private ComboBox<String> parkNamBtn;

	    @FXML
	    private Button waitingListTourBtn;

	    @FXML
	    private Button LogOutBtn;
	    
	    @FXML
	    private TextField getIDTourOrder;

	    @FXML
	    private Button updateDetalisGuideBtn;

	    @FXML
	    private Button NewOrderBtn;

	    @FXML
	    private Button myOrdersBtn;
         
	    @FXML
	    private Text TourNo;
	    
	    @FXML
	    private TextField NameOnOrder;
	    
	    @FXML
	    private Text txtErrorOrder;
	    
	    @FXML
	    private Button backButton;
	    
//	    	public void createAndSend(TourGuideOrderSController reciver, String A, String B) {
//	            reciver.recieve(A,B);
//	     }
	    
	    
	    ObservableList<String> list,list2,list3;
	    
	    private void setTimeComboBox() {
			ArrayList<String> al = new ArrayList<String>();	
			al.add("8:00");
			al.add("9:00");
			al.add("10:00");
			al.add("11:00");
			al.add("12:00");
			al.add("13:00");
			al.add("14:00");
			al.add("15:00");
	
			list = FXCollections.observableArrayList(al);
			ChooseAnotherName.setItems(list);
		}
	    private void setParkComboBox() {
			ArrayList<String> a2 = new ArrayList<String>();	
			a2.add("Park1");
			a2.add("Park2");
			a2.add("Park3");
			list2 = FXCollections.observableArrayList(a2);
			parkNamBtn.setItems(list2);
		}
	    
	    private void setNumOfVisitorsComboBox() {
			ArrayList<String> a3 = new ArrayList<String>();	
			a3.add("1");
			a3.add("2");
			a3.add("3");
			a3.add("4");
			a3.add("5");
			a3.add("6");
			a3.add("7");
			a3.add("8");
			a3.add("9");
			a3.add("10");
			a3.add("11");
			a3.add("12");
			a3.add("13");
			a3.add("14");
			a3.add("15");
			list3 = FXCollections.observableArrayList(a3);
			chooseNumVisitorsBtn.setItems(list3);
		}
	    
	    
	     
	    public void loadGuide(TourGuide tourguideO) {
	    	this.tourguide = tourguideO;
	         this.TourNo.setText(tourguideO.getFname());
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

	    }

	    @FXML
	    void chooseDayButton(ActionEvent event) {

	    }

	    @FXML
	    void chooseNumVisitorsButton(ActionEvent event) {

	    }

	    @FXML
	    void chooseTimeButton(ActionEvent event) {

	    }
	    
	    @FXML
	    void continueToPayButton(ActionEvent event) {
	    	String orderPark =parkNamBtn.getValue();
			LocalDate orderDate = chooseDayBtn.getValue();
//			if (orderDate== null || java.time.LocalDate.now().isAfter(orderDate))
//				JOptionPane.showMessageDialog(null, "You must enter right date", "error",
//						JOptionPane.INFORMATION_MESSAGE);
//			else {
			
	    	
	    	String orderTime=ChooseAnotherName.getValue();
	    	String orderNumOfVisitors=chooseNumVisitorsBtn.getValue(); 
	    	String orderEmail = (newOrderGetEmail.getText());
            String nameOnOrder= (NameOnOrder.getText());
            String orderID=(getIDTourOrder.getText());
            boolean emailT=validate(orderEmail);
            boolean idC=checkID(orderID);
            if(nameOnOrder.trim().isEmpty()||orderDate==null||java.time.LocalDate.now().isAfter(orderDate) ||  orderTime==null || orderNumOfVisitors==null ||orderEmail.trim().isEmpty()||orderID.trim().isEmpty()||emailT==false||idC==false) {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setHeaderText(null);
            	alert.setContentText("Fields missing or wrong date");
            	alert.show();
            }
           
            else {
            	
            	//Check max num at that day
            	
            	
            	String orderDate2=orderDate.toString();	
	    	tourguideorderr.setParkName(orderPark);
	    	tourguideorderr.setDate(orderDate2);
	    	tourguideorderr.setTime(orderTime);
	    	tourguideorderr.setNumOfVisitors(orderNumOfVisitors);
	    	tourguideorderr.setEmail(orderEmail);
	    	tourguideorderr.setNameOnOrder(nameOnOrder);
	    	tourguideorderr.setID(orderID);
	    	checkDate(tourguideorderr, null);
           	DataTransfer data = new DataTransfer(TypeOfMessage.TOURGUIDENEWORDER,tourguideorderr);
           	try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           	if(thereIsSpot) {
           	//System.out.print(thereIsSpot);
	    	//ClientUI.chat.accept(data);
	    	switchScenes("/client/boundaries/TourGuidePayment.fxml", "GoNature Enter");
			System.out.println("Order Updated Successfully");
           	}
           	else {
           		Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setHeaderText(null);
            	alert.setContentText("There is not spot. Please change date or enter waiting list.");
            	alert.show();	
           	}
            }
			//}	
	    }

	    @FXML
	    void myOrdersButton(ActionEvent event) {
	    	switchScenes("/client/boundaries/TourGuideMyOrders.fxml", "My Orders");
	    }

	    @FXML
	    void parkNamButton(ActionEvent event) {
               
	    }

	    @FXML
	    void CheckPrices(ActionEvent event) {
	    	   try {
	    	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/boundaries/payments.fxml"));
	    	        Parent root = (Parent) fxmlLoader.load();
	    	        Scene scene = new Scene(root);
	    	        Stage stage = new Stage();
	    	        stage.setScene(scene);
	    	        stage.show();
	    	} catch (IOException e) {
	    	    e.printStackTrace();
	    	  }
	    }
	    
	    
	    @FXML
	    void updateDetalisGuideButton(ActionEvent event) {
	    	switchScenes("/client/boundaries/TourGuideChangeDetails.fxml", "Change Details");
	    }
	    
	    @FXML
	    void changeOrder(ActionEvent event) {
	    	switchScenes("/client/boundaries/Existing Order.fxml", "New Order");
	    }

	    @FXML
	    void waitingListTourButton(ActionEvent event) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setHeaderText(null);
        	alert.setContentText("Enterd waiting list sucssesfully.");
        	alert.show();
        	WaitingList wait= new WaitingList(null, null, null ,null ,null ,null, null, null, null,null);
        	wait.setDate(tourguideorderr.getDate());
        	wait.setEmail(tourguideorderr.getEmail());
        	wait.setID(tourguideorderr.getID());
        	wait.setNameOnOrder(tourguideorderr.getNameOnOrder());
        	wait.setNumOfVisitors(tourguideorderr.getNumOfVisitors());
            wait.setParkName(tourguideorderr.getParkName());
            wait.setTime(tourguideorderr.getTime());
        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  		    LocalDateTime now = LocalDateTime.now();
  		   // wait.setTimeOfEntrance(dtf.format(now));
           	DataTransfer data = new DataTransfer(TypeOfMessage.TOURGUIDEWAITINGLIST,wait);
           	ClientUI.chat.accept(data);
           	switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "New waiting list"); 	
	    }

	    @Override
		public void initialize(URL location, ResourceBundle resources) {
	    	instance = this;
	    	loadGuide(ChatClient.tourguide);
	    	setTimeComboBox();
	    	setParkComboBox();
	    	setNumOfVisitorsComboBox();
	    	NameOnOrder.setText(ChatClient.tourguide.getFname());
	    	//getIDTourOrder.setText(ChatClient.tourguide.getId());
	    	
		}
	    
	    
	    private void alertMessage(){
	    	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setHeaderText(null);
        	alert.setContentText("Fields missing");
        	alert.show();	
	    }
	    
	    
	    
	    public maxVis checkDate(TourGuideOrder s, maxVis t) {
	    	 maxVis visMax= new maxVis(null, null, null, 0, 0, null, 0);
	           	DataTransfer data2 = new DataTransfer(TypeOfMessage.CHECKMAXVIS,s);
		    	ClientUI.chat.accept(data2);
	    	return visMax;
	    }
	    
	    public void checkDate2(maxVis t) {
	    	 maxVis visMax= new maxVis(null, null, null, 0, 0, null, 0);
		    	visMax.setDate(t.getDate());
		    	visMax.setPark(t.getPark());
		    	visMax.setVisitorsInOrder(t.getVisitorsInOrder());
		    	visMax.setAllowed1(t.getAllowed1());
		    	visMax.setAllowed2(t.getAllowed2());
		    	if(Integer.valueOf(visMax.getVisitorsInOrder()+visMax.getAllowed2())< visMax.getAllowed1())
		    	thereIsSpot=true;
	    } 
	    
	    
	    
	    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
	    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	    	public static boolean validate(String emailStr) {
	    	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
	    	        return matcher.find();
	    	}
	   public static boolean checkID(String ID) {
		   if(ID.length()==9 && ID.matches("[0-9]+"))
			   return true;
		   return false;
	   }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	   
}
