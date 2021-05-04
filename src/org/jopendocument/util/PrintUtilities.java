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

package org.jopendocument.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

/**
 * A simple way to print a component
 */
public class PrintUtilities implements Printable {
    private Component componentToBePrinted;

    public PrintUtilities(Component componentToBePrinted) {
        this.componentToBePrinted = componentToBePrinted;
    }

    public static void printComponent(Component c) {
        new PrintUtilities(c).print();
    }

    public void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException e) {
                System.out.println("Error printing: " + e);
            }
        }
    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return (NO_SUCH_PAGE);
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        disableDoubleBuffering(componentToBePrinted);
        componentToBePrinted.paint(g2d);
        enableDoubleBuffering(componentToBePrinted);
        return (PAGE_EXISTS);

    }

    /**
     * 
     * Disable double buffering painting of a Component
     * 
     * @param c the component
     */
    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    /**
     * 
     * Enable double buffering painting of a Component
     * 
     * @param c the component
     */
    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }
}
