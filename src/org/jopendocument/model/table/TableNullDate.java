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
public class TableNullDate {

    protected String tableDateValue;
    protected String tableValueType;

    /**
     * Gets the value of the tableDateValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDateValue() {
        if (this.tableDateValue == null) {
            return "1899-12-30";
        } else {
            return this.tableDateValue;
        }
    }

    /**
     * Gets the value of the tableValueType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableValueType() {
        if (this.tableValueType == null) {
            return "date";
        } else {
            return this.tableValueType;
        }
    }

    /**
     * Sets the value of the tableDateValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDateValue(final String value) {
        this.tableDateValue = value;
    }

    /**
     * Sets the value of the tableValueType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableValueType(final String value) {
        this.tableValueType = value;
    }

}
