package edu.remad.tutoring2.services.pdf.utilities;

import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import edu.remad.tutoring2.appconstants.TimeAppConstants;
import edu.remad.tutoring2.models.AddressEntity;
import edu.remad.tutoring2.models.InvoiceEntity;
import edu.remad.tutoring2.models.TutoringAppointmentEntity;
import edu.remad.tutoring2.models.UserEntity;
import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;
import edu.remad.tutoring2.services.pdf.exceptions.PdfUtilitiesException;

public class PdfUtilities {

	private PdfUtilities() {
	}

	public static ContentLayoutData createContentLayoutData(TutoringAppointmentEntity tutoringAppointment,
			InvoiceEntity invoice) {
		try {
			UserEntity user = invoice.getInvoiceUser();
			AddressEntity address = user.getAddresses().get(0);

			ContentLayoutData contentLayoutData = new ContentLayoutData();
			contentLayoutData.setLogo(createLogo());
			contentLayoutData.setCustomerName(user.getFirstName(), user.getLastName());
			contentLayoutData.setFont(PDType1Font.HELVETICA);
			contentLayoutData.setItalicFont(PDType1Font.HELVETICA_OBLIQUE);
			contentLayoutData.setFontColor(Color.BLACK);
			contentLayoutData.setStreetHouseNumber(address.getAddressStreet(), address.getAddressHouseNo());
			contentLayoutData.setLocationZipCode(address.getAddressZipCode().getZipCode(),
					address.getAddressZipCode().getZipCodeLocation());
			contentLayoutData.setContactCompany(ContentLayoutDataConstants.CONTACT_COMPANY);
			contentLayoutData.setContactName(ContentLayoutDataConstants.CONTACT_NAME);
			contentLayoutData.setContactStreetHouseNo(ContentLayoutDataConstants.CONTACT_STREET_HOUSE_NO);
			contentLayoutData.setContactMobile(ContentLayoutDataConstants.CONTACT_MOBILE);
			contentLayoutData.setContactEmail(ContentLayoutDataConstants.CONTACT_EMAIL);
			contentLayoutData.setDayFormatter(TimeAppConstants.GERMAN_DATE_FORMATTER);
			contentLayoutData.setTimeFormatter(TimeAppConstants.TIME_FORMATTER);
			contentLayoutData.setTableHeaderColor(ContentLayoutDataConstants.TABLE_HEADER_COLOR);
			contentLayoutData.setTableBodyColor(ContentLayoutDataConstants.TABLE_BODY_COLOR);
			contentLayoutData.setPaymentMethods(ContentLayoutDataConstants.PAYMENT_METHODS);
			contentLayoutData.setPaymentMethodColor(ContentLayoutDataConstants.PAYMENT_METHOD_COLOR);
			contentLayoutData.setTutoringAppointmentDate(
					tutoringAppointment.getTutoringAppointmentDate().format(TimeAppConstants.GERMAN_DATE_FORMATTER));
			contentLayoutData.setInvoiceCreationDate(
					invoice.getInvoiceCreationDate().format(TimeAppConstants.GERMAN_DATE_FORMATTER));
			contentLayoutData.setCapitalFontSize(ContentLayoutDataConstants.CAPITAL_FONT_SIZE);
			contentLayoutData.setTextFontSize(ContentLayoutDataConstants.TEXT_FONT_SIZE);
			contentLayoutData.setPaymentMethodFontSize(ContentLayoutDataConstants.PAYMENT_METHOD_FONT_SIZE);
			contentLayoutData.setbottomLine(ContentLayoutDataConstants.BOTTOM_LINE);
			contentLayoutData.setBottomLineFontSize(ContentLayoutDataConstants.BOTTOM_LINE_FONT_SIZE);
			contentLayoutData.setBottomLineFontColor(ContentLayoutDataConstants.BOTTOM_LINE_FONT_COLOR);
			contentLayoutData.setBottomLineWidth(ContentLayoutDataConstants.BOTTOM_LINE_WIDTH);
			contentLayoutData.setBottomRectColor(ContentLayoutDataConstants.BOTTOM_RECT_COLOR);
			contentLayoutData.setBottomRect(ContentLayoutDataConstants.BOTTOM_RECT);
			contentLayoutData.setAuthoSign(ContentLayoutDataConstants.AUTHO_SIGN);
			contentLayoutData.setAuthoSignColor(ContentLayoutDataConstants.AUTHO_SIGN_COLOR);
			contentLayoutData.setTableCellWidths(ContentLayoutDataConstants.TABLE_CELL_WIDTHS);
			contentLayoutData.setTableCellHeight(ContentLayoutDataConstants.TABLE_CELL_HEIGHT);
			contentLayoutData.setTableHeaders(ContentLayoutDataConstants.TABLE_HEADERS);
			contentLayoutData.setTableRows(createTableRows(tutoringAppointment, invoice));

//			contentLayout.setPageWidth((int) firstPage.getTrimBox().getWidth());
//			contentLayout.setPageHeight((int) firstPage.getTrimBox().getHeight());
//			contentLayout.setInvoiceNoLabel("Rechnungsnummer");
//			contentLayout.setInvoiceDateLabel("Rechnungsdatum");
//			contentLayout.setInvoicePerformanceDateLabel("Leistungsdatum");
//			contentLayout.setValueAddedTaxDisclaimerText(
//					new String[] { "Gemäß § 19 UStG wird keine Umsatzsteuer berechnet." });
//			contentLayout.setDocumentInformationCreator("Tutoring App");
//			contentLayout.getDocumentInformationCreator();
//			contentLayout.setDocumentInformationKeywords(
//					new String[] { "Rechnung", "142", contentLayout.getCustomerName() });
//			contentLayout.getDocumentInformationKeywords();
//			contentLayout.setHasMainContentLayoutData(true);

			return contentLayoutData;
		} catch (RuntimeException e) {
			String message = String.format("%s: Error while creating %s", "PdfUtilities", "ContentLayoutData");
			throw new PdfUtilitiesException(message, e);
		}
	}

	public static List<Map<String, String>> createTableRows(TutoringAppointmentEntity tutoringAppointment,
			InvoiceEntity invoice) {
		BigDecimal price = invoice.getPrice().getPrice();
		List<Map<String, String>> tableRows = new ArrayList<>();
		Map<String, String> row1 = new LinkedHashMap<>();
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(0), "1");
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(1),
				invoice.getInvoiceServiceContract().getServiceContractName());
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(2), String.valueOf(price));
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(3), "1");
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(4), String.valueOf(price) + "EUR");
		tableRows.add(row1);

		return tableRows;
	}

	public static File createLogo() {
		return new File(ContentLayoutDataConstants.LOGO_FILE_PATH);
	}

	public static File createCustomLogo() {
		return new File("src/main/resources/img/logo.png");
	}
}
