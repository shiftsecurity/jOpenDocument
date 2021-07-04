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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.renderer.ODTRenderer;

public class ODTPrinter implements Printable {
    protected ODTRenderer renderer;

    public ODTPrinter(final OpenDocument doc) {
        this.renderer = new ODTRenderer(doc);
        this.renderer.setPaintMaxResolution(true);
    }

    public void print() {
        final PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);
        Thread t = new Thread(new Runnable() {
            public void run() {
                if (printJob.printDialog()) {
                    try {
                        printJob.print();
                    } catch (PrinterException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        t.setName("ODTDPrinter Thread");
        t.setDaemon(true);
        t.start();

    }

    public int print(final Graphics g, final PageFormat pageFormat, final int pageIndex) throws PrinterException {
        if (pageIndex >= this.renderer.getPrintedPagesNumber()) {
            return (Printable.NO_SUCH_PAGE);
        }
        // Disable Double Buffering
        final RepaintManager currentManager = RepaintManager.currentManager(this.renderer);
        currentManager.setDoubleBufferingEnabled(false);
        // Move to printable area
        final Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        // Adapt zoom
        final double maxPrintWidth = this.renderer.getPrintWidth();
        final double pageWidth = pageFormat.getImageableWidth();
        final double resize = maxPrintWidth / pageWidth;
        this.renderer.setIgnoreMargins(true);
        this.renderer.setResizeFactor(resize);
        this.renderer.setCurrentPage(pageIndex);

        // Paint
        this.renderer.paintComponent(g2d);

        return (Printable.PAGE_EXISTS);

    }
}
