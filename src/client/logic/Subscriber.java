package client.logic;

public class Subscriber extends Visitor{
	
	private static final long serialVersionUID = 1L;

	private String amountOfFamilyMember;
	
	public Subscriber(String id, String fname, String sname, String email, String teln, String amountOfFamilyMember) {
		super(id, fname, sname, email, teln);
		this.amountOfFamilyMember=amountOfFamilyMember;
	}

	public String getAmountOfFamilyMember() {
		return amountOfFamilyMember;
	}

	public void setAmountOfFamilyMember(String amountOfFamilyMember) {
		this.amountOfFamilyMember = amountOfFamilyMember;
	}

//	@Override
//	public String toString() {
//		return "Subscriber [amountOfFamilyMember=" + amountOfFamilyMember + "]";
//	}
	
}
