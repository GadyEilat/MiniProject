package client.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkerLoginTest {
	WorkerLogin workerLogin;
	@BeforeEach
	void setUp() throws Exception {
		workerLogin = new WorkerLogin();
	}
	/**
	 * Checking the answer from the class in case of user and password input are empty
	 */
	@Test
	void testCheckLogInEmptyFields() {
		assertEquals("user name empty", workerLogin.getStringField("",""));
	}
	/**
	 * Checking the answer from the class in case of user input is empty
	 */
	@Test
	void testCheckLogInEmptyUserName() {
		assertEquals("user name empty", workerLogin.getStringField("","password"));
	}
	/**
	 * Checking the answer from the class in case of password input is empty
	 */
	@Test
	void testCheckLogInEmptypassword() {
		assertEquals("password empty", workerLogin.getStringField("username",""));
	}
	/**
	 * Checking the answer from the class in case of user and password input are full
	 */
	@Test
	void testCheckLogInGoodInput() {
		assertEquals("user name and password set", workerLogin.getStringField("username","password"));
	}
	/**
	 * Checking the answer from the class in case of user and password input are full
	 * user name and password include sign and number 
	 */
	@Test
	void testCheckLogInGoodInputWithSigns() {
		assertEquals("user name and password set", workerLogin.getStringField("username12!!","p@ssw0rd"));
	}


}
