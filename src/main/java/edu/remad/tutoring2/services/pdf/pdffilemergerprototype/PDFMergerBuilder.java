package edu.remad.tutoring2.services.pdf.pdffilemergerprototype;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.multipdf.PDFMergerUtility.DocumentMergeMode;

import edu.remad.tutoring2.services.pdf.constants.MemoryUsageSettingConstants;
import edu.remad.tutoring2.services.pdf.exceptions.PDFMergerBuilderException;

public class PDFMergerBuilder {

	private final PDFMergerUtility pdfMerger;

	private PDDocument destinationPDDocument;

	private MemoryUsageSetting memoryUsageSetting;

	public PDFMergerBuilder() {
		pdfMerger = new PDFMergerUtility();
	}

	/**
	 * Can be called multiple times until you build.
	 * 
	 * @param pdDocuments PDFDocument as {@link PDDocument}
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder addPDDocuments(List<PDDocument> pdDocuments) {
		if (destinationPDDocument == null) {
			destinationPDDocument = new PDDocument();
		}

		if (pdDocuments != null && !pdDocuments.isEmpty()) {
			try {
				for (PDDocument sourcePDDocument : pdDocuments) {
					pdfMerger.appendDocument(destinationPDDocument, sourcePDDocument);
				}

				return this;
			} catch (IOException e) {
				throw new PDFMergerBuilderException("PDFMergerBuilder: Cannot add PDDocuments.", e);
			}
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: Cannot add null PDDocuments.");
	}

	public PDFMergerBuilder addFile(File pdfFile) {
		if (pdfFile != null) {
			try {
				pdfMerger.addSource(pdfFile);
				return this;
			} catch (FileNotFoundException e) {
				throw new PDFMergerBuilderException("PDFMergerBuilder: File was not found " + pdfFile.getName() + ".",
						e);
			}
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: File was null.");
	}

	public PDFMergerBuilder addFiles(List<File> pdfFiles) {
		if (pdfFiles != null && !pdfFiles.isEmpty()) {
			try {
				for (File pdfFile : pdfFiles) {
					pdfMerger.addSource(pdfFile);

				}

				return this;
			} catch (FileNotFoundException e) {
				throw new PDFMergerBuilderException("PDFMergerBuilder: Files were not found.", e);
			}
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: Files to add are zero.");
	}

	public PDFMergerBuilder addSource(InputStream pdfInputStream){
		if (pdfInputStream != null) {
			pdfMerger.addSource(pdfInputStream);
			
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: pdfInputStream was null.");
	}

	public PDFMergerBuilder addSources(List<InputStream> pdfInputStreams) {
		if (pdfInputStreams != null && !pdfInputStreams.isEmpty()) {
			pdfMerger.addSources(pdfInputStreams);

			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: pdfInputStreams were null or empty.");
	}

	public PDFMergerBuilder addStringSource(String source) {
		if (StringUtils.isNotBlank(source)) {
			try {
				pdfMerger.addSource(source);
				return this;
			} catch (FileNotFoundException e) {
				throw new PDFMergerBuilderException("PDFMergerBuilder: source file was not found.", e);
			}
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: source is null or blank.");
	}

	public PDFMergerBuilder addStringSources(List<String> sources) {
		if (sources != null && !sources.isEmpty()) {
			try {
				for (String source : sources) {
					pdfMerger.addSource(source);
				}

				return this;
			} catch (FileNotFoundException e) {
				throw new PDFMergerBuilderException("PDFMergerBuilder: source files were not found.", e);
			}
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: sources is null or empty.");
	}

	public PDFMergerBuilder destinationPDDocumentInformation(PDDocumentInformation info) {
		if (info != null) {
			pdfMerger.setDestinationDocumentInformation(info);
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: info was null.");
	}

	public PDFMergerBuilder destinationFileName(String destinationFileName) {
		if (StringUtils.isNotBlank(destinationFileName) && pdfMerger.getDestinationStream() == null) {
			pdfMerger.setDestinationFileName(destinationFileName);
			return this;
		}

		throw new PDFMergerBuilderException(
				"PDFMergerBuilder: destination file name was null or blank or destination output stream was already set.");
	}

	public PDFMergerBuilder pDMetaData(PDMetadata metaData) {
		if (metaData != null) {
			pdfMerger.setDestinationMetadata(metaData);
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: meta data was null.");
	}

	public PDFMergerBuilder destinationStream(OutputStream destinationStream) {
		if (destinationStream != null && pdfMerger.getDestinationFileName() == null) {
			pdfMerger.setDestinationStream(destinationStream);
			return this;
		}

		throw new PDFMergerBuilderException(
				"PDFMergerBuilder: destination stream was null or destination file was already set.");
	}

	public PDFMergerBuilder documentMergeMode(DocumentMergeMode mode) {
		if (mode != null) {
			pdfMerger.setDocumentMergeMode(mode);
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: mode was null.");
	}

	public PDFMergerBuilder customizedMemoryUsageSetting(MemoryUsageSetting memoryUsageSetting) {
		if (memoryUsageSetting != null) {
			this.memoryUsageSetting = memoryUsageSetting;
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: memoryUsageSetting was null.");
	}

	public void build() {
		if (pdfMerger.getDestinationFileName() == null && pdfMerger.getDestinationStream() == null) {
			throw new PDFMergerBuilderException(
					"PDFMergerBuilder: Neither destination file name nor destination stream are set. Have no destination to write merged PDFs to.");
		}

		try {
			if (memoryUsageSetting != null) {

				pdfMerger.mergeDocuments(memoryUsageSetting);
			} else {
				pdfMerger.mergeDocuments(
						MemoryUsageSetting.setupMainMemoryOnly(MemoryUsageSettingConstants.CONSTANT_512_MB_MEMORY));
			}
		} catch (IOException e) {
			throw new PDFMergerBuilderException("PDFMergerBuilder: Merging error.", e);
		}
	}

}
