package edu.remad.tutoring2.services.pdf.pdf3a;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;

import edu.remad.tutoring2.services.pdf.constants.ContentLayoutDataConstants;

public class PDF3ABuilder {

	public static void main(String[] args) {
		try(PDDocument document = new PDDocument()) {
			PDPage pageA4 = ContentLayoutDataConstants.PAGE;
			document.addPage(pageA4);
			
			 // create a page with the message where needed
		      PDPageContentStream contentStream = new PDPageContentStream(document, pageA4);
		      contentStream.beginText();
//		      contentStream.setFont(PDTy);
		      contentStream.moveTextPositionByAmount(100, 700);
		      contentStream.drawString("hhhhhhh");
		      contentStream.endText();
		      contentStream.saveGraphicsState();
		      contentStream.close();
		      
		      PDMetadata metaData = new PDMetadata(document);
		      PDDocumentCatalog cat = document.getDocumentCatalog();
		      cat.setMetadata(metaData);
		      
		      
		      InputStream colorProfileRgbV4IccPreference = PDF3ABuilder.class.getResourceAsStream("/colorprofiles/sRGB_v4_ICC_preference.icc");
		      
		      PDOutputIntent outputIntent = new PDOutputIntent(document, colorProfileRgbV4IccPreference);
		      outputIntent.setInfo("sRGB v4 Preference");
		      outputIntent.setOutputCondition("sRGB v4 Preference");
		      outputIntent.setOutputConditionIdentifier("sRGB v4 Preference");
		      outputIntent.setRegistryName("http://www.color.org");
		      
		      document.save("C:\\Users\\remad\\pdf3a-versuch.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
