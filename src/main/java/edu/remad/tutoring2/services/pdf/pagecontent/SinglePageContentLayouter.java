package edu.remad.tutoring2.services.pdf.pagecontent;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.utilities.PageContentLayoutUtilities;

public class SinglePageContentLayouter {

	/**
	 * a blank DIN4 rectangled in-memory-PDF-Document
	 */
	private final PDDocument pdfDocument;
	
	/**
	 * PDF page
	 */
	private final PDPage pdfPage;

	/**
	 * pdf page content stream
	 */
	private final PDPageContentStream pageContentStream;

	/**
	 * content layout data
	 */
	private final ContentLayoutData contentLayoutData;

	/**
	 * page content text layouter
	 */
	private final PDFPageContentTextLayouter pdfPageContentTextLayouter;

	/**
	 * page content table layouter
	 */
	private PDFPageContentTableLayouter pageContentTableLayouter;

	/**
	 * PageContentLayouter Constructor
	 *
	 * @param pdfDocument In-Memory PDF-Document
	 * @param pdfPage     DIN A4 rectangled PDF page
	 * @throws IOException In case of the {@link PDPageContentStream} fails to
	 *                     initialize.
	 */
	public SinglePageContentLayouter(final PDDocument pdfDocument, final PDPage pdfPage,
			final ContentLayoutData contentLayoutData, final PDPageContentStream pageContentStream) throws IOException {
		this.pdfDocument = pdfDocument;
		this.pdfPage = pdfPage;
		this.contentLayoutData = contentLayoutData;
		this.pageContentStream = pageContentStream;
		this.pdfPageContentTextLayouter = new PDFPageContentTextLayouter(pdfDocument, pageContentStream);
	}

	/**
	 * Build the content and layouts
	 */
	public void build() throws IOException {
		generateLogo();
		generateCompanyNameOverLogo();
		generateContactDetail();
		generateCustomerContactDetails();
		generateBillingInformation();
		generateInvoiceTable();
		generateValueAddedTaxDisclaimer();
		generatePaymentMethods();
		generateAuthoSigned();
		generateBottomLine();
		generateBottomRectangle();
	}

	private void generateLogo() throws IOException {
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(contentLayoutData.getLogo().getPath());
		PDImageXObject headImage = PDImageXObject.createFromByteArray(pdfDocument, inputStream.readAllBytes(),
				contentLayoutData.getLogo().getPath());
		pageContentStream.drawImage(headImage, 25, contentLayoutData.getPageHeight() - 210, 181, 201);
	}

	private void generateCompanyNameOverLogo() throws IOException {
		pdfPageContentTextLayouter.addSingleLineText(contentLayoutData.getContactCompany(), 47,
				contentLayoutData.getPageHeight() - 48, contentLayoutData.getFont(),
				contentLayoutData.getCapitalFontSize(), contentLayoutData.getFontColor());
	}

	private void generateContactDetail() throws IOException {
		String[] companyContactDetails = PageContentLayoutUtilities.createCompanyContactDetails(contentLayoutData);

		PDFont font = contentLayoutData.getFont();
		pdfPageContentTextLayouter.addMultiLineText(companyContactDetails, 18,
				contentLayoutData.getPageWidth() - (int) (font.getStringWidth(
						Arrays.stream(companyContactDetails).max(Comparator.comparingInt(String::length)).get()) + 2200)
						/ 1000 * 15 - 10,
				contentLayoutData.getPageHeight() - 25, font, 15, contentLayoutData.getFontColor());
	}

	private void generateCustomerContactDetails() throws IOException {
		int pageHeight = contentLayoutData.getPageHeight();
		PDFont font = contentLayoutData.getFont();
		float fontSize = contentLayoutData.getTextFontSize();
		Color fontColor = contentLayoutData.getFontColor();

		pdfPageContentTextLayouter.addSingleLineText(contentLayoutData.getCustomerName(), 25, pageHeight - 250, font,
				fontSize, fontColor);
		pdfPageContentTextLayouter.addSingleLineText(contentLayoutData.getStreetHouseNumber(), 25, pageHeight - 274,
				font, fontSize, fontColor);
		pdfPageContentTextLayouter.addSingleLineText(contentLayoutData.getLocationZipCode(), 25, pageHeight - 294, font,
				fontSize, fontColor);
	}

	private void generateBillingInformation() throws IOException {
		PDFont font = contentLayoutData.getFont();
		int pageWidth = contentLayoutData.getPageWidth();
		int pageHeight = contentLayoutData.getPageHeight();
		Color fontColor = contentLayoutData.getFontColor();
		float fontSize = contentLayoutData.getTextFontSize();
		DateTimeFormatter dateTimeFormatter = contentLayoutData.getDateFormatter();
		DateTimeFormatter dateFormatter = contentLayoutData.getDateFormatter();
		String invoiceDateLabel = contentLayoutData.getInvoiceDateLabel() + ": ";
		String invoicePerformanceDateLabel = contentLayoutData.getInvoicePerformanceDateLabel() + ": ";

		float textWidth = pdfPageContentTextLayouter.getTextWidth(contentLayoutData.getInvoiceNo(), font, fontSize);
		pdfPageContentTextLayouter.addSingleLineText(contentLayoutData.getInvoiceNo(),
				(int) (pageWidth - 30 - textWidth), pageHeight - 250, font, fontSize, fontColor);

		String date = LocalDate.parse(contentLayoutData.getInvoiceCreationDate(), dateTimeFormatter)
				.format(dateFormatter);
		float dateTextWidth = pdfPageContentTextLayouter.getTextWidth(invoiceDateLabel + date, font, fontSize);
		pdfPageContentTextLayouter.addSingleLineText(invoiceDateLabel + date, (int) (pageWidth - 30 - dateTextWidth),
				pageHeight - 274, font, fontSize, fontColor);

		String tutoringDate = LocalDate.parse(contentLayoutData.getTutoringAppointmentDate(), dateTimeFormatter)
				.format(dateFormatter);
		float tutoringDateWidth = pdfPageContentTextLayouter.getTextWidth(invoicePerformanceDateLabel + tutoringDate,
				font, fontSize);
		pdfPageContentTextLayouter.addSingleLineText(invoicePerformanceDateLabel + tutoringDate,
				(int) (pageWidth - 30 - tutoringDateWidth), pageHeight - 298, font, fontSize, fontColor);
	}

	private void generateInvoiceTable() throws IOException {
		pageContentTableLayouter = new PDFPageContentTableLayouter(pdfDocument, pageContentStream, contentLayoutData);
		pageContentTableLayouter.layoutTable();
	}

	private void generateValueAddedTaxDisclaimer() throws IOException {
		pdfPageContentTextLayouter.addMultiLineText(contentLayoutData.getValueAddedTaxDisclaimerText(), 15, 25, 270,
				contentLayoutData.getItalicFont(), contentLayoutData.getPaymentMethodFontSize(),
				contentLayoutData.getPaymentMethodColor());
	}

	private void generatePaymentMethods() throws IOException {
		PDFont italicFont = contentLayoutData.getItalicFont();
		pdfPageContentTextLayouter.addMultiLineText(contentLayoutData.getPaymentMethods().toArray(String[]::new), 15,
				25, 250, italicFont, contentLayoutData.getPaymentMethodFontSize(),
				contentLayoutData.getPaymentMethodColor());
	}

	private void generateAuthoSigned() throws IOException {
		int pageWidth = contentLayoutData.getPageWidth();

		drawAuthoSignedGraphics(pageWidth);
		writeAuthoSignedText(pageWidth);
	}

	private void writeAuthoSignedText(int pageWidth) throws IOException {
		String authoSign = contentLayoutData.getAuthoSign();
		float fontSize = contentLayoutData.getTextFontSize();
		float authoSignWidth = pdfPageContentTextLayouter.getTextWidth(authoSign, contentLayoutData.getItalicFont(),
				fontSize);
		int xPosition = pageWidth - 250 + pageWidth - 25;
		pdfPageContentTextLayouter.addSingleLineText(authoSign, (int) (xPosition - authoSignWidth) / 2, 125,
				contentLayoutData.getFont(), fontSize, contentLayoutData.getFontColor());
	}

	private void drawAuthoSignedGraphics(int pageWidth) throws IOException {
		pageContentStream.setStrokingColor(contentLayoutData.getAuthoSignColor());
		pageContentStream.setLineWidth(2);
		pageContentStream.moveTo(pageWidth - 250, 150);
		pageContentStream.lineTo(pageWidth - 25, 150);
		pageContentStream.stroke();
	}

	private void generateBottomLine() throws IOException {
		String bottomLine = contentLayoutData.getBottomLine();
		float bottomLineWidth = pdfPageContentTextLayouter.getTextWidth(bottomLine, contentLayoutData.getFont(),
				contentLayoutData.getBottomLineWidth());
		pdfPageContentTextLayouter.addSingleLineText(bottomLine,
				(int) (contentLayoutData.getPageWidth() - bottomLineWidth) / 2, 50, contentLayoutData.getItalicFont(),
				contentLayoutData.getBottomLineFontSize(), contentLayoutData.getBottomLineFontColor());
	}

	private void generateBottomRectangle() throws IOException {
		pageContentStream.setNonStrokingColor(contentLayoutData.getBottomRectColor());
		pageContentStream.addRect((float) contentLayoutData.getBottomRect().getX(),
				(float) contentLayoutData.getBottomRect().getY(), contentLayoutData.getPageWidth(),
				(float) contentLayoutData.getBottomRect().getHeight());
		pageContentStream.fill();
	}
}