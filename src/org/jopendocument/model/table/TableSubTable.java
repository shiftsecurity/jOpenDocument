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
public class TableSubTable {

    protected List<Object> tableTableColumnsOrTableTableColumnOrTableTableColumnGroupOrTableTableHeaderColumns;
    protected List<Object> tableTableRowsOrTableTableRowOrTableTableRowGroupOrTableTableHeaderRows;

    /**
     * Gets the value of the
     * tableTableColumnsOrTableTableColumnOrTableTableColumnGroupOrTableTableHeaderColumns property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * tableTableColumnsOrTableTableColumnOrTableTableColumnGroupOrTableTableHeaderColumns property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableTableColumnsOrTableTableColumnOrTableTableColumnGroupOrTableTableHeaderColumns().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableTableColumns }
     * {@link TableTableColumn } {@link TableTableColumnGroup } {@link TableTableHeaderColumns }
     * 
     * 
     */
    public List<Object> getTableTableColumnsOrTableTableColumnOrTableTableColumnGroupOrTableTableHeaderColumns() {
        if (this.tableTableColumnsOrTableTableColumnOrTableTableColumnGroupOrTableTableHeaderColumns == null) {
            this.tableTableColumnsOrTableTableColumnOrTableTableColumnGroupOrTableTableHeaderColumns = new ArrayList<Object>();
        }
        return this.tableTableColumnsOrTableTableColumnOrTableTableColumnGroupOrTableTableHeaderColumns;
    }

    /**
     * Gets the value of the tableTableRowsOrTableTableRowOrTableTableRowGroupOrTableTableHeaderRows
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * tableTableRowsOrTableTableRowOrTableTableRowGroupOrTableTableHeaderRows property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableTableRowsOrTableTableRowOrTableTableRowGroupOrTableTableHeaderRows().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableTableRows }
     * {@link TableTableRow } {@link TableTableRowGroup } {@link TableTableHeaderRows }
     * 
     * 
     */
    public List<Object> getTableTableRowsOrTableTableRowOrTableTableRowGroupOrTableTableHeaderRows() {
        if (this.tableTableRowsOrTableTableRowOrTableTableRowGroupOrTableTableHeaderRows == null) {
            this.tableTableRowsOrTableTableRowOrTableTableRowGroupOrTableTableHeaderRows = new ArrayList<Object>();
        }
        return this.tableTableRowsOrTableTableRowOrTableTableRowGroupOrTableTableHeaderRows;
    }

}
