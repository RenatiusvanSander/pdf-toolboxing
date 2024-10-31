package edu.remad.tutoring2.services.pdf.exceptions;

public class PDFMergerBuilderException extends RuntimeException {

	/**
	 * generated UID
	 */
	private static final long serialVersionUID = -8422860137341677092L;

	public PDFMergerBuilderException() {
		super();
	}

	public PDFMergerBuilderException(String message) {
		super(message);
	}

	public PDFMergerBuilderException(String message, Throwable cause) {
		super(message, cause);
	}

	public PDFMergerBuilderException(Throwable cause) {
		super(cause);
	}

	public PDFMergerBuilderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
