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
public class TableLabelRange {

    protected String tableDataCellRangeAddress;
    protected String tableLabelCellRangeAddress;
    protected String tableOrientation;

    /**
     * Gets the value of the tableDataCellRangeAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDataCellRangeAddress() {
        return this.tableDataCellRangeAddress;
    }

    /**
     * Gets the value of the tableLabelCellRangeAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableLabelCellRangeAddress() {
        return this.tableLabelCellRangeAddress;
    }

    /**
     * Gets the value of the tableOrientation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableOrientation() {
        return this.tableOrientation;
    }

    /**
     * Sets the value of the tableDataCellRangeAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDataCellRangeAddress(final String value) {
        this.tableDataCellRangeAddress = value;
    }

    /**
     * Sets the value of the tableLabelCellRangeAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableLabelCellRangeAddress(final String value) {
        this.tableLabelCellRangeAddress = value;
    }

    /**
     * Sets the value of the tableOrientation property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableOrientation(final String value) {
        this.tableOrientation = value;
    }

}
