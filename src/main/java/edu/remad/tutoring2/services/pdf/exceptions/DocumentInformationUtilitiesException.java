package edu.remad.tutoring2.services.pdf.exceptions;

public class DocumentInformationUtilitiesException extends RuntimeException {
	
	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = 2717040849180165053L;
	
	/**
	 * Constructor
	 * 
	 * @param errorMessage error message
	 */
	public DocumentInformationUtilitiesException(String errorMessage) {
		super(errorMessage);
	}

}
