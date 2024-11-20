package edu.remad.tutoring2.services.pdf.exceptions;

public class DocumentInformationBuilderException extends RuntimeException {

	/**
	 * generated UID
	 */
	private static final long serialVersionUID = -4437355334416364166L;

	/**
	 * Creates {@link DocumentInformationBuilderException}
	 */
	public DocumentInformationBuilderException() {
		super();
	}

	/**
	 * Creates {@link DocumentInformationBuilderException}
	 * 
	 * @param message error message
	 */
	public DocumentInformationBuilderException(String message) {
		super(message);
	}

	/**
	 * Creates {@link DocumentInformationBuilderException}
	 * 
	 * @param message error message
	 * @param cause   {@link Throwable} with cause
	 */
	public DocumentInformationBuilderException(String message, Throwable cause) {
		super(message, cause);
	}
}
