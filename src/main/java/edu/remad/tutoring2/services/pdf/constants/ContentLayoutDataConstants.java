package edu.remad.tutoring2.services.pdf.constants;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.w3c.dom.css.Rect;

public class ContentLayoutDataConstants {

	/**
	 * private Constructor do not initialize instance.
	 */
	private ContentLayoutDataConstants() {
	}

	// unix and windows are different
	public static final String LOGO_FILE_PATH = "img/logo.png";

	public static final PDType1Font FONT = PDType1Font.HELVETICA;

	public static final PDType1Font ITALIC_FRONT = PDType1Font.HELVETICA_OBLIQUE;

	public static final Color FONT_COLOR = Color.BLACK;

	public static final String CONTACT_COMPANY = "Remy Meier Freelance Nachhilfe";

	public static final String CONTACT_NAME = "Remy Meier";

	public static final String CONTACT_STREET_HOUSE_NO = "Volksdorfer Grenzweg 40A";

	public static final String CONTACT_MOBILE = "+49 176 61 36 22 53";

	public static final String CONTACT_EMAIL = "remad@web.de";

	public static final Color TABLE_HEADER_COLOR = new Color(240, 93, 11);

	public static final Color TABLE_BODY_COLOR = new Color(219, 218, 198);

	public static final List<String> PAYMENT_METHODS = List.of("Paypal: remad@web.de",
			"Überweisung: DE62 1203 0000 1071 0649 66 / BYLADEM1001", "Bargeld", "Kleinanzeigen.de-Methoden");

	public static final Color PAYMENT_METHOD_COLOR = new Color(122, 122, 122);

	public static final Long CAPITAL_FONT_SIZE = 12L;

	public static final Float TEXT_FONT_SIZE = 16F;

	public static final Float PAYMENT_METHOD_FONT_SIZE = 10F;

	public static final String BOTTOM_LINE = "Lernen ist das halbe Leben!";

	public static final Integer BOTTOM_LINE_FONT_SIZE = 20;

	public static final Color BOTTOM_LINE_FONT_COLOR = Color.DARK_GRAY;

	public static final Float BOTTOM_LINE_WIDTH = 20F;

	public static final Color BOTTOM_RECT_COLOR = new Color(255, 91, 0);

	public static final Rectangle BOTTOM_RECT = new Rectangle(0, 0, 0, 30);
	
	public static final String AUTHO_SIGN = "Unterschrift";
	
	public static final Color AUTHO_SIGN_COLOR = Color.BLACK;
	
	public static final int[] TABLE_CELL_WIDTHS = new int[] { 80, 230, 70, 80, 80 };
	
	public static final Integer TABLE_CELL_HEIGHT = 30;
	
	public static final List<String> TABLE_HEADERS = List.of("Position", "Beschreibung", "Preis", "Menge", "Gesamt");
	
	public static final PDPage PAGE = new PDPage(PDRectangle.A4);
	
	public static final String INVOICE_NO_LABEL = "Rechnungsnummer";
	
	public static final String INVOICE_DATE_LABEL = "Rechnungsdatum";
	
	public static final String INVOICE_PERFORMANCE_DATE_LABEL = "Leistungsdatum";
	
	public static final String[] VALUE_ADDED_TAX_DISCLAIMER_TEXT =
			new String[] { "Gemäß § 19 UStG wird keine Umsatzsteuer berechnet." };
	
	public static final String DOCUMENT_INFORMATION_CREATOR = "Tutoring App";
	
	public static final String DOCUMENT_INFORMATION_KEYWORD_INVOICE = "Rechnung";
	
	public static final String CONTACT_ZIP = "22359";
	
	public static final String CONTACT_LOCATION = "Hamburg";
}
