package org.jopendocument.sample;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.renderer.ODTRenderer;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class SimplePDFGenerator {
    public static void main(String[] args) {

        long t1=System.nanoTime();
        try {

            // Load the ODS file
            final OpenDocument doc = new OpenDocument();
            doc.loadFrom("template/invoice.ods");

            // Open the PDF document
            Document document = new Document(PageSize.A4);
           
            File outFile = new File("invoice.pdf");

            PdfDocument pdf = new PdfDocument();

            document.addDocListener(pdf);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            PdfWriter writer = PdfWriter.getInstance(pdf, fileOutputStream);
            pdf.addWriter(writer);
            document.open();
            // Create a template and a Graphics2D object that corresponds with it
            Rectangle pageSize = document.getPageSize();
            int w = (int) (pageSize.getWidth() * 0.9);
            int h = (int) (pageSize.getHeight() * 0.95);
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(w, h);

            Graphics2D g2 = tp.createPrinterGraphics(w, h, null);
            // If you want to prevent copy/paste, you can use
            // g2 = tp.createGraphicsShapes(w, h, true, 0.9f);
            
            tp.setWidth(w);
            tp.setHeight(h);

            // Configure the renderer
            ODTRenderer renderer = new ODTRenderer(doc);
            renderer.setIgnoreMargins(true);
            renderer.setPaintMaxResolution(true);
            
            // Scale the renderer to fit width
            renderer.setResizeFactor(renderer.getPrintWidth() / w);
            // Render
            renderer.paintComponent(g2);
            g2.dispose();

            // Add our spreadsheet in the middle of the page
            float offsetX = (pageSize.getWidth() - w) / 2;
            float offsetY = (pageSize.getHeight() - h) / 2;
            cb.addTemplate(tp, offsetX, offsetY);

            // Close the PDF document
            document.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        long t2=System.nanoTime();
        System.out.println("Time:"+(t2-t1)/(1000*1000)+" ms");
        
    }
}
