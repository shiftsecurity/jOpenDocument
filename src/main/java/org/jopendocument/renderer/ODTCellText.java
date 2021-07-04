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

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.model.style.StyleParagraphProperties;
import org.jopendocument.model.style.StyleStyle;
import org.jopendocument.model.style.StyleTextProperties;
import org.jopendocument.model.table.TableTableCell;
import org.jopendocument.model.text.TextP;
import org.jopendocument.model.text.TextSpan;

public class ODTCellText {

    private List<ODTCellTextLine> lines = new ArrayList<ODTCellTextLine>(2);

    private List<TextP> textP;

    private Graphics2D g2;

    private double resizeFactor;

    private StyleStyle cellTextStyle;

    public ODTCellText(Graphics2D g, List<TextP> textp, double resizeFactor, StyleStyle cellStyle) {

        this.textP = textp;
        this.g2 = g;
        this.resizeFactor = resizeFactor;

        if (cellStyle == null) {
            throw new IllegalArgumentException("Default style null");
        }
        this.cellTextStyle = cellStyle;

        computeItems();
    }

    public String getFullText() {
        String t = "";
        for (TextP text : this.textP) {
            List<TextSpan> lt = text.getTextSpans();
            for (TextSpan tp : lt) {
                if (tp.getValue() != null) {
                    t += tp.getValue();
                }
            }
        }

        return t;
    }

    public boolean isEmpty() {
        for (TextP text : this.textP) {
            List<TextSpan> lt = text.getTextSpans();
            for (TextSpan tp : lt) {
                if (tp.getValue() != null && tp.getValue().length() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getHeight() {
        int height = 1;
        for (ODTCellTextLine line : this.lines) {
            height += Math.round(line.getHeight() * 1.55f);
        }
        return height;
    }

    public int getWidth() {
        int width = 0;
        for (ODTCellTextLine line : this.lines) {
            width = Math.max(width, line.getWidth());
        }
        return width;

    }

    public void draw(TableTableCell cell, int cellWidth, int padding, int x, int startY) {
        int y = startY - this.getHeight();

        for (ODTCellTextLine line : this.lines) {

            boolean justify = false;
            int offsetX = 0;
            StyleParagraphProperties paragraphProps = cell.getStyle().getParagraphProperties();
            if (paragraphProps != null) {
                String textAlign = paragraphProps.getTextAlign();
                if (textAlign != null) {
                    if (textAlign.equals("center")) {
                        int strWidth = line.getWidth();
                        offsetX = (cellWidth - strWidth) / 2;
                    } else if (textAlign.equals("end")) {
                        int strWidth = line.getWidth();
                        offsetX = cellWidth - strWidth - padding;
                    } else if (textAlign.equals("justify")) {
                        justify = true;
                        offsetX += padding;
                    } else {
                        // Left
                        offsetX += padding;
                    }
                } else {
                    final String tableValueType = cell.getTableValueType();
                    if (tableValueType != null && tableValueType.equals("float")) {
                        int strWidth = line.getWidth();
                        offsetX = cellWidth - strWidth - padding;
                    } else {
                        // Left
                        offsetX += padding;
                    }
                }
            } else {
                final String tableValueType = cell.getTableValueType();

                if (tableValueType != null && tableValueType.equals("float")) {
                    int strWidth = line.getWidth();
                    offsetX = cellWidth - strWidth - padding;
                } else {
                    // Left
                    offsetX += padding;
                }
            }
            y += Math.round(line.getHeight() * 1.55f);
            if (!justify) {
                line.draw(g2, x + offsetX, y);
            } else {
                line.drawJustified(g2, resizeFactor, x + offsetX, y, padding, cellWidth);
            }
        }

    }

    private void computeItems() {
        for (TextP text : this.textP) {
            final List<TextSpan> lt = text.getTextSpans();
            ODTCellTextLine line = new ODTCellTextLine();
            for (TextSpan textpan : lt) {
                textpan.setTextStyle(mergeStyle(cellTextStyle, textpan.getTextStyle()));
                ODTCellTextItem item = new ODTCellTextItem(g2, textpan.getValue(), resizeFactor, textpan.getTextStyle());
                line.add(item);
            }
            lines.add(line);
        }
    }

    private StyleStyle mergeStyle(StyleStyle cellTextStyle, StyleStyle spanTextStyle) {
        StyleStyle s = new StyleStyle();
        StyleTextProperties cellTextProperties = cellTextStyle.getStyleTextProperties();
        StyleTextProperties spanTextProperties = null;
        if (spanTextStyle != null) {
            spanTextProperties = spanTextStyle.getStyleTextProperties();
        }
        s.setTextProperties(mergeTextProperties(cellTextProperties, spanTextProperties));

        return s;
    }

    private StyleTextProperties mergeTextProperties(StyleTextProperties cellTextProperties, StyleTextProperties spanTextProperties) {

        // Merge font name
        String fontName = null;
        if (spanTextProperties != null) {
            fontName = spanTextProperties.getFontName();
        }
        if (fontName == null && cellTextProperties != null) {
            fontName = cellTextProperties.getFontName();
        }
        if (fontName == null) {
            fontName = "Arial";
        }
        // Merge font size
        String fontSize = null;
        if (spanTextProperties != null) {
            fontSize = spanTextProperties.getFontSize();
        }
        if (fontSize == null && cellTextProperties != null) {
            fontSize = cellTextProperties.getFontSize();
        }
        if (fontSize == null) {
            fontSize = "11pt";
        }
        // Merge font weight
        String fontWeight = null;
        if (spanTextProperties != null) {
            fontWeight = spanTextProperties.getFontWeight();
        }
        if (fontWeight == null && cellTextProperties != null) {
            fontWeight = cellTextProperties.getFontWeight();
        }
        if (fontWeight == null) {
            fontWeight = "normal";
        }
        // Merge font style
        String fontStyle = null;
        if (spanTextProperties != null) {
            fontStyle = spanTextProperties.getFontStyle();
        }
        if (fontStyle == null && cellTextProperties != null) {
            fontStyle = cellTextProperties.getFontStyle();
        }

        // Merge font color
        String fontColor = null;
        if (spanTextProperties != null) {
            fontColor = spanTextProperties.getColor();

        }
        if (fontColor == null && cellTextProperties != null) {
            fontColor = cellTextProperties.getColor();

        }
        if (fontColor == null) {
            fontColor = "#000000";
        }

        // OK

        return StyleTextProperties.getStyleTextProperties(fontName, fontSize, fontWeight, fontStyle, fontColor);
    }

}
