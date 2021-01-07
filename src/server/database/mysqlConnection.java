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
	//static Connection conn2;

	public static void connectToDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			/* handle the error */
		}

		try {

//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","ha89kha89k");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","Liran159357!");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","Aa123456");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","DA123456");

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
	
	public static Order getDBOrder(Object msg) {
		if (msg instanceof Order) //if its an order for Gady's screens.
		{
			Order ord = (Order)msg;
			Order ordInDB = new Order(null,null,null,null,null,null,null,null,null,null);
			if (conn != null) {
				try {
					Statement st = conn.createStatement();
					String sql = ("SELECT * FROM gonature.orders where OrderNumber = " + ord.getOrderNumber() + ";");
					ResultSet rs = st.executeQuery(sql);
					ResultSetMetaData metadata = rs.getMetaData();
				    //int columnCount = metadata.getColumnCount();
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
						
					}
					//conn.close();
					rs.close();
					return ordInDB;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			}
		}
		return null;
	}

	public static Order newDBOrder(Object msg) {
		if (msg instanceof Order) //if its an order for Aviv's screens.
		{
			Order order = (Order)msg;
			String updEmail=order.getEmail();
			String upPark=order.getParkName();
			String upDate= order.getDate();
			String upTime=order.getHour();
			String upNumOfVisitors=order.getNumOfVisitors();
			String nameOnOrder=order.getNameOnOrder();
			String upOrderNum= generateRandomChars("123456789", 5);
			String upTourGroupString= "RegularOrder";
			String insID=order.getID();
			order.setOrderNumber(upOrderNum);
	
			if (conn != null) {
				try {
					
					String sql = "INSERT INTO orders (Park, Time, Date, NumOfVisitors, Email,orderNumber,NameOnOrder, OrderKind, ID )" + " values ( ?, ?, ?, ?, ?, ?, ?, ?,?)";
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
				      preparedStmt.setString (1, upPark);
				      preparedStmt.setString (2, upTime);
				      preparedStmt.setString (3, upDate);
				      preparedStmt.setString (4, upNumOfVisitors);
				      preparedStmt.setString (5, updEmail);
				      preparedStmt.setString (6, upOrderNum);
				      preparedStmt.setString (7, nameOnOrder);
				      preparedStmt.setString (8, upTourGroupString);
				      preparedStmt.setString (9, insID);
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
	
	public static boolean newDBOrderFromWaitingList(Object msg) {
		if (msg instanceof Order) //if its an order for Gady's screens.
		{
			Order order = (Order)msg;
			String updEmail=order.getEmail();
			String upPark=order.getParkName();
			String upDate= order.getDate();
			String upTime=order.getHour();
			String upNumOfVisitors=order.getNumOfVisitors();
			String nameOnOrder=order.getNameOnOrder();
			String upOrderNum= order.getOrderNumber();
			String upOrderKind= order.getOrderKind();
			String insID=order.getID();

			if (conn != null) {
				try {
					String sql = "INSERT INTO orders (Park, Time, Date, NumOfVisitors, Email,orderNumber,NameOnOrder, OrderKind, ID )" + " values ( ?, ?, ?, ?, ?, ?, ?, ?,?)";
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
				      preparedStmt.setString (1, upPark);
				      preparedStmt.setString (2, upTime);
				      preparedStmt.setString (3, upDate);
				      preparedStmt.setString (4, upNumOfVisitors);
				      preparedStmt.setString (5, updEmail);
				      preparedStmt.setString (6, upOrderNum);
				      preparedStmt.setString (7, nameOnOrder);
				      preparedStmt.setString (8, upOrderKind);
				      preparedStmt.setString (9, insID);
				      preparedStmt.execute();
				      return true;
					}
					
				 catch (SQLException e) {
					e.printStackTrace();
				}
			
			}	
		
		}
	
		return false;
	}
	
	public static ArrayList<Object> getDB(Object msg) {
	String str = null;
	String sql;
		ArrayList<Object> answer = new ArrayList<>();
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
				while (rs.next()) {
					for(int i = 1;i<=columnCount;i++)
						answer.add(rs.getString(i));
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

	public static boolean updateWaitingListTour(Object updatedWaitingList) {
		if (updatedWaitingList instanceof WaitingList) {
			WaitingList updGuide= (WaitingList)updatedWaitingList;
			String updEmail=updGuide.getEmail();
			String upPark=updGuide.getParkName();
			String upDate= updGuide.getDate();
			String upTime=updGuide.getTime();
			String upNumOfVisitors=updGuide.getNumOfVisitors();
			String nameOnOrder=updGuide.getNameOnOrder();
			String upOrderNum= generateRandomChars("123456789", 5);
			String tourID=updGuide.getID();
			String waitingTime=updGuide.getTimeOfEntrance();
			String waitingDate=updGuide.getDateOfEntrance();
			//string upOrderNumber=
			//String updID=updGuide.getId();
			if (conn != null) {
				try {
					
					String query = " insert into waitinglist (Park, Date, Time, NumOfVisitors, Email,TourGroup,orderNumber,NameOnOrder,ID,TimeOfEnterence )"+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
					        
					PreparedStatement preparedStmt = conn.prepareStatement(query);
				      preparedStmt.setString (1, upPark);
				      preparedStmt.setString (2, upDate);
				      preparedStmt.setString (3, upTime);
				      preparedStmt.setString (4, upNumOfVisitors);
				      preparedStmt.setString (5, updEmail);
				      preparedStmt.setString (6, "TourGroup");
				      preparedStmt.setString (7, upOrderNum);
				      preparedStmt.setString (8, nameOnOrder);
				      preparedStmt.setString (9, tourID);
				      preparedStmt.setString (10, waitingTime);
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
	
	public static boolean CheckKind(String msg) {
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(msg);
				while(rs.next()) {
					if (rs.getString(1) ==null) {
						rs.close();
						return false; //there's no such ID in there.
					}
					else {
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
	
	public static boolean updateDBOrders(Object updatedTourOrder) {
		if (updatedTourOrder instanceof TourGuideOrder) {
			TourGuideOrder updGuide = (TourGuideOrder) updatedTourOrder;
			String updEmail = updGuide.getEmail();
			String upPark = updGuide.getParkName();
			String upDate = updGuide.getDate();
			String upTime = updGuide.getTime();
			String upNumOfVisitors = updGuide.getNumOfVisitors();
			String nameOnOrder = updGuide.getNameOnOrder();
			String upOrderNum = generateRandomChars("123456789", 5);
			String tourID = updGuide.getID();
			double orderPayment=((Integer.valueOf(updGuide.getNumOfVisitors())-1)*22.5);
	        String tourPayment=(String.format("%.2f", orderPayment));
			
			if (conn != null) {
				try {

					String query = " insert into orders (Park, Date, Time, NumOfVisitors, Email,TourGroup,orderNumber,NameOnOrder,ID,totalPrice )"+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

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
					preparedStmt.setString (10, tourPayment);
					preparedStmt.execute();

					//conn.close();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}

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

	public static String generateRandomChars(String candidateChars, int length) {
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    for (int i = 0; i < length; i++) {
	        sb.append(candidateChars.charAt(random.nextInt(candidateChars
	                .length())));
	    }

	    return sb.toString();
	}
	
	

	
	
	public static ObservableList<Object> getHistorySubOrders(Object msg) {
		ObservableList <Object> oblist=FXCollections.observableArrayList();
        String subID=(String)msg;
		try {
				
			Statement st = conn.createStatement();
			String sql = ("SELECT * FROM gonature.orders where ID = '" + subID + "';");
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				Order newS=new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(6), rs.getString(4), 
						rs.getString(7), rs.getString(9));
				
//				newT.setParkName(rs.getString(1));
//				newT.setHour(rs.getString(2));
//				newT.setDate(rs.getString(3));
//				newT.setEmail(rs.getString(4));
//				newT.setOrderNumber(rs.getString(5));
//				newT.setNumOfVisitors(rs.getString(6));
//				newT.setNameOnOrder(rs.getString(7));
//				newT.setID(rs.getString(8));
				
				oblist.add(newS);
				
				
			}
			rs.close();
			return oblist;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
        
		return null;
	}
	
	
	
	

	//ObservableList<Object>
	public static ObservableList<Object> getTourGuideOrders(Object msg) {
		ObservableList <Object> oblist=FXCollections.observableArrayList();
        String TourID=(String)msg;
		try {
			//ResultSet rs= conn.createStatement().executeQuery("select * from orders WHERE NameOnOrder='Zvika'");
			
			Statement st = conn.createStatement();
			String sql = ("SELECT * FROM gonature.orders where ID = '" + TourID + "';");
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				TourGuideOrder newT=new TourGuideOrder(null, null, null, null, null, null, null, null);
				newT.setParkName(rs.getString(1));
				newT.setTime(rs.getString(2));
				newT.setDate(rs.getString(3));
				newT.setNumOfVisitors(rs.getString(4));
				newT.setEmail(rs.getString(5));
				newT.setOrderNumber(rs.getString(6));
				newT.setNameOnOrder(rs.getString(7));
				oblist.add(newT);
				//oblist.add(new TourGuideOrder(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(3), null, null, null));
				
			}
			rs.close();
			return oblist;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
        
		return null;
	}

	
	
	
	public static maxVis checkMaxVisitors(Object msg) {
		maxVis orderC=(maxVis)msg;
		maxVis maxNum= new maxVis(null, null, null, 0, 0, null, 0);
		try {
			//ResultSet rs= conn.createStatement().executeQuery("SELECT COUNT (Date) FROM orders Where Date='2020-12-31'");
				ResultSet rs= conn.createStatement().executeQuery("select * from manageparks WHERE numberOfPark='" + orderC.getPark() + "';");

			while(rs.next()) {
				maxNum.setDate(orderC.getDate());
				maxNum.setVisitorsInOrder(orderC.getVisitorsInOrder());
				maxNum.setPark(orderC.getPark());
				maxNum.setTime(orderC.getTime());
				maxNum.setAllowed1(Integer.valueOf(rs.getString(2))-Integer.valueOf(rs.getString(3)));
				maxNum.setMaxTime(Integer.valueOf(rs.getString(4)));
			}
		//rs.close();
			String append=":00";
			String twoLetters=maxNum.getTime(); // Get hour(12:00 example)
			int loopC= Integer.valueOf(Integer.valueOf(maxNum.getMaxTime())*2);//4*2
			String hourLoop = twoLetters.substring(0, twoLetters.indexOf(':'));//Seprate till ':'
			String[] arrS= new String[Integer.valueOf(maxNum.getMaxTime())*2+1]; //Array for check
			int t=Integer.valueOf(hourLoop)-(loopC/2); // 12-4
			for(int i=0; i<=loopC; i++) {
				//int t=loopC/2;
				arrS[i]=String.valueOf(t)+append;
				t++;
			}
			
			
		for(int j=0; j<=loopC; j++) {	
		Statement stmt3 = conn.createStatement();
		rs=stmt3.executeQuery("select * from orders WHERE Time='" + arrS[j] + "' AND Date='" + orderC.getDate() + "' AND Park='" + orderC.getPark() + "';");
		while(rs.next()) {
			if(rs.getString(4)!=null)
			maxNum.setAllowed2(Integer.valueOf(rs.getString(4))+maxNum.getAllowed2());
		}
		
		}
		
		rs.close();
		return maxNum;	
	}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
		return null;
	}

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
			
	    	     else 
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
	
	public static String getPartStatus2(ParkStatus status) {
		try {
			String t=null;
			ResultSet rs= conn.createStatement().executeQuery("select * from manageparks WHERE numberOfPark='" + status.getPark() + "';");

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
	
	

	public static boolean setPartStatus(ParkStatus status) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean updateCasualTable(casualOrder order) {
		try {
			
			String sql = "INSERT INTO casualinvitation (Park, Date, Time, OrderKind, Payment,ExitTime,OrderNumber)" + " values ( ?, ?, ?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
		      preparedStmt.setString (1, order.getPark());
		      preparedStmt.setString (2, order.getDate());
		      preparedStmt.setString (3, order.getTime());
		      preparedStmt.setString (4, order.getOrderKind());
		      preparedStmt.setString (5, order.getPayment());
		      preparedStmt.setString (6, order.getExitTime());
		      preparedStmt.setString (7, order.getOrderNumber());
		      preparedStmt.execute();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

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