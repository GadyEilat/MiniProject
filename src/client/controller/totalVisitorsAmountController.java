package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class totalVisitorsAmountController extends AbstractScenes {

	public static totalVisitorsAmountController instance;
	private String mChoosen;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackBtn;

    @FXML
    private ImageView backBtnImage;

    @FXML
    private Text msgFromController;

    @FXML
    private Button getReportBtn;

    @FXML
    private ComboBox<String> selectMonth;

    @FXML
    private Text tourGroups;

    @FXML
    private Text subscribers;

    @FXML
    private Text regulars;

    @FXML
    private Text totalAmount;
    ObservableList<String> list;
    
    @FXML
    void GoBack(ActionEvent event) {
    	switchScenes("/client/boundaries/reportManager.fxml", "New waiting list");
    }

    @FXML
    void getReport(ActionEvent event) {
    	mChoosen=selectMonth.getValue();
    	ArrayList<String> pMonth= new ArrayList<String>();
    	pMonth.add(mChoosen);
    	pMonth.add(ChatClient.worker.getPark().getNumberOfPark());
    	DataTransfer data = new DataTransfer(TypeOfMessage.GET_TOTALVISITORSREPORT, pMonth);
    	ClientUI.chat.accept(data);
    }
    
    
    
    public void getAmount(int sumReg, int sumTour, int sumSub) {
    	regulars.setText(String.valueOf(sumReg));
    	tourGroups.setText(String.valueOf(sumTour));
    	subscribers.setText(String.valueOf(sumSub));
    	totalAmount.setText(String.valueOf(sumReg+sumTour+sumSub));
    	
		
	}

    private void setMonthComboBox() {
		ArrayList<String> al = new ArrayList<String>();
		al.add("2021-01");
		al.add("2020-12");
		al.add("2020-11");
		al.add("2020-10");
		al.add("2020-09");
		al.add("2020-08");
		al.add("2020-07");
		al.add("2020-06");
		al.add("2020-05");
		al.add("2020-04");
		al.add("2020-03");
		al.add("2020-02");
		al.add("2020-01");
		
		list = FXCollections.observableArrayList(al);
		selectMonth.setItems(list);
	}
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	instance = this;
        setMonthComboBox();
    }

	
}
