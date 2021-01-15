package server.database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MySqlConnectionForLogInTest {
	/**
	 * connect to DB for taking information
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mysqlConnection.connectToDB();
	}
	/**
	 * send query to check if there is a user name='Liran' and his password is = 'La123'
	 * if there is user name and the password correct take the needed information
	 * checking if the real info about this worker is the same as the return
	 */
	@Test
	void testGetWorkerDetailsFromDB() {
		String checkUserAndPassword = "SELECT Role, Park, name, LogIn FROM gonature.worker WHERE UserName = 'Liran'"
				+ " AND Password = 'La123';";
		ArrayList<Object> answerFromDBOfWorker = mysqlConnection.getDB(checkUserAndPassword);
		String roleOfWorker = answerFromDBOfWorker.get(0).toString();
		String parkOfWorker = answerFromDBOfWorker.get(1).toString();
		String nameOfWorker = answerFromDBOfWorker.get(2).toString();
		String logInOfWorker = answerFromDBOfWorker.get(3).toString();
		assertEquals("Manager", roleOfWorker);
		assertEquals("Park1", parkOfWorker);
		assertEquals("Liran", nameOfWorker);
		assertEquals("False", logInOfWorker);

	}
	/**
	 * send query to check if there is a user name='Li' and his password is = 'La123'
	 * the user name false we expected empty ArrayList
	 */
	@Test
	void testGetWorkerDetailsFromDBWhenUserNameIsFalse() {
		String checkUserAndPassword = "SELECT Role, Park, name, LogIn FROM gonature.worker WHERE UserName = 'Li'"
				+ " AND Password = 'La123';";
		ArrayList<Object> answerFromDBOfWorker = mysqlConnection.getDB(checkUserAndPassword);
		assertEquals("[]", answerFromDBOfWorker.toString());
		
	}
	/**
	 * send query to check if there is a user name='Liran' and his password is = 'Laaaa'
	 * the password false we expected empty ArrayList
	 */
	@Test
	void testGetWorkerDetailsFromDBWhenPasswordIsFalse() {
		String checkUserAndPassword = "SELECT Role, Park, name, LogIn FROM gonature.worker WHERE UserName = 'Liran'"
				+ " AND Password = 'Laaaa';";
		ArrayList<Object> answerFromDBOfWorker = mysqlConnection.getDB(checkUserAndPassword);
		assertEquals("[]", answerFromDBOfWorker.toString());
		
	}
	/**
	 * updating the DB that the user is now connected and checking if the
	 * LogIn now 'True' and changing back when logout
	 */
	@Test
	void testUpdateLoggedInToDB() {
		boolean connect = mysqlConnection.updateDB(
				"UPDATE gonature.worker SET LogIn = 'True' WHERE UserName = 'Liran'" + " AND Park = 'Park1';");
		assertTrue(connect);
		ArrayList<Object> answerFromDBOfWorkerLogIn = mysqlConnection
				.getDB("SELECT LogIn FROM gonature.worker " + "WHERE UserName = 'Liran'");
		String LoginStatus = answerFromDBOfWorkerLogIn.get(0).toString();
		assertEquals("True", LoginStatus);
		connect = mysqlConnection.updateDB(
				"UPDATE gonature.worker SET LogIn = 'False' WHERE UserName = 'Liran'" + " AND Park = 'Park1';");
		assertTrue(connect);
	}
	
	
}
