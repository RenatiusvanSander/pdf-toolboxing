package edu.remad.tutoring2.services.pdf.utilities;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;
import edu.remad.tutoring2.services.pdf.constants.PageContentConstants;

public final class PageContentLayoutUtilities {

	/**
	 * private PageLayoutUtilities Constructor for static access
	 */
	private PageContentLayoutUtilities() {
		// do not instantiate
	}

	/**
	 * Converts point to millimeters
	 *
	 * @param point 1 pt = 1/72 inch = 25.4/72 mm
	 * @return converted point value as millimeters
	 */
	public static float convertPointToMm(final float point) {
		return point * PageContentConstants.POINT_UNIT;
	}

	/**
	 * Converts mm to points.
	 *
	 * @param mm millimeters to convert into points
	 * @return converted millimeters values as points
	 */
	public static float convertMmToPoint(final float mm) {
		return mm * PageContentConstants.POINTS_CONSTANT;
	}

	/**
	 * Creates company contact details
	 * 
	 * @param pageContentLayout content layout data object
	 * @return array of {@link String}
	 */
	public static String[] createCompanyContactDetails(ContentLayoutData pageContentLayout) {
		return new String[] { pageContentLayout.getContactCompany(), pageContentLayout.getContactName(),
				pageContentLayout.getContactStreetHouseNo(), pageContentLayout.getContactZipAndLocation(),
				pageContentLayout.getContactEmail(), pageContentLayout.getContactMobile() };
	}
}