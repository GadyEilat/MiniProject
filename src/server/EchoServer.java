// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import client.logic.WaitingList;
import client.ClientUI;
import client.logic.EmailDetails;
import client.logic.Order;
import client.logic.ParkInfo;
import client.logic.Subscriber;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.Worker;
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
		DataTransfer returnData;
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
		case REQUESTINFO:
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

			break;
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
		case LOGIN_REQUEST:
			if (object instanceof Worker) {
				Worker worker = (Worker) object;
				Worker RoleAndPark = null;
				ParkInfo parkInfo;
				String checkUserAndPassword = "SELECT Role, Park, name FROM gonature.worker WHERE UserName = '"
						+ worker.getUserName() + "' AND Password = '" + worker.getPassword() + "';";
				arrOfAnswer = mysqlConnection.getDB(checkUserAndPassword);
				if (!arrOfAnswer.isEmpty()) {
					String scene;
					String role = (String) arrOfAnswer.get(0);
					String park = (String) arrOfAnswer.get(1);
					String workerName = (String) arrOfAnswer.get(2);
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
								(String) arrOfAnswer.get(2), (String) arrOfAnswer.get(3), (String) arrOfAnswer.get(4));
						RoleAndPark = new Worker(null, null, role, parkInfo, workerName, scene);

					} else if (role.equals("Department Manager")) {
						scene = "/client/boundaries/mainDepartmantManager.fxml";
						role = "Department Manager";
						parkInfo = new ParkInfo((String) arrOfAnswer.get(0), (String) arrOfAnswer.get(1),
								(String) arrOfAnswer.get(2), (String) arrOfAnswer.get(3), null);
						RoleAndPark = new Worker(null, null, role, parkInfo, workerName, scene);

					} else if (role.equals("Service")) {
						scene = "/client/boundaries/FamilySubscriptionRegistration.fxml";
						role = "Service Representative";
						parkInfo = new ParkInfo((String) arrOfAnswer.get(0), null, null, null, null);
						RoleAndPark = new Worker(null, null, role, parkInfo, workerName, scene);

					} else
						System.out.println("Error");

					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_SUCCESSFUL, RoleAndPark);
				} else
					returnData = new DataTransfer(TypeOfMessageReturn.LOGIN_FAILED, new Worker());
				try {
					client.sendToClient(returnData);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

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
				boolean ans2 = mysqlConnection.updateWaitingListTour(object);
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

			break;

		case DELETE_INFO:
			if (object instanceof String) {
				String strToBeDeleted = (String) object;
				String DeleteQuery = "DELETE FROM gonature.orders WHERE (OrderNumber = " + strToBeDeleted + ");";
				boolean ans = mysqlConnection.updateDB(DeleteQuery);
				if (ans) {
					ServerController.instance.displayMsg("Order was deleted");
					returnData = new DataTransfer(TypeOfMessageReturn.DELETE_ORDER_SUCCESS, null);
				} else {
					ServerController.instance.displayMsg("Order could not be deleted");
					returnData = new DataTransfer(TypeOfMessageReturn.DELETE_ORDER_FAILED, null);
				}
				try {
					client.sendToClient(returnData);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
