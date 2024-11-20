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
import java.util.Properties;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import edu.remad.mustangxrechnungproducer.XRechnungXmlProducer;
import edu.remad.tutoring2.models.InvoiceEntity;
import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationMultiplePagesBuilder;
import edu.remad.tutoring2.services.pdf.exceptions.PDFCreationBuilderException;
import edu.remad.tutoring2.services.pdf.pagecontent.SinglePageContentLayouter;
import edu.remad.tutoring2.services.pdf.pdf3a.PDF3AFileAttachmentBuilder;
import edu.remad.tutoring2.services.pdf.utilities.DocumentInformationUtilities;

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

	/** produce a XRechnung xml-file and to append */
	private boolean isXRechnung;

	private Properties customProperties;

	/** secure PDF with a password */
	private boolean isSecuredWithPassord;

	private InvoiceEntity invoiceData;

	/**
	 * PDFCreationBuilder constructor
	 */
	public PDFCreationBuilder() {
		pdfDocument = new PDDocument();
		pdfPages = new ArrayList<>();
		documentInformation = new PDDocumentInformation();
		contentLayoutDataList = new ArrayList<>();
		isXRechnung = false; // by default false
		isSecuredWithPassord = false; // by default false
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

	/**
	 * Enables an XRechnung xml-file is attached to created PDF.
	 * 
	 * @param customProperties customized Properties for XRechnung
	 * @param invoiceData      invoice object to populate XRechnung.
	 * @return {@link PDFCreationBuilder}
	 */
	public PDFCreationBuilder XRechnung(Properties customProperties, InvoiceEntity invoiceData) {
		this.isXRechnung = true;

		if (customProperties == null || invoiceData == null) {
			throw new PDFCreationBuilderException(
					"PDFCreationBuilderException: customProperties or invoiceData cannot be null.");
		}

		this.customProperties = customProperties;
		this.invoiceData = invoiceData;

		return this;
	}

	/**
	 * Enables a password protected PDF. Password is user password.
	 * 
	 * @param invoiceData data of invoice. May be null, when
	 *                    {@link PDFCreationBuilder#XRechnung(Properties, InvoiceEntity)}
	 *                    already used.
	 * 
	 * @return {@link PDFCreationBuilder}
	 */
	public PDFCreationBuilder secureWithPassord(InvoiceEntity invoiceData) {
		this.isSecuredWithPassord = true;
		if (this.invoiceData == null && invoiceData != null) {
			this.invoiceData = invoiceData;
			return this;
		} else if (this.invoiceData != null && (invoiceData == null || invoiceData != null)) {
			return this;
		} else {
			throw new PDFCreationBuilderException(
					"PDFCreationBuilderException: secure a PDF with password needs invoiceData.");
		}
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
			produceAndAttachXRechnungFile(contentLayoutData);
			if (pdfPages.size() == 1) {
				protectDocumentWithPassword();
			}
		}
	}

	private void protectDocumentWithPassword() {
		if (isSecuredWithPassord && invoiceData != null) {
			try {
				AccessPermission accessPermission = new AccessPermission();
				accessPermission.canPrint();
				accessPermission.setCanModify(false);
				accessPermission.setCanModifyAnnotations(false);
				accessPermission.setCanFillInForm(false);
				accessPermission.setCanExtractForAccessibility(false);
				accessPermission.setCanExtractContent(false);
				accessPermission.setCanAssembleDocument(false);
				accessPermission.setCanPrintDegraded(false);
				String password = invoiceData.getInvoiceUser().getPassword();
				StandardProtectionPolicy standardProtectionPolicy = new StandardProtectionPolicy(password, password,
						accessPermission);
				standardProtectionPolicy.setEncryptionKeyLength(256);

				pdfDocument.protect(standardProtectionPolicy);
			} catch (IOException e) {
				throw new PDFCreationBuilderException(
						"PDFCreationBuilderException: Cannot protect document with password. Reason is "
								+ e.getMessage(),
						e);
			}
		}
	}

	private void buildMultiplePagesPdfDocument() throws IOException {
		if (contentLayoutDataList != null && contentLayoutDataList.size() > 1) {
			int pdfPagesSize = pdfPages.size();
			for (int index = 0; index < pdfPagesSize; index++) {
				PDPage page = pdfPages.get(index);
				pdfDocument.addPage(page);
				ContentLayoutData contentLayoutData = contentLayoutDataList.get(index);
				buildSinglePagePdfDocument(page, contentLayoutData);
				produceAndAttachXRechnungFile(contentLayoutData);
			}

			if (pdfPagesSize > 1) {
				protectDocumentWithPassword();
			}
		}
	}

	private void produceAndAttachXRechnungFile(ContentLayoutData contentLayoutData) {
		if (isXRechnung) {
			byte[] xRechnungFile = new XRechnungXmlProducer(invoiceData, customProperties).produceXmlByteArray();
			attachXRechnungXmlFile(xRechnungFile, contentLayoutData);
		}
	}

	private void attachXRechnungXmlFile(byte[] xRechnungFile, ContentLayoutData contentLayoutData) {
		if (xRechnungFile != null && xRechnungFile.length > 0) {
			PDF3AFileAttachmentBuilder builder = new PDF3AFileAttachmentBuilder(pdfDocument)
					.contentLayoutData(contentLayoutData).xRechnungByteArray(xRechnungFile);
			builder.build();
		}
	}

	/**
	 * Builds PDF as byte array
	 * 
	 * @return byte array
	 * @throws IOException
	 */
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