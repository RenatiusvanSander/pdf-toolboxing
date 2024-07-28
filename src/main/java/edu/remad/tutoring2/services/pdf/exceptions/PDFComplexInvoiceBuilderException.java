package edu.remad.tutoring2.services.pdf.exceptions;

public class PDFComplexInvoiceBuilderException extends RuntimeException{

	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = 2265459715538240129L;

	public PDFComplexInvoiceBuilderException() {}
	
	public PDFComplexInvoiceBuilderException (String message) {
		super(message);
	}
	
	public PDFComplexInvoiceBuilderException (String message, Throwable cause) {
		super(message, cause);
	}
}
