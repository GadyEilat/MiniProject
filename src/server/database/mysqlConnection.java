package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gonature?serverTimezone=IST","root","Liran159357!");
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
	
	public static void addDB(ArrayList<String> msg)
	{
		try {
			PreparedStatement update = conn.prepareStatement("INSERT INTO visitors (Name, LastName, ID, Email, TelephoneNum) VALUES (?, ?, ?, ?, ?)");
//			for(int i=0;i<msg.size();i++)
//				update.setString(i+1,msg.get(i));
//			update.executeUpdate();
//			System.out.println("Add to DB");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}


	public static void updateDB(ArrayList<String> msg) {

	}

}