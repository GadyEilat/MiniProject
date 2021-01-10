package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.logic.Order;
import client.logic.ParkStatus;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.WaitingList;
import client.logic.casualOrder;
import client.logic.maxVis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.Controller.ServerController;

public class mysqlConnection {
	static Connection conn;
	// static Connection conn2;

	public static void connectToDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			/* handle the error */
		}

		try {


//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","ha89kha89k");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","Liran159357!");
<<<<<<< HEAD
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","Aa123456");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","DA123456");
=======
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","Aa123456");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","DA123456");
>>>>>>> refs/heads/main




			ServerController.instance.displayMsg("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public static void DisconnectConnection() {
		try {
			if (!conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
/**
 * Method for getting an order from DataBase.
 * The method gets the requested order by using the OrderNumber.
 * The method returns a full order entity including all the details of the order.
 * @param msg should be an entity of Order.
 * @return a full order entity from the DB is being returned.
 */
	public static Order getDBOrder(Object msg) {
		if (msg instanceof Order) // if its an order for Gady's screens.
		{
			Order ord = (Order)msg;
			Order ordInDB = new Order(null,null,null,null,null,null,null,null,null,null,null,null);
			if (conn != null) {
				try {
					Statement st = conn.createStatement();
					String sql = ("SELECT * FROM gonature.orders where OrderNumber = " + ord.getOrderNumber() + ";");
					ResultSet rs = st.executeQuery(sql);
					ResultSetMetaData metadata = rs.getMetaData();
					while (rs.next()) {
						ordInDB.setParkName(rs.getString(1));
						ordInDB.setHour(rs.getString(2));
						ordInDB.setDate(rs.getString(3));
						ordInDB.setNumOfVisitors(rs.getString(4));
						ordInDB.setEmail(rs.getString(5));
						ordInDB.setOrderNumber(rs.getString(6));
						ordInDB.setNameOnOrder(rs.getString(7));
						ordInDB.setOrderKind(rs.getString(8));
						ordInDB.setID(rs.getString(9));
						ordInDB.setPrePaid(rs.getString(11));
						ordInDB.setApproved(rs.getString(12));

					}

					rs.close();
					return ordInDB;
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return null;
	}

	public static WaitingList getDBWaitingList (Object msg) {
		if (msg instanceof WaitingList) {
			WaitingList ordWL = (WaitingList)msg;
			WaitingList ordInWL = new WaitingList(null,null,null,null,null,null,null,null,null,null,null,null);
			if (conn != null) {
				try {
					Statement st = conn.createStatement();
					String sql = ("SELECT * FROM gonature.waitinglist where OrderNumber = " + ordWL.getOrderNumber() + ";");
					ResultSet rs = st.executeQuery(sql);
					while (rs.next()) {
						ordInWL.setParkName(rs.getString(1));
						ordInWL.setTime(rs.getString(2));
						ordInWL.setDate(rs.getString(3));
						ordInWL.setNumOfVisitors(rs.getString(4));
						ordInWL.setEmail(rs.getString(5));
						ordInWL.setOrderNumber(rs.getString(6));
						ordInWL.setNameOnOrder(rs.getString(7));
						ordInWL.setOrderKind(rs.getString(8));
						ordInWL.setID(rs.getString(9));
						ordInWL.setTimeOfEntrance(rs.getString(10));
						ordInWL.setDateOfEntrance(rs.getString(11));
						ordInWL.setNeedsToApprove(rs.getString(12));
					}
					rs.close();
					return ordInWL;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/** Description of newDBOrder method
	 * This method making a new order and saves all the details in the database.
     * @param msg gets the order details.
     */
	public static Order newDBOrder(Object msg) {
		if (msg instanceof Order) // if its an order for Aviv's screens.
		{
			Order order = (Order)msg;
			String updEmail=order.getEmail();
			String upPark=order.getParkName();
			String upDate= order.getDate();
			String upTime=order.getHour();
			String upNumOfVisitors=order.getNumOfVisitors();
			String nameOnOrder=order.getNameOnOrder();
			String upOrderNum= generateRandomChars("123456789", 5);
			String insID=order.getID();
			String totalPrice = order.getTotalPrice();
			String prePaid = order.getPrePaid();
			String orderKind = order.getOrderKind();
			String approved = "false";
			order.setOrderNumber(upOrderNum);

			if (conn != null) {
				try {
					String sql = "INSERT INTO orders (Park, Time, Date, NumOfVisitors, Email,orderNumber,NameOnOrder, OrderKind, ID, totalPrice, prePaid, Approved )"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setString(1, upPark);
					preparedStmt.setString(2, upTime);
					preparedStmt.setString(3, upDate);
					preparedStmt.setString(4, upNumOfVisitors);
					preparedStmt.setString(5, updEmail);
					preparedStmt.setString(6, upOrderNum);
					preparedStmt.setString(7, nameOnOrder);
					preparedStmt.setString(8, orderKind);
					preparedStmt.setString(9, insID);
					preparedStmt.setString(10, totalPrice);
					preparedStmt.setString(11, prePaid);
					preparedStmt.setString(12, approved);

					preparedStmt.execute();

					return order;
				}

				catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

		return null;
	}
/**
 * Method for handling a Query that inserts a new order entity that comes from the waitinglist table into the order table
 * That query should be made after an order was moved from waiting list into order table.
 * @param msg the Order details we recieved from waiting list.
 * @return true if the method succeeded, false otherwise.
 */
	public static boolean newDBOrderFromWaitingList(Object msg) {
		if (msg instanceof Order) // if its an order for Gady's screens.
		{
			Order order = (Order) msg;
			String updEmail = order.getEmail();
			String upPark = order.getParkName();
			String upDate = order.getDate();
			String upTime = order.getHour();
			String upNumOfVisitors = order.getNumOfVisitors();
			String nameOnOrder = order.getNameOnOrder();
			String upOrderNum = order.getOrderNumber();
			String upOrderKind = order.getOrderKind();
			String insID = order.getID();
			String upTotalPrice = order.getTotalPrice();
			String upPrePaid = order.getPrePaid();
			String upApproved = order.getApproved();

			if (conn != null) {
				try {
					String sql = "INSERT INTO orders (Park, Time, Date, NumOfVisitors, Email,orderNumber,NameOnOrder, OrderKind, ID, totalPrice, prePaid, Approved)"
							+ " values ( ?, ?, ?, ?, ?, ?, ?, ?,?,?, ?, ?)";
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setString(1, upPark);
					preparedStmt.setString(2, upTime);
					preparedStmt.setString(3, upDate);
					preparedStmt.setString(4, upNumOfVisitors);
					preparedStmt.setString(5, updEmail);
					preparedStmt.setString(6, upOrderNum);
					preparedStmt.setString(7, nameOnOrder);
					preparedStmt.setString(8, upOrderKind);
					preparedStmt.setString(9, insID);
					preparedStmt.setString(10, upTotalPrice);
					preparedStmt.setString(11, upPrePaid);
					preparedStmt.setString(12, upApproved);
					preparedStmt.execute();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	 /** Description of getDB 
     * This function gets a line from the data base.
     * @param  msg the sql query that will be used in the function.
     * 
     * 
     */
	public static ArrayList<Object> getDB(Object msg) {
		String str = null;
		String sql;
		ArrayList<Object> answer = new ArrayList<>();
		if (msg instanceof String) {
			str = (String) msg;
			sql = (str);
		} else
			sql = (" ");

		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				ResultSetMetaData metadata = rs.getMetaData();
				int columnCount = metadata.getColumnCount();
				while (rs.next()) {
					for (int i = 1; i <= columnCount; i++)
						answer.add(rs.getString(i));
				}
				rs.close();
				return answer;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;

	}
	/**
	 * This method is used specifically for the little "Check Orders" button in ServerUI.
	 * The method receives a query to be made and returns an arraylist of recieved Order from the database.
	 * @param msg a query to be done.
	 * @return an arrayList of the order.
	 */
	//for serverController CheckButton
	public static ArrayList<Order> getDBArrayOrder(Object msg) {
		String str = null;
		String sql;
		Order ordInDB = new Order(null,null,null,null,null,null,null,null,null,null,null,null);
			ArrayList<Order> answer = new ArrayList<Order>();
			if (msg instanceof String) {
				str = (String) msg;
				sql = (str);
			}
			else
				sql = (" ");

			if (conn != null) {
				try {
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(sql);
					ResultSetMetaData metadata = rs.getMetaData();
				    int columnCount = metadata.getColumnCount();
				   // int rowsCount = metadata.
					while (rs.next()) {
						
						ordInDB.setParkName(rs.getString(1));
						ordInDB.setHour(rs.getString(2));
						ordInDB.setDate(rs.getString(3));
						ordInDB.setNumOfVisitors(rs.getString(4));
						ordInDB.setEmail(rs.getString(5));
						ordInDB.setOrderNumber(rs.getString(6));
						ordInDB.setNameOnOrder(rs.getString(7));
						ordInDB.setOrderKind(rs.getString(8));
						ordInDB.setID(rs.getString(9));
						ordInDB.setTotalPrice(rs.getString(10));
						ordInDB.setPrePaid(rs.getString(11));
						ordInDB.setApproved(rs.getString(12));
						answer.add(ordInDB);
					}
					//conn.close();
					rs.close();
					return answer;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			return null;

		}
	 /** Description of updateDB 
     * This function updates the data base, according the the query that it gets.
     * @param  msg the sql query that will be used in the function.
     * 
     * 
     */
	public static boolean updateDB(String msg) {
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				st.executeUpdate(msg);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;

	}
	/**
	 * A method for inserting an order that was just canceled/deleted for any reason to the "deletedOrders" table.
	 * The method gets all the deleted order details, and places it in the deletedOrders table.
	 * @param msg our Order to be moved to deletedOrders
	 * @return true for success, false otherwise.
	 */
	public static boolean insertIntoDeletedOrders(Object msg) {
		if (msg instanceof Order) {
			Order order = (Order) msg;
			String updEmail = order.getEmail();
			String upPark = order.getParkName();
			String upDate = order.getDate();
			String upTime = order.getHour();
			String upNumOfVisitors = order.getNumOfVisitors();
			String nameOnOrder = order.getNameOnOrder();
			String upOrderNum = order.getOrderNumber();
			String ID = order.getID();
			String upTotalPrice = order.getTotalPrice();
			String upOrderKind = order.getOrderKind();
			if (conn != null) {
				try {

					String query = " insert into deletedorders (Park, Time, Date, NumOfVisitors, Email,orderNumber,NameOnOrder,OrderKind,ID,totalPrice)"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, upPark);
					preparedStmt.setString(2, upTime);
					preparedStmt.setString(3, upDate);
					preparedStmt.setString(4, upNumOfVisitors);
					preparedStmt.setString(5, updEmail);
					preparedStmt.setString(6, upOrderNum);
					preparedStmt.setString(7, nameOnOrder);
					preparedStmt.setString(8, upOrderKind);
					preparedStmt.setString(9, ID);
					preparedStmt.setString(10, upTotalPrice);
					preparedStmt.execute();

					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}

	public static boolean updateWaitingListTour(Object updatedWaitingList) {
		if (updatedWaitingList instanceof WaitingList) {
			WaitingList updGuide = (WaitingList) updatedWaitingList;
			String updEmail = updGuide.getEmail();
			String upPark = updGuide.getParkName();
			String upDate = updGuide.getDate();
			String upTime = updGuide.getTime();
			String upNumOfVisitors = updGuide.getNumOfVisitors();
			String nameOnOrder = updGuide.getNameOnOrder();
			String upOrderNum = generateRandomChars("123456789", 5);
			String tourID = updGuide.getID();
			String waitingTime = updGuide.getTimeOfEntrance();
			String waitingDate = updGuide.getDateOfEntrance();
			String upApprove = updGuide.getNeedsToApprove();
			// string upOrderNumber=
			// String updID=updGuide.getId();
			if (conn != null) {
				try {

					String query = " insert into waitinglist (Park, Date, Time, NumOfVisitors, Email,OrderKind,orderNumber,NameOnOrder,ID,TimeOfEntrance,DateOfEntrance, NeedsToApprove )"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, upPark);
					preparedStmt.setString(2, upDate);
					preparedStmt.setString(3, upTime);
					preparedStmt.setString(4, upNumOfVisitors);
					preparedStmt.setString(5, updEmail);
					preparedStmt.setString(6, "TourGroup");
					preparedStmt.setString(7, upOrderNum);
					preparedStmt.setString(8, nameOnOrder);
					preparedStmt.setString(9, tourID);
					preparedStmt.setString (10, waitingTime);
				    preparedStmt.setString (11, waitingDate);
				    preparedStmt.setString (12, upApprove);
					preparedStmt.execute();

					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * updateWaitingListNewOrder method 
	 * @param updatedWaitingList gets the details of the order that needs to be set in the waiting list table.
	 * @return true of it could connect to db. else, return false.
	 */
	public static boolean updateWaitingListNewOrder(Object updatedWaitingList) {
		if (updatedWaitingList instanceof WaitingList) {
			WaitingList updOrder= (WaitingList)updatedWaitingList;
			String updEmail=updOrder.getEmail();
			String upPark=updOrder.getParkName();
			String upDate= updOrder.getDate();
			String upTime=updOrder.getTime();
			String upNumOfVisitors=updOrder.getNumOfVisitors();
			String nameOnOrder=updOrder.getNameOnOrder();
			String upOrderNum= generateRandomChars("123456789", 5);
			String travID=updOrder.getID();
			String orderKind = updOrder.getOrderKind();
			String waitingTime=updOrder.getTimeOfEntrance();
			String dateEntrance=updOrder.getDateOfEntrance();
			String upApprove = updOrder.getNeedsToApprove();
			if (conn != null) {
				try {
					
					String query = " insert into waitinglist (Park, Time, Date, NumOfVisitors, Email,orderNumber,NameOnOrder,OrderKind,ID,TimeOfEntrance, DateOfEntrance, NeedsToApprove )"+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
					        
					PreparedStatement preparedStmt = conn.prepareStatement(query);
				      preparedStmt.setString (1, upPark);
				      preparedStmt.setString (2, upTime);
				      preparedStmt.setString (3, upDate);
				      preparedStmt.setString (4, upNumOfVisitors);
				      preparedStmt.setString (5, updEmail);
				      preparedStmt.setString (6, upOrderNum);
				      preparedStmt.setString (7, nameOnOrder);
				      preparedStmt.setString (8, orderKind);
				      preparedStmt.setString (9, travID);
				      preparedStmt.setString (10, dateEntrance);
				      preparedStmt.setString (11, waitingTime);
				      preparedStmt.setString (12, upApprove);
				      preparedStmt.execute();
				      
				     // conn.close();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}
	/**
	 * A method for handling a query that should check the kind of traveler in the requested table.
	 * The method will tell you if the traveler is regular/Guide Order/Subscriber or not.
	 * @param msg a query requesting information about the orderkind coming from 
	 * one of the tables: Orders/Subscribers/TourGuide.
	 * @return returns true if its the traveler kind that was asked, false otherwise.
	 */
	public static boolean CheckKind(String msg) {
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(msg);
				while (rs.next()) {
					if (rs.getString(1) == null) {
						rs.close();
						return false; // there's no such ID in there.
					} else {
						rs.close();
						return true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	 /** Description of updateDBOrders 
     * This function updates the data base with a new order.
     * @param  updatedTourOrder is the entity of an order
     * that will be inserted into the data base.
     * 
     */
	public static boolean updateDBOrders(Object updatedTourOrder) {
		if (updatedTourOrder instanceof TourGuideOrder) {
			TourGuideOrder updGuide= (TourGuideOrder)updatedTourOrder;
			String updEmail=updGuide.getEmail();
			String upPark=updGuide.getParkName();
			String upDate= updGuide.getDate();
			String upTime=updGuide.getTime();
			String upNumOfVisitors=updGuide.getNumOfVisitors();
			String nameOnOrder=updGuide.getNameOnOrder();
			String upOrderNum= updGuide.getOrderNumber();
			String tourID=updGuide.getID();
			String isPrepaid=updGuide.getPrePaid();
			String tourPayment=updGuide.getPayment();
			
			if (conn != null) {
				try {

					String query = " insert into orders (Park, Date, Time, NumOfVisitors, Email,OrderKind,orderNumber,NameOnOrder,ID,totalPrice, prePaid )"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, upPark);
					preparedStmt.setString(2, upDate);
					preparedStmt.setString(3, upTime);
					preparedStmt.setString(4, upNumOfVisitors);
					preparedStmt.setString(5, updEmail);
					preparedStmt.setString(6, "TourGuide");
					preparedStmt.setString(7, upOrderNum);
					preparedStmt.setString(8, nameOnOrder);
					preparedStmt.setString(9, tourID);
					preparedStmt.setString(10, tourPayment);
					preparedStmt.setString (11, isPrepaid);
					preparedStmt.execute();

					// conn.close();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}
/**
 * A method for deleting something from the DB.
 * Usually used for deleting a row from a certain table.
 * A Delete query is received and being executed.
 * @param msg the Delete query to be executed.
 * @return True if it succeded deleting, false otherwise.
 */
	public static boolean deleteFromDB(String msg) {
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				st.executeUpdate(msg);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	 /** Description of generateRandomChars 
     * @param candidateChars gets the candidate chars to generate a random chars.  
     * @param length- chooses the size of the random chars.
     * @return A string is returned with the random chars.
     */
	public static String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}

		return sb.toString();
	}

	public static ObservableList<Object> getHistorySubOrders(Object msg) {
		ObservableList<Object> oblist = FXCollections.observableArrayList();
		String subID = (String) msg;
		try {

			Statement st = conn.createStatement();
			String sql = ("SELECT * FROM gonature.orders where ID = '" + subID + "';");
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Order newS = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5),
						rs.getString(6), rs.getString(4), rs.getString(7), rs.getString(9));

				oblist.add(newS);

			}
			rs.close();
			return oblist;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	 /** Description of getTourGuideOrders
		 * This function returns all the orders of the ID that it got.  
	     * @param msg gets the id of the TourGuide.  
	     * @return ObservableList of the orders details.
	     */
	// ObservableList<Object>
	public static ObservableList<Object> getTourGuideOrders(Object msg) {
		ObservableList<Object> oblist = FXCollections.observableArrayList();
		String TourID = (String) msg;
		try {

			Statement st = conn.createStatement();
			String sql = ("SELECT * FROM gonature.orders where ID = '" + TourID + "';");
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				TourGuideOrder newT = new TourGuideOrder(null, null, null, null, null, null, null, null, null, null);
				newT.setParkName(rs.getString(1));
				newT.setTime(rs.getString(2));
				newT.setDate(rs.getString(3));
				newT.setNumOfVisitors(rs.getString(4));
				newT.setEmail(rs.getString(5));
				newT.setOrderNumber(rs.getString(6));
				newT.setNameOnOrder(rs.getString(7));
				oblist.add(newT);
				// oblist.add(new TourGuideOrder(rs.getString(1), rs.getString(2),
				// rs.getString(3), rs.getString(3), null, null, null));

			}
			rs.close();
			return oblist;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/** Description of checkMaxVisitors
	 * This function checks if there is a spot to place the order.
	 * The function checks according to the max visit time the hours before and after
	 * The requsted hour of the new order.
     * @param msg gets the id of the TourGuide.  
     * @return maxVis entity that holds few details about the open spot.
     */
	public static maxVis checkMaxVisitors(Object msg) {
		maxVis orderC = (maxVis) msg;
		maxVis maxNum = new maxVis(null, null, null, 0, 0, null, 0);
		try {
			// ResultSet rs= conn.createStatement().executeQuery("SELECT COUNT (Date) FROM
			// orders Where Date='2020-12-31'");
			ResultSet rs = conn.createStatement()
					.executeQuery("select * from manageparks WHERE numberOfPark='" + orderC.getPark() + "';");

			while (rs.next()) {
				maxNum.setDate(orderC.getDate());
				maxNum.setVisitorsInOrder(orderC.getVisitorsInOrder());
				maxNum.setPark(orderC.getPark());
				maxNum.setTime(orderC.getTime());
				maxNum.setAllowed1(Integer.valueOf(rs.getString(2)) - Integer.valueOf(rs.getString(3)));
				maxNum.setMaxTime(Integer.valueOf(rs.getString(4)));
			}
			// rs.close();
			String append = ":00";
			String twoLetters = maxNum.getTime(); // Get hour(12:00 example)
			int loopC = Integer.valueOf(Integer.valueOf(maxNum.getMaxTime()) * 2);// 4*2
			String hourLoop = twoLetters.substring(0, twoLetters.indexOf(':'));// Seprate till ':'
			String[] arrS = new String[Integer.valueOf(maxNum.getMaxTime()) * 2 + 1]; // Array for check
			int t = Integer.valueOf(hourLoop) - (loopC / 2); // 12-4
			for (int i = 0; i <= loopC; i++) {
				// int t=loopC/2;
				arrS[i] = String.valueOf(t) + append;
				t++;
			}

			for (int j = 0; j <= loopC; j++) {
				Statement stmt3 = conn.createStatement();
				rs = stmt3.executeQuery("select * from orders WHERE Time='" + arrS[j] + "' AND Date='"
						+ orderC.getDate() + "' AND Park='" + orderC.getPark() + "';");
				while (rs.next()) {
					if (rs.getString(4) != null)
						maxNum.setAllowed2(Integer.valueOf(rs.getString(4)) + maxNum.getAllowed2());
				}

			}

			rs.close();
			return maxNum;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	 /** Description of getPartStatus
		 * This function checks the amount of visitors in the park
		 * according to which has been checked.
	     * @param status is an entity that holds 
	     * @return maxVis entity that holds few details about the open spot.
	     */
	public static String getPartStatus(ParkStatus status) {
		try {
			String t=null;
			//ResultSet rs= conn.createStatement().executeQuery("select * from parksstatus WHERE Date='" + status.getDate() + "';");
			ResultSet rs= conn.createStatement().executeQuery("select * from parksstatuss");
			while(rs.next()) {
				if(status.getPark().equals("Park1"))
				 t=(rs.getString(1));
			
			    if(status.getPark().equals("Park2"))
				 t=(rs.getString(2));
			
			    if(status.getPark().equals("Park3")) 
			     t=(rs.getString(3));	
		    }
			
			rs.close();
			return t;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	 /** Description of getPartStatus2
	 * This function checks the amount of visitors in the park
	 * according to which has been checked.
     * @param status is an entity that holds 
     * @return maxVis entity that holds few details about the open spot.
     */
	public static String getPartStatus2(ParkStatus status) {
		try {
			String t = null;
			ResultSet rs = conn.createStatement()
					.executeQuery("select * from manageparks WHERE numberOfPark='" + status.getPark() + "';");

			while (rs.next()) {
				t = (rs.getString(2));
			}

			rs.close();
			return t;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static boolean setPartStatus(ParkStatus status) {
		// TODO Auto-generated method stub
		return false;
	}
	/** Description of updateCasualTable
	 * This function updates an acsual order thats in the park.
     * @param order is an entity that holds all the order details. 
     * @return boolean if succsseded or not.
     */
	public static boolean updateCasualTable(casualOrder order) {
		try {
			
			String sql = "INSERT INTO casualinvitation (Park, Date, Time, OrderKind, Payment,ExitTime,OrderNumber,numOfVis)" + " values ( ?, ?, ?, ?, ?, ?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
		      preparedStmt.setString (1, order.getPark());
		      preparedStmt.setString (2, order.getDate());
		      preparedStmt.setString (3, order.getTime());
		      preparedStmt.setString (4, order.getOrderKind());
		      preparedStmt.setString (5, order.getPayment());
		      preparedStmt.setString (6, order.getExitTime());
		      preparedStmt.setString (7, order.getOrderNumber());
		      preparedStmt.setString (8, order.getNumOfVis());
		      preparedStmt.execute();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	/** Description of getDiscountPerDay
	 * This function checks if in the data base there is
	 * a discount to the same day.
     * @param status entity that holds the details needed for the check. 
     * @return String returns the discount, if there is one.
     */
	public static String getDiscountPerDay(ParkStatus status) {
		try {
			String t="select * from discountdates WHERE Dates='" + status.getDate() + "' AND numOfPark='" + status.getPark() + "'AND Approve=' True';";
			ResultSet rs= conn.createStatement().executeQuery("select * from discountdates WHERE Dates='" + status.getDate() + "' AND numOfPark='" + status.getPark() +  "'AND Approve='True';");

			while(rs.next()) {
				 t=(rs.getString(2));	
		    }
			
			rs.close();
			return t;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

}