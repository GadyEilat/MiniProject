package client.logic;

public class Subscriber extends Visitor{
	
	private static final long serialVersionUID = 1L;

	private String amountOfFamilyMember;
	private String CreditCard;
	private String subscriberNumber;
	public Subscriber() {super(null, null, null, null, null);}
	
	public Subscriber(String id, String fname, String sname, String email, String teln, String amountOfFamilyMember, String CreditCard, String subscriberNumber) {
		super(id, fname, sname, email, teln);
		this.amountOfFamilyMember=amountOfFamilyMember;
		this.CreditCard = CreditCard;
		this.subscriberNumber = subscriberNumber;
	}

	public String getCreditCard() {
		return CreditCard;
	}

	public void setCreditCard(String creditCard) {
		CreditCard = creditCard;
	}

	public String getSubscriberNumber() {
		return subscriberNumber;
	}

	public void setSubscriberNumber(String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
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
