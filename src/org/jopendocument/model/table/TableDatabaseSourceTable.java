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
public class TableDatabaseSourceTable {

    protected String tableDatabaseName;
    protected String tableTableName;

    /**
     * Gets the value of the tableDatabaseName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDatabaseName() {
        return this.tableDatabaseName;
    }

    /**
     * Gets the value of the tableTableName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableTableName() {
        return this.tableTableName;
    }

    /**
     * Sets the value of the tableDatabaseName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDatabaseName(final String value) {
        this.tableDatabaseName = value;
    }

    /**
     * Sets the value of the tableTableName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableTableName(final String value) {
        this.tableTableName = value;
    }

}
