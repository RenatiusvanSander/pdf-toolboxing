package edu.remad.tutoring2.services.pdf.pagecontent;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;

public class PageContentLayoutUtilities {

	  /**
	   * points constant
	   */
	  public static final float POINTS_CONSTANT = 2.8346438836889f;
	  /**
	   * point unit
	   */
	  public static final float POINT_UNIT = 25.4f / 72f;

	  /**
	   * private PageLayoutUtilities Constructor for static access
	   */
	  private PageContentLayoutUtilities() {
	  }

	  /**
	   * Converts point to millimeters
	   *
	   * @param point 1 pt = 1/72 inch = 25.4/72 mm
	   * @return converted point value as milimetres
	   */
	  public static float convertPointToMm(final float point) {
	    return point * POINT_UNIT;
	  }

	  /**
	   * Converts mm to points.
	   *
	   * @param mm millimeters to convert into points
	   * @return converted millimeters values as points
	   */
	  public static float convertMmToPoint(final float mm) {
	    return mm * POINTS_CONSTANT;
	  }

	  public static String[] createCompanyContactDetails ( ContentLayoutData pageContentLayout) {
	    return new String[]{pageContentLayout.getContactCompany(), pageContentLayout.getContactName(), pageContentLayout.getContactStreetHouseNo(),pageContentLayout.getContactZipAndLocation(), pageContentLayout.getContactEmail(), pageContentLayout.getContactMobile()};
	  }
	}