package edu.remad.tutoring2.services.pdf.pdffilemergerprototype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.remad.tutoring2.services.pdf.exceptions.PDFMergerBuilderException;

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
}
