package edu.remad.tutoring2.services.pdf.exceptions;

public class PDF3AFileAttachmentBuilderException extends RuntimeException {

	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = 5573504116746023262L;

	/**
	 * Creates {@link PDF3AFileAttachmentBuilderException} with error message
	 * 
	 * @param message Error message
	 */
	public PDF3AFileAttachmentBuilderException (String message) {
		super(message);
	}

	/**
	 * Creates {@link PDF3AFileAttachmentBuilderException} with error message and throwable-object
	 * 
	 * @param message Error message
	 * @param throwable any implementation of {@link Throwable}
	 */
	public PDF3AFileAttachmentBuilderException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
