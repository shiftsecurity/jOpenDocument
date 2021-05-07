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
public class TableSortGroups {

    protected String tableDataType;
    protected String tableOrder;

    /**
     * Gets the value of the tableDataType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDataType() {
        if (this.tableDataType == null) {
            return "automatic";
        } else {
            return this.tableDataType;
        }
    }

    /**
     * Gets the value of the tableOrder property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableOrder() {
        if (this.tableOrder == null) {
            return "ascending";
        } else {
            return this.tableOrder;
        }
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
     * Sets the value of the tableOrder property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableOrder(final String value) {
        this.tableOrder = value;
    }

}
