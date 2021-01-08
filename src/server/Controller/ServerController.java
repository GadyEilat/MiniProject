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

public class ServerController implements Initializable, Runnable {
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

//		Runnable r = new ServerController();
//        Thread t1 = new Thread(r);
//        Thread t2 = new Thread(r);
//        t1.start();
//        t2.start();

		Thread t = new Thread(this);
		t.start();
	}

	public void deleteInstanceOrder(Object object) {
		ArrayList<Object> arrOfAnswer = null;
		Order ordToBeDeleted = (Order) object;
		boolean ans;
		Order orderFromWaitingList = new Order(null, null, null, null, null, null, null, null, null, null, null, null);
		String saveDate = ordToBeDeleted.getDate();
		String saveTime = ordToBeDeleted.getHour();
		int numberOfReplacementInWaitingList = Integer.valueOf(ordToBeDeleted.getNumOfVisitors());

		ans = mysqlConnection.insertIntoDeletedOrders(ordToBeDeleted);
		if (ans) {
			ServerController.instance.displayMsg("Deleted order was added to deletedOrder table");
			String DeleteQuery = "DELETE FROM gonature.orders WHERE (OrderNumber = '" + ordToBeDeleted.getOrderNumber()
					+ "');";
			ans = mysqlConnection.updateDB(DeleteQuery);
			if (ans) {
				ServerController.instance.displayMsg("Order was deleted");

				// getting the first in line from the waiting list which fits the time and date.

				
					arrOfAnswer = mysqlConnection.getDB(
							"select * from manageparks WHERE numberOfPark='" + ordToBeDeleted.getParkName() + "';");
					// // orders Where Date='2020-12-31'");
					// ResultSet rs = conn.createStatement().executeQuery("select * from manageparks
					// WHERE numberOfPark='" + ordToBeDeleted.getParkName() + "';");

					String append = ":00";
					String twoLetters = ordToBeDeleted.getHour(); // Get hour(12:00 example)
					int loopC = Integer.valueOf(Integer.valueOf((String) arrOfAnswer.get(3)) * 2 - 1);// 4*2
					String hourLoop = twoLetters.substring(0, twoLetters.indexOf(':'));// Seperate till ':'
					String[] arrS = new String[Integer.valueOf((String) arrOfAnswer.get(3)) * 2 - 1]; // Array
																										// for
																										// check
					int t = Integer.valueOf(hourLoop) - (loopC / 2); // 12-4
					for (int i = 0; i < arrS.length; i++) {
						// int t=loopC/2;
						arrS[i] = String.valueOf(t) + append;
						t++;
					}
					for (int numOfVisitorMoved = 0; numOfVisitorMoved < numberOfReplacementInWaitingList; numOfVisitorMoved++) {

						arrOfAnswer = mysqlConnection.getDB(
								"SELECT MIN(DateOfEntrance) AS MinDate, Time, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors FROM gonature.waitinglist WHERE Date = '"
										+ saveDate + "' AND Park = '" + ordToBeDeleted.getParkName() + "';");

						if (!arrOfAnswer.isEmpty()) {
							int substructIfWeFoundInWaitingList = Integer.valueOf(arrOfAnswer.get(2).toString());
							String numOfVisitorsToCheck = String.valueOf(numberOfReplacementInWaitingList - numOfVisitorMoved);
							switch (loopC) {
							case 1:
//SELECT OrderNumber FROM gonature.waitinglist WHERE TimeOfEntrance = (SELECT MIN(MinDATE.TimeOfEntrance) FROM (SELECT TimeOfEntrance,OrderNumber, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors FROM gonature.waitinglist 
//								WHERE ( Date = '2021-01-09' AND Time IN ('7:00','8:00','9:00','10:00','11:00','12:00','6:00') AND DateOfEntrance = '2021-01-04' AND NumOfVisitors <= 7 ))  AS MinDATE);
								arrOfAnswer = mysqlConnection.getDB(
										"SELECT OrderNumber FROM gonature.waitinglist WHERE TimeOfEntrance = (SELECT MIN(MinDATE.TimeOfEntrance) FROM (SELECT TimeOfEntrance,OrderNumber, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors FROM gonature.waitinglist"
												+ " WHERE ( Date ='" + saveDate + "'  AND Time IN ('" + arrS[0]
												+ "') AND DateOfEntrance = '" + arrOfAnswer.get(0).toString()
												+ "' AND NumOfVisitors <="
												+ (numberOfReplacementInWaitingList - numOfVisitorMoved)
												+ ")) AS MinDATE);");

//										"SELECT MIN(MinDATE.TimeOfEntrance), MinDATE.OrderNumber FROM ( SELECT TimeOfEntrance,OrderNumber FROM gonature.waitinglist WHERE (Date = '"
//												+ saveDate + "' AND Time IN ('" + arrS[0] + "') AND DateOfEntrance = '"
//												+ arrOfAnswer.get(0).toString() + "' ))  AS MinDATE;");
								break;
							case 3:
								arrOfAnswer = mysqlConnection.getDB(
										"SELECT OrderNumber FROM gonature.waitinglist WHERE TimeOfEntrance = (SELECT MIN(MinDATE.TimeOfEntrance) FROM (SELECT TimeOfEntrance,OrderNumber, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors FROM gonature.waitinglist"
												+ " WHERE ( Date ='" + saveDate + "'  AND Time IN ('" + arrS[0] + "','" + arrS[1] + "','"
												+ arrS[2]+ "') AND DateOfEntrance = '" + arrOfAnswer.get(0).toString()
												+ "' AND NumOfVisitors <="
												+ (numberOfReplacementInWaitingList - numOfVisitorMoved)
												+ ")) AS MinDATE);");
								break;
							case 5:
								arrOfAnswer = mysqlConnection.getDB(
										"SELECT OrderNumber FROM gonature.waitinglist WHERE TimeOfEntrance = (SELECT MIN(MinDATE.TimeOfEntrance) FROM (SELECT TimeOfEntrance,OrderNumber, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors FROM gonature.waitinglist"
												+ " WHERE ( Date ='" + saveDate + "'  AND Time IN ('" + arrS[0] + "','" + arrS[1] + "','"
												+ arrS[2] + "','" + arrS[3] + "','" + arrS[4]
												+ "') AND DateOfEntrance = '" + arrOfAnswer.get(0).toString()
												+ "' AND NumOfVisitors <="
												+ (numberOfReplacementInWaitingList - numOfVisitorMoved)
												+ ")) AS MinDATE);");
								break;
							case 7:
								int y;
								arrOfAnswer = mysqlConnection.getDB(
										"SELECT OrderNumber FROM gonature.waitinglist "
										+ " WHERE TimeOfEntrance = (SELECT MIN(MinDATE.TimeOfEntrance) "
										+ "FROM (SELECT TimeOfEntrance,OrderNumber, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors "
										+ "FROM gonature.waitinglist WHERE ( Date ='" + saveDate + "'  AND Time IN ('" + arrS[0] + "','" + arrS[1] + "','"+ arrS[2] + "','" + arrS[3] + "','" + arrS[4] + "','" + arrS[5] + "','" + arrS[6] + "') AND DateOfEntrance = '" + arrOfAnswer.get(0).toString()+ "' AND NumOfVisitors <="+ (numOfVisitorsToCheck)+ ")) AS MinDATE);");
								break;
							case 9:
								arrOfAnswer = mysqlConnection.getDB(
										"SELECT OrderNumber FROM gonature.waitinglist WHERE TimeOfEntrance = (SELECT MIN(MinDATE.TimeOfEntrance) FROM (SELECT TimeOfEntrance,OrderNumber, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors FROM gonature.waitinglist"
												+ " WHERE ( Date ='" + saveDate + "'  AND Time IN ('" + arrS[0] + "','" + arrS[1] + "','"
												+ arrS[2] + "','" + arrS[3] + "','" + arrS[4] + "','" + arrS[5] + "','"
												+ arrS[6] + "','" + arrS[7] + "','" + arrS[8]
														 + "') AND DateOfEntrance = '" + arrOfAnswer.get(0).toString()
															+ "' AND NumOfVisitors <="
															+ (numberOfReplacementInWaitingList - numOfVisitorMoved)
															+ ")) AS MinDATE);");
								break;
							case 11:
								arrOfAnswer = mysqlConnection.getDB(
										"SELECT OrderNumber FROM gonature.waitinglist WHERE TimeOfEntrance = (SELECT MIN(MinDATE.TimeOfEntrance) FROM (SELECT TimeOfEntrance,OrderNumber, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors FROM gonature.waitinglist"
												+ " WHERE ( Date ='" + saveDate + "'  AND Time IN ('" + arrS[0] + "','" + arrS[1] + "','"
												+ arrS[2] + "','" + arrS[3] + "','" + arrS[4] + "','" + arrS[5] + "','"
												+ arrS[6] + "','" + arrS[7] + "','" + arrS[8] + "','" + arrS[9] + "','"
												+ arrS[10]  + "') AND DateOfEntrance = '" + arrOfAnswer.get(0).toString()
												+ "' AND NumOfVisitors <="
												+ (numberOfReplacementInWaitingList - numOfVisitorMoved)
												+ ")) AS MinDATE);");
								break;
							case 13:
								arrOfAnswer = mysqlConnection.getDB(
										"SELECT OrderNumber FROM gonature.waitinglist WHERE TimeOfEntrance = (SELECT MIN(MinDATE.TimeOfEntrance) FROM (SELECT TimeOfEntrance,OrderNumber, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors FROM gonature.waitinglist"
												+ " WHERE ( Date ='" + saveDate + "'  AND Time IN ('" + arrS[0] + "','" + arrS[1] + "','"
												+ arrS[2] + "','" + arrS[3] + "','" + arrS[4] + "','" + arrS[5] + "','"
												+ arrS[6] + "','" + arrS[7] + "','" + arrS[8] + "','" + arrS[9] + "','"
												+ arrS[10] + "','" + arrS[11] + "','" + arrS[12]
														 + "') AND DateOfEntrance = '" + arrOfAnswer.get(0).toString()
															+ "' AND NumOfVisitors <="
															+ (numberOfReplacementInWaitingList - numOfVisitorMoved)
															+ ")) AS MinDATE);");
								break;
							case 15:
								arrOfAnswer = mysqlConnection.getDB(
										"SELECT OrderNumber FROM gonature.waitinglist WHERE TimeOfEntrance = (SELECT MIN(MinDATE.TimeOfEntrance) FROM (SELECT TimeOfEntrance,OrderNumber, CAST(NumOfVisitors AS UNSIGNED) AS intNumOfVisitors FROM gonature.waitinglist"
												+ " WHERE ( Date ='" + saveDate + "'  AND Time IN ('" + arrS[0] + "','" + arrS[1] + "','"
												+ arrS[2] + "','" + arrS[3] + "','" + arrS[4] + "','" + arrS[5] + "','"
												+ arrS[6] + "','" + arrS[7] + "','" + arrS[8] + "','" + arrS[9] + "','"
												+ arrS[10] + "','" + arrS[11] + "','" + arrS[12] + "','" + arrS[13]
												+ "','" + arrS[14] + "') AND DateOfEntrance = '" + arrOfAnswer.get(0).toString()
												+ "' AND NumOfVisitors <="
												+ (numberOfReplacementInWaitingList - numOfVisitorMoved)
												+ ")) AS MinDATE);");
								break;
							default:
								System.out.println("ERROR");
								arrOfAnswer = mysqlConnection.getDB(
										"SELECT MIN(MinDATE.TimeOfEntrance), MinDATE.OrderNumber FROM ( SELECT TimeOfEntrance,OrderNumber FROM gonature.waitinglist WHERE (Date = '"
												+ saveDate + "' AND Time = '" + saveTime + "' AND DateOfEntrance = '"
												+ arrOfAnswer.get(0).toString() + "' ))  AS MinDATE;");
								break;
							}

							if (arrOfAnswer.get(0) != null) {
								arrOfAnswer = mysqlConnection
										.getDB("SELECT * FROM gonature.waitinglist WHERE OrderNumber = '"
												+ arrOfAnswer.get(0).toString() + "';");
								if (!arrOfAnswer.isEmpty()) {
									orderFromWaitingList.setParkName((String) arrOfAnswer.get(0));
									orderFromWaitingList.setHour((String) arrOfAnswer.get(1));
									orderFromWaitingList.setDate((String) arrOfAnswer.get(2));
									orderFromWaitingList.setNumOfVisitors((String) arrOfAnswer.get(3));
									orderFromWaitingList.setEmail((String) arrOfAnswer.get(4));
									orderFromWaitingList.setOrderNumber((String) arrOfAnswer.get(5));
									orderFromWaitingList.setNameOnOrder((String) arrOfAnswer.get(6));
									orderFromWaitingList.setOrderKind((String) arrOfAnswer.get(7));
									orderFromWaitingList.setID((String) arrOfAnswer.get(8));

									ans = mysqlConnection.newDBOrderFromWaitingList(orderFromWaitingList);
									if (ans) {
										ServerController.instance.displayMsg("Order from waiting list was added");
										String DeleteQuery2 = "DELETE FROM gonature.waitinglist WHERE (OrderNumber = '"
												+ orderFromWaitingList.getOrderNumber() + "');";
										ans = mysqlConnection.updateDB(DeleteQuery2); //// if was added then
																						//// delete
										if (ans) {
											ServerController.instance.displayMsg("Order was deleted from waiting list");
											numOfVisitorMoved += substructIfWeFoundInWaitingList;
											// if delete add the num of visitors that moved
										} else {
											ServerController.instance
													.displayMsg("Order could not be deleted from waiting list");
										}
									} else {
										ServerController.instance
												.displayMsg("Order from waiting list couldn't be added");
										// Query to delete the order from waitinglist table that was moved to
										// the
										// orders
										// table

									}
								}
							}
						}
					}

				
			} else {
				ServerController.instance.displayMsg("Order could not be deleted");

			}

		} else {
			ServerController.instance.displayMsg("Deleted order couldn't be moved to deletedOrder table");
		}

	}

	@Override
	public void run() {
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
				ordInDB = new Order(null, null, null, null, null, null, null, null, null, null, null, null);
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

				Thread.sleep(7200000); // wait for 2 hours.
				for (int i = 0; i < answer.size(); i++) {
					Order isItApprovedOrder = mysqlConnection.getDBOrder(answer.get(i));
					if (!(isItApprovedOrder.getApproved().equals("true"))) {
						deleteInstanceOrder(answer.get(i));
					} else { // its true (so it was approved and should be kept)
						displayMsg("The order " + answer.get(i).getOrderNumber() + " was approved!"); // dont cancel the
																										// order.
					}
				}
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