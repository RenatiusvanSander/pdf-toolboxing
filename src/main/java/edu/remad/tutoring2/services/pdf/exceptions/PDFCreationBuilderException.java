package edu.remad.tutoring2.services.pdf.exceptions;

public class PDFCreationBuilderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3066049477218215753L;

	public PDFCreationBuilderException() {
	}

	public PDFCreationBuilderException(String message) {
		super(message);
	}

	public PDFCreationBuilderException(String message, Throwable cause) {
		super(message, cause);
	}
}
