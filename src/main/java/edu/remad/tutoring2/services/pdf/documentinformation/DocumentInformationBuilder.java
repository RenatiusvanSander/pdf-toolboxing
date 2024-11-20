package edu.remad.tutoring2.services.pdf.documentinformation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import edu.remad.tutoring2.services.pdf.utilities.DocumentInformationUtilities;

/**
 * builder for {@link PDDocumentInformation}
 */
public class DocumentInformationBuilder {

  /**
   * prefix of title
   */
  public static final String INVOICE_TITLE_PREFIX = "Invoice ";

  /**
   * constant for empty {@link String}
   */
  public static final String EMPTY_STRING = "";

  /**
   * author
   */
  private String author;

  /**
   * invoice number
   */
  private Long invoiceNumber;

  /**
   * creator
   */
  private String creator;

  /**
   * subject
   */
  private String subject;

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
  public DocumentInformationBuilder() {
  }

  /**
   * Gets author
   *
   * @return the author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Sets author
   *
   * @param author string-encoded author to set
   * @return document information builder
   */
  public DocumentInformationBuilder setAuthor(String author) {
    this.author = author;

    return this;
  }

  /**
   * Gets invoice number.
   *
   * @return invoice no
   */
  public Long getInvoiceNumber() {
    return invoiceNumber;
  }

  /**
   * Sets invoice number
   *
   * @param invoiceNumber numeric invoice number to set
   * @return document information builder
   */
  public DocumentInformationBuilder setInvoiceNumber(Long invoiceNumber) {
    this.invoiceNumber = invoiceNumber;

    return this;
  }

  /**
   * Gets creator
   *
   * @return creator
   */
  public String getCreator() {
    return creator;
  }

  /**
   * Sets creator
   *
   * @param creator string-encoded creator
   * @return document information builder
   */
  public DocumentInformationBuilder setCreator(String creator) {
    this.creator = creator;

    return this;
  }

  /**
   * Gets subject
   *
   * @return subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Sets subject
   *
   * @param subject string-encoded subject to set
   * @return document information builder
   */
  public DocumentInformationBuilder setSubject(String subject) {
    this.subject = subject;

    return this;
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
   * Sets creation date
   *
   * @param creationDate creation date to set
   * @return document information builder
   */
  public DocumentInformationBuilder setCreationDate(Date creationDate) {
    this.creationDate = creationDate;

    return this;
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
  public DocumentInformationBuilder setKeywords(String[] keywords) {
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
    documentInformation.setAuthor(
        this.author != null && this.author.length() > 2 ? this.author : EMPTY_STRING);
    String invoiceTitle = this.invoiceNumber != null && this.invoiceNumber > 0
        ? this.invoiceNumber.toString() : EMPTY_STRING;
    documentInformation.setTitle(INVOICE_TITLE_PREFIX + invoiceTitle);
    documentInformation.setCreator(
        this.creator != null && creator.length() > 2 ? this.creator : EMPTY_STRING);
    documentInformation.setSubject(
        this.subject != null && this.subject.length() > 2 ? this.subject : EMPTY_STRING);

    Calendar convertedCreationDate = new GregorianCalendar();
    convertedCreationDate.setTime(this.creationDate != null ? this.creationDate : new Date());
    documentInformation.setCreationDate(convertedCreationDate);
    documentInformation.setModificationDate(convertedCreationDate);
    documentInformation.setKeywords(DocumentInformationUtilities.joinKeywordtoString(this.keywords));

    return documentInformation;
  }
}
