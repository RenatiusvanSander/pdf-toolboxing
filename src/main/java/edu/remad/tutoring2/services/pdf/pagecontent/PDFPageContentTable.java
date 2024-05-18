package edu.remad.tutoring2.services.pdf.pagecontent;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;

public class PDFPageContentTable {

    /**
     * in.memory PDF-Document
     */
    PDDocument document;

    /**
     * content stream
     */
    PDPageContentStream contentStream;

    /**
     * column widths
     */
    private int[] colWidths;

    /**
     * height of cell
     */
    private int cellHeight;

    /**
     * y position
     */
    private int yPosition;

    /**
     * x position
     */
    private int xPosition;

    /**
     * column position
     */
    private int colPosition = 0;

    /**
     * initial start value position of x
     */
    private int xInitialPosition;

    /**
     * font size in points
     */
    private float fontSize;

    /**
     * font type
     */
    private PDFont font;

    /**
     * the font color
     */
    private Color fontColor;

    /**
     * MyTableClass Constructor
     *
     * @param document      PDF-document to add a table
     * @param contentStream PDF-page content stream to write table on page
     */
    public PDFPageContentTable(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
    }

    /**
     * Sets table
     *
     * @param colWidths  column widths
     * @param cellHeight cell height
     * @param xPosition  x position
     * @param yPosition  y position
     */
    void setTable(int[] colWidths, int cellHeight, int xPosition, int yPosition) {
        this.colWidths = colWidths;
        this.cellHeight = cellHeight;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xInitialPosition = xPosition;
    }

    /**
     * Sets table font
     *
     * @param font      the font to set for table
     * @param fontSize  the font size to set for text in table
     * @param fontColor the font color to et in table
     */
    void setTableFont(PDFont font, float fontSize, Color fontColor) {
        this.font = font;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
    }

    /**
     * Adds cell
     *
     * @param text      the cell text write
     * @param fillColor the cell fillcolor for cell-background color
     * @throws IOException
     */
    void addCell(String text, Color fillColor) throws IOException {
        contentStream.setStrokingColor(1f);

        if (fillColor != null) {
            contentStream.setNonStrokingColor(fillColor);
        }

        contentStream.addRect(xPosition, yPosition, colWidths[colPosition], cellHeight);

        if (fillColor == null) {
            contentStream.stroke();
        } else {
            contentStream.fillAndStroke();
        }

        contentStream.beginText();
        contentStream.setNonStrokingColor(fontColor);

        if (colPosition == 4 || colPosition == 2) {
            float fontWidth = font.getStringWidth(text) / 1000 * fontSize;
            contentStream.newLineAtOffset(xPosition + colWidths[colPosition] - 20 - fontWidth,
                    yPosition + 10);
        } else {
            contentStream.newLineAtOffset(xPosition + 20, yPosition + 10);
        }

        contentStream.showText(text);
        contentStream.endText();

        xPosition = xPosition + colWidths[colPosition];
        colPosition++;

        if (colPosition == colWidths.length) {
            colPosition = 0;
            xPosition = xInitialPosition;
            yPosition -= cellHeight;
        }
    }
}