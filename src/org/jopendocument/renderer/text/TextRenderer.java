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

package org.jopendocument.renderer.text;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.model.office.OfficeBody;

public class TextRenderer extends JPanel {

    private double resizeFactor;

    private OfficeBody body;

    private static boolean debug = false;

    private TextPage currentPage;

    private boolean paintMaxResolution;

    private OpenDocument od;

    private int currentPageIndex;

    private int printWidthPixel;

    private int printHeightPixel;

    private int nbPages;

    public TextRenderer(OpenDocument doc) {
        this.body = doc.getBody();
        this.setBackground(Color.WHITE);
        this.currentPageIndex = 0;
        this.currentPage = (TextPage) doc.getPrintedPage(this.currentPageIndex);
        setResizeFactor(360);
        this.od = doc;

    }

    public void setCurrentPage(int i) {
        this.currentPageIndex = i;
        this.currentPage = (TextPage) od.getPrintedPage(i);

        updateSize();
        repaint();
    }

    private void updateSize() {
        this.setSize(getPageWidthInPixel(), getPageHeightInPixel());
    }

    public double getPageHeight() {
        return this.getPageHeightInPixel() * resizeFactor;
    }

    public int getPageHeightInPixel() {
        int h = 0;

        return h;
    }

    public double getPageWidth() {
        return this.getPageWidthInPixel() * resizeFactor;
    }

    public int getPageWidthInPixel() {
        int w = 0;
        return w;
    }

    public synchronized void setResizeFactor(double resizeFactor) {
        this.resizeFactor = resizeFactor;

        updateSize();
        repaint();
    }

    public int getPrintWidthInPixel() {
        return printWidthPixel;
    }

    public int getPrintHeightInPixel() {
        return printHeightPixel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        currentPage.render(g2);

    }

    public void printRendererInformation() {
        System.out.println("==== Text Renderer ===");
        System.out.println("Rendering file: " + this.od.getZipFile().getName());
        System.out.println("Max image quality: " + this.paintMaxResolution);
        System.out.println("Current page: " + (this.currentPageIndex + 1) + "  / " + this.getPrintedPagesNumber());
        System.out.println("Width: " + (long) getPageWidthInPixel() + " micrometers (" + getPageWidthInPixel() / 10000 + " cms)");
        System.out.println("Height: " + (long) getPageHeightInPixel() + " micrometers (" + getPageHeightInPixel() / 10000 + " cms)");
        System.out.println("Size in pixels: " + this.getPrintWidthInPixel() + "x" + this.getPrintHeightInPixel());
        System.out.println("Resize factor " + this.resizeFactor);

        System.out.println();
    }

    private int getPrintedPagesNumber() {

        return nbPages;
    }

    public void setDebug(boolean b) {
        debug = true;
    }

}
