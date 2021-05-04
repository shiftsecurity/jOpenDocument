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
public class TableDatabaseRange {

    protected String tableContainsHeader;
    protected List<Object> tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQuery;
    protected String tableDisplayFilterButtons;
    protected TableFilter tableFilter;
    protected String tableHasPersistantData;
    protected String tableIsSelection;
    protected String tableName;
    protected String tableOnUpdateKeepSize;
    protected String tableOnUpdateKeepStyles;
    protected String tableOrientation;
    protected String tableRefreshDelay;
    protected TableSort tableSort;
    protected TableSubtotalRules tableSubtotalRules;
    protected String tableTargetRangeAddress;

    /**
     * Gets the value of the tableContainsHeader property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableContainsHeader() {
        if (this.tableContainsHeader == null) {
            return "true";
        } else {
            return this.tableContainsHeader;
        }
    }

    /**
     * Gets the value of the
     * tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableDatabaseSourceSql }
     * {@link TableDatabaseSourceTable } {@link TableDatabaseSourceQuery }
     * 
     * 
     */
    public List<Object> getTableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQuery() {
        if (this.tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQuery == null) {
            this.tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQuery = new ArrayList<Object>();
        }
        return this.tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQuery;
    }

    /**
     * Gets the value of the tableDisplayFilterButtons property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDisplayFilterButtons() {
        if (this.tableDisplayFilterButtons == null) {
            return "false";
        } else {
            return this.tableDisplayFilterButtons;
        }
    }

    /**
     * Gets the value of the tableFilter property.
     * 
     * @return possible object is {@link TableFilter }
     * 
     */
    public TableFilter getTableFilter() {
        return this.tableFilter;
    }

    /**
     * Gets the value of the tableHasPersistantData property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableHasPersistantData() {
        if (this.tableHasPersistantData == null) {
            return "true";
        } else {
            return this.tableHasPersistantData;
        }
    }

    /**
     * Gets the value of the tableIsSelection property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableIsSelection() {
        if (this.tableIsSelection == null) {
            return "false";
        } else {
            return this.tableIsSelection;
        }
    }

    /**
     * Gets the value of the tableName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * Gets the value of the tableOnUpdateKeepSize property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableOnUpdateKeepSize() {
        if (this.tableOnUpdateKeepSize == null) {
            return "true";
        } else {
            return this.tableOnUpdateKeepSize;
        }
    }

    /**
     * Gets the value of the tableOnUpdateKeepStyles property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableOnUpdateKeepStyles() {
        if (this.tableOnUpdateKeepStyles == null) {
            return "false";
        } else {
            return this.tableOnUpdateKeepStyles;
        }
    }

    /**
     * Gets the value of the tableOrientation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableOrientation() {
        if (this.tableOrientation == null) {
            return "row";
        } else {
            return this.tableOrientation;
        }
    }

    /**
     * Gets the value of the tableRefreshDelay property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableRefreshDelay() {
        return this.tableRefreshDelay;
    }

    /**
     * Gets the value of the tableSort property.
     * 
     * @return possible object is {@link TableSort }
     * 
     */
    public TableSort getTableSort() {
        return this.tableSort;
    }

    /**
     * Gets the value of the tableSubtotalRules property.
     * 
     * @return possible object is {@link TableSubtotalRules }
     * 
     */
    public TableSubtotalRules getTableSubtotalRules() {
        return this.tableSubtotalRules;
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
     * Sets the value of the tableContainsHeader property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableContainsHeader(final String value) {
        this.tableContainsHeader = value;
    }

    /**
     * Sets the value of the tableDisplayFilterButtons property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDisplayFilterButtons(final String value) {
        this.tableDisplayFilterButtons = value;
    }

    /**
     * Sets the value of the tableFilter property.
     * 
     * @param value allowed object is {@link TableFilter }
     * 
     */
    public void setTableFilter(final TableFilter value) {
        this.tableFilter = value;
    }

    /**
     * Sets the value of the tableHasPersistantData property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableHasPersistantData(final String value) {
        this.tableHasPersistantData = value;
    }

    /**
     * Sets the value of the tableIsSelection property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableIsSelection(final String value) {
        this.tableIsSelection = value;
    }

    /**
     * Sets the value of the tableName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableName(final String value) {
        this.tableName = value;
    }

    /**
     * Sets the value of the tableOnUpdateKeepSize property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableOnUpdateKeepSize(final String value) {
        this.tableOnUpdateKeepSize = value;
    }

    /**
     * Sets the value of the tableOnUpdateKeepStyles property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableOnUpdateKeepStyles(final String value) {
        this.tableOnUpdateKeepStyles = value;
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
     * Sets the value of the tableRefreshDelay property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableRefreshDelay(final String value) {
        this.tableRefreshDelay = value;
    }

    /**
     * Sets the value of the tableSort property.
     * 
     * @param value allowed object is {@link TableSort }
     * 
     */
    public void setTableSort(final TableSort value) {
        this.tableSort = value;
    }

    /**
     * Sets the value of the tableSubtotalRules property.
     * 
     * @param value allowed object is {@link TableSubtotalRules }
     * 
     */
    public void setTableSubtotalRules(final TableSubtotalRules value) {
        this.tableSubtotalRules = value;
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
