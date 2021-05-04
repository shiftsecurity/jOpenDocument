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
public class TableDatabaseSourceSql {

    protected String tableDatabaseName;
    protected String tableParseSqlStatements;
    protected String tableSqlStatement;

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
     * Gets the value of the tableParseSqlStatements property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableParseSqlStatements() {
        if (this.tableParseSqlStatements == null) {
            return "false";
        } else {
            return this.tableParseSqlStatements;
        }
    }

    /**
     * Gets the value of the tableSqlStatement property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableSqlStatement() {
        return this.tableSqlStatement;
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
     * Sets the value of the tableParseSqlStatements property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableParseSqlStatements(final String value) {
        this.tableParseSqlStatements = value;
    }

    /**
     * Sets the value of the tableSqlStatement property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableSqlStatement(final String value) {
        this.tableSqlStatement = value;
    }

}
