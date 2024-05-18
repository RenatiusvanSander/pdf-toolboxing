package edu.remad.tutoring2.services.pdf.utilities;

import java.io.File;

import edu.remad.tutoring2.models.InvoiceEntity;
import edu.remad.tutoring2.models.TutoringAppointmentEntity;
import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;

public class PdfUtilities {
	
	public static void createContentLayoutData(TutoringAppointmentEntity tutoringAppointment, InvoiceEntity invoice) {
		ContentLayoutData contentLayoutData = new ContentLayoutData();
		contentLayoutData.setLogo(createLogo());
		contentLayoutData.setCustomerName(invoice.getInvoiceUser().getFirstName(), invoice.getInvoiceUser().getLastName());
	}
	
	public static File createLogo() {
		return new File(ContentLayoutDataConstants.LOGO_FILE_PATH);
	}
	
	public static File createCustomLogo() {
		return new File("src/main/resources/img/logo.png");
	}
}
