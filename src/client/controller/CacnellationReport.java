package client.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import client.ChatClient;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.print.PageLayout;
import javafx.print.PageRange;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

/**
 * CacnellationReport class. The class implements Serializable which transmits
 * the information from the client to the server. The class is responsible for
 * presenting the cancellation report.
 * 
 * @author Liran Amilov
 */

public class CacnellationReport implements Initializable {

	@FXML
	private Text reportName;

	@FXML
	private Text monthText;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;

	@FXML
	private BarChart<String, Number> barCancelation;

	@FXML
	private Button printReport;

	final static String Regular_Traveler = "Regular Traveler";
	final static String Family_subscription = "Family subscription";
	final static String Group = "Group";
	int regularNumCancel;
	int subNumCancel;
	int guideNumCancel;
	int regularNumNotArrived;
	int subNumNotArrived;
	int guideNumNotArrived;

	/**
	 * printCancelReport method. This method is an auxiliary function for the
	 * process of printing the cancellation report.
	 * 
	 * @param event
	 */

	@FXML
	void printCancelReport(ActionEvent event) {
		print(printReport.getScene().getWindow(),
				printReport.getScene().getWindow().getScene().getRoot().snapshot(null, null));
	}

	/**
	 * print method. This method is responsible for printing the report.
	 * 
	 * @param myWindow
	 * @param screenshot
	 */

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

	/**
	 * initialize method. This method is responsible for defining variables by
	 * communicating with the server, is responsible for report visibility (caption
	 * and titles) and on-screen functionality.
	 * 
	 * @param location
	 * @param resources
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize(URL location, ResourceBundle resources) {
		reportName.setText("Cacnellation Report");
		monthText.setText(LocalDate.now().withDayOfMonth(1) + " To " + LocalDate.now().minusDays(1));
		if (ChatClient.reportsData.getRegularCancel() != null) {
			regularNumCancel = Integer.valueOf(ChatClient.reportsData.getRegularCancel());
		} else {
			regularNumCancel = 0;
		}
		if (ChatClient.reportsData.getSubCancel() != null) {
			subNumCancel = Integer.valueOf(ChatClient.reportsData.getSubCancel());
		} else {
			subNumCancel = 0;
		}
		if (ChatClient.reportsData.getGuideCancel() != null) {
			guideNumCancel = Integer.valueOf(ChatClient.reportsData.getGuideCancel());
		} else {
			guideNumCancel = 0;
		}

		if (ChatClient.reportsData.getRegularnotArrived() != null) {
			regularNumNotArrived = Integer.valueOf(ChatClient.reportsData.getRegularnotArrived());
		} else {
			regularNumNotArrived = 0;
		}
		if (ChatClient.reportsData.getSubnotArrived() != null) {
			subNumNotArrived = Integer.valueOf(ChatClient.reportsData.getSubnotArrived());
		} else {
			subNumNotArrived = 0;
		}
		if (ChatClient.reportsData.getGuidenotArrived() != null) {
			guideNumNotArrived = Integer.valueOf(ChatClient.reportsData.getGuidenotArrived());
		} else {
			guideNumNotArrived = 0;
		}

		barCancelation.setTitle("Cacnellation Report");
		xAxis.setLabel("Traveler kind");
		yAxis.setLabel("Number of visitors");
		XYChart.Series Canceld = new XYChart.Series<>();
		Canceld.setName("Canceled");

		Canceld.getData().add(new XYChart.Data(Regular_Traveler, regularNumCancel));
		Canceld.getData().add(new XYChart.Data(Family_subscription, subNumCancel));
		Canceld.getData().add(new XYChart.Data(Group, guideNumCancel));
//		
		XYChart.Series approvedAndNotArrived = new XYChart.Series();
		approvedAndNotArrived.setName("Not Arrived");
		approvedAndNotArrived.getData().add(new XYChart.Data(Regular_Traveler, regularNumNotArrived));
		approvedAndNotArrived.getData().add(new XYChart.Data(Family_subscription, subNumNotArrived));
		approvedAndNotArrived.getData().add(new XYChart.Data(Family_subscription, guideNumNotArrived));
		barCancelation.getData().addAll(Canceld, approvedAndNotArrived);

	}

}
