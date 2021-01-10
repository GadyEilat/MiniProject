package client.logic;

/**
 * Subscriber class. This class extends Visitor which contains the visitor
 * object.
 * 
 * @author Daniella Amdur
 */

public class Subscriber extends Visitor {

	private static final long serialVersionUID = 1L;

	private String amountOfFamilyMember;
	private String CreditCard;
	private String subscriberNumber;

	public Subscriber() {
		super(null, null, null, null, null);
	}

	/**
	 * Subscriber method. A constructor that contains specific fields.
	 * 
	 * @param id
	 * @param fname
	 * @param sname
	 * @param email
	 * @param teln
	 * @param amountOfFamilyMember
	 * @param CreditCard
	 * @param subscriberNumber
	 */

	public Subscriber(String id, String fname, String sname, String email, String teln, String amountOfFamilyMember,
			String CreditCard, String subscriberNumber) {
		super(id, fname, sname, email, teln);
		this.amountOfFamilyMember = amountOfFamilyMember;
		this.CreditCard = CreditCard;
		this.subscriberNumber = subscriberNumber;
	}

	/**
	 * getCreditCard method.
	 * 
	 * @return CreditCard
	 */

	public String getCreditCard() {
		return CreditCard;
	}

	/**
	 * setCreditCard method. The method inserts the creditCard into the creditCard
	 * of the object.
	 * 
	 * @param creditCard
	 */

	public void setCreditCard(String creditCard) {
		CreditCard = creditCard;
	}

	/**
	 * getSubscriberNumber method.
	 * 
	 * @return subscriberNumber
	 */

	public String getSubscriberNumber() {
		return subscriberNumber;
	}

	/**
	 * setSubscriberNumber method. The method inserts the subscriberNumber into the
	 * subscriberNumber of the object.
	 * 
	 * @param subscriberNumber
	 */

	public void setSubscriberNumber(String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
	}

	/**
	 * getAmountOfFamilyMember method.
	 * 
	 * @return amountOfFamilyMember
	 */

	public String getAmountOfFamilyMember() {
		return amountOfFamilyMember;
	}

	/**
	 * setAmountOfFamilyMember method. The method inserts the amountOfFamilyMember
	 * into the amountOfFamilyMember of the object.
	 * 
	 * @param amountOfFamilyMember
	 */

	public void setAmountOfFamilyMember(String amountOfFamilyMember) {
		this.amountOfFamilyMember = amountOfFamilyMember;
	}

//	@Override
//	public String toString() {
//		return "Subscriber [amountOfFamilyMember=" + amountOfFamilyMember + "]";
//	}

}
