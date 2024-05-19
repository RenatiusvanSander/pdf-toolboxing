package edu.remad.tutoring2.services.pdf.exceptions;

public class DocumentInformationBuilderException extends RuntimeException {

	/**
	 * generated UID
	 */
	private static final long serialVersionUID = -4437355334416364166L;
	
	public DocumentInformationBuilderException() {
		super();
	}
	
	public DocumentInformationBuilderException(String message) {
		super(message);
	}
	
	public DocumentInformationBuilderException(String message, Throwable cause) {
        super(message, cause);
    }
}
