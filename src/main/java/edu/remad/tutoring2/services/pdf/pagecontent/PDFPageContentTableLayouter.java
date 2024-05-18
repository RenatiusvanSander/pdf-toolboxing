package edu.remad.tutoring2.services.pdf.pagecontent;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import edu.remad.tutoring2.services.pdf.ContentLayoutData;

public class PDFPageContentTableLayouter {

    private PDFPageContentTable pageContentTable;

    private final ContentLayoutData pageContentLayout;

    public PDFPageContentTableLayouter(PDDocument document, PDPageContentStream contentStream, final ContentLayoutData pageContentLayout) throws IOException {
        pageContentTable = new PDFPageContentTable(document, contentStream);
        this.pageContentLayout = pageContentLayout;
    }

    public void layoutTable() throws IOException {
        generateBasicTable();
        layoutHeader();
        generateTableRowsAndCells();
        generateVATRows();
    }

    private void generateBasicTable() {
        pageContentTable.setTable(pageContentLayout.getTableCellWidths(), pageContentLayout.getTableCellHeight(), 25, pageContentLayout.getPageHeight() - 350);
        pageContentTable.setTableFont(pageContentLayout.getFont(), pageContentLayout.getTextFontSize(), pageContentLayout.getFontColor());
    }

    private void layoutHeader() throws IOException {
        Color tableHeadColor = pageContentLayout.getTableHeaderColor();
        for(String header : pageContentLayout.getTableHeaders()) {
            pageContentTable.addCell(header, tableHeadColor);
        }
    }

    private void generateTableRowsAndCells() throws IOException {
        Color tableBodyColor = pageContentLayout.getTableBodyColor();
        for(Map<String, String> row : pageContentLayout.getTableRows()) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                pageContentTable.addCell(entry.getValue(), tableBodyColor);
            }
        }
    }

    private void generateVATRows() throws IOException {
        Color tableHeadColor = pageContentLayout.getTableHeaderColor();
        pageContentTable.addCell("", null);
        pageContentTable.addCell("", null);
        pageContentTable.addCell("Zwischen-Summe", null);
        pageContentTable.addCell("", null);
        pageContentTable.addCell(pageContentLayout.getInvoicePositionsAsFullPrice(), null);

        pageContentTable.addCell("", null);
        pageContentTable.addCell("", null);
        pageContentTable.addCell("USt", null);
        pageContentTable.addCell("0%", null);
        pageContentTable.addCell("0", null);

        pageContentTable.addCell("", null);
        pageContentTable.addCell("", null);
        pageContentTable.addCell("", null);
        pageContentTable.addCell("Summe", tableHeadColor);
        pageContentTable.addCell(pageContentLayout.getInvoicePositionsAsFullPrice(), tableHeadColor);
    }
}

