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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.jopendocument.model.style.StyleStyle;
import org.jopendocument.model.style.StyleTextProperties;

public class ODTCellTextItem {

    private Graphics2D g2;

    private String text;

    private double resizeFactor;

    private StyleStyle textStyle;

    public ODTCellTextItem(Graphics2D g2, String textSpan, double resizeFactor, StyleStyle textStyle) {
        this.g2 = g2;
        this.text = textSpan;
        this.resizeFactor = resizeFactor;
        this.textStyle = textStyle;
    }

    public ODTCellTextItem(ODTCellTextItem item, String textSpan) {
        this.g2 = item.g2;
        this.text = textSpan;
        this.resizeFactor = item.resizeFactor;
        this.textStyle = item.textStyle;
    }

    public int getHeight() {
        try {
            g2.setFont(this.getFont());
            return g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent();
        } catch (Exception e) {
            e.printStackTrace();
            return 10;
        }
    }

    public int getWidth() {
        g2.setFont(this.getFont());
        return g2.getFontMetrics().stringWidth(getText());
    }

    public int getWidthWithSpace() {
        g2.setFont(this.getFont());
        return g2.getFontMetrics().stringWidth(getText() + " ");
    }

    public int getWidthWithoutSpace() {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            final char c = text.charAt(i);
            if (c != ' ') {
                b.append(c);
            }
        }
        g2.setFont(this.getFont());
        return g2.getFontMetrics().stringWidth(b.toString());
    }

    public Font getFont() {
        if (textStyle != null) {
            final StyleTextProperties styleTextProperties = textStyle.getStyleTextProperties();
            if (styleTextProperties != null) {
                return styleTextProperties.getFont(resizeFactor);
            }
        } else {
            System.err.println("ODTCellTextItem:textStyle null: '" + getText() + "'");
        }
        return null;
    }

    public String getColor() {

        if (textStyle != null) {

            final StyleTextProperties styleTextProperties = textStyle.getStyleTextProperties();
            if (styleTextProperties != null) {
                String color = styleTextProperties.getColor();
                if (color == null) {
                    color = "#000000";
                }
                return color;
            }
        } else {
            System.err.println("ODTCellTextItem:textStyle null: '" + getText() + "'");
        }
        return "#000000";
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextItem:" + this.getText();
    }

}
