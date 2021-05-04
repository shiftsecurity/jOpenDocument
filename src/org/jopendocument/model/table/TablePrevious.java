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

package org.jopendocument.model.table;

/**
 * 
 */
public class TablePrevious {

    protected TableChangeTrackTableCell tableChangeTrackTableCell;
    protected String tableId;

    /**
     * Gets the value of the tableChangeTrackTableCell property.
     * 
     * @return possible object is {@link TableChangeTrackTableCell }
     * 
     */
    public TableChangeTrackTableCell getTableChangeTrackTableCell() {
        return this.tableChangeTrackTableCell;
    }

    /**
     * Gets the value of the tableId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableId() {
        return this.tableId;
    }

    /**
     * Sets the value of the tableChangeTrackTableCell property.
     * 
     * @param value allowed object is {@link TableChangeTrackTableCell }
     * 
     */
    public void setTableChangeTrackTableCell(final TableChangeTrackTableCell value) {
        this.tableChangeTrackTableCell = value;
    }

    /**
     * Sets the value of the tableId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableId(final String value) {
        this.tableId = value;
    }

}
