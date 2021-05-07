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

import org.jopendocument.model.office.OfficeChangeInfo;

/**
 * 
 */
public class TableRejection {

    protected OfficeChangeInfo officeChangeInfo;
    protected String tableAcceptanceState;
    protected TableDeletions tableDeletions;
    protected TableDependences tableDependences;
    protected String tableId;
    protected String tableRejectingChangeId;

    /**
     * Gets the value of the officeChangeInfo property.
     * 
     * @return possible object is {@link OfficeChangeInfo }
     * 
     */
    public OfficeChangeInfo getOfficeChangeInfo() {
        return this.officeChangeInfo;
    }

    /**
     * Gets the value of the tableAcceptanceState property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableAcceptanceState() {
        if (this.tableAcceptanceState == null) {
            return "pending";
        } else {
            return this.tableAcceptanceState;
        }
    }

    /**
     * Gets the value of the tableDeletions property.
     * 
     * @return possible object is {@link TableDeletions }
     * 
     */
    public TableDeletions getTableDeletions() {
        return this.tableDeletions;
    }

    /**
     * Gets the value of the tableDependences property.
     * 
     * @return possible object is {@link TableDependences }
     * 
     */
    public TableDependences getTableDependences() {
        return this.tableDependences;
    }

    /**
     * Gets the value of the tableId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableId() {
        return this.tableId;
    }

    /**
     * Gets the value of the tableRejectingChangeId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableRejectingChangeId() {
        return this.tableRejectingChangeId;
    }

    /**
     * Sets the value of the officeChangeInfo property.
     * 
     * @param value allowed object is {@link OfficeChangeInfo }
     * 
     */
    public void setOfficeChangeInfo(final OfficeChangeInfo value) {
        this.officeChangeInfo = value;
    }

    /**
     * Sets the value of the tableAcceptanceState property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableAcceptanceState(final String value) {
        this.tableAcceptanceState = value;
    }

    /**
     * Sets the value of the tableDeletions property.
     * 
     * @param value allowed object is {@link TableDeletions }
     * 
     */
    public void setTableDeletions(final TableDeletions value) {
        this.tableDeletions = value;
    }

    /**
     * Sets the value of the tableDependences property.
     * 
     * @param value allowed object is {@link TableDependences }
     * 
     */
    public void setTableDependences(final TableDependences value) {
        this.tableDependences = value;
    }

    /**
     * Sets the value of the tableId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableId(final String value) {
        this.tableId = value;
    }

    /**
     * Sets the value of the tableRejectingChangeId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableRejectingChangeId(final String value) {
        this.tableRejectingChangeId = value;
    }

}
