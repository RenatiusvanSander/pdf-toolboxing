package edu.remad.tutoring2.services.pdf.pagecontent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationUtilities;

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
   * PDFCreationBuilder constructor
   */
  public PDFCreationBuilder() {
    pdfDocument = new PDDocument();
    pdfPages = new ArrayList<>();
    documentInformation = new PDDocumentInformation();
    contentLayoutDataList = new ArrayList<>();
  }

  /**
   * Adds page
   *
   * @param page PDF page to add to pdf document
   * @return PDF creation builder
   */
  public PDFCreationBuilder addPage(PDPage page) {
    this.pdfPages.add(page);

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
   * Sets document information
   *
   * @param contentLayoutData@return PDF creation builder
   */
  public PDFCreationBuilder setDocumentInformation(ContentLayoutData contentLayoutData) {
    this.documentInformation = new DocumentInformationBuilder()
        .setAuthor(contentLayoutData.getContactName())
        .setInvoiceNumber(Long.parseLong(contentLayoutData.getInvoiceNo()))
        .setCreator(contentLayoutData.getCreator())
        .setSubject(contentLayoutData.getSubject())
        .setCreationDate(DocumentInformationUtilities.extractCreationDate(contentLayoutData))
        .setKeywords(contentLayoutData.getDocumentInformationKeywords())
        .build();

    return this;
  }

  /**
   * Builds PDF
   *
   * @return built PDF document
   */
  public PDDocument build() throws IOException {
    buildSinglePagePdfDocument();

      for (PDPage page : this.pdfPages) {
        this.pdfDocument.addPage(page);
      }

    this.pdfDocument.setDocumentInformation(this.documentInformation);

    return this.pdfDocument;
  }

  private void buildSinglePagePdfDocument() throws IOException {
    if (pdfPages.size() == 1 && contentLayoutDataList.size() == 1) {
      pdfDocument.addPage(pdfPages.get(0));
      buildSinglePagePdfDocument(pdfPages.get(0), contentLayoutDataList.get(0));
    }
  }

  private void buildSinglePagePdfDocument(PDPage pdfPage, ContentLayoutData contentLayoutData) throws IOException {
    PDPageContentStream pageContentStream = new PDPageContentStream(pdfDocument, pdfPages.get(0));
    SinglePageContentLayouter contentLayouter = new SinglePageContentLayouter(pdfDocument, pdfPages.get(0),
            contentLayoutDataList.get(0), pageContentStream);
    pageContentStream.close();
  }

  private void buildMultiplePagesPdfDocument() throws IOException {
    int pdfPagesSize = pdfPages.size();
    for (int index = 0; index < pdfPagesSize; index++) {
      PDPage page = pdfPages.get(index);
      pdfDocument.addPage(page);
      ContentLayoutData contentLayoutData = contentLayoutDataList.get(index);
      buildSinglePagePdfDocument(page, contentLayoutData);
    }
  }
}