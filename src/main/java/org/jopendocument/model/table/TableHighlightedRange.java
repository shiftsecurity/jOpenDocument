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
public class TableHighlightedRange {

    protected String tableCellRangeAddress;
    protected String tableContainsError;
    protected String tableDirection;
    protected String tableMarkedInvalid;

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
     * Gets the value of the tableContainsError property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableContainsError() {
        return this.tableContainsError;
    }

    /**
     * Gets the value of the tableDirection property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDirection() {
        return this.tableDirection;
    }

    /**
     * Gets the value of the tableMarkedInvalid property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableMarkedInvalid() {
        return this.tableMarkedInvalid;
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
     * Sets the value of the tableContainsError property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableContainsError(final String value) {
        this.tableContainsError = value;
    }

    /**
     * Sets the value of the tableDirection property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDirection(final String value) {
        this.tableDirection = value;
    }

    /**
     * Sets the value of the tableMarkedInvalid property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableMarkedInvalid(final String value) {
        this.tableMarkedInvalid = value;
    }

}
