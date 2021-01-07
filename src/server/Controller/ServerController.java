package server.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import client.ClientUI;
import client.logic.EmailDetails;
import client.logic.Order;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.EchoServer;
import server.ServerUI;
import server.database.mysqlConnection;

public class ServerController implements Initializable {
	public static ServerController instance;
	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnRun = null;
	@FXML
	private Label lbllist;
	@FXML
	private TextField portxt;
	@FXML
	public TextArea msgArea;

	@FXML
	private Button btnCheck;

	boolean isRunning = false;

	public void Run(ActionEvent event) throws Exception {
		int port;
		if (portxt.getText().isEmpty()) {
			port = ServerUI.DEFAULT_PORT;
			ServerUI.runServer(port);
		} else {
			try { // if the port is not an int, don't runServer.
				port = (new Integer(portxt.getText()));
				ServerUI.runServer(port);
			} catch (NumberFormatException e) {
				displayMsg("The entered port is invalid");
			}
		}
//		new SendEmail("liam69516@gmail.com", "TEST", "tmuna or kishalon");
	}

	public void disableRunBtn() {
		portxt.setDisable(true);
		btnRun.setDisable(true);
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/server/boundaries/serverGUI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("GoNature Server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void getExitBtn(ActionEvent event) throws Exception {
		displayMsg("exit GoNature Server!");
		ServerUI.stopListen();
		System.exit(0);
	}

	public void displayMsg(String msg) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					msgArea.appendText(msg + "\n");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

	}

	@FXML
	public void Check(ActionEvent event) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Order ordInDB;
		ArrayList<Order> answer = new ArrayList<Order>();
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		// System.out.println(tomorrow);
		ArrayList<Object> arrOfAnswer = new ArrayList<Object>();
		arrOfAnswer = mysqlConnection.getDB(
				"SELECT Park,Time,Date,NumOfVisitors,Email,OrderNumber,NameOnOrder,OrderKind,ID FROM gonature.orders WHERE Date='"
						+ tomorrow + "';");

		// ArrayList<Order> tomOrders=new ArrayList<Order>();

		if (!arrOfAnswer.isEmpty()) {

			for (int i = 0; i < arrOfAnswer.size(); i += 9) {
				ordInDB = new Order(null, null, null, null, null, null, null, null, null, null, null);
				ordInDB.setParkName(arrOfAnswer.get(i + 0).toString());
				ordInDB.setHour(arrOfAnswer.get(i + 1).toString());
				ordInDB.setDate(arrOfAnswer.get(i + 2).toString());
				ordInDB.setNumOfVisitors(arrOfAnswer.get(i + 3).toString());
				ordInDB.setEmail(arrOfAnswer.get(i + 4).toString());
				ordInDB.setOrderNumber(arrOfAnswer.get(i + 5).toString());
				ordInDB.setNameOnOrder(arrOfAnswer.get(i + 6).toString());
				ordInDB.setOrderKind(arrOfAnswer.get(i + 7).toString());
				ordInDB.setID(arrOfAnswer.get(i + 8).toString());

				answer.add(ordInDB);

			}
			for (int i = 0; i < answer.size(); i++) {
				String toSend = "Hello dear " + answer.get(i).getNameOnOrder()
						+ ".<br>Will we see you tomorrow ? You need to approve your order by entering GoNature software and visit the Existing Order section.<br>"
						+ "The order details are:<br>Order Number: " + answer.get(i).getOrderNumber() + "<br>Park: "
						+ answer.get(i).getParkName() + "<br>Date: " + answer.get(i).getDate() + "<br>Time: "
						+ answer.get(i).getHour() + "<br>Amount of visitors: " + answer.get(i).getNumOfVisitors();
				
				new SendEmail(answer.get(i).getEmail(), "GoNature Approve Order", toSend);
			}
			Thread t= new Thread();
			try {
				t.wait(7200000);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}

}