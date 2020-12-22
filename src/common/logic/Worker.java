package common.logic;

public class Worker {
	private String UserName;
	private String Password;

	public Worker(String userName, String password) {
		UserName = userName;
		Password = password;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPasswoord() {
		return Password;
	}

	public void setPasswoord(String passwoord) {
		Password = passwoord;
	}

	@Override
	public String toString() {
		return "Worker [UserName=" + UserName + "]";
	}
}
