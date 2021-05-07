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
public class TableSourceCellRange {

    protected String tableCellRangeAddress;
    protected TableFilter tableFilter;

    /**
     * Gets the value of the tableCellRangeAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCellRangeAddress() {
        return this.tableCellRangeAddress;
    }

    /**
     * Gets the value of the tableFilter property.
     * 
     * @return possible object is {@link TableFilter }
     * 
     */
    public TableFilter getTableFilter() {
        return this.tableFilter;
    }

    /**
     * Sets the value of the tableCellRangeAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCellRangeAddress(final String value) {
        this.tableCellRangeAddress = value;
    }

    /**
     * Sets the value of the tableFilter property.
     * 
     * @param value allowed object is {@link TableFilter }
     * 
     */
    public void setTableFilter(final TableFilter value) {
        this.tableFilter = value;
    }

}
