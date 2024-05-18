package edu.remad.tutoring2.services.pdf.documentinformation;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;

public final class DocumentInformationUtilities {

    private static final String SPLIT_DELIMITER = "/";

    private DocumentInformationUtilities () {}

    public static Date extractCreationDate(ContentLayoutData contentLayoutData) {
        String[] splitCreationDate = contentLayoutData.getInvoiceCreationDate().split(SPLIT_DELIMITER);

        int year = Integer.parseInt(splitCreationDate[2]);
        int month = Integer.parseInt(splitCreationDate[1]);
        int day = Integer.parseInt(splitCreationDate[0]);

        Calendar calendarDate = new GregorianCalendar();
        calendarDate.set(year, month, day);
        Date creationDate = calendarDate.getTime();

        return creationDate;
    }

    public static String joinKeywordtoString(String[] documentInformationKeywords) {
        if(documentInformationKeywords == null || documentInformationKeywords.length == 0 || documentInformationKeywords[1].length() < 3 ) {
            throw new IllegalArgumentException("Not enough letters.");
        }

        String joinedKeywords = Arrays.asList(documentInformationKeywords).stream().collect(Collectors.joining(", "));

        return joinedKeywords;
    }
}
