package client.logic;

import java.io.Serializable;

public class Worker implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String role;
	private ParkInfo Park;
	private String scene;
	private String workerName;
	public Worker() {}

	public Worker(String userName, String password, String role, ParkInfo Park ,String workerName, String scene) {
		this.userName = userName;
		this.password = password;
		this.role=role;
		this.Park = Park;
		this.workerName=workerName;
		this.scene=scene;
	}
	public Worker(String userName, String password, String role, ParkInfo Park) {
		this.userName = userName;
		this.password = password;
		this.role=role;
		this.Park = Park;
	}

	public void setPark(ParkInfo park) {
		Park = park;
	}

	public String getScene() {
		return scene;
	}
	
	public String getWorkerName() {
		return workerName;
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
	
	public ParkInfo getPark() {
		return Park;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Worker [userName =" + userName +"]";
	}

}
