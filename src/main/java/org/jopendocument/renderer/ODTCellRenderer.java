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

import org.jopendocument.model.style.StyleTableCellProperties;
import org.jopendocument.model.table.TableTableCell;

/**
 * A renderer for a cell of a spreadsheet
 * 
 */

public interface ODTCellRenderer {

    /**
     * Draws a cell
     * 
     * @param g the graphics on which the cell will be renderered
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @param cellWidth the width of the cell
     * @param cellHeight the height of the cell
     * @param resizeFactor the resize factor applied to the current rendering
     * @param cell the cell to render
     * @param cellProps the properties of the cell
     */
    public void draw(final Graphics2D g, final int x, final int y, final int cellWidth, final int cellHeight, final double resizeFactor, final TableTableCell cell,
            final StyleTableCellProperties cellProps);

}
