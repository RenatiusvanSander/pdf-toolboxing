package edu.remad.tutoring2.services.pdf;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;

@ExtendWith(MockitoExtension.class)
public class PDFCreationBuilderTest {

	private ContentLayoutData contentLayoutData;

	@BeforeEach
	public void setUp() {
		contentLayoutData = createPage1();
	}

	@Test
	public void createDefaultPDFTest() throws IOException {
		PDFCreationBuilder builder = new PDFCreationBuilder();
		builder.contentLayoutData(List.of(contentLayoutData));

		byte[] actualPdf = builder.buildAsByteArray();
		
		assertNotNull(actualPdf);
	}

	private static ContentLayoutData createPage1() {
		PDPage firstPage = new PDPage(PDRectangle.A4);

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

		return contentLayout;
	}

	private static ContentLayoutData createPage2() {
		PDPage secondPage = new PDPage(PDRectangle.A4);
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

		return contentLayout;
	}
}
