package client.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.ReportsData;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageRange;
import javafx.print.PrinterJob;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Window;

public class VisitorPerHourReport implements Initializable {
	public static VisitorPerHourReport instance;
	@FXML
	private Text reportName;

	@FXML
	private Button printReport;

	@FXML
	private BarChart<?, ?> showHourandAmount;

	@FXML
	private CategoryAxis openHourOfPark;

	@FXML
	private NumberAxis amountOfPeople;

	@FXML
	private Button showData;

	@FXML
	private DatePicker pickDate;
	final static String hour1 = "8:00";
	final static String hour2 = "9:00";
	final static String hour3 = "10:00";
	final static String hour4 = "11:00";
	final static String hour5 = "12:00";
	final static String hour6 = "13:00";
	final static String hour7 = "14:00";
	final static String hour8 = "15:00";

	
	public static void printCurrWindow(Window myWindow) {
		print(myWindow, myWindow.getScene().getRoot().snapshot(null, null));
	}

	@FXML
	void printVisitReport(ActionEvent event) {
		printCurrWindow(printReport.getScene().getWindow());
	}

	private static void print(Window myWindow, WritableImage screenshot) {
		PrinterJob job = PrinterJob.createPrinterJob();
		if (job != null) {
			job.getJobSettings().setPageRanges(new PageRange(1, 1));
			if (!job.showPageSetupDialog(myWindow) || !job.showPrintDialog(myWindow)) {
				return;
			}
			final PageLayout pageLayout = job.getJobSettings().getPageLayout();
			final double sizeX = pageLayout.getPrintableWidth() / screenshot.getWidth();
			final double sizeY = pageLayout.getPrintableHeight() / screenshot.getHeight();
			final double size = Math.min(sizeX, sizeY);
			final ImageView print_node = new ImageView(screenshot);
			print_node.getTransforms().add(new Scale(size, size));
			job.printPage(print_node);
			job.endJob();

		}
	}
	
	@FXML
	void showTimeChart(ActionEvent event) {
		LocalDate date = pickDate.getValue();
		if (date != null) {
			String dateToCheck = date.toString();
			ReportsData visitorPerHour = new ReportsData();
			visitorPerHour.setParkNumber(ChatClient.worker.getPark().getNumberOfPark());
			visitorPerHour.setDate(dateToCheck);
			DataTransfer data = new DataTransfer(TypeOfMessage.REQUEST_VISITREPORT, visitorPerHour);
			ClientUI.chat.accept(data);
		} else {
			// showERROR No Pick date
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unlikely-arg-type" })
	public void showChart() {
		

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				showHourandAmount.getData().removeAll(Collections.singleton(showHourandAmount.getData().setAll()));
				String[][] dataToShow = ChatClient.reportsDataforvisitors.getAmountPerOrderKind();
				showHourandAmount.setTitle("Visit Per Hour");
				openHourOfPark.setLabel("Traveler kind");
				amountOfPeople.setLabel("Number of visitors");
				XYChart.Series Regular_Traveler = new XYChart.Series<>();
				Regular_Traveler.setName("Regular Traveler");

				Regular_Traveler.getData().add(new XYChart.Data(hour1, Integer.valueOf(dataToShow[0][2])));
				Regular_Traveler.getData().add(new XYChart.Data(hour2, Integer.valueOf(dataToShow[1][2])));
				Regular_Traveler.getData().add(new XYChart.Data(hour3, Integer.valueOf(dataToShow[2][2])));
				Regular_Traveler.getData().add(new XYChart.Data(hour4, Integer.valueOf(dataToShow[3][2])));
				Regular_Traveler.getData().add(new XYChart.Data(hour5, Integer.valueOf(dataToShow[4][2])));
				Regular_Traveler.getData().add(new XYChart.Data(hour6, Integer.valueOf(dataToShow[5][2])));
				Regular_Traveler.getData().add(new XYChart.Data(hour7, Integer.valueOf(dataToShow[6][2])));
				Regular_Traveler.getData().add(new XYChart.Data(hour8, Integer.valueOf(dataToShow[7][2])));
		
				XYChart.Series Family_subscription = new XYChart.Series();
				Family_subscription.setName("Family subscription");
				Family_subscription.getData().add(new XYChart.Data(hour1, Integer.valueOf(dataToShow[0][1])));
				Family_subscription.getData().add(new XYChart.Data(hour2, Integer.valueOf(dataToShow[1][1])));
				Family_subscription.getData().add(new XYChart.Data(hour3, Integer.valueOf(dataToShow[2][1])));
				Family_subscription.getData().add(new XYChart.Data(hour4, Integer.valueOf(dataToShow[3][1])));
				Family_subscription.getData().add(new XYChart.Data(hour5, Integer.valueOf(dataToShow[4][1])));
				Family_subscription.getData().add(new XYChart.Data(hour6, Integer.valueOf(dataToShow[5][1])));
				Family_subscription.getData().add(new XYChart.Data(hour7, Integer.valueOf(dataToShow[6][1])));
				Family_subscription.getData().add(new XYChart.Data(hour8, Integer.valueOf(dataToShow[7][1])));

				XYChart.Series Group = new XYChart.Series<>();
				Group.setName("Tour Guide");
				Group.getData().add(new XYChart.Data(hour1, Integer.valueOf(dataToShow[0][0])));
				Group.getData().add(new XYChart.Data(hour2, Integer.valueOf(dataToShow[1][0])));
				Group.getData().add(new XYChart.Data(hour3, Integer.valueOf(dataToShow[2][0])));
				Group.getData().add(new XYChart.Data(hour4, Integer.valueOf(dataToShow[3][0])));
				Group.getData().add(new XYChart.Data(hour5, Integer.valueOf(dataToShow[4][0])));
				Group.getData().add(new XYChart.Data(hour6, Integer.valueOf(dataToShow[5][0])));
				Group.getData().add(new XYChart.Data(hour7, Integer.valueOf(dataToShow[6][0])));
				Group.getData().add(new XYChart.Data(hour8, Integer.valueOf(dataToShow[7][0])));

				showHourandAmount.getData().addAll(Regular_Traveler,Family_subscription,Group);
			}

		});

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		reportName.setText("Visit Per Hour");

	}

}
