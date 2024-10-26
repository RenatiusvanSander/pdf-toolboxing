package edu.remad.tutoring2.services.pdf.pdffilemergerprototype;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.PDFMergerUtility.DocumentMergeMode;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;


import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.PDFCreationBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationMultiplePagesBuilder;

public class PdfFileMergerExample {

	public static void main(String[] args) throws IOException {
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
