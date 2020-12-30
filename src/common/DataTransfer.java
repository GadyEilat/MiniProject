package common;

import java.io.Serializable;


public class DataTransfer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TypeOfMessage typeOfMessage;
	private TypeOfMessageReturn typeOfMessageReturn;
	private Object object;

	public DataTransfer(TypeOfMessage typeOfMessage, Object object) {
		this.typeOfMessage = typeOfMessage;
		this.object = object;
	}

	public TypeOfMessageReturn getTypeOfMessageReturn() {
		return typeOfMessageReturn;
	}

	public void setTypeOfMessageReturn(TypeOfMessageReturn typeOfMessageReturn) {
		this.typeOfMessageReturn = typeOfMessageReturn;
	}

	public DataTransfer(TypeOfMessageReturn typeOfMessageReturn, Object object) {
		this.typeOfMessageReturn = typeOfMessageReturn;
		this.object = object;
	}

	public TypeOfMessage getTypeOfMessage() {
		return typeOfMessage;
	}

	public void setTypeOfMessage(TypeOfMessage typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "DataTransfer [typeOfMessage=" + typeOfMessage + ", object=" + object + "]";
	}
}