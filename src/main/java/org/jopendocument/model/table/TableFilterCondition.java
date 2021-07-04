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
public class TableFilterCondition {

    protected String tableCaseSensitive;
    protected String tableDataType;
    protected String tableFieldNumber;
    protected String tableOperator;
    protected String tableValue;

    /**
     * Gets the value of the tableCaseSensitive property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCaseSensitive() {
        if (this.tableCaseSensitive == null) {
            return "false";
        } else {
            return this.tableCaseSensitive;
        }
    }

    /**
     * Gets the value of the tableDataType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDataType() {
        if (this.tableDataType == null) {
            return "text";
        } else {
            return this.tableDataType;
        }
    }

    /**
     * Gets the value of the tableFieldNumber property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableFieldNumber() {
        return this.tableFieldNumber;
    }

    /**
     * Gets the value of the tableOperator property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableOperator() {
        return this.tableOperator;
    }

    /**
     * Gets the value of the tableValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableValue() {
        return this.tableValue;
    }

    /**
     * Sets the value of the tableCaseSensitive property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCaseSensitive(final String value) {
        this.tableCaseSensitive = value;
    }

    /**
     * Sets the value of the tableDataType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDataType(final String value) {
        this.tableDataType = value;
    }

    /**
     * Sets the value of the tableFieldNumber property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableFieldNumber(final String value) {
        this.tableFieldNumber = value;
    }

    /**
     * Sets the value of the tableOperator property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableOperator(final String value) {
        this.tableOperator = value;
    }

    /**
     * Sets the value of the tableValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableValue(final String value) {
        this.tableValue = value;
    }

}
