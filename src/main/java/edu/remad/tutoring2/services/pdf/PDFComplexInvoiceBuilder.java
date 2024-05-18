package edu.remad.tutoring2.services.pdf;

import java.awt.Color;
import java.awt.Rectangle;
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

import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationUtilities;
import edu.remad.tutoring2.services.pdf.pagecontent.PDFCreationBuilder;
import edu.remad.tutoring2.services.pdf.pagecontent.SinglePageContentLayouter;

public class PDFComplexInvoiceBuilder {

	/**
	   * Runs creation of complex invoice
	   *
	   * @param args arguments from environment
	   * @throws IOException In case of creation of PDF fails.
	   */
	  public static void main(String[] args) throws IOException {
	    PDFCreationBuilder pdfBuilder = new PDFCreationBuilder();

	    PDDocument document = new PDDocument();
	    PDPage firstPage = new PDPage(PDRectangle.A4);
	    document.addPage(firstPage);

	    ContentLayoutData contentLayout = new ContentLayoutData();
	    contentLayout.setCustomerName("Sharon", "Tetteh");
	    File logo = new File("src/main/resources/img/logo.png");
	    contentLayout.setLogo(logo);
	    contentLayout.setFont(PDType1Font.HELVETICA);
	    contentLayout.setItalicFont(PDType1Font.HELVETICA_OBLIQUE);
	    contentLayout.setFontColor(Color.BLACK);
	    contentLayout.setStreetHouseNumber("Karl-Arnold-Ring","26");
	    contentLayout.setLocationZipCode("Hamburg","21109");
	    contentLayout.setContactCompany("Remy Meier Freelance Nachhilfe");
	    contentLayout.setContactName("Remy Meier");
	    contentLayout.setContactStreetHouseNo("Volksdorfer Grenzweg 40A");
	    contentLayout.setContactZipAndLocation("22359 Hamburg");
	    contentLayout.setContactMobile("+49 176 61 36 22 53");
	    contentLayout.setContactEmail("remad@web.de");
	    contentLayout.setInvoiceNo("144");
	    contentLayout.setDayFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    contentLayout.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
	    contentLayout.setTableHeaderColor(new Color(240, 93, 11));
	    contentLayout.setTableBodyColor(new Color(219, 218, 198));
	    List<String> paymentMethods = List.of("Paypal: remad@web.de","Überweisung: DE62 1203 0000 1071 0649 66 / BYLADEM1001","Bargeld","Kleinanzeigen.de-Methoden");
	    contentLayout.setPaymentMethods(paymentMethods);
	    contentLayout.setPaymentMethodColor(new Color(122, 122, 122));
	    contentLayout.setTutoringAppointmentDate("17/05/2024");
	    contentLayout.setInvoiceCreationDate("17/05/2024");
	    contentLayout.setCapitalFontSize(12F);
	    contentLayout.setTextFontSize(16F);
	    contentLayout.setPaymentMethodFontSize(10F);
	    contentLayout.setbottomLine("Lernen ist das halbe Leben!");
	    contentLayout.setBottomLineFontSize(20);
	    contentLayout.setBottomLineFontColor(Color.DARK_GRAY);
	    contentLayout.setBottomLineWidth(20F);
	    contentLayout.setBottomRectColor(new Color(255, 91, 0));
	    contentLayout.setBottomRect(new Rectangle(0, 0, 0, 30));
	    contentLayout.setAuthoSign("Unterschrift");
	    contentLayout.setAuthoSignColor(Color.BLACK);
	    contentLayout.setTableCellWidths(new int[]{80, 230, 70, 80, 80});
	    contentLayout.setTableCellHeight(30);
	    List<String> tableHeaders = List.of("Position", "Beschreibung", "Preis", "Menge", "Gesamt");
	    contentLayout.setTableHeaders(tableHeaders);
	    List<Map<String, String>> tableRows = new ArrayList<>();
	    Map<String,String> row1 = new LinkedHashMap<>();
	    row1.put("Position", "1");
	    row1.put("Beschreibung", "Nachhilfe Mathe");
	    row1.put("Preis", "13");
	    row1.put("Menge", "2");
	    row1.put("Gesamt", "26 EUR");
	    tableRows.add(row1);
	    contentLayout.setTableRows(tableRows);
	    contentLayout.setPageWidth((int) firstPage.getTrimBox().getWidth());
	    contentLayout.setPageHeight((int) firstPage.getTrimBox().getHeight());
	    contentLayout.setInvoiceNoLabel("Rechnungsnummer");
	    contentLayout.setInvoiceDateLabel("Rechnungsdatum");
	    contentLayout.setInvoicePerformanceDateLabel("Leistungsdatum");
	    contentLayout.setValueAddedTaxDisclaimerText(new String[]{"Gemäß § 19 UStG wird keine Umsatzsteuer berechnet."});
	    contentLayout.setDocumentInformationCreator("Tutoring App");
	    contentLayout.getDocumentInformationCreator();
	    contentLayout.setDocumentInformationKeywords(new String[]{"Rechnung","142", contentLayout.getCustomerName()});
	    contentLayout.getDocumentInformationKeywords();
	    contentLayout.setHasMainContentLayoutData(true);

	    PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
	    SinglePageContentLayouter pageContenLayouter = new SinglePageContentLayouter(document, firstPage, contentLayout, contentStream);
	    contentStream.close();

	    DocumentInformationBuilder documentInformationBuilder = populateDocumentInformationBuilder(contentLayout);
	    document.setDocumentInformation(documentInformationBuilder.build());
	    document.save("C:\\Users\\Remy Meier\\2023-09-eclipse-workspace\\pdf-toolboxing\\invoice_generated.pdf");
	    document.close();
	    System.out.println("Document created.");
	  }

	  private static DocumentInformationBuilder populateDocumentInformationBuilder(ContentLayoutData contentLayout) {
	    DocumentInformationBuilder builder = new DocumentInformationBuilder();
	    builder.setAuthor(contentLayout.getContactName());
	    builder.setInvoiceNumber(Long.getLong(contentLayout.getInvoiceNo()));
	    builder.setCreator(contentLayout.getCreator());
	    builder.setSubject(contentLayout.getInvoiceNoLabel() + " " + contentLayout.getInvoiceNo());
	    builder.setCreationDate(DocumentInformationUtilities.extractCreationDate(contentLayout));
	    builder.setKeywords(contentLayout.getDocumentInformationKeywords());

	    return builder;
	  }
}
