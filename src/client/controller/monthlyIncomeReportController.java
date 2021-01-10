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

/**
 * monthlyIncomeReportController class
 * @author Aviv Kamary
 * This controller is responsible for the Monthly Report screen to the Park Manager.
 * The controller expands the AbstractScenes class that replaces the scenes within the main stage. 
 * It is possible to return back to the manager park reports screen.
 */
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
    ObservableList<String> list;
    public static monthlyIncomeReportController instance;
    
    /**
	 * GoBack method
	 * @param event
	 * In this method you can return to the manager report screen.
	 */
    @FXML
    void GoBack(ActionEvent event) {
    	switchScenes("/client/boundaries/reportManager.fxml", "Park Manager");
    }

    /**
	 * getReport method
	 * @param event
	 * This method gets the date chosen and taking for the park selected of the park manager and then sends it to the server
	 * for taking the values from the database
	 */
    @FXML
    void getReport(ActionEvent event) {
    	mChoosen=selectMonth.getValue();
    	ArrayList<String> pMonth= new ArrayList<String>();
    	pMonth.add(mChoosen);
    	pMonth.add(ChatClient.worker.getPark().getNumberOfPark());
    	DataTransfer data = new DataTransfer(TypeOfMessage.GET_INVITATIONS, pMonth);
    	ClientUI.chat.accept(data);
    }

    /**
	 * setMonthComboBox method
	 * @param object
	 * This method received all the data sent from the server and then display the Usage Chart to the screen
	 */
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

    /**
     * printReport method
     * @param income
     * This method receive the monthly income and display it on the screen
     */
    public void printReport(double income) {
    	requestedMonth.setText(String.valueOf(income));
    }
    
    /**
	 * initialize method
	 * @param location
	 * @param resources
	 * This method is responsible for defining variables by communicating with the server, 
	 * is responsible for screen visibility (caption and titles) and on-screen functionality.
	 * This method holding the usageReport instance
	 * Also, this method setting the combo box and display it in the screen.
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources){
    	instance = this;
      setMonthComboBox();

    }
}
