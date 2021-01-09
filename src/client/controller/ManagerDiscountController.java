package client.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.ParkInfo;
import client.logic.Worker;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.MonthDay;

/**
 * ManagerDiscountController class. This class expands the AbstractScenes class
 * that replaces the scenes within the main stage. This class is responsible for
 * the discounts the park manager wants to enter. It must enter the discount
 * percentage and the intended date. It is possible to disconnect from the park
 * manager user, it is possible to go to the status screen, the reports screen
 * and the park management screen.
 * 
 * @author Liran Amilov
 */

public class ManagerDiscountController extends AbstractScenes {
	public static ManagerDiscountController instance;
	Integer month;
	Integer day;
	String color;
	String datesToShow[][];
	String discount;
	Map<Integer, Month> map = new HashMap<Integer, Month>();
	List<LocalDate> discountDates = new ArrayList<>();
	@FXML
	private TextField discountField;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Button btnSave;

	@FXML
	private Text managerName;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnStatus;

	@FXML
	private Button btnReport;

	@FXML
	private Button btmManagingPark;

	/**
	 * logout method. This method is responsible for disconnecting from the
	 * department manager user and transferring to the main login screen.
	 * 
	 * @param event
	 */

	@FXML
	void logout(ActionEvent event) {
//    	exitConnection
		DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
		ClientUI.chat.accept(data);
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		ChatClient.connected = false;
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

	@FXML
	void saveDiscountAndDate(ActionEvent event) {
		String discount = discountField.getText();
		LocalDate discountDate = datePicker.getValue();

		if (discount.trim().isEmpty() || discountDate == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("One of the fields is missing");
			alert.show();
		} else if (!discount.matches("\\d*") || Integer.parseInt(discount) > 100 || Integer.parseInt(discount) < 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Enter reasonable discount %");
			alert.show();
		} else {
			String date = discountDate.toString();
			ArrayList<String> dateAndDiscount = new ArrayList<String>();
			dateAndDiscount.add(discount);
			dateAndDiscount.add(date);
			dateAndDiscount.add(ChatClient.worker.getPark().getNumberOfPark());
			DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO_REQUEST, dateAndDiscount);
			discountField.clear();
			datePicker.setValue(null);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Wait To Department Manager To Approve");
			alert.show();
			ClientUI.chat.accept(data);
		}
	}
	
	/**
	 * notFond method. This method displays an error message stating that the data
	 * could not be taken from the database because it was not found.
	 */

	public void notFond() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Can not take data from DataBase!");
		alert.show();
	}

	/**
	 * showManagingPark method. This method is responsible for transferring the
	 * screen to the park management screen.
	 * 
	 * @param event
	 * @throws IOException
	 */

	@FXML
	void showManagingPark(ActionEvent event) throws IOException {

		switchScenes("/client/boundaries/managingPark.fxml", "Manager");
	}

	/**
	 * showReport method. This method is responsible for transferring the screen to
	 * the reports screen.
	 * 
	 * @param event
	 */

	@FXML
	void showReport(ActionEvent event) {
		switchScenes("/client/boundaries/reportManager.fxml", "Manager");
	}

	/**
	 * showStatus method. This method is responsible for transferring the screen to
	 * the status screen.
	 * 
	 * @param event
	 */

	@FXML
	void showStatus(ActionEvent event) {
		switchScenes("/client/boundaries/manager.fxml", "Manager");
	}

	/**
	 * This method is responsible for classifying colors according to approved or
	 * not checked yet.
	 */

	final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		public DateCell call(final DatePicker datePicker) {
			return new DateCell() {
				@Override
				public void updateItem(LocalDate date, boolean empty) {
					super.updateItem(date, empty);
					for (int i = 0; i < discountDates.size(); i++) {
						if (LocalDate.from(date).equals(discountDates.get(i))) {
							discount = datesToShow[i][1];
							setTooltip(new Tooltip("Discount : " + discount + "%"));
							if (datesToShow[i][2].equals("toCheck")) {
								color = "#ff4444;";
							} else {
								color = "#26e055;";
							}
							setStyle("-fx-background-color: " + color);
						}
					}
					LocalDate today = LocalDate.now();

					setDisable(empty || date.compareTo(today) < 0);
				}
			};
		}
	};

	/**
	 * convertToMonth method. This method is responsible for displaying the calendar
	 * in order to select a date.
	 * 
	 * @param month
	 * @return Calendar
	 */

	private Month convertToMonth(int month) {
		return map.get(month);
	}

	/**
	 * updateDatePicker method. This method is responsible for updating the selected
	 * date.
	 */

	public void updateDatePicker() {
		this.datesToShow = ChatClient.datesToShow;
		for (int i = 0; i < datesToShow.length; i++) {
			String[] result = datesToShow[i][0].split("-");
			int year = Integer.valueOf(result[0]);
			month = Integer.valueOf(result[1]);
			day = Integer.valueOf(result[2]);
			discountDates.add(LocalDate.of(year, convertToMonth(month), day));
		}
		datePicker.setDayCellFactory(dayCellFactory);
	}

	/**
	 * checkIfThereNewDatesToShow method. This method is responsible for checking
	 * new dates in order to display them.
	 */

	private void checkIfThereNewDatesToShow() {
		DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO, new ParkInfo(null,
				ChatClient.worker.getPark().getNumberOfPark(), null, null, null, ChatClient.worker.getRole()));
		ClientUI.chat.accept(data);
	}

	/**
	 * initialize method. This method is responsible for defining variables by
	 * communicating with the server, is responsible for screen visibility (caption
	 * and titles) and on-screen functionality.
	 * 
	 * @param location
	 * @param resources
	 */

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		managerName.setText("Hello " + ChatClient.worker.getWorkerName());
		map.put(1, Month.JANUARY);
		map.put(2, Month.FEBRUARY);
		map.put(3, Month.MARCH);
		map.put(4, Month.APRIL);
		map.put(5, Month.MAY);
		map.put(6, Month.JUNE);
		map.put(7, Month.JULY);
		map.put(8, Month.AUGUST);
		map.put(9, Month.SEPTEMBER);
		map.put(10, Month.OCTOBER);
		map.put(11, Month.NOVEMBER);
		map.put(12, Month.DECEMBER);
		checkIfThereNewDatesToShow();
	}

}
