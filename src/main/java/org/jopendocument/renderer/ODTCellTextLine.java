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

package org.jopendocument.renderer;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.util.ValueHelper;

public class ODTCellTextLine {

    private List<ODTCellTextItem> items = new ArrayList<ODTCellTextItem>(2);
    private int maxH = 0;
    int w = 0;
    public int getHeight() {
        return maxH;
    }

    public int getWidth() {

        return w;
    }

    public void draw(Graphics2D g2, int startX, int y) {
        int x = startX;
        final int size = items.size();
        for (int i = 0; i < size; i++) {
            final ODTCellTextItem item = items.get(i);
            final Font f = item.getFont();
            g2.setFont(f);
            g2.setColor(ValueHelper.getColor(item.getColor()));
            final String text = item.getText();
            if (text != null) {
                g2.drawString(text, x, y);
                x += item.getWidth();
            }
        }

    }

    public void drawJustified(Graphics2D g2, double resizeFactor, int x, int y, int padding, int cellWidth) {
        final int maxLineWidth = cellWidth - 2 * padding;
        int w = 0;

        ODTCellTextLineItem currentLine = new ODTCellTextLineItem();
        final ArrayList<ODTCellTextLineItem> lines = new ArrayList<ODTCellTextLineItem>();
        lines.add(currentLine);
        int maxHeight = 0;
        List<ODTCellTextItem> smallitems = splitAtSpaces(this.items);
        // Split the elements in lines
        for (ODTCellTextItem item : smallitems) {
            final int widthWithSpace = item.getWidthWithSpace();
            w += widthWithSpace;

            if (w > maxLineWidth) {

                currentLine = new ODTCellTextLineItem();

                maxHeight = 0;
                w = widthWithSpace;
                lines.add(currentLine);
            }
            final int height = item.getHeight();
            if (height > maxHeight) {
                maxHeight = height;
            }

            currentLine.addItem(item);
        }
        int cx = x + padding;
        int cy = y;
        for (int i = 0; i < lines.size(); i++) {
            // For every line...
            final ODTCellTextLineItem line = lines.get(i);

            int addSpace = 0;
            // totalSpace is the empty space that should be equally distributed between words
            int totalSpace = maxLineWidth - line.getTotalWidthWithoutSpace();
            if (line.getSize() > 1 && totalSpace > 0 && i < lines.size() - 1) {
                addSpace = totalSpace / (line.getSize() - 1);
            }

            for (ODTCellTextItem item : line.getItems()) {
                Font f = item.getFont();

                g2.setFont(f);
                g2.setColor(ValueHelper.getColor(item.getColor()));
                String text = item.getText();

                if (text != null) {
                    g2.drawString(text, cx, cy);
                    cx += item.getWidth();
                    if (text.charAt(0) == ' ' || text.charAt(text.length() - 1) == ' ') {
                        cx += addSpace;
                    }
                }
            }
            cy += Math.round(line.getHeight() * 1.55f);
            cx = x + padding;
        }
    }

    private List<ODTCellTextItem> splitAtSpaces(List<ODTCellTextItem> items) {
        final List<ODTCellTextItem> result = new ArrayList<ODTCellTextItem>();
        for (ODTCellTextItem item : items) {
            final String text = item.getText();
            int s = 0;
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c == ' ' && i > 0) {
                    String subString = text.substring(s, i + 1);
                    ODTCellTextItem it = new ODTCellTextItem(item, subString);
                    result.add(it);
                    s = i + 1;
                }
            }
            if (s < text.length()) {
                String subString = text.substring(s, text.length());
                ODTCellTextItem it = new ODTCellTextItem(item, subString);
                result.add(it);
            }
        }

        return result;
    }

    public void add(ODTCellTextItem item) {
        this.items.add(item);
        int height = item.getHeight();
        if (height > maxH) {
            maxH = height;
        }
        this.w += item.getWidth();
    }
}
