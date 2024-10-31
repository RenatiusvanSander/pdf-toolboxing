package edu.remad.tutoring2.services.pdf.pdffilemergerprototype;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.PDFMergerUtility.DocumentMergeMode;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.common.PDMetadata;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.PDFCreationBuilder;
import edu.remad.tutoring2.services.pdf.constants.MemoryUsageSettingConstants;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationMultiplePagesBuilder;

public class PDFMergerExample {

	public static void main(String[] args) throws IOException {
		mergeTwoInputStreamsAsPDF();

		mergeTwoPdfFilesWithMetaDataToFile();
		
		mergeTwoPDDocuments();
	}

	private static void mergeTwoPDDocuments() throws IOException, FileNotFoundException {
		File pdf_1 = new File("C:\\Users\\remad\\invoice_2_generated.pdf");
		File pdf_2 = new File("C:\\Users\\remad\\invoice_generated.pdf");
		PDDocument document_source = PDDocument.load(pdf_1);
		PDDocument document_source_2 = PDDocument.load(pdf_2);
		PDDocument document_destination = new PDDocument();
		PDFMergerUtility pdfMerge2 = new PDFMergerUtility();
		pdfMerge2.setDocumentMergeMode(DocumentMergeMode.OPTIMIZE_RESOURCES_MODE);
		pdfMerge2.setDestinationFileName("C:\\Users\\remad\\pdfMergePerFiles2.pdf");
		pdfMerge2.addSources(List.of(new FileInputStream(pdf_1), new FileInputStream(pdf_2)));
		pdfMerge2.appendDocument(document_destination, document_source);
		pdfMerge2.appendDocument(document_destination, document_source_2);
		pdfMerge2.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly(MemoryUsageSettingConstants.CONSTANT_256_MB_MEMORY));
	}

	private static void mergeTwoPdfFilesWithMetaDataToFile() throws IOException, FileNotFoundException {
		File pdf_1 = new File("C:\\Users\\remad\\invoice_2_generated.pdf");
		File pdf_2 = new File("C:\\Users\\remad\\invoice_generated.pdf");
		PDDocument document = PDDocument.load(pdf_1);
		PDMetadata metaData = new PDMetadata(document, new FileInputStream(pdf_1));
		PDFMergerUtility pdfMerge2 = new PDFMergerUtility();
		pdfMerge2.setDocumentMergeMode(DocumentMergeMode.OPTIMIZE_RESOURCES_MODE);
		pdfMerge2.setDestinationMetadata(metaData);
		pdfMerge2.setDestinationFileName("C:\\Users\\remad\\pdfMergePerFiles.pdf");
		pdfMerge2.addSource(pdf_1);
		pdfMerge2.addSource(pdf_2);
		pdfMerge2.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
	}

	private static void mergeTwoInputStreamsAsPDF() throws IOException, FileNotFoundException {
		List<ContentLayoutData> contentLayoutData1 = List.of(PDFCreationBuilder.createPage1());
		PDFCreationBuilder builder1 = new PDFCreationBuilder().contentLayoutData(contentLayoutData1);
		byte[] firstPdfFile = builder1.buildAsByteArray();
		InputStream firstFile = new ByteArrayInputStream(firstPdfFile);

		List<ContentLayoutData> contentLayoutData2 = List.of(PDFCreationBuilder.createPage2());
		PDFCreationBuilder builder2 = new PDFCreationBuilder().contentLayoutData(contentLayoutData2);
		byte[] secondPdfFile = builder2.buildAsByteArray();
		InputStream secondFile = new ByteArrayInputStream(secondPdfFile);

		List<ContentLayoutData> documentInfos = new ArrayList<>(contentLayoutData1);
		documentInfos.addAll(contentLayoutData2);
		DocumentInformationMultiplePagesBuilder documentInfoBuilder = new DocumentInformationMultiplePagesBuilder();
		PDDocumentInformation destinationDocumentInformation = documentInfoBuilder.contentLayoutDatas(documentInfos)
				.build();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		pdfMerger.addSources(List.of(firstFile, secondFile));
		pdfMerger.setDestinationDocumentInformation(destinationDocumentInformation);
		pdfMerger.setDestinationStream(os);
		pdfMerger.setDocumentMergeMode(DocumentMergeMode.OPTIMIZE_RESOURCES_MODE);
		pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

		byte[] mergedPdfs = os.toByteArray();
		System.out.println(mergedPdfs.length);
		try (BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream("C:\\Users\\remad\\merged_pdfs_2.pdf"))) {
			out.write(mergedPdfs);
		}
	}
}
