package edu.remad.tutoring2.services.pdf.documentinformation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;

public class DocumentInformationMultiplePagesBuilder {

	private List<ContentLayoutData> contentLayoutDatas = new ArrayList<>();

	public void contentLayoutDatas(List<ContentLayoutData> contentLayoutDatas) {
		this.contentLayoutDatas = contentLayoutDatas;

		if (!contentLayoutDatas.isEmpty()) {
			int size = contentLayoutDatas.size();
			authors = new String[size];
			invoiceNumbers = new Long[size];
			creators = new String[size];
			subjects = new String[size];
			keywords = new String[size];
		}

		int index = 0;
		for (ContentLayoutData contentLayoutData : contentLayoutDatas) {
			authors[index] = contentLayoutData.getContactName();
			invoiceNumbers[index] = Long.valueOf(contentLayoutData.getInvoiceNo());
			creators[index] = contentLayoutData.getCreator();
			subjects[index] = contentLayoutData.getSubject();
			keywords[index] = contentLayoutData.getDocumentInformationKeywords();
			index++;
		}
	}

	/**
	 * prefix of title
	 */
	public static final String INVOICE_TITLE_PREFIX = "Invoice ";

	/**
	 * constant for empty {@link String}
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * authors
	 */
	private String[] authors;

	/**
	 * invoice number
	 */
	private Long[] invoiceNumbers;

	/**
	 * creators
	 */
	private String[] creators;

	/**
	 * subjects
	 */
	private String[] subjects;

	/**
	 * creation data, also modification date
	 */
	private Date creationDate;

	/**
	 * keywords
	 */
	private String[] keywords;

	/**
	 * DocumentInformationBuilder Constructor
	 */
	public DocumentInformationMultiplePagesBuilder() {
	}

	/**
	 * Gets author
	 *
	 * @return the authors
	 */
	public String[] getAuthors() {
		return authors;
	}

	/**
	 * Gets invoice numbers.
	 *
	 * @return invoice numbers
	 */
	public Long[] getInvoiceNumber() {
		return invoiceNumbers;
	}

	/**
	 * Gets creators
	 *
	 * @return creators
	 */
	public String[] getCreator() {
		return creators;
	}

	/**
	 * Gets subjects
	 *
	 * @return subjects
	 */
	public String[] getSubject() {
		return subjects;
	}

	/**
	 * Gets creation date
	 *
	 * @return creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Gets keywords
	 *
	 * @return string-encoded keywords
	 */
	public String[] getKeywords() {
		return keywords;
	}

	/**
	 * Sets keywords
	 *
	 * @param keywords string-encoded keywords to set
	 * @return document information builder
	 */
	public DocumentInformationMultiplePagesBuilder setKeywords(String[] keywords) {
		this.keywords = keywords;

		return this;
	}

	/**
	 * Builds the document information
	 *
	 * @return PDF document information, {@link PDDocumentInformation}
	 */
	public PDDocumentInformation build() {
		PDDocumentInformation documentInformation = new PDDocumentInformation();
		documentInformation.setAuthor(this.authors != null && this.authors.length() > 2 ? this.authors : EMPTY_STRING);
		String invoiceTitle = this.invoiceNumbers != null && this.invoiceNumbers > 0 ? this.invoiceNumbers.toString()
				: EMPTY_STRING;
		documentInformation.setTitle(INVOICE_TITLE_PREFIX + invoiceTitle);
		documentInformation.setCreator(this.creators != null && creators.length() > 2 ? this.creators : EMPTY_STRING);
		documentInformation
				.setSubject(this.subjects != null && this.subjects.length() > 2 ? this.subjects : EMPTY_STRING);

		Calendar convertedCreationDate = new GregorianCalendar();
		convertedCreationDate.setTime(this.creationDate != null ? this.creationDate : new Date());
		documentInformation.setCreationDate(convertedCreationDate);
		documentInformation.setModificationDate(convertedCreationDate);
		documentInformation.setKeywords(DocumentInformationUtilities.joinKeywordtoString(this.keywords));

		return documentInformation;
	}
}