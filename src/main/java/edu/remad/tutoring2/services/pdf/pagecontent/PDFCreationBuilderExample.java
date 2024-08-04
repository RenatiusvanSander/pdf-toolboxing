package edu.remad.tutoring2.services.pdf.pagecontent;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationBuilder;
import edu.remad.tutoring2.services.pdf.exceptions.PDFComplexInvoiceBuilderException;

public class PDFCreationBuilderExample {

	/**
	 * Runs creation of complex invoice
	 *
	 * @param args arguments from environment
	 * @throws IOException In case of creation of PDF fails.
	 */
	public static void main(String[] args) throws IOException {
		PDPage firstPage = createPage1();
		PDPage secondPage = createPage2();
		
		PDFCreationBuilder builder = new PDFCreationBuilder();
		PDDocument document = builder.addPage(firstPage).addPage(secondPage).build();
	}
	
	private static PDPage createPage1() {
		try (PDDocument document = new PDDocument()) {
			PDPage firstPage = new PDPage(PDRectangle.A4);
			document.addPage(firstPage);

			ContentLayoutData contentLayout = new ContentLayoutData();
			contentLayout.setCustomerName("Max", "Mustermann");
			File logo = new File(ContentLayoutDataConstants.LOGO_FILE_PATH);
			contentLayout.setLogo(logo);
			contentLayout.setFont(PDType1Font.HELVETICA);
			contentLayout.setItalicFont(PDType1Font.HELVETICA_OBLIQUE);
			contentLayout.setFontColor(Color.BLACK);
			contentLayout.setStreetHouseNumber("Musterstraße", "26");
			contentLayout.setLocationZipCode("00000", "Musterstadt");
			contentLayout.setContactCompany(ContentLayoutDataConstants.CONTACT_COMPANY);
			contentLayout.setContactName(ContentLayoutDataConstants.CONTACT_NAME);
			contentLayout.setContactStreetHouseNo(ContentLayoutDataConstants.CONTACT_STREET_HOUSE_NO);
			contentLayout.setContactZipAndLocation(
					ContentLayoutDataConstants.CONTACT_ZIP + " " + ContentLayoutDataConstants.CONTACT_LOCATION);
			contentLayout.setContactMobile(ContentLayoutDataConstants.CONTACT_MOBILE);
			contentLayout.setContactEmail(ContentLayoutDataConstants.CONTACT_EMAIL);
			contentLayout.setInvoiceNo("151");
			contentLayout.setDayFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			contentLayout.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
			contentLayout.setTableHeaderColor(ContentLayoutDataConstants.TABLE_HEADER_COLOR);
			contentLayout.setTableBodyColor(ContentLayoutDataConstants.TABLE_BODY_COLOR);
			contentLayout.setPaymentMethods(ContentLayoutDataConstants.PAYMENT_METHODS);
			contentLayout.setPaymentMethodColor(ContentLayoutDataConstants.PAYMENT_METHOD_COLOR);
			contentLayout.setTutoringAppointmentDate("12/07/2024");
			contentLayout.setInvoiceCreationDate("12/07/2024");
			contentLayout.setCapitalFontSize(ContentLayoutDataConstants.CAPITAL_FONT_SIZE);
			contentLayout.setTextFontSize(ContentLayoutDataConstants.TEXT_FONT_SIZE);
			contentLayout.setPaymentMethodFontSize(ContentLayoutDataConstants.PAYMENT_METHOD_FONT_SIZE);
			contentLayout.setbottomLine(ContentLayoutDataConstants.BOTTOM_LINE);
			contentLayout.setBottomLineFontSize(ContentLayoutDataConstants.BOTTOM_LINE_FONT_SIZE);
			contentLayout.setBottomLineFontColor(Color.DARK_GRAY);
			contentLayout.setBottomLineWidth(ContentLayoutDataConstants.BOTTOM_LINE_WIDTH);
			contentLayout.setBottomRectColor(ContentLayoutDataConstants.BOTTOM_RECT_COLOR);
			contentLayout.setBottomRect(ContentLayoutDataConstants.BOTTOM_RECT);
			contentLayout.setAuthoSign(ContentLayoutDataConstants.AUTHO_SIGN);
			contentLayout.setAuthoSignColor(ContentLayoutDataConstants.AUTHO_SIGN_COLOR);
			contentLayout.setTableCellWidths(ContentLayoutDataConstants.TABLE_CELL_WIDTHS);
			contentLayout.setTableCellHeight(ContentLayoutDataConstants.TABLE_CELL_HEIGHT);
			contentLayout.setTableHeaders(ContentLayoutDataConstants.TABLE_HEADERS);
			List<Map<String, String>> tableRows = new ArrayList<>();
			Map<String, String> row1 = new LinkedHashMap<>();
			row1.put("Position", "1");
			row1.put("Beschreibung", "Nachhilfe Mathe");
			row1.put("Preis", "13");
			row1.put("Menge", "2");
			row1.put("Gesamt", "26 EUR");
			tableRows.add(row1);
			contentLayout.setTableRows(tableRows);
			contentLayout.setPageWidth((int) firstPage.getTrimBox().getWidth());
			contentLayout.setPageHeight((int) firstPage.getTrimBox().getHeight());
			contentLayout.setInvoiceNoLabel(ContentLayoutDataConstants.INVOICE_NO_LABEL);
			contentLayout.setInvoiceDateLabel(ContentLayoutDataConstants.INVOICE_DATE_LABEL);
			contentLayout.setInvoicePerformanceDateLabel(ContentLayoutDataConstants.INVOICE_PERFORMANCE_DATE_LABEL);
			contentLayout.setValueAddedTaxDisclaimerText(ContentLayoutDataConstants.VALUE_ADDED_TAX_DISCLAIMER_TEXT);
			contentLayout.setDocumentInformationCreator(ContentLayoutDataConstants.DOCUMENT_INFORMATION_CREATOR);
			contentLayout.setDocumentInformationKeywords(
					new String[] { ContentLayoutDataConstants.DOCUMENT_INFORMATION_KEYWORD_INVOICE, "151",
							contentLayout.getCustomerName() });
			contentLayout.getDocumentInformationKeywords();
			contentLayout.setHasMainContentLayoutData(true);

			PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
			SinglePageContentLayouter pageContenLayouter = new SinglePageContentLayouter(document, firstPage,
					contentLayout, contentStream);
			pageContenLayouter.build();
			contentStream.close();
			
			return firstPage;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	private static PDPage createPage2() {
		try (PDDocument document = new PDDocument()) {
			PDPage secondPage = new PDPage(PDRectangle.A4);
			document.addPage(secondPage);

			ContentLayoutData contentLayout = new ContentLayoutData();
			contentLayout.setCustomerName("Maxim", "Musterfrau");
			File logo = new File(ContentLayoutDataConstants.LOGO_FILE_PATH);
			contentLayout.setLogo(logo);
			contentLayout.setFont(PDType1Font.HELVETICA);
			contentLayout.setItalicFont(PDType1Font.HELVETICA_OBLIQUE);
			contentLayout.setFontColor(Color.BLACK);
			contentLayout.setStreetHouseNumber("Musterring", "26");
			contentLayout.setLocationZipCode("00000", "Mustercity");
			contentLayout.setContactCompany(ContentLayoutDataConstants.CONTACT_COMPANY);
			contentLayout.setContactName(ContentLayoutDataConstants.CONTACT_NAME);
			contentLayout.setContactStreetHouseNo(ContentLayoutDataConstants.CONTACT_STREET_HOUSE_NO);
			contentLayout.setContactZipAndLocation(
					ContentLayoutDataConstants.CONTACT_ZIP + " " + ContentLayoutDataConstants.CONTACT_LOCATION);
			contentLayout.setContactMobile(ContentLayoutDataConstants.CONTACT_MOBILE);
			contentLayout.setContactEmail(ContentLayoutDataConstants.CONTACT_EMAIL);
			contentLayout.setInvoiceNo("152");
			contentLayout.setDayFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			contentLayout.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
			contentLayout.setTableHeaderColor(ContentLayoutDataConstants.TABLE_HEADER_COLOR);
			contentLayout.setTableBodyColor(ContentLayoutDataConstants.TABLE_BODY_COLOR);
			contentLayout.setPaymentMethods(ContentLayoutDataConstants.PAYMENT_METHODS);
			contentLayout.setPaymentMethodColor(ContentLayoutDataConstants.PAYMENT_METHOD_COLOR);
			contentLayout.setTutoringAppointmentDate("12/07/2024");
			contentLayout.setInvoiceCreationDate("12/07/2024");
			contentLayout.setCapitalFontSize(ContentLayoutDataConstants.CAPITAL_FONT_SIZE);
			contentLayout.setTextFontSize(ContentLayoutDataConstants.TEXT_FONT_SIZE);
			contentLayout.setPaymentMethodFontSize(ContentLayoutDataConstants.PAYMENT_METHOD_FONT_SIZE);
			contentLayout.setbottomLine(ContentLayoutDataConstants.BOTTOM_LINE);
			contentLayout.setBottomLineFontSize(ContentLayoutDataConstants.BOTTOM_LINE_FONT_SIZE);
			contentLayout.setBottomLineFontColor(Color.DARK_GRAY);
			contentLayout.setBottomLineWidth(ContentLayoutDataConstants.BOTTOM_LINE_WIDTH);
			contentLayout.setBottomRectColor(ContentLayoutDataConstants.BOTTOM_RECT_COLOR);
			contentLayout.setBottomRect(ContentLayoutDataConstants.BOTTOM_RECT);
			contentLayout.setAuthoSign(ContentLayoutDataConstants.AUTHO_SIGN);
			contentLayout.setAuthoSignColor(ContentLayoutDataConstants.AUTHO_SIGN_COLOR);
			contentLayout.setTableCellWidths(ContentLayoutDataConstants.TABLE_CELL_WIDTHS);
			contentLayout.setTableCellHeight(ContentLayoutDataConstants.TABLE_CELL_HEIGHT);
			contentLayout.setTableHeaders(ContentLayoutDataConstants.TABLE_HEADERS);
			List<Map<String, String>> tableRows = new ArrayList<>();
			Map<String, String> row1 = new LinkedHashMap<>();
			row1.put("Position", "1");
			row1.put("Beschreibung", "Nachhilfe Mathe");
			row1.put("Preis", "13");
			row1.put("Menge", "2");
			row1.put("Gesamt", "26 EUR");
			tableRows.add(row1);
			contentLayout.setTableRows(tableRows);
			contentLayout.setPageWidth((int) secondPage.getTrimBox().getWidth());
			contentLayout.setPageHeight((int) secondPage.getTrimBox().getHeight());
			contentLayout.setInvoiceNoLabel(ContentLayoutDataConstants.INVOICE_NO_LABEL);
			contentLayout.setInvoiceDateLabel(ContentLayoutDataConstants.INVOICE_DATE_LABEL);
			contentLayout.setInvoicePerformanceDateLabel(ContentLayoutDataConstants.INVOICE_PERFORMANCE_DATE_LABEL);
			contentLayout.setValueAddedTaxDisclaimerText(ContentLayoutDataConstants.VALUE_ADDED_TAX_DISCLAIMER_TEXT);
			contentLayout.setDocumentInformationCreator(ContentLayoutDataConstants.DOCUMENT_INFORMATION_CREATOR);
			contentLayout.setDocumentInformationKeywords(
					new String[] { ContentLayoutDataConstants.DOCUMENT_INFORMATION_KEYWORD_INVOICE, "152",
							contentLayout.getCustomerName() });
			contentLayout.getDocumentInformationKeywords();
			contentLayout.setHasMainContentLayoutData(true);

			PDPageContentStream contentStream = new PDPageContentStream(document, secondPage);
			SinglePageContentLayouter pageContenLayouter = new SinglePageContentLayouter(document, secondPage,
					contentLayout, contentStream);
			pageContenLayouter.build();
			contentStream.close();
			
			return secondPage;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
