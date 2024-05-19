package edu.remad.tutoring2.services.pdf.utilities;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.util.List;

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
	
	private PdfUtilities() {}

	public static ContentLayoutData createContentLayoutData(TutoringAppointmentEntity tutoringAppointment, InvoiceEntity invoice) {
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
			contentLayoutData.setTutoringAppointmentDate(tutoringAppointment.getTutoringAppointmentDate().format(TimeAppConstants.GERMAN_DATE_FORMATTER));
			contentLayoutData.setInvoiceCreationDate(invoice.getInvoiceCreationDate().format(TimeAppConstants.GERMAN_DATE_FORMATTER));
			contentLayoutData.setCapitalFontSize(ContentLayoutDataConstants.CAPITAL_FONT_SIZE);
			contentLayoutData.setTextFontSize(ContentLayoutDataConstants.TEXT_FONT_SIZE);
			contentLayoutData.setPaymentMethodFontSize(ContentLayoutDataConstants.PAYMENT_METHOD_FONT_SIZE);
			contentLayoutData.setbottomLine(ContentLayoutDataConstants.BOTTOM_LINE);
			contentLayoutData.setBottomLineFontSize(ContentLayoutDataConstants.BOTTOM_LINE_FONT_SIZE);
			contentLayoutData.setBottomLineFontColor(ContentLayoutDataConstants.BOTTOM_LINE_FONT_COLOR);
			contentLayoutData.setBottomLineWidth(ContentLayoutDataConstants.BOTTOM_LINE_WIDTH);
			contentLayoutData.setBottomRectColor(ContentLayoutDataConstants.BOTTOM_RECT_COLOR);
			contentLayout.setBottomRect(ContentLayoutDataConstants.BOTTOM_RECT);
			contentLayout.setAuthoSign("Unterschrift");
			contentLayout.setAuthoSignColor(Color.BLACK);
			contentLayout.setTableCellWidths(new int[] { 80, 230, 70, 80, 80 });
			
			return contentLayoutData;
		} catch (RuntimeException e) {
			String message = String.format("%s: Error while creating %s", "PdfUtilities", "ContentLayoutData");
			throw new PdfUtilitiesException(message, e);
		}
	}

	public static File createLogo() {
		return new File(ContentLayoutDataConstants.LOGO_FILE_PATH);
	}

	public static File createCustomLogo() {
		return new File("src/main/resources/img/logo.png");
	}
}
