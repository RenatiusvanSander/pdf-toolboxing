package edu.remad.tutoring2.services.pdf.documentinformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

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
			keywords[index] = contentLayoutData.getDocumentInformationKeywordByIndex(index);
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
	 * Builds the document information
	 *
	 * @return PDF document information, {@link PDDocumentInformation}
	 */
	public PDDocumentInformation build() {
		PDDocumentInformation documentInformation = new PDDocumentInformation();
		documentInformation.setAuthor(this.authors != null && this.authors.length > 1
				? Arrays.asList(this.authors).stream().collect(Collectors.joining(" "))
				: this.authors[0]);
		String invoiceTitle = this.invoiceNumbers != null && this.invoiceNumbers.length > 1
				? Arrays.asList(this.invoiceNumbers).stream().map(String::valueOf).collect(Collectors.joining(" "))
				: String.valueOf(this.invoiceNumbers[0]);
		documentInformation.setTitle(INVOICE_TITLE_PREFIX + invoiceTitle);
		documentInformation.setCreator(this.creators != null && this.creators.length > 1
				? Arrays.asList(this.creators).stream().collect(Collectors.joining(" "))
				: this.creators[0]);
		documentInformation.setSubject(this.subjects != null && this.subjects.length > 1
				? Arrays.asList(this.subjects).stream().collect(Collectors.joining(" "))
				: this.subjects[0]);

		Calendar convertedCreationDate = new GregorianCalendar();
		convertedCreationDate.setTime(this.creationDate != null ? this.creationDate : new Date());
		documentInformation.setCreationDate(convertedCreationDate);
		documentInformation.setModificationDate(convertedCreationDate);
		documentInformation.setKeywords(DocumentInformationUtilities.joinKeywordtoString(this.keywords));

		return documentInformation;
	}
}
