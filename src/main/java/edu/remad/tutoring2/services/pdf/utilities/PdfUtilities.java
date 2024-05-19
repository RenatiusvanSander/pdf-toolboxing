package edu.remad.tutoring2.services.pdf.utilities;

import java.awt.Color;
import java.io.File;
import java.time.format.DateTimeFormatter;

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
