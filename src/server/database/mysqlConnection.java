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
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import client.logic.WaitingList;
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

	public static boolean updateDB(String updatedTourGuide) {
//		if (updatedTourGuide instanceof TourGuide) {
//			TourGuide updGuide= (TourGuide)updatedTourGuide;
//			String updEmail=updGuide.getEmail();
//			String upName=updGuide.getFname();
//			String upLName= updGuide.getLname();
//			String upNumber=updGuide.getTeln();
			//String updID=updGuide.getId();
			if (conn != null) {
				try {
					Statement st = conn.createStatement();
					st.executeUpdate(updatedTourGuide);
//					PreparedStatement query =conn.prepareStatement("UPDATE tourguides SET Name=?, LastName=?, Email=?, phoneNumber=? WHERE ID=?");
//					query.setString(1, upName);
//					query.setString(2, upLName);
//					query.setString(3, updEmail);
//					query.setString(4, upNumber);
//					query.setString(5, updGuide.getId());
					//query.executeUpdate();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		//}
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
			String waitingTime=updGuide.getTimeOfEnterence();
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
				      preparedStmt.setString (6, "True");
				      preparedStmt.setString (7, upOrderNum);
				      preparedStmt.setString (8, nameOnOrder);
				      preparedStmt.setString (9, tourID);
				      preparedStmt.setString (10, waitingTime);
				      preparedStmt.execute();
				      
				      conn.close();
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
		int xd;
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












