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
import java.awt.Image;
import java.util.List;

import org.jopendocument.model.draw.DrawFrame;
import org.jopendocument.model.draw.DrawImage;
import org.jopendocument.model.style.StyleTableCellProperties;
import org.jopendocument.model.table.TableTableCell;
import org.jopendocument.util.ValueHelper;

public class ODTCellImageRenderer implements ODTCellRenderer {
    boolean maxResolution;

    public void setPaintMaxResolution(boolean maxResolution) {
        this.maxResolution = maxResolution;
    }

    public void draw(Graphics2D g, int x, int y, int cellWidth, int cellHeight, double resizeFactor, TableTableCell cell, StyleTableCellProperties cellProps) {
        List<DrawFrame> drawFrames = cell.getDrawFrames();
        if (drawFrames != null) {
            for (DrawFrame frame : drawFrames) {
                DrawImage dIm = frame.getDrawImage();
                if (dIm != null) {
                    double dx = ValueHelper.getLength(frame.getSvgX()) / resizeFactor;
                    double dy = ValueHelper.getLength(frame.getSvgY()) / resizeFactor;
                    double w = ValueHelper.getLength(frame.getSvgWidth()) / resizeFactor;
                    double h = ValueHelper.getLength(frame.getSvgHeight()) / resizeFactor;
                    Image im = null;
                    if (!maxResolution) {
                        im = cell.getRow().getTable().getSpreadsheet().getBody().getDocument().getImage(dIm.getXlinkHref(), (int) w, (int) h);
                        g.drawImage(im, x + (int) Math.round(dx), y + (int) Math.round(dy), null);
                    } else {
                        im = cell.getRow().getTable().getSpreadsheet().getBody().getDocument().getImage(dIm.getXlinkHref());
                        g.drawImage(im, x + (int) Math.round(dx), y + (int) Math.round(dy), (int) Math.round(w), (int) Math.round(h), null);
                    }
                }
            }
        }
    }

}
