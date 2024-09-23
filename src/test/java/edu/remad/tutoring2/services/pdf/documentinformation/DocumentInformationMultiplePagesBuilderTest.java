package edu.remad.tutoring2.services.pdf.documentinformation;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;

public class DocumentInformationMultiplePagesBuilderTest {

	@Test
	public void testAll() {
		List<ContentLayoutData> contentLayoutDatas = createMultipleContentLayoutDatas();
	}

	private List<ContentLayoutData> createMultipleContentLayoutDatas() {
		ContentLayoutData contentLayoutData1 = new ContentLayoutData();
		
		ContentLayoutData contentLayoutData2 = new ContentLayoutData();
		
		return List.of(contentLayoutData1, contentLayoutData2);
	}
}
