package edu.remad.tutoring2.services.pdf.pagecontent;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;

public class PDFPageContentTextLayouter {
    /**
     * the pdf-document
     */
    private final PDDocument document;

    /**
     * pdf page content stream
     */
    private final PDPageContentStream contentStream;

    /**
     * MyTextClass Constructor
     *
     * @param document      in-memmory PDF-document
     * @param contentStream page constent stream to stream text on page
     */
    public PDFPageContentTextLayouter(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
    }

    /**
     * Adds single line of text
     *
     * @param text      text to add
     * @param xPosition x position
     * @param yPosition y position
     * @param font      font type to foat text
     * @param fontSize  font size of text to add
     * @param color     color for text to add
     * @throws IOException In case of input / output errors to write on PDF-page.
     */
    public void addSingleLineText(String text, int xPosition, int yPosition, PDFont font,
                                  float fontSize, Color color)
            throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setStrokingColor(color);
        contentStream.newLineAtOffset(xPosition, yPosition);
        contentStream.showText(text);
        contentStream.endText();
        contentStream.moveTo(0, 0);
    }

    /**
     * Adds multi line text.
     *
     * @param textArray multi line text to write on PDF-page
     * @param leading   the leading
     * @param xPosition x position
     * @param yPosition y position
     * @param font      font type to foat text
     * @param fontSize  font size of text to add
     * @param color     color for text to add
     * @throws IOException In case of input / output errors to write on PDF-page.
     */
    public void addMultiLineText(String[] textArray, float leading, int xPosition, int yPosition,
                                 PDFont font, float fontSize, Color color) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(xPosition, yPosition);

        for (String text : textArray) {
            contentStream.showText(text);
            contentStream.newLine();
        }
        contentStream.endText();
        contentStream.moveTo(0, 0);
    }

    /**
     * Gets text width
     *
     * @param text     the text to calculate text width
     * @param font     the font type
     * @param fontSize font size in points
     * @return text width
     * @throws IOException In case of input or output exceptions.
     */
    public float getTextWidth(String text, PDFont font, float fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }
}
