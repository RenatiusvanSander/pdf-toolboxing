package edu.remad.tutoring2.services.pdf;

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

import edu.remad.tutoring2.models.InvoiceEntity;
import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationUtilities;
import edu.remad.tutoring2.services.pdf.exceptions.PDFComplexInvoiceBuilderException;
import edu.remad.tutoring2.services.pdf.pagecontent.SinglePageContentLayouter;
import edu.remad.tutoring2.services.pdf.utilities.PdfUtilities;

public class PDFComplexInvoiceBuilder {

	private InvoiceEntity invoice;

	private PDDocument pdfDoucment;

	/**
	 * Runs creation of complex invoice
	 *
	 * @param args arguments from environment
	 * @throws IOException In case of creation of PDF fails.
	 */
	public static void main(String[] args) throws IOException {
		try (PDDocument document = new PDDocument()) {
			PDFCreationBuilder pdfBuilder = new PDFCreationBuilder();
			PDPage firstPage = new PDPage(PDRectangle.A4);
			document.addPage(firstPage);

			ContentLayoutData contentLayout = new ContentLayoutData();
			contentLayout.setCustomerName("Sharon", "Tetteh");
			File logo = new File(ContentLayoutDataConstants.LOGO_FILE_PATH);
			contentLayout.setLogo(logo);
			contentLayout.setFont(PDType1Font.HELVETICA);
			contentLayout.setItalicFont(PDType1Font.HELVETICA_OBLIQUE);
			contentLayout.setFontColor(Color.BLACK);
			contentLayout.setStreetHouseNumber("Karl-Arnold-Ring", "26");
			contentLayout.setLocationZipCode("21109", "Hamburg");
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

			DocumentInformationBuilder documentInformationBuilder = populateDocumentInformationBuilder(contentLayout);
			document.setDocumentInformation(documentInformationBuilder.build());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				document.save("C:\\Users\\remad\\invoice_generated.pdf");
				document.save(out);
			} catch (IOException e) {
				throw new PDFComplexInvoiceBuilderException("Demo PDF not saved!", e);
			}

			byte[] invoice = out.toByteArray();
			out.close();
			System.out.println("Document created.");
		} catch (IOException e) {
			throw new PDFComplexInvoiceBuilderException("Demo Document not saved as Byte-Array.", e);
		}
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

	public PDFComplexInvoiceBuilder invoice(InvoiceEntity invoice) {
		this.invoice = invoice;

		return this;
	}

	public byte[] build() {
		return buildDocument(PdfUtilities.createContentLayoutData2(invoice));
	}

	private byte[] buildDocument(ContentLayoutData contentLayout) {
		try (PDDocument document = pdfDoucment != null ? pdfDoucment : new PDDocument();
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			if (pdfDoucment == null) {
				PDPage firstPage = ContentLayoutDataConstants.PAGE;
				document.addPage(firstPage);
				buildPageContent(contentLayout, document, firstPage);
			}

			document.setDocumentInformation(populateDocumentInformationBuilder(contentLayout).build());

			try {
				document.save(out);
			} catch (IOException e) {
				throw new RuntimeException();
			}

			return out.toByteArray();
		} catch (PDFComplexInvoiceBuilderException | IOException e) {
			throw new PDFComplexInvoiceBuilderException("Invoice was not created.", e);
		}
	}

	private void buildPageContent(ContentLayoutData contentLayout, PDDocument document, PDPage firstPage) {
		try (PDPageContentStream contentStream = new PDPageContentStream(document, firstPage)) {
			new SinglePageContentLayouter(document, firstPage, contentLayout, contentStream).build();
		} catch (Exception e) {
			throw new PDFComplexInvoiceBuilderException("Invoice page content was not rendered", e);
		}
	}

	public PDPage buildPage() {
		try (PDDocument document = new PDDocument()) {
			ContentLayoutData contentLayout = PdfUtilities.createContentLayoutData2(invoice);
			PDPage firstPage = ContentLayoutDataConstants.PAGE;
			document.addPage(firstPage);
			buildPageContent(contentLayout, document, firstPage);
			this.pdfDoucment = document;

			return firstPage;
		} catch (IOException e) {
			throw new PDFComplexInvoiceBuilderException("PDF Page was not created.", e);
		}
	}
}
