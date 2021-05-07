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
public class TableSubtotalRules {

    protected String tableBindStylesToContent;
    protected String tableCaseSensitive;
    protected String tablePageBreaksOnGroupChange;
    protected List<Object> tableSortGroupsOrTableSubtotalRule;

    /**
     * Gets the value of the tableBindStylesToContent property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableBindStylesToContent() {
        if (this.tableBindStylesToContent == null) {
            return "true";
        } else {
            return this.tableBindStylesToContent;
        }
    }

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
     * Gets the value of the tablePageBreaksOnGroupChange property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTablePageBreaksOnGroupChange() {
        if (this.tablePageBreaksOnGroupChange == null) {
            return "false";
        } else {
            return this.tablePageBreaksOnGroupChange;
        }
    }

    /**
     * Gets the value of the tableSortGroupsOrTableSubtotalRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the tableSortGroupsOrTableSubtotalRule
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableSortGroupsOrTableSubtotalRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableSortGroups }
     * {@link TableSubtotalRule }
     * 
     * 
     */
    public List<Object> getTableSortGroupsOrTableSubtotalRule() {
        if (this.tableSortGroupsOrTableSubtotalRule == null) {
            this.tableSortGroupsOrTableSubtotalRule = new ArrayList<Object>();
        }
        return this.tableSortGroupsOrTableSubtotalRule;
    }

    /**
     * Sets the value of the tableBindStylesToContent property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableBindStylesToContent(final String value) {
        this.tableBindStylesToContent = value;
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
     * Sets the value of the tablePageBreaksOnGroupChange property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTablePageBreaksOnGroupChange(final String value) {
        this.tablePageBreaksOnGroupChange = value;
    }

}
