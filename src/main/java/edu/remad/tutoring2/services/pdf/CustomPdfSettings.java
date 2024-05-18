package edu.remad.tutoring2.services.pdf;

import java.util.Map;
import java.util.Properties;

import edu.remad.tutoring2.services.pdf.constants.CustomPdfSettingsConstants;

public class CustomPdfSettings extends Properties{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3294209271918727034L;

	public CustomPdfSettings() {}
	
	CustomPdfSettings(Map<String, String> settings) {
		if(!settings.isEmpty()) {
			this.putAll(settings);
		}
	}
	
	public void addLogo(String logoFilePath) {
		setProperty(CustomPdfSettingsConstants.LOGO_KEY, logoFilePath);
	}
}
