package client.logic;

public class Visitor {
	private String id;
	private String fname;
	private String lname;
	private String email;
	private String teln;

	public Visitor(String id, String fname, String sname, String email, String teln) {
		this.id = id;
		this.fname = fname;
		this.lname = sname;
		this.email = email;
		this.teln = teln;
	}

//getter
	public String getId() {
		return id;
	}

	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getEmail() {
		return email;
	}

	public String getTeln() {
		return teln;
	}

	// setter
	public void setId(String id) {
		this.id = id;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setteln(String teln) {
		this.teln = teln;
	}

	public String toString() {
		return String.format("%s %s %s %s %s", id, fname, lname, email, teln);
	}
}
