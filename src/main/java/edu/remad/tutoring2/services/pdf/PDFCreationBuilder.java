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
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationMultiplePagesBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationUtilities;
import edu.remad.tutoring2.services.pdf.exceptions.PDFCreationBuilderException;
import edu.remad.tutoring2.services.pdf.pagecontent.SinglePageContentLayouter;

/**
 * A pdf creator written with a builder pattern
 */
public class PDFCreationBuilder {

	/**
	 * empty in-memory PDF document
	 */
	private final PDDocument pdfDocument;

	/**
	 * PDF page to add to PDF document
	 */
	private final ArrayList<PDPage> pdfPages;

	/**
	 * pdf document information
	 */
	private PDDocumentInformation documentInformation;

	/**
	 * several contentLayoutDatas'
	 */
	private final List<ContentLayoutData> contentLayoutDataList;

	/**
	 * PDRectangle for example DIN A4, can be set or not.
	 */
	private PDRectangle paperFormat;

	/**
	 * PDFCreationBuilder constructor
	 */
	public PDFCreationBuilder() {
		pdfDocument = new PDDocument();
		pdfPages = new ArrayList<>();
		documentInformation = new PDDocumentInformation();
		contentLayoutDataList = new ArrayList<>();
	}

	/**
	 * set content layout data.
	 * 
	 * @param contentLayoutDatas a list of {@link ContentLayoutData} instances
	 */
	public PDFCreationBuilder contentLayoutData(List<ContentLayoutData> contentLayoutDatas) {
		this.contentLayoutDataList.addAll(contentLayoutDatas);

		return this;
	}

	/**
	 * Adds pages.
	 *
	 * @param pages PDF pages to add to document
	 * @return PDF creation builder
	 */
	public PDFCreationBuilder addPages(List<PDPage> pages) {
		this.pdfPages.addAll(pages);

		return this;
	}

	/**
	 * Sets an customized paper format.
	 * 
	 * @param paperFormat {@link PDRectangle}
	 * @return PDF creation builder
	 */
	public PDFCreationBuilder paperFormat(PDRectangle paperFormat) {
		this.paperFormat = paperFormat;

		return this;
	}

	private void createDocumentInformation() {
		if (contentLayoutDataList != null && contentLayoutDataList.size() == 1) {
			ContentLayoutData contentLayoutData = contentLayoutDataList.get(0);
			documentInformation = new DocumentInformationBuilder().setAuthor(contentLayoutData.getContactName())
					.setInvoiceNumber(Long.parseLong(contentLayoutData.getInvoiceNoWithoutPrefix()))
					.setCreator(contentLayoutData.getCreator()).setSubject(contentLayoutData.getSubject())
					.setCreationDate(DocumentInformationUtilities.extractCreationDate(contentLayoutData))
					.setKeywords(contentLayoutData.getDocumentInformationKeywords()).build();
		} else if (contentLayoutDataList != null && contentLayoutDataList.size() > 1) {
			DocumentInformationMultiplePagesBuilder builder = new DocumentInformationMultiplePagesBuilder();
			builder.contentLayoutDatas(contentLayoutDataList);
			documentInformation = builder.build();
		} else {
			throw new PDFCreationBuilderException("PDFCreationBuilderException: DocumentInformation was not created.");
		}
	}

	/**
	 * Builds PDF
	 *
	 * @return built PDF document
	 */
	public PDDocument build() throws IOException {
		if (contentLayoutDataList != null && !contentLayoutDataList.isEmpty()) {
			addEmptyPdfPages();
			createDocumentInformation();
			buildSinglePagePdfDocument();
			buildMultiplePagesPdfDocument();
			pdfDocument.setDocumentInformation(documentInformation);

			return this.pdfDocument;
		}

		throw new PDFCreationBuilderException("PDFCreationBuilderException: PDF Document was not build.");
	}

	private void addEmptyPdfPages() {
		if (pdfPages.isEmpty()) {

			// default is DIN A4
			PDRectangle rectangle = paperFormat != null ? paperFormat : PDRectangle.A4;

			for (ContentLayoutData contentLayoutData : this.contentLayoutDataList) {
				pdfPages.add(new PDPage(rectangle));
			}
		}
	}

	private void buildSinglePagePdfDocument() throws IOException {
		if (pdfPages.size() == 1 && contentLayoutDataList.size() == 1) {
			pdfDocument.addPage(pdfPages.get(0));
			buildSinglePagePdfDocument(pdfPages.get(0), contentLayoutDataList.get(0));
		}
	}

	private void buildSinglePagePdfDocument(PDPage pdfPage, ContentLayoutData contentLayoutData) throws IOException {
		try (PDPageContentStream pageContentStream = new PDPageContentStream(pdfDocument, pdfPage)) {
			SinglePageContentLayouter contentLayouter = new SinglePageContentLayouter(pdfDocument, pdfPage,
					contentLayoutData, pageContentStream);
			contentLayouter.build();
		}
	}

	private void buildMultiplePagesPdfDocument() throws IOException {
		if (contentLayoutDataList != null && contentLayoutDataList.size() > 1) {
			int pdfPagesSize = pdfPages.size();
			for (int index = 0; index < pdfPagesSize; index++) {
				PDPage page = pdfPages.get(index);
				pdfDocument.addPage(page);
				buildSinglePagePdfDocument(page, contentLayoutDataList.get(index));
			}
		}
	}

	public byte[] buildAsByteArray() throws IOException {
		// check build has already run, zero page means nothing is built.
		if (pdfDocument.getNumberOfPages() == 0) {
			build();
		}

		return writePdfToByteArray();
	}

	private byte[] writePdfToByteArray() {
		byte[] pdfFile = null;

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			pdfDocument.save(out);
			pdfFile = out.toByteArray();
		} catch (IOException e) {
			throw new PDFCreationBuilderException("PDFCreationBuilderException: Multi pages PDF not created as bytes.");
		}

		return pdfFile;
	}

	/**
	 * Runs creation of complex invoice
	 *
	 * @param args arguments from environment
	 * @throws IOException In case of creation of PDF fails.
	 */
	public static void main(String[] args) throws IOException {
		ContentLayoutData data1 = createPage1();
		ContentLayoutData data2 = createPage2();
		List<ContentLayoutData> contentLayoutDatas = new ArrayList<>();
		contentLayoutDatas.add(data1);
		contentLayoutDatas.add(data2);

		PDFCreationBuilder builder = new PDFCreationBuilder().contentLayoutData(contentLayoutDatas);
		PDDocument document = builder.build();
		document.save("C:\\Users\\remad\\multiple_page_invoice_generated.pdf");

		byte[] pdfFile = builder.buildAsByteArray();
		System.out.println(String.format("PDF-File size in bytes is %d", pdfFile.length));
	}

	public static ContentLayoutData createPage1() {
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

	public static ContentLayoutData createPage2() {
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