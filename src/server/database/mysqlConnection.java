package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST", "root","Liran159357!");
			// Connection conn =
			// DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
//    		ServerController.instance.displayMsg("SQL connection succeed");
			// createTableCourses(conn);
			ServerController.instance.displayMsg("Connected To DB Visitors");
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
				String sql = ("SELECT * FROM gonature.visitors where id = " + str + ";");
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

	public static boolean updateDB(Object updatedVisitor) {
		if (updatedVisitor instanceof Visitor) {
			Visitor updVis= (Visitor)updatedVisitor;
			String updEmail=updVis.getEmail();
			String updID=updVis.getId();
			if (conn != null) {
				try {
					PreparedStatement query =conn.prepareStatement("UPDATE visitors SET Email=? WHERE ID=?");
					query.setString(1, updEmail);
					query.setString(2, updID);
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

}

