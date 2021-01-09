// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

import client.logic.WaitingList;
import client.ClientUI;
import client.logic.EmailDetails;
import client.logic.Order;
import client.logic.ParkInfo;
import client.logic.ParkStatus;
import client.logic.Subscriber;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.Worker;
import client.logic.casualOrder;
import client.logic.maxVis;
import common.DataTransfer;
import common.TypeOfMessage;
import common.TypeOfMessageReturn;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import ocsf.server.*;
import server.Controller.SendEmail;
import server.Controller.ServerController;
import server.database.mysqlConnection;

public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	ArrayList<Object> arrOfAnswer = null;
	Order order = new Order(null, null, null, null, null, null, null, null);
	String visitor = null;
	String TourID;
	public static int flag = 0;
	/**
	 * The default port to listen on.
	 */
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */

	public EchoServer(int port) {
		super(port);
		mysqlConnection.connectToDB();
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		ServerController.instance.displayMsg("Message received : " + msg + "\nfrom : " + client);
		DataTransfer data = (DataTransfer) msg;
		Object object = data.getObject();
		DataTransfer returnData = null;
		switch (data.getTypeOfMessage()) {
		
		case NEW_ORDER:
			if (object instanceof Order) {
				object = mysqlConnection.newDBOrder(object);
				if (object != null) {
					ServerController.instance.displayMsg("New Order created");
					returnData = new DataTransfer(TypeOfMessageReturn.NEWORDER_SUCCESS, object);
				} else {
					ServerController.instance.displayMsg("New Order could not be created");
					returnData = new DataTransfer(TypeOfMessageReturn.NEWORDER_FAILED, null);
				}
				try {
					client.sendToClient(returnData);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
			
		case GET_INFO:
			if (object instanceof Order) {
				order = mysqlConnection.getDBOrder(object);
				if (order != null) {
					ServerController.instance.displayMsg("Got Existing Order Details");
					returnData = new DataTransfer(TypeOfMessageReturn.RETURN_ORDER, order);
				} else {
					ServerController.instance.displayMsg("Couldn't recieve existing order details");
					returnData = new DataTransfer(TypeOfMessageReturn.RETURN_ORDER_FAILED, null);
				}
				try {
					client.sendToClient(returnData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
			
			/**
			 * This case relates to a family subscriber, 
			 * requests the server the entire order history of the subscriber and returns the orders as a list of objects
			 * The server sends the data to the ChatCliant.
			 */

		case REQUESTINFO_HISTORY:

			if (object instanceof Subscriber) {
				Subscriber subscriber = (Subscriber) object;
				ObservableList<Object> ans3 = mysqlConnection.getHistorySubOrders(subscriber.getId());
				// DataTransfer data = new DataTransfer(TypeOfMessage.SUCCSESS, ans3);

				if (ans3 != null) {
					for (int i = 0 ; i < ans3.size(); i++) {
						try {
							returnData = new DataTransfer(TypeOfMessageReturn.HISTORY_ORDERS, ans3.get(i));
							client.sendToClient(returnData);
							// client.sendToClient(ans3);
						}

						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

			break;

		case CHECK_KIND:
			if (object instanceof Order) {
				Order ord = (Order) object;
				String CheckQuery = "SELECT ID FROM gonature.subscriber WHERE ID ='" + ord.getID() + "';";
				boolean ans = mysqlConnection.CheckKind(CheckQuery); // if ans == true, ID exist in sub. else
				if (ans) {
					ServerController.instance.displayMsg("ID belongs to a Subscriber");
					returnData = new DataTransfer(TypeOfMessageReturn.IS_SUBSCRIBER, true);
				} else {
					CheckQuery = "SELECT ID FROM gonature.tourguides WHERE ID ='" + ord.getID() + "';";
					ans = mysqlConnection.CheckKind(CheckQuery); // if ans == true, ID exist in tourguide.
					if (ans) {
						ServerController.instance.displayMsg("ID belong to a Tour Guide");
						returnData = new DataTransfer(TypeOfMessageReturn.IS_GUIDE, true);
					} else {
						ServerController.instance.displayMsg("ID belong to a Regular Traveler");
						returnData = new DataTransfer(TypeOfMessageReturn.IS_REGULAR, true);
					}
				}
				try {
					client.sendToClient(returnData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (object instanceof String) { // if it came from ChangeOrderDetails pretty much.
				String id = (String) object;
				String CheckQuery = "SELECT ID FROM gonature.subscriber WHERE ID ='" + id + "';";
				boolean ans = mysqlConnection.CheckKind(CheckQuery); // if ans == true, ID exist in sub. else
				if (ans) {
					ServerController.instance.displayMsg("ID belongs to a Subscriber");
					returnData = new DataTransfer(TypeOfMessageReturn.IS_SUBSCRIBER, new Integer(1)); // just to
																										// seperate it
																										// from the
																										// instanceof
																										// order.
				} else {
					CheckQuery = "SELECT ID FROM gonature.tourguides WHERE ID ='" + id + "';";
					ans = mysqlConnection.CheckKind(CheckQuery); // if ans == true, ID exist in tourguide.
					if (ans) {
						ServerController.instance.displayMsg("ID belong to a Tour Guide");
						returnData = new DataTransfer(TypeOfMessageReturn.IS_GUIDE, new Integer(1));
					} else {
						ServerController.instance.displayMsg("ID belong to a Regular Traveler");
						returnData = new DataTransfer(TypeOfMessageReturn.IS_REGULAR, new Integer(1));
					}
				}
				try {
					client.sendToClient(returnData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
			
			/**
			 * This case requests information from the database.
			 */

		case REQUESTINFO:
			
			/**
			 * If the object is a subscription type, it requests all the subscriber information from the database by subscription number.
			 * The server checks whether the database request was successful or failed.
			 * The server sends the data to the ChatCliant.
			 */
			
			if (object instanceof Subscriber) {
				Subscriber subscriber = (Subscriber) object;
				String checkSubExist = "SELECT * FROM gonature.subscriber WHERE subscriberNumber ='"
						+ subscriber.getSubscriberNumber() + "';";
				arrOfAnswer = mysqlConnection.getDB(checkSubExist);

				if (!arrOfAnswer.isEmpty()) {
					subscriber.setId((String) arrOfAnswer.get(0));
					subscriber.setFname((String) arrOfAnswer.get(1));
					subscriber.setLname((String) arrOfAnswer.get(2));
					subscriber.setEmail((String) arrOfAnswer.get(3));
					subscriber.setteln((String) arrOfAnswer.get(4));
					subscriber.setAmountOfFamilyMember((String) arrOfAnswer.get(5));
					subscriber.setCreditCard((String) arrOfAnswer.get(6));
					subscriber.setSubscriberNumber((String) arrOfAnswer.get(7));

					returnData = new DataTransfer(TypeOfMessageReturn.REQUESTINFO_SUCCESS, subscriber);
					try {
						client.sendToClient(returnData);

					} catch (IOException e) {
						e.printStackTrace();
					}
				} else
					ServerController.instance.displayMsg("subscriber REQUESTINFO details failed");

			}
			// update dates and discount on manager screen anf take all park setting on DM
			if (object instanceof ParkInfo) {
				ParkInfo parkInfo = (ParkInfo) object;
				if (parkInfo.getRole().equals("Manager")) {
					arrOfAnswer = mysqlConnection
							.getDB("SELECT Dates,Discount,Approve FROM gonature.discountdates WHERE numOfPark = '"
									+ parkInfo.getNumberOfPark() + "';");
					if (!arrOfAnswer.isEmpty()) {
						String[][] datesDiscount = new String[arrOfAnswer.size() / 3][3];
						int getData = 0;
						for (int i = 0; i < arrOfAnswer.size() / 3; i++) {
							for (int j = 0; j < 3; j++) {
								datesDiscount[i][j] = (String) arrOfAnswer.get(getData);
								getData++;
							}

						}
						parkInfo.setDatesDiscount(datesDiscount);
						returnData = new DataTransfer(TypeOfMessageReturn.REQUESTINFO_SUCCESS, parkInfo);
					} else {
						returnData = new DataTransfer(TypeOfMessageReturn.REQUESTINFO_FAILED, parkInfo);
					}
				} else if (parkInfo.getRole().equals("Department Manager")) {
					arrOfAnswer = mysqlConnection
							.getDB("SELECT Dates,Discount FROM gonature.discountdates WHERE numOfPark = '"
									+ parkInfo.getNumberOfPark() + "' AND Approve = 'toCheck';");
					if (!arrOfAnswer.isEmpty()) {
						String[][] datesDiscount = new String[arrOfAnswer.size() / 2][2];
						int getData = 0;
						for (int i = 0; i < arrOfAnswer.size() / 2; i++) {
							for (int j = 0; j < 2; j++) {
								datesDiscount[i][j] = (String) arrOfAnswer.get(getData);
								getData++;
							}

						}
						parkInfo.setDatesDiscount(datesDiscount);
					}
					arrOfAnswer = mysqlConnection
							.getDB("SELECT maxVisitors,gapOfVisitors,maxHourToVisit FROM gonature.manageparkstoapprove "
									+ "WHERE numberOfPark = '" + parkInfo.getNumberOfPark()
									+ "' AND Approve = 'toCheck';");
					if (!arrOfAnswer.isEmpty()) {
						if (arrOfAnswer.get(0) != null)
							parkInfo.setMaxVisitors(arrOfAnswer.get(0).toString());
						if (arrOfAnswer.get(1) != null)
							parkInfo.setGapOfVisitors(arrOfAnswer.get(1).toString());
						if (arrOfAnswer.get(2) != null)
							parkInfo.setMaxHourToVisit(arrOfAnswer.get(2).toString());

					}
					returnData = new DataTransfer(TypeOfMessageReturn.REQUESTINFO_SUCCESS, parkInfo);
//						try {
//							client.sendToClient(returnData);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
				} else {
					returnData = new DataTransfer(TypeOfMessageReturn.REQUESTINFO_FAILED, parkInfo);
				}
				try {
					client.sendToClient(returnData);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			break;
			
		case DELETEINFO:
			if (object instanceof ParkInfo) {
				ParkInfo parkInfo = (ParkInfo) object;
				boolean answer;
				if (parkInfo.getGapOfVisitors() != null) {
					answer = mysqlConnection
							.updateDB("UPDATE gonature.manageparkstoapprove SET gapOfVisitors = null WHERE "
									+ "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					if (answer)
						ServerController.instance.displayMsg(
								"gapOfVisitors DELETEINFO details updated at " + parkInfo.getNumberOfPark());
					else {
						ServerController.instance
								.displayMsg("gapOfVisitors DELETEINFO details failed at " + parkInfo.getNumberOfPark());
					}
				}

				if (parkInfo.getMaxVisitors() != null) {
					answer = mysqlConnection
							.updateDB("UPDATE gonature.manageparkstoapprove SET maxVisitors = null WHERE "
									+ "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					if (answer) {
						ServerController.instance
								.displayMsg("maxVisitors DELETEINFO details updated at " + parkInfo.getNumberOfPark());

					} else {
						ServerController.instance
								.displayMsg("maxVisitors DELETEINFO details failed at " + parkInfo.getNumberOfPark());
					}
				}
				if (parkInfo.getMaxHourToVisit() != null) {
					answer = mysqlConnection
							.updateDB("UPDATE gonature.manageparkstoapprove SET maxHourToVisit = null WHERE "
									+ "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					if (answer) {
						ServerController.instance.displayMsg(
								"maxHourToVisit DELETEINFO details updated at " + parkInfo.getNumberOfPark());
					} else {
						ServerController.instance.displayMsg(
								"maxHourToVisit DELETEINFO details failed at " + parkInfo.getNumberOfPark());
					}
				}
				if (parkInfo.isChangeSettingToTrue()) {
					answer = mysqlConnection.updateDB("UPDATE gonature.manageparkstoapprove SET Approve = 'True' WHERE "
							+ "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					if (answer) {
						ServerController.instance.displayMsg("Approve = True DELETEINFO details updated ");

					} else {
						ServerController.instance.displayMsg("Approve = True DELETEINFO details failed");
					}
				}

				if (parkInfo.getDiscountDates() != null) {
					String[][] deletDates = parkInfo.getDiscountDates();
					for (int i = 0; i < deletDates.length; i++) {
						answer = mysqlConnection.updateDB("DELETE FROM gonature.discountdates WHERE Dates = '"
								+ deletDates[i][0] + "' AND " + "numOfPark = '" + parkInfo.getNumberOfPark() + "';");
						if (answer) {
							ServerController.instance.displayMsg(
									"Date '" + deletDates[i][0] + "' for discount DELETEINFO details updated ");

						} else {
							ServerController.instance.displayMsg(
									"Date '" + deletDates[i][0] + "' for discount DELETEINFO details failed");
						}
					}
				}
			}

			if (object instanceof Order) { // delete an order from Orders, check if there's an order in waitinglist good
											// to advance to
				// orders, move it to orders, delete the order that was moved from waitinglist
				// table.
				Order ordToBeDeleted = (Order) object;
				Order orderFromWaitingList = new Order(null, null, null, null, null, null, null, null, null, null,
						null);
				String saveDate = ordToBeDeleted.getDate();
				String saveTime = ordToBeDeleted.getHour();

				String DeleteQuery = "DELETE FROM gonature.orders WHERE (OrderNumber = "
						+ ordToBeDeleted.getOrderNumber() + ");";
				boolean ans = mysqlConnection.updateDB(DeleteQuery);
				if (ans) {
					ServerController.instance.displayMsg("Order was deleted");
				} else {
					ServerController.instance.displayMsg("Order could not be deleted");
				}
				// getting the first in line from the waiting list which fits the time and date.
				arrOfAnswer = mysqlConnection.getDB("SELECT MIN(DateOfEntrance)  FROM gonature.waitinglist;");
				if (!arrOfAnswer.isEmpty()) {
					arrOfAnswer = mysqlConnection.getDB(
							"SELECT MIN(MinDATE.TimeOfEntrance), MinDATE.OrderNumber FROM ( SELECT TimeOfEntrance,OrderNumber FROM gonature.waitinglist WHERE (Date = '"
									+ saveDate + "' AND Time = '" + saveTime + "' AND DateOfEntrance = '"
									+ arrOfAnswer.get(0).toString() + "' ))  AS MinDATE;");

					// Object visMax = new maxVis(null, null, null, 0, 0, null, 0);
//					maxVis t = new maxVis(null, null, null, 0, 0, null, 0);
//					t.setDate(saveDate);
//					t.setPark(ordToBeDeleted.getParkName());
//					t.setVisitorsInOrder(ordToBeDeleted.getNumOfVisitors());
//					t.setTime(saveTime);
//					mysqlConnection.checkMaxVisitorsForWaitingList(t);

					// fix here. +/- 3 hours.
					if (arrOfAnswer.get(0) != null) {
						arrOfAnswer = mysqlConnection.getDB("SELECT * FROM gonature.waitinglist WHERE OrderNumber = '"
								+ arrOfAnswer.get(1).toString() + "';");
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
							} else {
								ServerController.instance.displayMsg("Order from waiting list couldn't be added");
							} // Query to delete the order from waitinglist table that was moved to the orders
								// table
							String DeleteQuery2 = "DELETE FROM gonature.waitinglist WHERE (OrderNumber = '"
									+ orderFromWaitingList.getOrderNumber() + "');";
							ans = mysqlConnection.updateDB(DeleteQuery2);
							if (ans) {
								ServerController.instance.displayMsg("Order was deleted from waiting list");
							} else {
								ServerController.instance.displayMsg("Order could not be deleted from waiting list");
							}
						}
					}
				}
			}
			break;
			
			/**
			 * This case updates the database.
			 */
			
		case UPDATEINFO:
			if (object instanceof Order) {
				Order ord = (Order) object;
				String UpdateQuery = "UPDATE gonature.orders SET Park = '" + ord.getParkName() + "'," + " Time= '"
						+ ord.getHour() + "'," + " Date = '" + ord.getDate() + "'," + " NumOfVisitors = '"
						+ ord.getNumOfVisitors() + "' WHERE OrderNumber = '" + ord.getOrderNumber() + "';";
				boolean ans = mysqlConnection.updateDB(UpdateQuery);
				if (ans) {
					ServerController.instance.displayMsg("order UPDATE_ORDER details updated");
					returnData = new DataTransfer(TypeOfMessageReturn.UPDATE_SUCCESS, new Order());
				} else {
					ServerController.instance.displayMsg("order UPDATE_ORDER details could not be updated");
					returnData = new DataTransfer(TypeOfMessageReturn.UPDATE_FAILED, new Order());
				}
				try {
					client.sendToClient(returnData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * If the object is a subscription type, it updates all the subscriber in the database of the subscriber.
			 * The server checks to see if the database update was successful or failed.
			 * The server sends the data to the ChatCliant.
			 */
			
			if (object instanceof Subscriber) {
				Subscriber subscriber = (Subscriber) object;
				String upDateSub = "UPDATE `gonature`.`subscriber` SET `ID` = '" + subscriber.getId()
						+ "', `FirstName` = '" + subscriber.getFname() + "', " + "`LastName` = '"
						+ subscriber.getLname() + "', `Email` = '" + subscriber.getEmail() + "', `Telephone` = '"
						+ subscriber.getTeln() + "', " + "`AmountOfFamilyMembers` = '"
						+ subscriber.getAmountOfFamilyMember() + "', `CreditCard` = '" + subscriber.getCreditCard()
						+ "' WHERE " + "(`ID` = '" + subscriber.getId() + "') and " + "(`subscriberNumber` = '"
						+ subscriber.getSubscriberNumber() + "');";
				boolean Answer = mysqlConnection.updateDB(upDateSub);
				if (Answer) {
					ServerController.instance.displayMsg("subscriber UPDATEINFO details updated");

				} else {
					ServerController.instance.displayMsg("subscriber UPDATEINFO details failed");
				}
			}
			
			if (object instanceof ParkInfo) {
				ParkInfo parkInfo = (ParkInfo) object;
				boolean answer;
				if (parkInfo.getGapOfVisitors() != null) {
					answer = mysqlConnection
							.updateDB("UPDATE gonature.manageparks SET gapOfVisitors = '" + parkInfo.getGapOfVisitors()
									+ "' WHERE " + "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					if (answer) {
						answer = mysqlConnection
								.updateDB("UPDATE gonature.manageparkstoapprove SET gapOfVisitors = null WHERE "
										+ "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
						ServerController.instance.displayMsg(
								"gapOfVisitors UPDATEINFO details updated at " + parkInfo.getNumberOfPark());

					} else {
						ServerController.instance
								.displayMsg("gapOfVisitors UPDATEINFO details failed at " + parkInfo.getNumberOfPark());
					}
				}
				if (parkInfo.getMaxVisitors() != null) {
					answer = mysqlConnection
							.updateDB("UPDATE gonature.manageparks SET maxVisitors = '" + parkInfo.getMaxVisitors()
									+ "' WHERE " + "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					if (answer) {
						ServerController.instance
								.displayMsg("maxVisitors UPDATEINFO details updated at " + parkInfo.getNumberOfPark());
						answer = mysqlConnection
								.updateDB("UPDATE gonature.manageparkstoapprove SET maxVisitors = null WHERE "
										+ "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					} else {
						ServerController.instance
								.displayMsg("maxVisitors UPDATEINFO details failed at " + parkInfo.getNumberOfPark());
					}
				}
				if (parkInfo.getMaxHourToVisit() != null) {
					answer = mysqlConnection.updateDB(
							"UPDATE gonature.manageparks SET maxHourToVisit = '" + parkInfo.getMaxHourToVisit()
									+ "' WHERE " + "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					if (answer) {
						ServerController.instance.displayMsg(
								"maxHourToVisit UPDATEINFO details updated at " + parkInfo.getNumberOfPark());
						answer = mysqlConnection
								.updateDB("UPDATE gonature.manageparkstoapprove SET maxHourToVisit = null WHERE "
										+ "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					} else {
						ServerController.instance.displayMsg(
								"maxHourToVisit UPDATEINFO details failed at " + parkInfo.getNumberOfPark());
					}
				}
				if (parkInfo.isChangeSettingToTrue()) {
					answer = mysqlConnection.updateDB("UPDATE gonature.manageparkstoapprove SET Approve = 'True' WHERE "
							+ "numberOfPark = '" + parkInfo.getNumberOfPark() + "';");
					if (answer) {
						ServerController.instance.displayMsg("Approve = True UPDATEINFO details updated ");

					} else {
						ServerController.instance.displayMsg("Approve = True UPDATEINFO details failed");
					}
				}
				if (parkInfo.getDiscountDates() != null) {
					String[][] approveDates = parkInfo.getDiscountDates();
					for (int i = 0; i < approveDates.length; i++) {
						answer = mysqlConnection
								.updateDB("UPDATE gonature.discountdates SET Approve = 'True' WHERE Dates = '"
										+ approveDates[i][0] + "' AND " + "numOfPark = '" + parkInfo.getNumberOfPark()
										+ "';");
						if (answer) {
							ServerController.instance.displayMsg(
									"Date '" + approveDates[i][0] + "' for discount DELETEINFO details updated ");

						} else {
							ServerController.instance.displayMsg(
									"Date '" + approveDates[i][0] + "' for discount DELETEINFO details failed");
						}
					}

				}

			}
			break;
			
			/**
			 * This case inserts information into the database.
			 */
			
		case INSERTINFO:
			
			/**
			 * If the object is a subscription type, it inserts information into the database of the subscriber.
			 * The server creates a family subscription in the database and sends the user an email with his registration data.
			 * The server sends the data to the ChatCliant.
			 */
			
			if (object instanceof Subscriber) {
				Subscriber newSubscriber = (Subscriber) object;

				arrOfAnswer = mysqlConnection.getDB("SELECT COUNT(*)+11111 FROM `gonature`.`subscriber`");
				String subscriberNumber = "S" + arrOfAnswer.get(0).toString();
				boolean subAdded = mysqlConnection.updateDB(
						"INSERT INTO `gonature`.`subscriber` (`ID`, `FirstName`, `LastName`, `Email`, `Telephone`, `AmountOfFamilyMembers`, `CreditCard`, `subscriberNumber`) "
								+ "VALUES ('" + newSubscriber.getId() + "', '" + newSubscriber.getFname() + "', '"
								+ newSubscriber.getLname() + "'," + " '" + newSubscriber.getEmail() + "', '"
								+ newSubscriber.getTeln() + "', '" + newSubscriber.getAmountOfFamilyMember() + "',"
								+ " '" + newSubscriber.getCreditCard() + "', '" + subscriberNumber + "');");
				if (subAdded) {
					new SendEmail(newSubscriber.getEmail(),
							"Welcome " + newSubscriber.getFname() + " " + newSubscriber.getLname(),
							"Your subscription number: " + subscriberNumber + "\n" + "Your ID : "
									+ newSubscriber.getId() + "\n" + "Amount of family members: "
									+ newSubscriber.getAmountOfFamilyMember() + "\n"
									+ "The subscription is free, you can now enter and place an order.\n"
									+ "Enjoy the discounts :)");
					newSubscriber.setSubscriberNumber(subscriberNumber);
					returnData = new DataTransfer(TypeOfMessageReturn.UPDATE_SUCCESS, newSubscriber);
					try {
						client.sendToClient(returnData);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			
			if (object instanceof TourGuide) {
				TourGuide newTourGuide = (TourGuide) object;
				boolean tourAdded = mysqlConnection.updateDB(
						"INSERT INTO `gonature`.`tourguides` (`Name`, `LastName`, `ID`, `Email`, `PhoneNumber`) "
								+ "VALUES ('" + newTourGuide.getFname() + "', '" + newTourGuide.getLname() + "', '"
								+ newTourGuide.getId() + "'," + " '" + newTourGuide.getEmail() + "', '"
								+ newTourGuide.getTeln() + "');");
				if (tourAdded) {
					new SendEmail(newTourGuide.getEmail(),
							"Welcome " + newTourGuide.getFname() + " " + newTourGuide.getLname(),
							"You are now part of GoNature Family!\n" + "Your ID : " + newTourGuide.getId() + "\n"
									+ "You can now enter and place an order.\n" + "Enjoy the discounts :)");
					returnData = new DataTransfer(TypeOfMessageReturn.UPDATE_SUCCESS, newTourGuide);
					try {
						client.sendToClient(returnData);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
			
			/**
			 * This case requests the server to login to the user.
			 */
			
		case LOGIN_REQUEST:
			if (object instanceof Worker) {
				Worker worker = (Worker) object;
//				boolean alreadyConnect = false;
				Worker RoleAndPark = null;
				ParkInfo parkInfo;
				String checkUserAndPassword = "SELECT Role, Park, name, LogIn FROM gonature.worker WHERE UserName = '"
						+ worker.getUserName() + "' AND Password = '" + worker.getPassword() + "';";
				arrOfAnswer = mysqlConnection.getDB(checkUserAndPassword);
				if (!arrOfAnswer.isEmpty()) {
					if (arrOfAnswer.get(3).toString().equals("True")) {
						returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED_CONNECTED, new Worker());
//						alreadyConnect = true;
					} else {
						String scene;
						String role = (String) arrOfAnswer.get(0);
						String park = (String) arrOfAnswer.get(1);
						String workerName = (String) arrOfAnswer.get(2);
						boolean connect = mysqlConnection
								.updateDB("UPDATE gonature.worker SET LogIn = 'True' WHERE UserName = '"
										+ worker.getUserName() + "' AND Park = '" + park + "';");
						if (connect) {
							ServerController.instance.displayMsg(worker.getUserName() + " Connected [UPDATEINFO]");
							arrOfAnswer = mysqlConnection.getDB("SELECT COUNT(*) FROM gonature.subscriber;");
							String countSub = arrOfAnswer.get(0).toString();
							String updateCountSub = "UPDATE gonature.manageparks SET numberOfSub = '" + countSub
									+ "' WHERE numberOfPark = '" + park + "';";
							boolean Answer = mysqlConnection.updateDB(updateCountSub);
							if (Answer) {
								ServerController.instance.displayMsg("manageparks numOfSub UPDATEINFO details updated");

							} else {
								ServerController.instance.displayMsg("manageparks numOfSub UPDATEINFO details failed");
							}
							arrOfAnswer = mysqlConnection
									.getDB("SELECT * FROM gonature.manageparks WHERE numberOfPark = '" + park + "'");
							if (role.equals("Manager")) {
								scene = "/client/boundaries/manager.fxml";
								role = "Manager";
								parkInfo = new ParkInfo((String) arrOfAnswer.get(0), (String) arrOfAnswer.get(1),
										(String) arrOfAnswer.get(2), (String) arrOfAnswer.get(3),
										(String) arrOfAnswer.get(4));
								RoleAndPark = new Worker(worker.getUserName(), null, role, parkInfo, workerName, scene);

							} else if (role.equals("Department Manager")) {
								scene = "/client/boundaries/mainDepartmantManager.fxml";
								role = "Department Manager";
								parkInfo = new ParkInfo((String) arrOfAnswer.get(0), (String) arrOfAnswer.get(1),
										(String) arrOfAnswer.get(2), (String) arrOfAnswer.get(3), null);
								RoleAndPark = new Worker(worker.getUserName(), null, role, parkInfo, workerName, scene);

							} else if (role.equals("Service")) {
								scene = "/client/boundaries/FamilySubscriptionRegistration.fxml";
								role = "Service Representative";
								parkInfo = new ParkInfo((String) arrOfAnswer.get(0), null, null, null, null);
								RoleAndPark = new Worker(worker.getUserName(), null, role, parkInfo, workerName, scene);

							} else if (role.equals("ParkReception")) {
								scene = "/client/boundaries/WorkerParkEnternece.fxml";
								role = "Park Reception";
								parkInfo = new ParkInfo((String) arrOfAnswer.get(0), null, null, null, null);
								RoleAndPark = new Worker(null, null, role, parkInfo, workerName, scene);
							} else
								System.out.println("Error");
							returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_SUCCESSFUL, RoleAndPark);
						} else {
							ServerController.instance.displayMsg(worker.getUserName() + " UPDATEINFO details failed");
							returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED, new Worker());
						}
					}
				} else {// if (!alreadyConnect)
					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED, new Worker());
				}
				try {
					client.sendToClient(returnData);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/**
			 * If the object is a subscription type, it requests the server to login to the subscriber user by the database.
			 * The server checks if the login was successful or failed.
			 * The server sends the data to the ChatCliant.
			 */
			
			if (object instanceof Subscriber) {
				Subscriber subscriber = (Subscriber) object;
				String checkSubExist = "SELECT * FROM gonature.subscriber WHERE subscriberNumber ='"
						+ subscriber.getSubscriberNumber() + "' " + "OR ID = '" + subscriber.getId() + "'; ";
				arrOfAnswer = mysqlConnection.getDB(checkSubExist);

				if (!arrOfAnswer.isEmpty()) {
					subscriber.setId((String) arrOfAnswer.get(0));
					subscriber.setFname((String) arrOfAnswer.get(1));
					subscriber.setLname((String) arrOfAnswer.get(2));
					subscriber.setEmail((String) arrOfAnswer.get(3));
					subscriber.setteln((String) arrOfAnswer.get(4));
					subscriber.setAmountOfFamilyMember((String) arrOfAnswer.get(5));
					subscriber.setCreditCard((String) arrOfAnswer.get(6));
					subscriber.setSubscriberNumber((String) arrOfAnswer.get(7));

					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_SUCCESSFUL, subscriber);
				} else
					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED, new Subscriber());
				try {
					client.sendToClient(returnData);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
			
		case LOGOUT:
			if (object instanceof Worker) {
				Worker worker = (Worker) object;
				boolean connect = mysqlConnection
						.updateDB("UPDATE gonature.worker SET LogIn = 'False' WHERE UserName = '" + worker.getUserName()
								+ "' AND Park = '" + worker.getPark().getNumberOfPark() + "';");

				if (connect) {
					ServerController.instance.displayMsg(worker.getUserName() + " Logged Out [UPDATEINFO]");

				} else {
					ServerController.instance.displayMsg(worker.getUserName() + "UPDATEINFO details failed");
				}
			}
			break;
			
		case UPDATEINFO_REQUEST:
			if (object instanceof ParkInfo) {
				ParkInfo parkInfo = (ParkInfo) object;
				String UpdateQuery;
				if (parkInfo.getGapOfVisitors() != null) {
					UpdateQuery = "UPDATE gonature.manageparkstoapprove SET Approve = 'toCheck', "
							+ " gapOfVisitors = '" + parkInfo.getGapOfVisitors() + "' WHERE numberOfPark = '"
							+ parkInfo.getNumberOfPark() + "';";
					boolean ans = mysqlConnection.updateDB(UpdateQuery);
					if (ans)
						ServerController.instance
								.displayMsg("parkInfo UPDATEINFO_REQUEST gapOfVisitors details updated");
					else
						ServerController.instance
								.displayMsg("parkInfo UPDATEINFO_REQUEST gapOfVisitors details could not be updated");
				}
				if (parkInfo.getMaxHourToVisit() != null) {
					UpdateQuery = "UPDATE gonature.manageparkstoapprove SET Approve = 'toCheck', "
							+ " maxHourToVisit = '" + parkInfo.getMaxHourToVisit() + "' WHERE numberOfPark = '"
							+ parkInfo.getNumberOfPark() + "';";
					boolean ans = mysqlConnection.updateDB(UpdateQuery);
					if (ans)
						ServerController.instance
								.displayMsg("parkInfo UPDATEINFO_REQUEST maxHourToVisit details updated");
					else
						ServerController.instance
								.displayMsg("parkInfo UPDATEINFO_REQUEST maxHourToVisit details could not be updated");
				}
				if (parkInfo.getMaxVisitors() != null) {
					UpdateQuery = "UPDATE gonature.manageparkstoapprove SET Approve = 'toCheck', maxVisitors = '"
							+ parkInfo.getMaxVisitors() + "' WHERE " + "numberOfPark = '" + parkInfo.getNumberOfPark()
							+ "';";
					boolean ans = mysqlConnection.updateDB(UpdateQuery);
					if (ans)
						ServerController.instance.displayMsg("parkInfo UPDATEINFO_REQUEST maxVisitors details updated");
					else
						ServerController.instance
								.displayMsg("parkInfo UPDATEINFO_REQUEST maxVisitors details could not be updated");
				}

			}
			if (object instanceof ArrayList<?>) {
				ArrayList<String> discount = (ArrayList<String>) object;
				boolean answer;
				String insertNewDiscount = "INSERT INTO gonature.discountdates (`Dates`, `Discount`, `Approve`, `numOfPark`) VALUES ('"
						+ discount.get(1) + "', '" + discount.get(0) + "', 'toCheck', '" + discount.get(2) + "');";
				String updateExistDiscount = "UPDATE gonature.discountdates SET Discount ='" + discount.get(0)
						+ "' , Approve = 'toCheck' WHERE" + " numOfPark = '" + discount.get(2) + "' AND Dates = '"
						+ discount.get(1) + "';";
				arrOfAnswer = mysqlConnection
						.getDB("SELECT Dates FROM gonature.discountdates WHERE Dates ='" + discount.get(1) + "';");
				if (arrOfAnswer.isEmpty()) {
					answer = mysqlConnection.updateDB(insertNewDiscount);
				} else {
					answer = mysqlConnection.updateDB(updateExistDiscount);
				}
				if (answer)
					ServerController.instance.displayMsg("Discount UPDATEINFO_REQUEST details updated");
				else
					ServerController.instance.displayMsg("Discount UPDATEINFO_REQUEST details could not be updated");

			}
			if (object instanceof Order) {
				Order ord = (Order) object;
				String UpdateQuery = "UPDATE gonature.orders SET totalPrice = '" + ord.getTotalPrice()
						+ "' WHERE OrderNumber = '" + ord.getOrderNumber() + "';";
				boolean ans = mysqlConnection.updateDB(UpdateQuery);
				if (ans) {
					ServerController.instance.displayMsg("Order price was updated");
				} else {
					ServerController.instance.displayMsg("Order price couldn't be updated");
				}
			}
			break;

		case TOURGUIDELOGIN:
			if (object instanceof TourGuide) {
				TourGuide tourguide = (TourGuide) object;
				arrOfAnswer = mysqlConnection
						.getDB("SELECT * FROM gonature.tourguides where id = '" + tourguide.getId() + "';");
				TourID = (String) arrOfAnswer.get(2);
				tourguide.setFname(arrOfAnswer.get(0).toString());
				tourguide.setLname(arrOfAnswer.get(1).toString());
				tourguide.setId(arrOfAnswer.get(2).toString());
				tourguide.setEmail(arrOfAnswer.get(3).toString());
				tourguide.setteln(arrOfAnswer.get(4).toString());
				if (!arrOfAnswer.isEmpty()) {
					try {
						returnData = new DataTransfer(TypeOfMessageReturn.TOUR_DETAILS, tourguide);
						client.sendToClient(returnData);

					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED, tourguide);
					try {
						client.sendToClient(returnData);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;

		case TOURGETORDERS:
			if (object instanceof Integer) {
				ObservableList<Object> ans3 = mysqlConnection.getTourGuideOrders(TourID);
				// DataTransfer data = new DataTransfer(TypeOfMessage.SUCCSESS, ans3);

				if (ans3 != null) {
					for (int i = 0; i < ans3.size(); i++) {
						try {
							returnData = new DataTransfer(TypeOfMessageReturn.TOUR_MYORDERS, ans3.get(i));
							client.sendToClient(returnData);
							// client.sendToClient(ans3);
						}

						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			break;

		case TOURGUIDEDETAILS:
			if (object instanceof TourGuide) {
				TourGuide updGuide = (TourGuide) object;
				String updEmail = updGuide.getEmail();
				String upName = updGuide.getFname();
				String upLName = updGuide.getLname();
				String upNumber = updGuide.getTeln();

				String query = "UPDATE tourguides SET Name='" + upName + "', LastName='" + upLName + "', Email='"
						+ updEmail + "', phoneNumber='" + upNumber + "' WHERE ID='" + updGuide.getId() + "'";

				boolean ans = mysqlConnection.updateDB(query);
				if (ans)
					ServerController.instance.displayMsg("TourGuide details updated");
				else
					ServerController.instance.displayMsg("TourGuide details could not be updated");
			}
			break;

		case TOURGUIDENEWORDER:
			if (object instanceof TourGuideOrder) {
				boolean ans2 = mysqlConnection.updateDBOrders(object);
				if (ans2)
					ServerController.instance.displayMsg("TourGuide details updated");
				else
					ServerController.instance.displayMsg("TourGuide details could not be updated");
			}
			break;
		case TOURGUIDEWAITINGLIST:
			if (object instanceof WaitingList) {
				WaitingList wait = (WaitingList) object;
				boolean ans2 = mysqlConnection.updateWaitingListTour(wait);
				if (ans2)
					ServerController.instance.displayMsg("Waitinglist details updated");
				else
					ServerController.instance.displayMsg("Waitinglist details could not be updated");
			}

			break;
		case NEW_ORDERWAITINGLIST:
			if (object instanceof WaitingList) {
				boolean ans2 = mysqlConnection.updateWaitingListNewOrder(object);
				if (ans2)
					ServerController.instance.displayMsg("Waitinglist details updated");
				else
					ServerController.instance.displayMsg("Waitinglist details could not be updated");
			}
			break;

		case CHECKMAXVIS:
			if (object instanceof TourGuideOrder) {
				Object visMax = new maxVis(null, null, null, 0, 0, null, 0);
				maxVis t = new maxVis(null, null, null, 0, 0, null, 0);
				t.setDate(((TourGuideOrder) object).getDate());
				t.setPark(((TourGuideOrder) object).getParkName());
				t.setVisitorsInOrder(((TourGuideOrder) object).getNumOfVisitors());
				t.setTime(((TourGuideOrder) object).getTime());

				visMax = mysqlConnection.checkMaxVisitors(t);

				visMax = (Object) visMax;

				if (visMax != null) {
					try {
						returnData = new DataTransfer(TypeOfMessageReturn.TOUR_MAXVISCHECK, visMax);
						client.sendToClient(returnData);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			if (object instanceof Order) {
				Object visMax = new maxVis(null, null, null, 0, 0, null, 0);
				maxVis t = new maxVis(null, null, null, 0, 0, null, 0);
				t.setDate(((Order) object).getDate());
				t.setPark(((Order) object).getParkName());
				t.setVisitorsInOrder(((Order) object).getNumOfVisitors());
				t.setTime(((Order) object).getHour());

				visMax = mysqlConnection.checkMaxVisitors(t);
				visMax = (Object) visMax;
				if (visMax != null) {
					try {
						returnData = new DataTransfer(TypeOfMessageReturn.NEW_ORDERMAXVISCHECK, visMax);
						client.sendToClient(returnData);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			break;

			/**
			 * This case is responsible for placing a new order for a family subscription.
			 * The server checks whether the new order is approved or not by checking the data with the database.
			 * The server sends the data to the ChatCliant.
			 */
			
		case SUBSCRIBER_NEWORDER:
			if (object instanceof Order) {
				Order order = (Order) object;
				maxVis visMax = new maxVis(null, null, null, 0, 0, null, 0);
				maxVis tempMaxVisForCheck = new maxVis(null, null, null, 0, 0, null, 0);
				tempMaxVisForCheck.setDate(order.getDate());
				tempMaxVisForCheck.setPark(order.getParkName());
				tempMaxVisForCheck.setVisitorsInOrder(order.getNumOfVisitors());
				tempMaxVisForCheck.setTime(order.getHour());
				

				visMax = mysqlConnection.checkMaxVisitors(tempMaxVisForCheck);
				if (visMax != null) {
					if (Integer.valueOf(visMax.getVisitorsInOrder() + visMax.getAllowed2()) < visMax.getAllowed1()) {
						order = mysqlConnection.newDBOrder(object);
						if (order != null) {
							ServerController.instance.displayMsg("New Order created");
							returnData = new DataTransfer(TypeOfMessageReturn.SUB_NEW_ORDER_SUCCESS, order);
						} else {
							ServerController.instance.displayMsg("New Order could not be created");
							returnData = new DataTransfer(TypeOfMessageReturn.SUB_NEW_ORDER_FAILED, null);
						}
						try {
							client.sendToClient(returnData);

						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						// not answer on our check
						ServerController.instance.displayMsg("New Order could not be created");
						returnData = new DataTransfer(TypeOfMessageReturn.SUB_NEW_ORDER_FAILED, null);
						try {
							client.sendToClient(returnData);

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					// send fail - visMax empty
					ServerController.instance.displayMsg("New Order could not be created");
					returnData = new DataTransfer(TypeOfMessageReturn.SUB_NEW_ORDER_FAILED, null);
					try {
						client.sendToClient(returnData);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			break;

		case GETINFOPARKENTER:

			if (object instanceof Order) {
				order = mysqlConnection.getDBOrder(object);
				if (order != null) {
					ServerController.instance.displayMsg("Got order details");
					returnData = new DataTransfer(TypeOfMessageReturn.PARKENTERRETURNORDER, order);
				} else {
					ServerController.instance.displayMsg("Couldn't recieve existing order details");
					returnData = new DataTransfer(TypeOfMessageReturn.RETURN_ORDER_FAILED, null);
				}
				try {
					client.sendToClient(returnData);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			break;

		case PARTKENTERGETSTATUS:
			if (object instanceof ParkStatus) {
				ParkStatus status = (ParkStatus) object;
				String set = mysqlConnection.getPartStatus(status);
				String get = mysqlConnection.getPartStatus2(status);
				String t = "select Discount from gonature.discountdates WHERE Dates='" + status.getDate()
						+ "' AND numOfPark='" + status.getPark() + "'AND Approve='True';";
				arrOfAnswer = mysqlConnection.getDB(t);
				status.setAmount(set);
				status.setMaxAmount(get);
				if (!arrOfAnswer.isEmpty())
					status.setDiscount((String) arrOfAnswer.get(0));
				if (status.getAmount() != null) {
					try {
						returnData = new DataTransfer(TypeOfMessageReturn.PARK_STATUS, status);
						client.sendToClient(returnData);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			break;

		case PARKENTERSENDSTATUS:
			if (object instanceof ParkStatus) {
				ParkStatus status = (ParkStatus) object;
				String t = status.getPark();
				int x = Integer.valueOf(status.getAmount());
				// String query ="UPDATE parksstatus SET "+t+"="+t+"+ "+x+" WHERE
				// DATE='"+status.getDate()+"';";
				String query = "UPDATE parksstatuss SET " + t + "=" + t + "+ " + x + ";";
				boolean ans2 = mysqlConnection.updateDB(query);
				if (ans2)
					ServerController.instance.displayMsg("Park Status details updated");
				else
					ServerController.instance.displayMsg("Park Statust details could not be updated");
			}

			break;

		case CASUALVISITUPDATE:
			if (object instanceof casualOrder) {
				casualOrder order = (casualOrder) object;
				boolean ans2 = mysqlConnection.updateCasualTable(order);
				if (ans2)
					ServerController.instance.displayMsg("New Casual Visit");
				else
					ServerController.instance.displayMsg("Casual visit failed");

			}
			break;

		case CheckDiscounts:
			if (object instanceof ParkStatus) {
				ParkStatus status = (ParkStatus) object;
				String t = "select * from discountdates WHERE Dates='" + status.getDate() + "' AND numOfPark='"
						+ status.getPark() + "'AND Approve='True';";
				arrOfAnswer = mysqlConnection.getDB(t);
				status.setAmount((String) arrOfAnswer.get(1));
				if (!arrOfAnswer.isEmpty()) {
					try {
						returnData = new DataTransfer(TypeOfMessageReturn.PARK_DISCOUNT, status);
						client.sendToClient(returnData);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			break;

		case RESETPARKSTATUS:
			if (object instanceof String) {
				String t = (String) object;
				String sql = "UPDATE parksstatuss SET " + t + "=" + 1 + ";";
				boolean ans2 = mysqlConnection.updateDB(sql);
				if (ans2)
					ServerController.instance.displayMsg("Park reseted");
				else
					ServerController.instance.displayMsg("Park did not resert");
			}

			break;

		case SENDMAIL:
			if (object instanceof EmailDetails) {
				EmailDetails emailDetails = (EmailDetails) object;
				new SendEmail(emailDetails.getMailTo(), emailDetails.getSubject(), emailDetails.getText());

			}
		default:
			break;
		}

		if (flag == 0) { // in the first connection, display ip, host and status.
			ServerController.instance.displayMsg("Client IP: " + client.getInetAddress().getHostAddress());
			ServerController.instance.displayMsg("Hostname: " + client.getInetAddress().getHostName());
			if (client.isAlive()) {
				ServerController.instance.displayMsg("Client Status: Connected");
			} else {
				ServerController.instance.displayMsg("Client Status: Disconnected");
			}
			flag = 1;
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		ServerController.instance.displayMsg("Server has start listening for connections at port : " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		ServerController.instance.displayMsg("Server has stopped listening for connections.");
	}

}
//End of EchoServer class
