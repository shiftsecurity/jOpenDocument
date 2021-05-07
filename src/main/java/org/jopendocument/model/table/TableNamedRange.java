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
public class TableNamedRange {

    protected String tableBaseCellAddress;
    protected String tableCellRangeAddress;
    protected String tableName;
    protected String tableRangeUsableAs;

    /**
     * Gets the value of the tableBaseCellAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableBaseCellAddress() {
        return this.tableBaseCellAddress;
    }

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
     * Gets the value of the tableName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * Gets the value of the tableRangeUsableAs property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableRangeUsableAs() {
        if (this.tableRangeUsableAs == null) {
            return "none";
        } else {
            return this.tableRangeUsableAs;
        }
    }

    /**
     * Sets the value of the tableBaseCellAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableBaseCellAddress(final String value) {
        this.tableBaseCellAddress = value;
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
     * Sets the value of the tableName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableName(final String value) {
        this.tableName = value;
    }

    /**
     * Sets the value of the tableRangeUsableAs property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableRangeUsableAs(final String value) {
        this.tableRangeUsableAs = value;
    }

}
