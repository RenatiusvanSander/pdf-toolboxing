package edu.remad.tutoring2.services.pdf.constants;

public enum FileSubtype {

	APPLICATION_XML_VALUE(0, "application/xml");

	private final int ordinalNo;
	private final String stringValue;

	/**
	 * Creates {@link FileSubtype}
	 * 
	 * @param ordinalNo ordinal Number to set
	 * @param stringValue string encoded file sub type value
	 */
	FileSubtype(int ordinalNo, String stringValue) {
		this.ordinalNo = ordinalNo;
		this.stringValue = stringValue;
	}

	/**
	 *  @return ordinal number
	 */
	public int getOrdinalNo() {
		return ordinalNo;
	}

	/**
	 * @return string encoded value
	 */
	public String getStringValue() {
		return stringValue;
	}
}
