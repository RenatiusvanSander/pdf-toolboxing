package edu.remad.tutoring2.services.pdf.pagecontent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationMultiplePagesBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationUtilities;
import edu.remad.tutoring2.services.pdf.exceptions.PDFCreationBuilderException;

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
					.setInvoiceNumber(Long.parseLong(contentLayoutData.getInvoiceNo()))
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
}