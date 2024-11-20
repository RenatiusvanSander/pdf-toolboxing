package edu.remad.tutoring2.services.pdf.exceptions;

public class PdfUtilitiesException extends RuntimeException {

	/**
	 * generated UID
	 */
	private static final long serialVersionUID = 674272294969224176L;

	/**
	 * Constructor for {@link PdfUtilitiesException}
	 */
	public PdfUtilitiesException() {}
	
	/**
	 * Constructor for {@link PdfUtilitiesException}
	 * 
	 * @param message error message
	 */
	public PdfUtilitiesException (String message) {
		super(message);
	}
	
	/**
	 * Constructor for {@link PdfUtilitiesException}
	 * 
	 * @param message error message
	 * @param cause {@link Throwable} with cause
	 */
	public PdfUtilitiesException (String message, Throwable cause) {
		super(message, cause);
	}
}
