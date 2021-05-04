/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008-2013 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU
 * General Public License Version 3 only ("GPL").  
 * You may not use this file except in compliance with the License. 
 * You can obtain a copy of the License at http://www.gnu.org/licenses/gpl-3.0.html
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 * 
 */

package org.jopendocument.print;

import org.jopendocument.util.StringInputStream;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.RepaintManager;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jopendocument.model.OpenDocument;

public class ODTPrinterXML extends ODTPrinter {
    
    private static final boolean debug = false;

    public ODTPrinterXML(OpenDocument doc) {
        super(doc);
        
    }

    @Override
    public int print(final Graphics g, final PageFormat pageFormat, final int pageIndex) throws PrinterException {

        if (pageIndex >= this.renderer.getPrintedPagesNumber()) {
            return (Printable.NO_SUCH_PAGE);
        }
        // Disable Double Buffering
        final RepaintManager currentManager = RepaintManager.currentManager(this.renderer);
        currentManager.setDoubleBufferingEnabled(false);

        // Move to printable area
        final Graphics2D g2d = (Graphics2D) g;
        int dx = 0;
        int dy = 0;
        boolean centerX = false;
        boolean centerY = false;

        double ratio = 1f;
        // Adapt zoom
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(new StringInputStream(this.renderer.getDocument().getOdsp()));

            Element root = document.getRootElement();
            Element offset = root.getChild("offset");
            // Decalage
            if (offset != null) {
                final Attribute offsetXAttribute = offset.getAttribute("x");
                if (offsetXAttribute != null) {
                    dx = offsetXAttribute.getIntValue();
                }
                final Attribute offsetYAttribute = offset.getAttribute("y");
                if (offsetYAttribute != null) {
                    dy = offsetYAttribute.getIntValue();
                }
            }
            // Centrage
            Element center = root.getChild("center");
            if (center != null) {
                final Attribute centerXAttribute = center.getAttribute("horizontal");
                if (centerXAttribute != null) {
                    centerX = centerXAttribute.getBooleanValue();
                }
                final Attribute centerYAttribute = center.getAttribute("vertical");
                if (centerYAttribute != null) {
                    centerY = centerYAttribute.getBooleanValue();
                }
            }

            // resize
            Element resize = root.getChild("resize");
            final Attribute ratioAttribute = resize.getAttribute("percent");
            if (ratioAttribute != null) {
                String s = ratioAttribute.getValue();
                s = s.replace('%', ' ').trim();
                ratio = Double.parseDouble(s) / 100;
                if (ratio <= 0 || ratio > 1) {
                    throw new IllegalStateException("Ratio out of bound:" + s + "(should be > 0% and <= 100%");
                }
            }
            final double maxPrintWidth = this.renderer.getPrintWidth();
            final double pageWidth = pageFormat.getImageableWidth();
            final double maxPrintHeight = this.renderer.getPrintHeight();
            final double pageHeight = pageFormat.getImageableHeight();
            System.out.println("Ratio:" + ratio);
            final double resizeDocument1 = (maxPrintWidth) / (pageWidth * ratio);
            // final double resizeDocument2 = (maxPrintHeight) / (pageHeight * ratio);

            this.renderer.setIgnoreMargins(true);
            // if (resizeDocument1 > resizeDocument2) {
            System.err.println("resize factor " + resizeDocument1);
            this.renderer.setResizeFactor(resizeDocument1);
            // } else {
            // System.err.println("resize factor " + resizeDocument2);
            // this.renderer.setResizeFactor(resizeDocument2);
            // }

            if (centerX) {
                dx += (pageWidth - this.renderer.getPrintWidthInPixel()) / 2;
            }
            if (centerY) {
                dy += (pageHeight - this.renderer.getPrintHeightInPixel()) / 2;
            }
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            if (debug) {
                g2d.setColor(Color.RED);
                g2d.drawRect(0, 0, (int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight() - 1);
            }
            g2d.translate(dx, dy);
            if (debug) {
                g2d.setColor(Color.YELLOW);
                g2d.drawRect(0, 0, this.renderer.getPrintWidthInPixel() - 1, this.renderer.getPrintHeightInPixel() - 1);
            }
            this.renderer.setCurrentPage(pageIndex);
            // Paint
            this.renderer.paintComponent(g2d);
        } catch (Exception e) {
            // The DTD parsing failed
            e.printStackTrace();
        }

        currentManager.setDoubleBufferingEnabled(true);

        return (Printable.PAGE_EXISTS);

    }
}
