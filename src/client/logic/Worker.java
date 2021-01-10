package client.logic;

import java.io.Serializable;

/**
 * Worker class. The class implements Serializable which transmits the
 * information from the client to the server. This class is responsible for the
 * worker object.
 * 
 * @author Liran Amilov
 *
 */

public class Worker implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String role;
	private ParkInfo Park;
	private String scene;
	private String workerName;

	/**
	 * Worker method. Empty builder.
	 */

	public Worker() {
	}

	/**
	 * Worker method. A constructor that contains specific fields.
	 * 
	 * @param userName
	 * @param password
	 * @param role
	 * @param Park
	 * @param workerName
	 * @param scene
	 */

	public Worker(String userName, String password, String role, ParkInfo Park, String workerName, String scene) {
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.Park = Park;
		this.workerName = workerName;
		this.scene = scene;
	}

	/**
	 * Worker method. A constructor that contains specific fields.
	 * 
	 * @param userName
	 * @param password
	 * @param role
	 * @param Park
	 */

	public Worker(String userName, String password, String role, ParkInfo Park) {
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.Park = Park;
	}

	public void setPark(ParkInfo park) {
		Park = park;
	}

	/**
	 * getScene method.
	 * 
	 * @return scene
	 */

	public String getScene() {
		return scene;
	}

	/**
	 * getWorkerName method.
	 * 
	 * @return workerName
	 */

	public String getWorkerName() {
		return workerName;
	}

	/**
	 * getUserName method.
	 * 
	 * @return userName
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * setUserName method. The method inserts the username into the username of the
	 * object.
	 * 
	 * @param userName
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * getPassword method.
	 * @return password
	 */

	public String getPassword() {
		return password;
	}
	
	/**
	 * ParkInfo method.
	 * @return Park
	 */

	public ParkInfo getPark() {
		return Park;
	}
	
	/**
	 * getRole method.
	 * @return role
	 */

	public String getRole() {
		return role;
	}
	
	/**
	 * setRole method. The method inserts the role into the role of the
	 * @param role
	 */

	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * toString method. This method prints a string.
	 * @return string
	 */

	@Override
	public String toString() {
		return "Worker [userName =" + userName + "]";
	}

}
