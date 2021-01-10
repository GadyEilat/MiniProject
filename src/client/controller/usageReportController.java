package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.DataTransfer;
import common.TypeOfMessage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

/**
 * usageReportController class
 * @author Aviv Kamary
 * This controller is responsible for the Usage Report screen to the Park Manager.
 * The controller expands the AbstractScenes class that replaces the scenes within the main stage. 
 * It is possible to return back to the manager park reports screen.
 */
public class usageReportController extends AbstractScenes {
	public static usageReportController instance;
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
	private Button getReportBtn;

	@FXML
	private ComboBox<String> selectMonth;

	@FXML
	private Text requestedMonth;

	@FXML
	private ScatterChart<String, Integer> UsageChart;

	@FXML
	private CategoryAxis CategoryAxis;

	@FXML
	private NumberAxis NumberAxis;

	ObservableList<String> list;

	/**
	 * setMonthComboBox method
	 * This method adding months to the date combo box in the screen and display it.
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
		mChoosen = selectMonth.getValue();
		ArrayList<String> pMonth = new ArrayList<String>();
		pMonth.add(mChoosen);
		pMonth.add(ChatClient.worker.getPark().getNumberOfPark());
		DataTransfer data = new DataTransfer(TypeOfMessage.GET_USAGEREPORT, pMonth);
		ClientUI.chat.accept(data);
	}

	/**
	 * setTimesUsage method
	 * @param object
	 * This method received all the data sent from the server and then display the Usage Chart to the screen
	 */
	public void setTimesUsage(Object object) {
		Platform.runLater(new Runnable() {

			@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
			@Override
			public void run() {
				UsageChart.getData().removeAll(Collections.singleton(UsageChart.getData().setAll()));
				NumberAxis.setLabel("Hours");
				CategoryAxis.setLabel("Days");
				
				ArrayList<String> usageArr = (ArrayList<String>) object;
				int j = 0;
				XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();

				for (int i = 0; i < usageArr.size() / 3; i++) {
					String hourA = usageArr.get(j + 1);
					String dateA = usageArr.get(j + 2);
					String hourLoop = hourA.substring(0, 2);
					String subS = dateA.substring(dateA.length() - 2);
					String zero = "0";
					String checkSub = subS.substring(0, 1);
					if (checkSub.matches("0"))
						subS = subS.substring(1);

					series.getData().add(new XYChart.Data<>(subS, Integer.valueOf(hourLoop)));
					j += 3;
				}

				UsageChart.getData().addAll(series);
			}

		});

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
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		setMonthComboBox();
	}

}
