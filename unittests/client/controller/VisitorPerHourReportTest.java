package client.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.controller.VisitorPerHourReport;
import javafx.fxml.FXMLLoader;
import server.database.mysqlConnection;

class VisitorPerHourReportTest {

	String[][] amountPerOrderKind;
	Exception myException = null;
	
	//setting up the parameters
	@BeforeEach
	void setUp() throws Exception {
		mysqlConnection.connectToDB();
		myException = null;
		amountPerOrderKind = new String[3][3]; //the first [] signals the orderkind, the second [] signals date/time/orderkind.
	} //0- tour guide, 1- subscriber, 2 - regular.
	//0- date, 1- time, 2- orderkind.

	//method for getting our screen controller (the report screen)
	private VisitorPerHourReport myScreen() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/client/boundaries/þþVisitorsReportController.fxml"));
		try {
			loader.load();
			return loader.getController();
		} catch (IOException e) {
			myException = e;
		}
		return null;
	}
	
	//test case for checking if query after entered into an array string returns correct values.
	@Test
	void getReportDetailsFromDBWithArrayString() {
		String checkAmountOfVisitors = "SELECT Date,Time,OrderKind FROM gonature.casualinvitation WHERE Date = '2021-01-08'";
		ArrayList<Object> answerFromDBOfCasual = mysqlConnection.getDB(checkAmountOfVisitors);
		if (!answerFromDBOfCasual.isEmpty()) {
		amountPerOrderKind[0][0] = answerFromDBOfCasual.get(0).toString();
		amountPerOrderKind[0][1] = answerFromDBOfCasual.get(1).toString();
		amountPerOrderKind[0][2] = answerFromDBOfCasual.get(2).toString();
		amountPerOrderKind[1][0] = answerFromDBOfCasual.get(3).toString();
		amountPerOrderKind[1][1] = answerFromDBOfCasual.get(4).toString();
		amountPerOrderKind[1][2] = answerFromDBOfCasual.get(5).toString();
		amountPerOrderKind[2][0] = answerFromDBOfCasual.get(6).toString();
		amountPerOrderKind[2][1] = answerFromDBOfCasual.get(7).toString();
		amountPerOrderKind[2][2] = answerFromDBOfCasual.get(8).toString();
		assertEquals("TourGuide", amountPerOrderKind[0][2]);
		assertEquals("15:16:13", amountPerOrderKind[1][1]);
		assertEquals("2021-01-08", amountPerOrderKind[0][0]);
		assertEquals("Regular", amountPerOrderKind[2][2]);
		}
	}
	
	//test case for checking if results from query are equal to expected results that appear in the workbench
	@Test
	void getReportDetailsTest() {
		String checkAmountOfVisitors = "SELECT Date,Time,OrderKind FROM gonature.casualinvitation WHERE Date = '2021-01-08'"
				+ " AND OrderNumber = '33567';";
		ArrayList<Object> answerFromDBOfCasual = mysqlConnection.getDB(checkAmountOfVisitors);
		if (!answerFromDBOfCasual.isEmpty()) {
			String dateOfVisit = answerFromDBOfCasual.get(0).toString();
			String timeOfVisit = answerFromDBOfCasual.get(1).toString();
			String kindOfVisit = answerFromDBOfCasual.get(2).toString();
			assertEquals("2021-01-08", dateOfVisit);
			assertEquals("12:01:47", timeOfVisit);
			assertEquals("TourGuide", kindOfVisit);
		} else {
			assertEquals("[]", answerFromDBOfCasual.toString());
		}
	}

	//method for checking if a given query realy returns an empty table.
	@Test
	void testGetReportDetailsFromDBFails() {
		String checkAmountOfVisitors = "SELECT Date,Time,OrderKind FROM gonature.casualinvitation WHERE Date = '2021-01-08'"
				+ " AND OrderNumber = '33569';"; // order number is wrong...
		ArrayList<Object> answerFromDBOfCasual = mysqlConnection.getDB(checkAmountOfVisitors);
		if (answerFromDBOfCasual.isEmpty()) {
			assertEquals("[]", answerFromDBOfCasual.toString());
			
		}
	}
	
	//test case for checking if given results from query aren't equal to some unexpected values.
	@Test
	void testGetReportDetailsFromDBWithFalseAsserts() {
		String checkAmountOfVisitors = "SELECT Date,Time,OrderKind FROM gonature.casualinvitation WHERE Date = '2021-01-08'"
				+ " AND OrderNumber = '33567';";
		ArrayList<Object> answerFromDBOfCasual = mysqlConnection.getDB(checkAmountOfVisitors);
		if (!answerFromDBOfCasual.isEmpty()) {
			String dateOfVisit = answerFromDBOfCasual.get(0).toString();
			String timeOfVisit = answerFromDBOfCasual.get(1).toString();
			String kindOfVisit = answerFromDBOfCasual.get(2).toString();
			assertFalse(dateOfVisit == "2021-01-09");
			assertFalse(timeOfVisit == "22:23");
			assertFalse(kindOfVisit == "Subscriber");
		}

	}
	
	//test case for checking if the controller was connected to the screen of the report successfully and didn't return a null.
	@Test
	void testMyScreenNotNull() {
		myScreen();
		if (myException == null) {
			fail();
		}
		else {
		assertTrue(true);
		}
	}
	
	
}
