package edu.remad.tutoring2.services.pdf.pdffilemergerprototype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.PDFCreationBuilder;
import edu.remad.tutoring2.services.pdf.documentinformation.DocumentInformationBuilder;
import edu.remad.tutoring2.services.pdf.exceptions.PDFMergerBuilderException;
import edu.remad.tutoring2.services.pdf.pdffilemerger.PDFMergerBuilder;

public class PDFMergerBuilderTest {

	private PDFMergerBuilder builder;

	@BeforeEach
	public void setUp() {
		builder = new PDFMergerBuilder();
	}

	@Test
	public void testPDFMergerBuildException() {
		PDFMergerBuilderException exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.addFile(null);
		});
		assertEquals("PDFMergerBuilder: File was null.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.addPDDocuments(null);
		});
		assertEquals("PDFMergerBuilder: Cannot add null PDDocuments.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.addFiles(null);
		});
		assertEquals("PDFMergerBuilder: Files to add are zero.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.addSource(null);
		});
		assertEquals("PDFMergerBuilder: pdfInputStream was null.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.addSources(null);
		});
		assertEquals("PDFMergerBuilder: pdfInputStreams were null or empty.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.addStringSource(null);
		});
		assertEquals("PDFMergerBuilder: source is null or blank.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.addStringSources(null);
		});
		assertEquals("PDFMergerBuilder: sources is null or empty.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.destinationPDDocumentInformation(null);
		});
		assertEquals("PDFMergerBuilder: info was null.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.build();
		});
		assertEquals(
				"PDFMergerBuilder: Neither destination file name nor destination stream are set. Have no destination to write merged PDFs to.",
				exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.destinationFileName(null);
		});
		assertEquals(
				"PDFMergerBuilder: destination file name was null or blank or destination output stream was already set.",
				exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.documentMergeMode(null);
		});
		assertEquals("PDFMergerBuilder: mode was null.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.pDMetaData(null);
		});
		assertEquals("PDFMergerBuilder: meta data was null.", exception.getMessage());

		exception = Assertions.assertThrows(PDFMergerBuilderException.class, () -> {
			builder.destinationStream(null);
		});
		assertEquals("PDFMergerBuilder: destination stream was null or destination file was already set.",
				exception.getMessage());
	}

	@Test
	public void buildTest() throws IOException {
		List<ContentLayoutData> contentLayoutData1 = List.of(PDFCreationBuilder.createPage1());
		PDFCreationBuilder builder1 = new PDFCreationBuilder().contentLayoutData(contentLayoutData1);
		InputStream firstFile = new ByteArrayInputStream(builder1.buildAsByteArray());

		List<ContentLayoutData> contentLayoutData2 = List.of(PDFCreationBuilder.createPage2());
		PDFCreationBuilder builder2 = new PDFCreationBuilder().contentLayoutData(contentLayoutData2);
		InputStream secondFile = new ByteArrayInputStream(builder2.buildAsByteArray());

		ByteArrayOutputStream destinationStream = new ByteArrayOutputStream();
		Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		PDDocumentInformation documentInformation = new DocumentInformationBuilder().setAuthor("Rémy Meier")
				.setInvoiceNumber(151L).setCreator("Tutoring2").setSubject("Merged PDF Invoices")
				.setKeywords(new String[] { "Invoice1", "Invoice2" }).setCreationDate(date).build();

		builder.addSource(firstFile);
		builder.addSources(List.of(secondFile));
		builder.destinationStream(destinationStream);
		builder.destinationPDDocumentInformation(documentInformation);
		builder.build();

		byte[] mergedPdfFile = destinationStream.toByteArray();
		assertEquals(11163, mergedPdfFile.length);
	}
}
