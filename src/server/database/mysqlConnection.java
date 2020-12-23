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

import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","ha89kha89k");
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

	public static ArrayList<Object> getDB(Object msg) {
	String str = null;
		ArrayList<Object> answer = new ArrayList<>();
		if (msg instanceof String) {
			str = (String) msg;
		}
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String sql = ("SELECT * FROM gonature.tourguides where id = " + str + ";");
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

	public static boolean updateDB(Object updatedTourGuide) {
		if (updatedTourGuide instanceof TourGuide) {
			TourGuide updGuide= (TourGuide)updatedTourGuide;
			String updEmail=updGuide.getEmail();
			String upName=updGuide.getFname();
			String upLName= updGuide.getLname();
			String upNumber=updGuide.getTeln();
			//String updID=updGuide.getId();
			if (conn != null) {
				try {
					PreparedStatement query =conn.prepareStatement("UPDATE tourguides SET Name=?, LastName=?, Email=?, phoneNumber=? WHERE ID=?");
					query.setString(1, upName);
					query.setString(2, upLName);
					query.setString(3, updEmail);
					query.setString(4, upNumber);
					query.setString(5, updGuide.getId());
					query.executeUpdate();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
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
			//string upOrderNumber=
			//String updID=updGuide.getId();
			if (conn != null) {
				try {
					
					String query = " insert into orders (Park, Date, Time, NumOfVisitors, Email,TourGroup,orderNumber,NameOnOrder )"+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
					        
					PreparedStatement preparedStmt = conn.prepareStatement(query);
				      preparedStmt.setString (1, upPark);
				      preparedStmt.setString (2, upDate);
				      preparedStmt.setString (3, upTime);
				      preparedStmt.setString (4, upNumOfVisitors);
				      preparedStmt.setString (5, updEmail);
				      preparedStmt.setString (6, "True");
				      preparedStmt.setString (7, upOrderNum);
				      preparedStmt.setString (8, nameOnOrder);
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
        
		try {
			ResultSet rs= conn.createStatement().executeQuery("select * from orders");
			while(rs.next()) {
				oblist.add(new TourGuideOrder(rs.getString("OrderNumber"), rs.getString("Date"), rs.getString("Park"), null, null, null, null));
				
			}
			rs.close();
			return oblist;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
        
		return null;
	}

}












