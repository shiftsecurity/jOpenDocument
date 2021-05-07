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
public class TableSort {

    protected String tableAlgorithm;
    protected String tableBindStylesToContent;
    protected String tableCaseSensitive;
    protected String tableCountry;
    protected String tableLanguage;
    protected List<TableSortBy> tableSortBy;
    protected String tableTargetRangeAddress;

    /**
     * Gets the value of the tableAlgorithm property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableAlgorithm() {
        return this.tableAlgorithm;
    }

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
     * Gets the value of the tableCountry property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCountry() {
        return this.tableCountry;
    }

    /**
     * Gets the value of the tableLanguage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableLanguage() {
        return this.tableLanguage;
    }

    /**
     * Gets the value of the tableSortBy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the tableSortBy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableSortBy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableSortBy }
     * 
     * 
     */
    public List<TableSortBy> getTableSortBy() {
        if (this.tableSortBy == null) {
            this.tableSortBy = new ArrayList<TableSortBy>();
        }
        return this.tableSortBy;
    }

    /**
     * Gets the value of the tableTargetRangeAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableTargetRangeAddress() {
        return this.tableTargetRangeAddress;
    }

    /**
     * Sets the value of the tableAlgorithm property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableAlgorithm(final String value) {
        this.tableAlgorithm = value;
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
     * Sets the value of the tableCountry property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCountry(final String value) {
        this.tableCountry = value;
    }

    /**
     * Sets the value of the tableLanguage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableLanguage(final String value) {
        this.tableLanguage = value;
    }

    /**
     * Sets the value of the tableTargetRangeAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableTargetRangeAddress(final String value) {
        this.tableTargetRangeAddress = value;
    }

}
