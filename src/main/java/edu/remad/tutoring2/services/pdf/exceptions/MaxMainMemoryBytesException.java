package edu.remad.tutoring2.services.pdf.exceptions;

public class MaxMainMemoryBytesException extends RuntimeException {

	private static final long serialVersionUID = -9021952377790852335L;

	/**
	 * Creates instance of {@link message}
	 * 
	 * @param message error message
	 */
	public MaxMainMemoryBytesException(String message) {
		super(message);
	}
}
