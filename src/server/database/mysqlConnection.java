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
	public static void connectToDB()
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
        	/* handle the error*/
        	 }
        
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gonatureschema?serverTimezone=IST","root","Aa123456");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
//    		ServerController.instance.displayMsg("SQL connection succeed");
            //createTableCourses(conn);
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
   	}
	
	public static void addDB(Object msg)
	{
		//connectToDB();
		System.out.println("shalom");
		Visitor message = (Visitor)msg;
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String sql = ("SELECT * FROM visitors;");
				ResultSet rs = st.executeQuery(sql);
				if(rs.next()) { 
				 String fName = rs.getString("firstName"); 
				 String lName = rs.getString("lastName");
				 int id = rs.getInt("identificationNumber");
				 String email = rs.getString("email");
				 int pNumber = rs.getInt("phoneNumber");
				 
				 System.out.println(rs.getString(1));
				 System.out.println(rs.getString(2));
				 System.out.println(rs.getInt(3));
				 System.out.println(rs.getString(4));
				 System.out.println(rs.getInt(5));
				 
				 conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	/*	try {
			
			PreparedStatement update = conn.prepareStatement("INSERT INTO visitors (firstName, lastName, id, email, telephoneNumber) VALUES (?, ?, ?, ?, ?)");
//			for(int i=0;i<msg.size();i++)
//				update.setString(i+1,msg.get(i));
//			update.executeUpdate();
//			System.out.println("Add to DB");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
	}


	public static void updateDB(ArrayList<String> msg) {

	}

}