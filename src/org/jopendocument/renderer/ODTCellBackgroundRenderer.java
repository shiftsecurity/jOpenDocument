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
import java.awt.Graphics2D;

import org.jopendocument.model.style.StyleTableCellProperties;
import org.jopendocument.model.table.TableTableCell;
import org.jopendocument.util.ValueHelper;

public class ODTCellBackgroundRenderer implements ODTCellRenderer {

    public final void draw(final Graphics2D g, final int x, final int y, final int cellWidth, final int cellHeight, final double resizeFactor, final TableTableCell cell,
            final StyleTableCellProperties cellProps) {

        if (cellProps != null) {
            String backgroundColor = cellProps.getBackgroundColor();
            Color c = null;
            if (backgroundColor != null) {
                c = ValueHelper.getColor(backgroundColor);
            }
            // Background
            if (c != null && c != ValueHelper.TRANSPARENT) {
                g.setColor(c);
                g.fillRect(x, y, cellWidth, cellHeight);
            }
        }

    }

}
