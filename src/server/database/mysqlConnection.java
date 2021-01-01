package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.logic.Order;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.maxVis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.Controller.ServerController;

public class mysqlConnection {
	static Connection conn;

	public static void connectToDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			/* handle the error */
		}

		try {
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","ha89kha89k");

			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","Liran159357!");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","Aa123456");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","DA123456");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","DA123456");

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
			Order ordInDB = new Order(null,null,null,null,null,null,null,null);
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
						//8 no need.
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
			String upTourGroupString= "False";
			order.setOrderNumber(upOrderNum);
	
			if (conn != null) {
				try {
					
					String sql = "INSERT INTO orders (Park, Time, Date, NumOfVisitors, Email,orderNumber,NameOnOrder, TourGroup )" + " values ( ?, ?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
				      preparedStmt.setString (1, upPark);
				      preparedStmt.setString (2, upTime);
				      preparedStmt.setString (3, upDate);
				      preparedStmt.setString (4, upNumOfVisitors);
				      preparedStmt.setString (5, updEmail);
				      preparedStmt.setString (6, upOrderNum);
				      preparedStmt.setString (7, nameOnOrder);
				      preparedStmt.setString (8, upTourGroupString);
				      preparedStmt.execute();
				      
				      //conn.close();
				      return order;
					}
					
				 catch (SQLException e) {
					e.printStackTrace();
				}
			
			}	
		
		}
	
		return null;
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

public static boolean updateDBOrders(Object updatedTourOrder) {
		if (updatedTourOrder instanceof TourGuideOrder) {
			TourGuideOrder updGuide= (TourGuideOrder)updatedTourOrder;
			String updEmail=updGuide.getEmail();
			String upPark=updGuide.getParkName();
			String upDate= updGuide.getDate();
			String upTime=updGuide.getTime();
			String upNumOfVisitors=updGuide.getNumOfVisitors();
			String nameOnOrder=updGuide.getNameOnOrder();
			String upOrderNum= generateRandomChars("123456789", 5);
			String tourID=updGuide.getID();
			//string upOrderNumber=
			//String updID=updGuide.getId();
			if (conn != null) {
				try {
					
					String query = " insert into orders (Park, Date, Time, NumOfVisitors, Email,TourGroup,orderNumber,NameOnOrder,ID )"+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
					        
					PreparedStatement preparedStmt = conn.prepareStatement(query);
				      preparedStmt.setString (1, upPark);
				      preparedStmt.setString (2, upDate);
				      preparedStmt.setString (3, upTime);
				      preparedStmt.setString (4, upNumOfVisitors);
				      preparedStmt.setString (5, updEmail);
				      preparedStmt.setString (6, "True");
				      preparedStmt.setString (7, upOrderNum);
				      preparedStmt.setString (8, nameOnOrder);
				      preparedStmt.setString (9, tourID);
				      preparedStmt.execute();
				      
				      conn.close();
					//PreparedStatement query =conn.prepareStatement("UPDATE orders SET Park=?, Date=?, Time=?, NumOfVisitors=?, Email=?, TourGroup=? WHERE orderNumber=?");
					//query.setString(1, upPark);
					//query.setString(2, upDate);
					//query.setString(3, upTime);
					//query.setString(4, upNumOfVisitors);
					//query.setString(5, updEmail);
					//query.setString(6, "True");
					//query.setString(7,"1");
					//query.executeUpdate();
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
		String date=(String)msg;
		maxVis maxNum= new maxVis(null, null, null);
		try {
			//ResultSet rs= conn.createStatement().executeQuery("SELECT COUNT (Date) FROM orders Where Date='2020-12-31'");
				ResultSet rs= conn.createStatement().executeQuery("select * from maxvisitors WHERE Date='" + date + "';");

			while(rs.next()) {
				maxNum.setDate(rs.getString(1));
				maxNum.setVisitorsInOrder(rs.getString(2));
				maxNum.setMaxVisitors(rs.getString(3));
				
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
	

}