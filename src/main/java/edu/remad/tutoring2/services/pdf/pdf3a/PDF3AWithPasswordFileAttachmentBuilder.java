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
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;

import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;

public class PDF3AWithPasswordFileAttachmentBuilder {

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

			PDDocumentInformation docuemntInformation = new PDDocumentInformation();
			docuemntInformation.setProducer("Remy");
			docuemntInformation.setAuthor("meier");
			document.setDocumentInformation(docuemntInformation);

			PDMarkInfo markinfo = new PDMarkInfo();
			markinfo.setMarked(true);
			document.getDocumentCatalog().setMarkInfo(markinfo);

			pdfaid.setPart(3);
			pdfaid.setConformance("A");
			pdfaid.setAbout("");

			metaData.importXMPMetadata(xmpMeta.asByteArray());

			InputStream colorProfileRgbV4IccPreference = PDF3ABuilder.class
					.getResourceAsStream("/colorprofiles/sRGB_v4_ICC_preference.icc");

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
            fileNode.setNames(Collections.singletonMap("My invoice",  complexFileSpecification));
            List<PDEmbeddedFilesNameTreeNode> fileNodeChildren = new ArrayList<PDEmbeddedFilesNameTreeNode>();
            fileNodeChildren.add(fileNode);
            embeddedFilesNameTree.setKids(fileNodeChildren);
            PDDocumentNameDictionary nameDictionary = new PDDocumentNameDictionary(catalog);  
            nameDictionary.setEmbeddedFiles(embeddedFilesNameTree);
            document.getDocumentCatalog().setNames(nameDictionary);
            
            AccessPermission accessPermission = new AccessPermission();
            accessPermission.canPrint();
            accessPermission.setCanModify(false);
            accessPermission.setCanModifyAnnotations(false);
            accessPermission.setCanFillInForm(false);
            accessPermission.setCanExtractForAccessibility(false);
            accessPermission.setCanExtractContent(false);
            accessPermission.setCanAssembleDocument(false);
            accessPermission.setCanPrintDegraded(false);
            
            StandardProtectionPolicy standardProtectionPolicy = new StandardProtectionPolicy("12345678", "12345678", accessPermission);
            standardProtectionPolicy.setEncryptionKeyLength(256);
            document.protect(standardProtectionPolicy);
                        
			document.save("C:\\Users\\remad\\pdf3a-with-passsword-file-attachment.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
