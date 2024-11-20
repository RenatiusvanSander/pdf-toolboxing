package edu.remad.tutoring2.services.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import edu.remad.tutoring2.models.InvoiceEntity;
import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;
import edu.remad.tutoring2.services.pdf.exceptions.PDFComplexInvoiceBuilderException;
import edu.remad.tutoring2.services.pdf.pagecontent.SinglePageContentLayouter;
import edu.remad.tutoring2.services.pdf.utilities.PdfUtilities;

public class PDFComplexInvoiceBuilder {

	private InvoiceEntity invoice;

	private PDDocument pdfDoucment;

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

			document.setDocumentInformation(PdfUtilities.populateDocumentInformationBuilder(contentLayout).build());
			document.save(out);

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
