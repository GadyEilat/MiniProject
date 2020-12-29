package client.logic;

import java.io.Serializable;

public class Worker implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private String role;
	private String Park;


	public Worker(String userName, String password, String role, String Park ) {
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.Park = Park;

		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}
	
	public String getPark() {
		return Park;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

//	@Override
//	public String toString() {
//		return "Worker [userName=" + userName + ", password=" + password + "]";
//	}

}
