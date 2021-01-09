package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class monthlyIncomeReportController extends AbstractScenes {
	public static monthlyIncomeReportController monthlyIncome;
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
    private StackedBarChart<String, Double> incomeChart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private Button getReportBtn;

    @FXML
    private ComboBox<String> selectMonth;
    @FXML
    private Text requestedMonth;
    ObservableList<String> list, list2, list3;
    public static monthlyIncomeReportController instance;
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
    	DataTransfer data = new DataTransfer(TypeOfMessage.GET_INVITATIONS, pMonth);
    	ClientUI.chat.accept(data);
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

    
    public void printReport(double income) {
    	requestedMonth.setText(String.valueOf(income));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
    	instance = this;
      setMonthComboBox();

    }
}
