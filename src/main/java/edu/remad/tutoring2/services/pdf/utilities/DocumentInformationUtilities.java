package edu.remad.tutoring2.services.pdf.utilities;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.exceptions.DocumentInformationUtilitiesException;

public final class DocumentInformationUtilities {

	private static final String SPLIT_DELIMITER = "/";

	private DocumentInformationUtilities() {
		// do not instantiate
	}

	/**
	 * Extracts creation date and converts to {@link Date}.
	 * 
	 * @param contentLayoutData content layout data object,
	 *                          {@link ContentLayoutData}
	 * @return {@link Date}
	 */
	public static Date extractCreationDate(ContentLayoutData contentLayoutData) {
		if (contentLayoutData == null || contentLayoutData.getInvoiceCreationDate() == null
				|| contentLayoutData.getInvoiceCreationDate().length() < 2) {
			throw new DocumentInformationUtilitiesException(
					"DocumentInformationUtilitiesException: contentLayoutData may not be null or creation date may not be null.");
		}

		String[] splitCreationDate = contentLayoutData.getInvoiceCreationDate()
				.split(contentLayoutData.getSplitDelimiter() == null ? SPLIT_DELIMITER
						: contentLayoutData.getSplitDelimiter());

		int year = Integer.parseInt(splitCreationDate[2]);
		int month = Integer.parseInt(splitCreationDate[1]);
		int day = Integer.parseInt(splitCreationDate[0]);
		Calendar calendarDate = new GregorianCalendar(year, month, day);

		return calendarDate.getTime();
	}

	/**
	 * Joins keywords to a comma separated string
	 * 
	 * @param documentInformationKeywords keywords
	 * @return {@link String}
	 */
	public static String joinKeywordtoString(String[] documentInformationKeywords) {
		if (documentInformationKeywords == null || documentInformationKeywords.length == 0
				|| documentInformationKeywords[1].length() < 3) {
			throw new DocumentInformationUtilitiesException(
					"DocumentInformationUtilitiesException: Not enough letters.");
		}

		return Arrays.asList(documentInformationKeywords).stream().collect(Collectors.joining(", "));
	}

	/**
	 * Joins string array to one string.
	 * 
	 * @param array array of {@link String} to join and separated by empty space
	 * @return {@link String}
	 */
	public static String joinsArrayToString(String[] array) {
		if (array == null || array.length == 0) {
			throw new DocumentInformationUtilitiesException(
					"DocumentInformationUtilitiesException: array of strings is null or empty.");
		}

		Set<String> withoutDuplicates = Arrays.asList(array).stream().collect(Collectors.toSet());

		return withoutDuplicates.stream().collect(Collectors.joining(" "));
	}

	/**
	 * Joins array of {@link Long} to a {@link String} object.
	 * 
	 * @param array arrays of {@link Long} to join and separated by empty spaces
	 * @return {@link String}
	 */
	public static String joinsLongsToString(Long[] array) {
		if (array == null || array.length == 0) {
			throw new DocumentInformationUtilitiesException(
					"DocumentInformationUtilitiesException: array of longs is null or empty.");
		}

		Set<Long> withOutDuplicates = Stream.of(array).collect(Collectors.toSet());

		return withOutDuplicates.stream().map(String::valueOf).collect(Collectors.joining(" "));
	}
}
