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
public class TableDataPilotField {

    protected TableDataPilotLevel tableDataPilotLevel;
    protected String tableFunction;
    protected String tableIsDataLayoutField;
    protected String tableOrientation;
    protected String tableSourceFieldName;
    protected String tableUsedHierarchy;

    /**
     * Gets the value of the tableDataPilotLevel property.
     * 
     * @return possible object is {@link TableDataPilotLevel }
     * 
     */
    public TableDataPilotLevel getTableDataPilotLevel() {
        return this.tableDataPilotLevel;
    }

    /**
     * Gets the value of the tableFunction property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableFunction() {
        return this.tableFunction;
    }

    /**
     * Gets the value of the tableIsDataLayoutField property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableIsDataLayoutField() {
        if (this.tableIsDataLayoutField == null) {
            return "false";
        } else {
            return this.tableIsDataLayoutField;
        }
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
     * Gets the value of the tableSourceFieldName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableSourceFieldName() {
        return this.tableSourceFieldName;
    }

    /**
     * Gets the value of the tableUsedHierarchy property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableUsedHierarchy() {
        if (this.tableUsedHierarchy == null) {
            return "1";
        } else {
            return this.tableUsedHierarchy;
        }
    }

    /**
     * Sets the value of the tableDataPilotLevel property.
     * 
     * @param value allowed object is {@link TableDataPilotLevel }
     * 
     */
    public void setTableDataPilotLevel(final TableDataPilotLevel value) {
        this.tableDataPilotLevel = value;
    }

    /**
     * Sets the value of the tableFunction property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableFunction(final String value) {
        this.tableFunction = value;
    }

    /**
     * Sets the value of the tableIsDataLayoutField property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableIsDataLayoutField(final String value) {
        this.tableIsDataLayoutField = value;
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

    /**
     * Sets the value of the tableSourceFieldName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableSourceFieldName(final String value) {
        this.tableSourceFieldName = value;
    }

    /**
     * Sets the value of the tableUsedHierarchy property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableUsedHierarchy(final String value) {
        this.tableUsedHierarchy = value;
    }

}
