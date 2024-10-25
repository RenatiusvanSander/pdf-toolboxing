package edu.remad.tutoring2.services.pdf.pdffilemergerprototype;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import edu.remad.tutoring2.services.pdf.PDFComplexInvoiceBuilder;
import edu.remad.tutoring2.services.pdf.PDFCreationBuilder;

public class PdfFileMergerExample {

	public static void main(String[] args) {
		PDFComplexInvoiceBuilder builder1 = new PDFComplexInvoiceBuilder();
		PDFComplexInvoiceBuilder builder2 = new PDFComplexInvoiceBuilder();
		
		PDFCreationBuilder.createPage1();
		PDFCreationBuilder.createPage2();
		
		
		PDFMergerUtility PDFmerger = new PDFMergerUtility();
	}
}
