package edu.remad.tutoring2.services.pdf.constants;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public final class ContentLayoutDataConstants {

	/**
	 * private Constructor do not initialize instance.
	 */
	private ContentLayoutDataConstants() {
		// do not instantiate
	}

	/** prefix tutoring at value */
	public static final String TUTORING_APPOINTMENT_PREFIX = "Nachhilfe am:";
	
	/** prefix of invoice number */
	public static final String INVOICE_NO_PREFIX = "Rechnungsnummer: ";
	
	/** prefix of full name	 */
	public static final String NAME_PREFIX = "Frau / Herrn ";

	/** logo file path value */
	public static final String LOGO_FILE_PATH = "img/logo.png";

	/** font Helvetica */
	public static final PDType1Font FONT = PDType1Font.HELVETICA;

	/** italic font Helvetica Oblique */
	public static final PDType1Font ITALIC_FRONT = PDType1Font.HELVETICA_OBLIQUE;

	/** font color value */
	public static final Color FONT_COLOR = Color.BLACK;

	/** contact company value */
	public static final String CONTACT_COMPANY = "Remy Meier Freelance Nachhilfe";

	/** contact name value */
	public static final String CONTACT_NAME = "Remy Meier";

	/** contact street house number value */
	public static final String CONTACT_STREET_HOUSE_NO = "Volksdorfer Grenzweg 40A";

	/** contact mobile value */
	public static final String CONTACT_MOBILE = "+49 176 61 36 22 53";

	/** contact email value */
	public static final String CONTACT_EMAIL = "remad@web.de";

	/** table header color */
	public static final Color TABLE_HEADER_COLOR = new Color(240, 93, 11);

	/** table body color object */
	public static final Color TABLE_BODY_COLOR = new Color(219, 218, 198);

	/** payment methods values */
	public static final List<String> PAYMENT_METHODS = List.of("Paypal: remad@web.de",
			"Überweisung: DE62 1203 0000 1071 0649 66 / BYLADEM1001", "Bargeld", "Kleinanzeigen.de-Methoden");

	/** payment method color value */
	public static final Color PAYMENT_METHOD_COLOR = new Color(122, 122, 122);

	/** capital font size value */
	public static final Long CAPITAL_FONT_SIZE = 12L;

	/** text font size value */
	public static final Float TEXT_FONT_SIZE = 16F;

	/** payment method font size value */
	public static final Float PAYMENT_METHOD_FONT_SIZE = 10F;

	/** bottom line text value */
	public static final String BOTTOM_LINE = "Lernen ist das halbe Leben!";

	/** bottom line font size value */
	public static final Integer BOTTOM_LINE_FONT_SIZE = 20;

	/** bottom line font color value */
	public static final Color BOTTOM_LINE_FONT_COLOR = Color.DARK_GRAY;

	/** bottom line width value */
	public static final Float BOTTOM_LINE_WIDTH = 20F;

	/** bottom rectangle color value */
	public static final Color BOTTOM_RECT_COLOR = new Color(255, 91, 0);

	/** bottom rectangle object */
	public static final Rectangle BOTTOM_RECT = new Rectangle(0, 0, 0, 30);

	/** autho sign value */
	public static final String AUTHO_SIGN = "Unterschrift";

	/** author signed color value */
	public static final Color AUTHO_SIGN_COLOR = Color.BLACK;

	/** table cell widths values */
	public static final int[] TABLE_CELL_WIDTHS = new int[] { 80, 230, 70, 80, 80 };

	/** table cell height value */
	public static final Integer TABLE_CELL_HEIGHT = 30;

	/** table headers values */
	public static final List<String> TABLE_HEADERS = List.of("Position", "Beschreibung", "Preis", "Menge", "Gesamt");

	/** page A4 Rectangle */
	public static final PDPage PAGE = new PDPage(PDRectangle.A4);

	/** invoice number label value */
	public static final String INVOICE_NO_LABEL = "Rechnungsnummer";

	/** invoice date label value */
	public static final String INVOICE_DATE_LABEL = "Rechnungsdatum";

	/** invoice performance date label */
	public static final String INVOICE_PERFORMANCE_DATE_LABEL = "Leistungsdatum";

	/** disclaimer text value */
	public static final String[] VALUE_ADDED_TAX_DISCLAIMER_TEXT = new String[] {
			"Gemäß § 19 UStG wird keine Umsatzsteuer berechnet." };

	/** document creator value */
	public static final String DOCUMENT_INFORMATION_CREATOR = "Tutoring App";

	/** keyword invoice value */
	public static final String DOCUMENT_INFORMATION_KEYWORD_INVOICE = "Rechnung";

	/** contact zip value */
	public static final String CONTACT_ZIP = "22359";

	/** contact location value */
	public static final String CONTACT_LOCATION = "Hamburg";
}
