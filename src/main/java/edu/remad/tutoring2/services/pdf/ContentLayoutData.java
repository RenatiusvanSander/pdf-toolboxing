package edu.remad.tutoring2.services.pdf;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.font.PDFont;

import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;

/**
 * Layouts the content
 */
public class ContentLayoutData {

	/**
	 * full customer's name
	 */
	private String customerName;

	/**
	 * customers street and house number
	 */
	private String streetHouseNumber;

	/**
	 * customer's location and zip code united
	 */
	private String locationZipCode;

	/**
	 * the contact name
	 */
	private String contactName;

	/**
	 * the contact company
	 */
	private String contactCompany;

	/**
	 * the contact mobile number
	 */
	private String contactMobile;

	/**
	 * contact E-Mail
	 */
	private String contactEmail;

	/**
	 * invoice number
	 */
	private String invoiceNo;

	/**
	 * the invoice creation date
	 */
	private String invoiceCreationDate;

	/**
	 * tutoring appointment date and time
	 */
	private String tutoringAppointmentDateTime;

	/**
	 * logo file path
	 */
	private File logo;

	/**
	 * font color as {@link Color}
	 */
	private Color fontColor;

	/**
	 * Normal used font
	 */
	private PDFont font;

	/**
	 * font for italic styled texte
	 */
	private PDFont italicFont;

	/**
	 * contact's street and house number
	 */
	private String contactStreetHouseNo;

	/**
	 * contact's Zip and location
	 */
	private String contactZipAndLocation;
	private DateTimeFormatter dateFormatter;
	private DateTimeFormatter timeFormatter;
	private Color tableHeaderColor;
	private Color tableBodyColor;
	private List<String> paymentMethods;
	private String tutoringAppointmentDate;
	private float capitalFontSize;
	private float textFontSize;
	private float paymentMethodFontSize;
	private String bottomLine;
	private int bottomLineFontSize;
	private Color bottomLineFontColor;
	private Color bottomRectColor;
	private float bottomLineWidth;
	private Rectangle bottomRect;
	private String authoSign;
	private Color authoSignColor;
	private int[] tableCellWidths;
	private int tableCellHeight;
	private List<String> tableHeaders = new ArrayList<>();
	private Color paymentMethodColor;
	private List<Map<String, String>> tableRows;
	private int pageWidth;
	private int pageHeight;
	private String[] valueAddedTaxDisclaimerText;
	private String creator;
	private String documentInformationCreator;
	private String[] documentInformationKeywords;
	private String subject;
	private String tableRowsFullPrice;
	private String splitDelimiter = null;
	private String invoiceNoLabel;
	private String invoiceDateLabel;
	private String invoicePerformanceDateLabel;
	private boolean hasMainContentLayoutData;

	/**
	 * Sets full name
	 *
	 * @param firstName customer's first name
	 * @param lastName  customer's last name
	 */
	public void setCustomerName(String firstName, String lastName) {
		this.customerName = String.format("%s %s %s", ContentLayoutDataConstants.NAME_PREFIX, firstName, lastName);
	}

	/**
	 * Gets full name
	 *
	 * @return the full name
	 */
	public String getCustomerName() {
		return this.customerName;
	}

	/**
	 * Sets street and house number
	 *
	 * @param street      customer's street to set
	 * @param houseNumber customer's house number
	 */
	public void setStreetHouseNumber(String street, String houseNumber) {
		this.streetHouseNumber = String.format("%s %s", street, houseNumber);
	}

	/**
	 * Gets street house number
	 *
	 * @return united street and house number
	 */
	public String getStreetHouseNumber() {
		return this.streetHouseNumber;
	}

	/**
	 * Sets location and zip code
	 *
	 * @param location customer's location
	 * @param zipCode  customer's zip code
	 */
	public void setLocationZipCode(String location, String zipCode) {
		this.locationZipCode = String.format("%s %s", location, zipCode);
	}

	/**
	 * Gets location zip code
	 *
	 * @return location and zip code
	 */
	public String getLocationZipCode() {
		return this.locationZipCode;
	}

	/**
	 * Gets contact name
	 *
	 * @return contact name
	 */
	public String getContactName() {
		return this.contactName;
	}

	/**
	 * Sets contact name
	 *
	 * @param contactName contact name t set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Gets contact company
	 *
	 * @return contact company
	 */
	public String getContactCompany() {
		return this.contactCompany;
	}

	/**
	 * Sets contact company
	 *
	 * @param contactCompany
	 */
	public void setContactCompany(String contactCompany) {
		this.contactCompany = contactCompany;
	}

	/**
	 * Gets contact mobile
	 *
	 * @return contact mobile number
	 */
	public String getContactMobile() {
		return this.contactMobile;
	}

	/**
	 * Sets contact mobile.
	 *
	 * @param contactMobile contact mobile number to set
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * Gets contact e-Mail
	 *
	 * @return contact e-mail
	 */
	public String getContactEmail() {
		return this.contactEmail;
	}

	/**
	 * Sets contact
	 *
	 * @param contactEmail contact E-Mail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * Gets invoice number
	 *
	 * @return invoice number
	 */
	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	/**
	 * Sets invoice number
	 *
	 * @param invoiceNo invoice number to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = String.format("%s %s", ContentLayoutDataConstants.INVOICE_NO_PREFIX, invoiceNo);
	}

	/**
	 * Gets only invoice number
	 * 
	 * @return string encoded invoice number
	 */
	public String getInvoiceNoWithoutPrefix() {
		return invoiceNo != null ? invoiceNo.replace(ContentLayoutDataConstants.INVOICE_NO_PREFIX + " ", "") : "";
	}

	/**
	 * Gets invoice number label
	 * 
	 * @return string encoded invoice number label
	 */
	public String getInvoiceNoLabel() {
		return invoiceNoLabel;
	}

	/**
	 * Gets invoice date label
	 * 
	 * @return invoice date label
	 */
	public String getInvoiceDateLabel() {
		return invoiceDateLabel;
	}

	/**
	 * Gets invoice performance date label
	 * 
	 * @return invoice performance date label
	 */
	public String getInvoicePerformanceDateLabel() {
		return invoicePerformanceDateLabel;
	}

	/**
	 * Gets invoice creation date
	 *
	 * @return string-encoded creation date
	 */
	public String getInvoiceCreationDate() {
		return this.invoiceCreationDate;
	}

	/**
	 * Sets invoice creation date
	 *
	 * @param invoiceCreationDate invoice creation date to set
	 */
	public void setInvoiceCreationDate(String invoiceCreationDate) {
		this.invoiceCreationDate = invoiceCreationDate;
	}

	/**
	 * Gets tutoring appointment date and time
	 *
	 * @return string-encoded tutoring appointment date and time
	 */
	public String getTutoringAppointmentDateTime() {
		return this.tutoringAppointmentDateTime;
	}

	/**
	 * Sets tutoring apoint date and time.
	 *
	 * @param date string-encoded and DIN 5008:2020 formatted date to set
	 * @param time string-encoded and DIN 5008:2020 formatted time to set.
	 */
	public void setTutoringAppointmentDateTime(String date, String time) {
		this.tutoringAppointmentDateTime = String.format("%s %s %s",
				ContentLayoutDataConstants.TUTORING_APPOINTMENT_PREFIX, date, time);
	}

	/**
	 * Gets logo.
	 *
	 * @return path to logo as {@link File}
	 */
	public File getLogo() {
		return logo;
	}

	/**
	 * Sets logo
	 *
	 * @param logo file path to the logo file
	 */
	public void setLogo(File logo) {
		this.logo = logo;
	}

	/**
	 * Gets font color.
	 *
	 * @return color of the font, {@link Color}
	 */
	public Color getFontColor() {
		return fontColor;
	}

	/**
	 * Sets font color
	 *
	 * @param fontColor the font color as instance of {@link Color}
	 */
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * Gets font
	 * 
	 * @return {@link PDFont}
	 */
	public PDFont getFont() {
		return font;
	}

	/**
	 * Sets font.
	 * 
	 * @param font {@link PDFont}
	 */
	public void setFont(PDFont font) {
		this.font = font;
	}

	/**
	 * Gets italic font
	 * 
	 * @return {@link PDFont}
	 */
	public PDFont getItalicFont() {
		return italicFont;
	}

	/**
	 * Sets italic font
	 * 
	 * @param italicFont {@link PDFont}
	 */
	public void setItalicFont(PDFont italicFont) {
		this.italicFont = italicFont;
	}

	public String getContactStreetHouseNo() {
		return contactStreetHouseNo;
	}

	public void setContactStreetHouseNo(String contactStreetHouseNo) {
		this.contactStreetHouseNo = contactStreetHouseNo;
	}

	public String getContactZipAndLocation() {
		return contactZipAndLocation;
	}

	public void setContactZipAndLocation(String contactZipAndLocation) {
		this.contactZipAndLocation = contactZipAndLocation;
	}

	public void setDayFormatter(DateTimeFormatter simpleDateFormat) {
		this.dateFormatter = simpleDateFormat;
	}

	public DateTimeFormatter getDateFormatter() {
		return dateFormatter;
	}

	public void setDateFormatter(DateTimeFormatter dateFormatter) {
		this.dateFormatter = dateFormatter;
	}

	public DateTimeFormatter getTimeFormatter() {
		return timeFormatter;
	}

	public void setTimeFormatter(DateTimeFormatter timeFormatter) {
		this.timeFormatter = timeFormatter;
	}

	public Color getTableHeaderColor() {
		return tableHeaderColor;
	}

	public void setTableHeaderColor(Color color) {
		this.tableHeaderColor = color;
	}

	public Color getTableBodyColor() {
		return tableBodyColor;
	}

	public void setTableBodyColor(Color color) {
		this.tableBodyColor = color;
	}

	public void setPaymentMethods(List<String> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public List<String> getPaymentMethods() {
		return paymentMethods;
	}

	public void setTutoringAppointmentDate(String s) {
		this.tutoringAppointmentDate = s;
	}

	public String getTutoringAppointmentDate() {
		return tutoringAppointmentDate;
	}

	public void setCapitalFontSize(float capitalFontSize) {
		this.capitalFontSize = capitalFontSize;
	}

	public float getCapitalFontSize() {
		return capitalFontSize;
	}

	public void setTextFontSize(float textFontSize) {
		this.textFontSize = textFontSize;
	}

	public float getTextFontSize() {
		return textFontSize;
	}

	public void setPaymentMethodFontSize(float paymentMethodFontSize) {
		this.paymentMethodFontSize = paymentMethodFontSize;
	}

	public float getPaymentMethodFontSize() {
		return paymentMethodFontSize;
	}

	public void setbottomLine(String bottomLine) {
		this.bottomLine = bottomLine;
	}

	public String getBottomLine() {
		return bottomLine;
	}

	public void setBottomLineFontSize(int bottomLineFontSize) {
		this.bottomLineFontSize = bottomLineFontSize;
	}

	public int getBottomLineFontSize() {
		return bottomLineFontSize;
	}

	public void setBottomLineFontColor(Color bottomLineFontColor) {
		this.bottomLineFontColor = bottomLineFontColor;
	}

	public Color getBottomLineFontColor() {
		return bottomLineFontColor;
	}

	public void setBottomRectColor(Color bottomRectColor) {
		this.bottomRectColor = bottomRectColor;
	}

	public Color getBottomRectColor() {
		return bottomRectColor;
	}

	public void setBottomLineWidth(float bottomLineWidth) {
		this.bottomLineWidth = bottomLineWidth;
	}

	public float getBottomLineWidth() {
		return bottomLineWidth;
	}

	public void setBottomRect(Rectangle bottomRect) {
		this.bottomRect = bottomRect;
	}

	public Rectangle getBottomRect() {
		return bottomRect;
	}

	public void setAuthoSign(String authoSign) {
		this.authoSign = authoSign;
	}

	public String getAuthoSign() {
		return authoSign;
	}

	public void setAuthoSignColor(Color authoSignColor) {
		this.authoSignColor = authoSignColor;
	}

	public Color getAuthoSignColor() {
		return authoSignColor;
	}

	public void setTableCellWidths(int[] tableCellWidths) {
		this.tableCellWidths = tableCellWidths;
	}

	public int[] getTableCellWidths() {
		return tableCellWidths;
	}

	public void setTableCellHeight(int tableCellHeight) {
		this.tableCellHeight = tableCellHeight;
	}

	public int getTableCellHeight() {
		return tableCellHeight;
	}

	public void setTableHeaders(List<String> tableHeaders) {
		this.tableHeaders = tableHeaders;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

	public void setPaymentMethodColor(Color paymentMethodColor) {
		this.paymentMethodColor = paymentMethodColor;
	}

	public Color getPaymentMethodColor() {
		return paymentMethodColor;
	}

	public void setTableRows(List<Map<String, String>> tableRows) {
		this.tableRows = tableRows;
	}

	public List<Map<String, String>> getTableRows() {
		return tableRows;
	}

	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}

	public int getPageWidth() {
		return pageWidth;
	}

	public void setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
	}

	public int getPageHeight() {
		return this.pageHeight;
	}

	public void setInvoiceNoLabel(String invoiceNoLabel) {
		this.invoiceNoLabel = invoiceNoLabel;
	}

	public void setInvoiceDateLabel(String invoiceDateLabel) {
		this.invoiceDateLabel = invoiceDateLabel;
	}

	public void setInvoicePerformanceDateLabel(String invoicePerformanceDateLabel) {
		this.invoicePerformanceDateLabel = invoicePerformanceDateLabel;
	}

	public void setValueAddedTaxDisclaimerText(String[] valueAddedTaxDisclaimerText) {
		this.valueAddedTaxDisclaimerText = valueAddedTaxDisclaimerText;
	}

	public String[] getValueAddedTaxDisclaimerText() {
		return this.valueAddedTaxDisclaimerText;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setDocumentInformationCreator(String documentInformationCreator) {
		this.documentInformationCreator = documentInformationCreator;
	}

	public String getDocumentInformationCreator() {
		return this.documentInformationCreator;
	}

	public void setDocumentInformationKeywords(String[] documentInformationKeywords) {
		this.documentInformationKeywords = documentInformationKeywords;
	}

	public String[] getDocumentInformationKeywords() {
		return this.documentInformationKeywords;
	}

	public String getDocumentInformationKeywordByIndex(int index) {
		if (index == this.documentInformationKeywords.length || index > this.documentInformationKeywords.length) {
			return "";
		}

		return this.documentInformationKeywords[index];
	}

	public String getSubject() {
		if ((subject == null || subject.length() < 2) && (invoiceNoLabel != null && invoiceNo != null)) {
			subject = invoiceNoLabel + " " + invoiceNo;
		}

		return subject;
	}

	public boolean isHasMainContentLayoutData() {
		return hasMainContentLayoutData;
	}

	public void setHasMainContentLayoutData(boolean hasMainContentLayoutData) {
		this.hasMainContentLayoutData = hasMainContentLayoutData;
	}

	/**
	 * Gets invoice positions as full price
	 * 
	 * @return {@link String}
	 */
	public String getInvoicePositionsAsFullPrice() {
		if (tableRowsFullPrice == null) {
			double sum = 0;

			for (Map<String, String> row : tableRows) {
				String price = row.get("Gesamt");

				if (price != null && price.length() > 2) {
					price = price.replace(" EUR", "");
					price = price.replace("EUR", "");
					double doublePrice = Double.parseDouble(price);
					sum += doublePrice;
				}
			}

			String formattedPrice = String.format("%,.2f", sum).replace(",", ".").replace(".00", "");
			tableRowsFullPrice = formattedPrice;
		}

		return tableRowsFullPrice;
	}

	/**
	 * Gets split delimiter
	 * 
	 * @return string encoded delimiter
	 */
	public String getSplitDelimiter() {
		return splitDelimiter;
	}

	/**
	 * Sets split delimiter
	 * 
	 * @param splitDelimiter the split delimiter to set
	 */
	public void setSplitDelimiter(String splitDelimiter) {
		this.splitDelimiter = splitDelimiter;
	}
}