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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class TableTrackedChanges {

    protected List<Object> tableCellContentChangeOrTableInsertionOrTableDeletionOrTableMovementOrTableRejection;
    protected String tableProtected;
    protected String tableProtectionKey;
    protected String tableTrackChanges;

    /**
     * Gets the value of the
     * tableCellContentChangeOrTableInsertionOrTableDeletionOrTableMovementOrTableRejection
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * tableCellContentChangeOrTableInsertionOrTableDeletionOrTableMovementOrTableRejection
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableCellContentChangeOrTableInsertionOrTableDeletionOrTableMovementOrTableRejection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableCellContentChange }
     * {@link TableInsertion } {@link TableDeletion } {@link TableMovement } {@link TableRejection }
     * 
     * 
     */
    public List<Object> getTableCellContentChangeOrTableInsertionOrTableDeletionOrTableMovementOrTableRejection() {
        if (this.tableCellContentChangeOrTableInsertionOrTableDeletionOrTableMovementOrTableRejection == null) {
            this.tableCellContentChangeOrTableInsertionOrTableDeletionOrTableMovementOrTableRejection = new ArrayList<Object>();
        }
        return this.tableCellContentChangeOrTableInsertionOrTableDeletionOrTableMovementOrTableRejection;
    }

    /**
     * Gets the value of the tableProtected property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableProtected() {
        if (this.tableProtected == null) {
            return "false";
        } else {
            return this.tableProtected;
        }
    }

    /**
     * Gets the value of the tableProtectionKey property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableProtectionKey() {
        return this.tableProtectionKey;
    }

    /**
     * Gets the value of the tableTrackChanges property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableTrackChanges() {
        if (this.tableTrackChanges == null) {
            return "true";
        } else {
            return this.tableTrackChanges;
        }
    }

    /**
     * Sets the value of the tableProtected property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableProtected(final String value) {
        this.tableProtected = value;
    }

    /**
     * Sets the value of the tableProtectionKey property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableProtectionKey(final String value) {
        this.tableProtectionKey = value;
    }

    /**
     * Sets the value of the tableTrackChanges property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableTrackChanges(final String value) {
        this.tableTrackChanges = value;
    }

}
