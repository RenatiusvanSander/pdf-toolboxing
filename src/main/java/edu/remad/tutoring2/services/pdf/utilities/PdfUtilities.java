package edu.remad.tutoring2.services.pdf.utilities;

import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	public static ContentLayoutData createContentLayoutData2(InvoiceEntity invoice) {
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
			contentLayoutData.setContactZipAndLocation(ContentLayoutDataConstants.CONTACT_ZIP + " " + ContentLayoutDataConstants.CONTACT_LOCATION);
			contentLayoutData.setContactMobile(ContentLayoutDataConstants.CONTACT_MOBILE);
			contentLayoutData.setContactEmail(ContentLayoutDataConstants.CONTACT_EMAIL);
			contentLayoutData.setInvoiceNo(String.valueOf(invoice.getInvoiceNo()));
			contentLayoutData.setDayFormatter(TimeAppConstants.GERMAN_DATE_FORMATTER);
			contentLayoutData.setTimeFormatter(TimeAppConstants.TIME_FORMATTER);
			contentLayoutData.setTableHeaderColor(ContentLayoutDataConstants.TABLE_HEADER_COLOR);
			contentLayoutData.setTableBodyColor(ContentLayoutDataConstants.TABLE_BODY_COLOR);
			contentLayoutData.setPaymentMethods(ContentLayoutDataConstants.PAYMENT_METHODS);
			contentLayoutData.setPaymentMethodColor(ContentLayoutDataConstants.PAYMENT_METHOD_COLOR);
			contentLayoutData.setTutoringAppointmentDate(
					invoice.getInvoiceTutoringDate().format(TimeAppConstants.GERMAN_DATE_FORMATTER));
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
			contentLayoutData.setTableRows(createTableRows(invoice));
			contentLayoutData.setPageWidth((int)ContentLayoutDataConstants.PAGE.getTrimBox().getWidth());
			contentLayoutData.setPageHeight((int)ContentLayoutDataConstants.PAGE.getTrimBox().getHeight());
			contentLayoutData.setInvoiceNoLabel(ContentLayoutDataConstants.INVOICE_NO_LABEL);
			contentLayoutData.setInvoiceDateLabel(ContentLayoutDataConstants.INVOICE_DATE_LABEL);
			contentLayoutData.setInvoicePerformanceDateLabel(ContentLayoutDataConstants.INVOICE_PERFORMANCE_DATE_LABEL);
			contentLayoutData.setValueAddedTaxDisclaimerText(
					ContentLayoutDataConstants.VALUE_ADDED_TAX_DISCLAIMER_TEXT);
			contentLayoutData.setDocumentInformationCreator(ContentLayoutDataConstants.DOCUMENT_INFORMATION_CREATOR);
			contentLayoutData.getDocumentInformationCreator();
			contentLayoutData.setDocumentInformationKeywords(
					new String[] { ContentLayoutDataConstants.DOCUMENT_INFORMATION_KEYWORD_INVOICE, String.valueOf(invoice.getInvoiceNo()), contentLayoutData.getCustomerName() });
			contentLayoutData.setHasMainContentLayoutData(true);
			contentLayoutData.setSplitDelimiter("\\.");

			return contentLayoutData;
		} catch (RuntimeException e) {
			String message = "PdfUtilities: Error while creating ContentLayoutData";
			throw new PdfUtilitiesException(message, e);
		}
	}

	public static List<Map<String, String>> createTableRows(TutoringAppointmentEntity tutoringAppointment,
			InvoiceEntity invoice) {
		BigDecimal price = invoice.getPrice().getPrice().setScale(2, RoundingMode.HALF_UP);
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
	
	public static List<Map<String, String>> createTableRows(InvoiceEntity invoice) {
		BigDecimal price = invoice.getPrice().getPrice().setScale(2, RoundingMode.HALF_UP);
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
		return new File("img/logo.png");
	}

	public static File createCustomLogo() {
		return new File(ContentLayoutDataConstants.LOGO_FILE_PATH);
	}
	
	public static List<ContentLayoutData> createContentLayoutDatas(List<InvoiceEntity> invoices) {
		List<ContentLayoutData> contentLayoutDatas = new ArrayList<>();
		
		for(InvoiceEntity invoice : invoices) {
			contentLayoutDatas.add(createContentLayoutData2(invoice));
		}
		
		return contentLayoutDatas;
	}
}
