package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import client.logic.Visitor;
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root",
					"Liran159357!");
			// Connection conn =
			// DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
//    		ServerController.instance.displayMsg("SQL connection succeed");
			// createTableCourses(conn);
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
		ArrayList<Object> answer = new ArrayList<String>();
		if (msg instanceof String) {
			str = (String) msg;
		}
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String sql = ("SELECT * FROM gonature.visitors where id = " + str + ";");
				ResultSet rs = st.executeQuery(sql);
				int i = 1;
				while (rs.next()) {
					answer.add(rs.getString(i));
					i++;
				}
				//conn.close();
				rs.close();
				return answer;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/*
		 * try {
		 * 
		 * PreparedStatement update = conn.
		 * prepareStatement("INSERT INTO visitors (firstName, lastName, id, email, telephoneNumber) VALUES (?, ?, ?, ?, ?)"
		 * ); // for(int i=0;i<msg.size();i++) // update.setString(i+1,msg.get(i)); //
		 * update.executeUpdate(); // System.out.println("Add to DB");
		 * 
		 * } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		return null;

	}

	public static void updateDB(ArrayList<String> msg) {

	}

}