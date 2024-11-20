package edu.remad.tutoring2.services.pdf.exceptions;

public class PDFMergerBuilderException extends RuntimeException {

	/**
	 * generated UID
	 */
	private static final long serialVersionUID = -8422860137341677092L;

	/**
	 * Constructor for {@link PDFMergerBuilderException}
	 */
	public PDFMergerBuilderException() {
		super();
	}

	/**
	 * Constructor for {@link PDFMergerBuilderException}
	 * 
	 * @param message error message
	 */
	public PDFMergerBuilderException(String message) {
		super(message);
	}

	/**
	 * Constructor for {@link PDFMergerBuilderException}
	 * 
	 * @param message error message
	 * @param cause   {@link Throwable} with cause
	 */
	public PDFMergerBuilderException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for {@link PDFMergerBuilderException}
	 * 
	 * @param cause {@link Throwable} with cause
	 */
	public PDFMergerBuilderException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for {@link PDFMergerBuilderException}
	 * 
	 * @param message            error message
	 * @param cause              {@link Throwable} with cause
	 * @param enableSuppression  {@code true} enables suppression
	 * @param writableStackTrace {@code true} writes stack trace
	 */
	public PDFMergerBuilderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
