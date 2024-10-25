package edu.remad.tutoring2.services.pdf.documentinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.junit.jupiter.api.Test;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;

public class DocumentInformationMultiplePagesBuilderTest {

	@Test
	public void testAll() {
		List<ContentLayoutData> contentLayoutDatas = createMultipleContentLayoutDatas();
		
		DocumentInformationMultiplePagesBuilder builder = new DocumentInformationMultiplePagesBuilder();
		builder.contentLayoutDatas(contentLayoutDatas);
		
		PDDocumentInformation documentInformation = builder.build();
		assertEquals("Remmad Maiere", documentInformation.getAuthor());
		assertEquals("Remy Meier Mitarbeiter", documentInformation.getCreator());
		assertEquals("dgdgd jsjhdhd, lkeuud hhddggd", documentInformation.getKeywords());
		assertEquals("Rechnungsnummern:  144  147", documentInformation.getSubject());
		assertEquals("Invoice: 144 147", documentInformation.getTitle());
	}

	private List<ContentLayoutData> createMultipleContentLayoutDatas() {
		ContentLayoutData contentLayoutData1 = new ContentLayoutData();
		contentLayoutData1.setCreator("Remy Meier");
		contentLayoutData1.setInvoiceNo("144");
		contentLayoutData1.setContactName("Remmad");
		contentLayoutData1.setInvoiceNoLabel("Rechnung");
		contentLayoutData1.setDocumentInformationKeywords(new String[]{"dgdgd","jsjhdhd"});
		
		ContentLayoutData contentLayoutData2 = new ContentLayoutData();
		contentLayoutData2.setCreator("Mitarbeiter");
		contentLayoutData2.setInvoiceNo("147");
		contentLayoutData2.setContactName("Maiere");
		contentLayoutData2.setInvoiceNoLabel("Rechnung");
		contentLayoutData2.setDocumentInformationKeywords(new String[]{"lkeuud","hhddggd"});
		
		return List.of(contentLayoutData1, contentLayoutData2);
	}
}
