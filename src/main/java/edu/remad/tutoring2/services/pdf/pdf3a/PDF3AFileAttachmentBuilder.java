package edu.remad.tutoring2.services.pdf.pdf3a;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.jempbox.xmp.XMPSchemaBasic;
import org.apache.jempbox.xmp.XMPSchemaDublinCore;
import org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAId;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;
import edu.remad.tutoring2.services.pdf.constants.FileSubtype;
import edu.remad.tutoring2.services.pdf.exceptions.PDF3AFileAttachmentBuilderException;

public class PDF3AFileAttachmentBuilder {

	private final PDDocument document;

	private ContentLayoutData contentLayoutData;

	private byte[] xRechnungData;

	private static final String S_RGB_V4_PREFERENCE = "sRGB v4 Preference";

	public PDF3AFileAttachmentBuilder(PDDocument document) {
		if (document == null) {
			throw new PDF3AFileAttachmentBuilderException(
					"PDF3AFileAttachmentBuilderException: document has to be not null.");
		}

		this.document = document;
	}

	public static void main(String[] args) throws Exception {
		try (PDDocument document = new PDDocument()) {
			PDPage pageA4 = ContentLayoutDataConstants.PAGE;
			document.addPage(pageA4);

			// create a page with the message where needed
			PDPageContentStream contentStream = new PDPageContentStream(document, pageA4);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12f);
			contentStream.moveTextPositionByAmount(100, 700);
			contentStream.drawString("hhhhhhh");
			contentStream.endText();
			contentStream.saveGraphicsState();
			contentStream.close();

			PDMetadata metaData = new PDMetadata(document);
			PDDocumentCatalog catalog = document.getDocumentCatalog();
			catalog.setMetadata(metaData);

			XMPMetadata xmpMeta = new XMPMetadata();
			XMPSchemaPDFAId pdfaid = new XMPSchemaPDFAId(xmpMeta);
			xmpMeta.addSchema(pdfaid);

			XMPSchemaDublinCore dublinCore = xmpMeta.addDublinCoreSchema();
			dublinCore.addCreator("fff");
			dublinCore.setAbout("ddd");

			XMPSchemaBasic xmpSchemaBasic = xmpMeta.addBasicSchema();
			xmpSchemaBasic.setAbout("");
			xmpSchemaBasic.setCreatorTool("Tutoring");
			xmpSchemaBasic.setCreateDate(GregorianCalendar.getInstance());

			PDDocumentInformation documentInformation = new PDDocumentInformation();
			documentInformation.setProducer("Remy");
			documentInformation.setAuthor("meier");
			document.setDocumentInformation(documentInformation);

			PDMarkInfo markinfo = new PDMarkInfo();
			markinfo.setMarked(true);
			document.getDocumentCatalog().setMarkInfo(markinfo);

			pdfaid.setPart(3);
			pdfaid.setConformance("A");
			pdfaid.setAbout("");

			metaData.importXMPMetadata(xmpMeta.asByteArray());

			InputStream colorProfileRgbV4IccPreference = PDF3ABuilder.class
					.getResourceAsStream("colorprofiles/sRGB_v4_ICC_preference.icc");

			PDOutputIntent outputIntent = new PDOutputIntent(document, colorProfileRgbV4IccPreference);
			outputIntent.setInfo("sRGB v4 Preference");
			outputIntent.setOutputCondition("sRGB v4 Preference");
			outputIntent.setOutputConditionIdentifier("sRGB v4 Preference");
			outputIntent.setRegistryName("http://www.color.org");

			// save a file
			PDEmbeddedFilesNameTreeNode embeddedFilesNameTree = new PDEmbeddedFilesNameTreeNode();
			PDComplexFileSpecification complexFileSpecification = new PDComplexFileSpecification();
			complexFileSpecification.setFile("rechnung.xml");
			byte[] data = "This is the contents of the embedded file".getBytes("ISO-8859-1");
			ByteArrayInputStream fakeFile = new ByteArrayInputStream(data);
			PDEmbeddedFile file = new PDEmbeddedFile(document, fakeFile);
			file.setSubtype("application/xml");
			file.setSize(data.length);
			file.setCreationDate(Calendar.getInstance());
			complexFileSpecification.setEmbeddedFile(file);
			PDEmbeddedFilesNameTreeNode fileNode = new PDEmbeddedFilesNameTreeNode();
			fileNode.setNames(Collections.singletonMap("My invoice", complexFileSpecification));
			List<PDEmbeddedFilesNameTreeNode> fileNodeChildren = new ArrayList<PDEmbeddedFilesNameTreeNode>();
			fileNodeChildren.add(fileNode);
			embeddedFilesNameTree.setKids(fileNodeChildren);
			PDDocumentNameDictionary nameDictionary = new PDDocumentNameDictionary(catalog);
			nameDictionary.setEmbeddedFiles(embeddedFilesNameTree);
			document.getDocumentCatalog().setNames(nameDictionary);

			document.save("C:\\Users\\remad\\pdf3a-with-file-attachment.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PDF3AFileAttachmentBuilder contentLayoutData(ContentLayoutData contentLayoutData) {
		if (document == null) {
			throw new PDF3AFileAttachmentBuilderException(
					"PDF3AFileAttachmentBuilderException: contentLayoutData has to be not null.");
		}

		this.contentLayoutData = contentLayoutData;

		return this;
	}
	
	public PDF3AFileAttachmentBuilder xRechnungByteArray(byte[] xRechnungData) {
		if(xRechnungData == null || xRechnungData.length == 0) {
			throw new PDF3AFileAttachmentBuilderException("PDF3AFileAttachmentBuilderException: xRechnungData has to be not null or not empty byte array.");
		}
		
		this.xRechnungData = xRechnungData;
		
		 return this;
	}

	public void build() {
		if (contentLayoutData == null || xRechnungData == null || xRechnungData.length == 0) {
			throw new PDF3AFileAttachmentBuilderException(
					"PDF3AFileAttachmentBuilderException: Cannot build with no data.");
		}

		try {
			String about = "Invoice No " + contentLayoutData.getInvoiceNo();
			PDMetadata metaData = new PDMetadata(document);
			
			PDMarkInfo markinfo = new PDMarkInfo();
			markinfo.setMarked(true);

			XMPMetadata xmpMeta = new XMPMetadata();
			XMPSchemaPDFAId pdfaid = createPdfAId(about, xmpMeta);
			xmpMeta.addSchema(pdfaid);
			createXMPShemaDublinCore(about, xmpMeta);
			createXMPSchemaBasic(about, xmpMeta);
			PDDocumentCatalog catalog = document.getDocumentCatalog();
			catalog.setMetadata(metaData);
			catalog.setMarkInfo(markinfo);
			metaData.importXMPMetadata(xmpMeta.asByteArray());
			createPDOutputIntent();
			PDEmbeddedFile file = createPDEmbeddedFile();
			PDComplexFileSpecification complexFileSpecification = createPDComplexFileSpecification(file);
			PDEmbeddedFilesNameTreeNode fileNode = new PDEmbeddedFilesNameTreeNode();
			fileNode.setNames(Collections.singletonMap(about.replace("\\s", "_"), complexFileSpecification));
			List<PDEmbeddedFilesNameTreeNode> fileNodeChildren = new ArrayList<PDEmbeddedFilesNameTreeNode>();
			fileNodeChildren.add(fileNode);
			PDEmbeddedFilesNameTreeNode embeddedFilesNameTree = new PDEmbeddedFilesNameTreeNode();
			embeddedFilesNameTree.setKids(fileNodeChildren);
			createAndAddDictionaryToDocument(catalog, embeddedFilesNameTree);
		} catch (Exception e) {
			throw new PDF3AFileAttachmentBuilderException(
					"PDF3AFileAttachmentBuilderException: File was not attached and reason is: " + e.getMessage(), e);
		}
	}

	private PDComplexFileSpecification createPDComplexFileSpecification(PDEmbeddedFile file) {
		PDComplexFileSpecification complexFileSpecification = new PDComplexFileSpecification();
		complexFileSpecification.setFile("rechnung.xml");
		complexFileSpecification.setEmbeddedFile(file);
		
		return complexFileSpecification;
	}

	private PDEmbeddedFile createPDEmbeddedFile() throws IOException {
		PDEmbeddedFile file = new PDEmbeddedFile(document, new ByteArrayInputStream(xRechnungData));
		file.setSubtype(FileSubtype.APPLICATION_XML_VALUE.getStringValue());
		file.setSize(xRechnungData.length);
		file.setCreationDate(Calendar.getInstance());
		
		return file;
	}

	private void createAndAddDictionaryToDocument(PDDocumentCatalog catalog,
			PDEmbeddedFilesNameTreeNode embeddedFilesNameTree) {
		PDDocumentNameDictionary nameDictionary = new PDDocumentNameDictionary(catalog);
		nameDictionary.setEmbeddedFiles(embeddedFilesNameTree);
		document.getDocumentCatalog().setNames(nameDictionary);
	}

	private void createPDOutputIntent() throws IOException {
		PDOutputIntent outputIntent = new PDOutputIntent(document, PDF3AFileAttachmentBuilder.class
				.getResourceAsStream("/colorprofiles/sRGB_v4_ICC_preference.icc"));
		outputIntent.setInfo(S_RGB_V4_PREFERENCE);
		outputIntent.setOutputCondition(S_RGB_V4_PREFERENCE);
		outputIntent.setOutputConditionIdentifier(S_RGB_V4_PREFERENCE);
		outputIntent.setRegistryName(S_RGB_V4_PREFERENCE);
	}

	private void createXMPSchemaBasic(String about, XMPMetadata xmpMeta) {
		XMPSchemaBasic xmpSchemaBasic = xmpMeta.addBasicSchema();
		xmpSchemaBasic.setAbout(about);
		xmpSchemaBasic.setCreatorTool(contentLayoutData.getCreator());
		xmpSchemaBasic.setCreateDate(GregorianCalendar.getInstance());
	}

	private void createXMPShemaDublinCore(String about, XMPMetadata xmpMeta) {
		XMPSchemaDublinCore dublinCore = xmpMeta.addDublinCoreSchema();
		dublinCore.addCreator(contentLayoutData.getCreator());
		dublinCore.setAbout(about);
	}

	private XMPSchemaPDFAId createPdfAId(String about, XMPMetadata xmpMeta) {
		XMPSchemaPDFAId pdfaid = new XMPSchemaPDFAId(xmpMeta);
		pdfaid.setPart(3);
		pdfaid.setConformance("A");
		pdfaid.setAbout(about);
		
		return pdfaid;
	}
}
