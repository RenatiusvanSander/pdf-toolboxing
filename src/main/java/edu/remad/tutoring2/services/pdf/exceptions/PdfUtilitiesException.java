package edu.remad.tutoring2.services.pdf.exceptions;

public class PdfUtilitiesException extends RuntimeException {

	/**
	 * generated UID
	 */
	private static final long serialVersionUID = 674272294969224176L;

	public PdfUtilitiesException() {}
	
	public PdfUtilitiesException (String message) {
		super(message);
	}
	
	public PdfUtilitiesException (String message, Throwable cause) {
		super(message, cause);
	}
}
