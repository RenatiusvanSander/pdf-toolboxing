package edu.remad.tutoring2.services.pdf.pdffilemerger;

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

import edu.remad.tutoring2.services.pdf.constants.MaxMainMemoryBytes;
import edu.remad.tutoring2.services.pdf.exceptions.PDFMergerBuilderException;

public class PDFMergerBuilder {

	private final PDFMergerUtility pdfMerger;

	private PDDocument destinationPDDocument;

	private MemoryUsageSetting memoryUsageSetting;
	
	private MaxMainMemoryBytes maxMainMemoryBytes;

	/**
	 * Constructor
	 */
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

	/**
	 * Adds a file to merge.
	 * 
	 * @param pdfFile the PDF file to merge
	 * @return {@link PDFMergerBuilder}
	 */
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

	/**
	 * Adds files to merge
	 * 
	 * @param pdfFiles the PDF files to merge
	 * @return {@link PDFMergerBuilder}
	 */
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

	/**
	 * Add source input stream.
	 * 
	 * @param pdfInputStream PDF file as input stream
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder addSource(InputStream pdfInputStream){
		if (pdfInputStream != null) {
			pdfMerger.addSource(pdfInputStream);
			
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: pdfInputStream was null.");
	}

	/**
	 * Adds sources and merge into one PDF
	 * 
	 * @param pdfInputStreams PDF input streams to merge
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder addSources(List<InputStream> pdfInputStreams) {
		if (pdfInputStreams != null && !pdfInputStreams.isEmpty()) {
			pdfMerger.addSources(pdfInputStreams);

			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: pdfInputStreams were null or empty.");
	}

	/**
	 * Adds string encoded file
	 * 
	 * @param source string encoded file
	 * @return {@link PDFMergerBuilder}
	 */
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

	/**
	 * adds string encoded sources
	 * 
	 * @param sources list of files
	 * @return {@link PDFMergerBuilder}
	 */
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

	/**
	 * Document Information.
	 * 
	 * @param info document information for merged documents
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder destinationPDDocumentInformation(PDDocumentInformation info) {
		if (info != null) {
			pdfMerger.setDestinationDocumentInformation(info);
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: info was null.");
	}

	/**
	 * Destination file name
	 * 
	 * @param destinationFileName path to file with file extension
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder destinationFileName(String destinationFileName) {
		if (StringUtils.isNotBlank(destinationFileName) && pdfMerger.getDestinationStream() == null) {
			pdfMerger.setDestinationFileName(destinationFileName);
			return this;
		}

		throw new PDFMergerBuilderException(
				"PDFMergerBuilder: destination file name was null or blank or destination output stream was already set.");
	}

	/**
	 * Adds meta data to merge documents
	 * 
	 * @param metaData {@link PDMetaData to set}
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder pDMetaData(PDMetadata metaData) {
		if (metaData != null) {
			pdfMerger.setDestinationMetadata(metaData);
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: meta data was null.");
	}

	/**
	 * Destination Stream
	 * 
	 * @param destinationStream file output stream to save merged PDFs
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder destinationStream(OutputStream destinationStream) {
		if (destinationStream != null && pdfMerger.getDestinationFileName() == null) {
			
			pdfMerger.setDestinationStream(destinationStream);
			return this;
		}

		throw new PDFMergerBuilderException(
				"PDFMergerBuilder: destination stream was null or destination file was already set.");
	}

	/**
	 * Document merge Mode
	 * 
	 * @param mode the document merge mode
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder documentMergeMode(DocumentMergeMode mode) {
		if (mode != null) {
			pdfMerger.setDocumentMergeMode(mode);
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: mode was null.");
	}

	/**
	 * Sets customized memory usage setting
	 * 
	 * @param memoryUsageSetting set {@link MemoryUsageSetting}
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder customizedMemoryUsageSetting(MemoryUsageSetting memoryUsageSetting) {
		if (memoryUsageSetting != null) {
			this.memoryUsageSetting = memoryUsageSetting;
			return this;
		}

		throw new PDFMergerBuilderException("PDFMergerBuilder: memoryUsageSetting was null.");
	}
	
	/**
	 * MaxMainMemoryBytes
	 * 
	 * @param maxMainMemoryBytes instance of {@link MaxMainMemoryBytes}
	 * @return {@link PDFMergerBuilder}
	 */
	public PDFMergerBuilder maxMainMemoryBytes(MaxMainMemoryBytes maxMainMemoryBytes) {
		if(this.memoryUsageSetting != null) {
			throw new PDFMergerBuilderException("PDFMergerBuilderException: When you want to set maxMainMemoryBytes, there has to be none memoryUsageSetting set.");
		}
		
		this.maxMainMemoryBytes = maxMainMemoryBytes;
		
		return this;
	}

	/**
	 * Builds PDF from files to merge
	 */
	public void build() {
		if (pdfMerger.getDestinationFileName() == null && pdfMerger.getDestinationStream() == null) {
			throw new PDFMergerBuilderException(
					"PDFMergerBuilder: Neither destination file name nor destination stream are set. Have no destination to write merged PDFs to.");
		}

		try {
			if (memoryUsageSetting != null) {
				pdfMerger.mergeDocuments(memoryUsageSetting);
			} else if(this.maxMainMemoryBytes != null && memoryUsageSetting == null) {
				pdfMerger.mergeDocuments(
						MemoryUsageSetting.setupMainMemoryOnly(maxMainMemoryBytes.getMaxMainMemory()));
			} else {
				pdfMerger.mergeDocuments(
						MemoryUsageSetting.setupMainMemoryOnly(MaxMainMemoryBytes.CONSTANT_512_MB_MEMORY.getMaxMainMemory()));
			}
		} catch (IOException e) {
			throw new PDFMergerBuilderException("PDFMergerBuilder: Merging error.", e);
		}
	}

}
