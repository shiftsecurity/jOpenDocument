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
public class TableDataPilotTable {

    protected String tableApplicationData;
    protected String tableButtons;
    protected List<Object> tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQueryOrTableSourceServiceOrTableSourceCellRange;
    protected List<TableDataPilotField> tableDataPilotField;
    protected String tableGrandTotal;
    protected String tableIdentifyCategories;
    protected String tableIgnoreEmptyRows;
    protected String tableName;
    protected String tableTargetRangeAddress;

    /**
     * Gets the value of the tableApplicationData property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableApplicationData() {
        return this.tableApplicationData;
    }

    /**
     * Gets the value of the tableButtons property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableButtons() {
        return this.tableButtons;
    }

    /**
     * Gets the value of the
     * tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQueryOrTableSourceServiceOrTableSourceCellRange
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQueryOrTableSourceServiceOrTableSourceCellRange
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQueryOrTableSourceServiceOrTableSourceCellRange().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableDatabaseSourceSql }
     * {@link TableDatabaseSourceTable } {@link TableDatabaseSourceQuery }
     * {@link TableSourceService } {@link TableSourceCellRange }
     * 
     * 
     */
    public List<Object> getTableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQueryOrTableSourceServiceOrTableSourceCellRange() {
        if (this.tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQueryOrTableSourceServiceOrTableSourceCellRange == null) {
            this.tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQueryOrTableSourceServiceOrTableSourceCellRange = new ArrayList<Object>();
        }
        return this.tableDatabaseSourceSqlOrTableDatabaseSourceTableOrTableDatabaseSourceQueryOrTableSourceServiceOrTableSourceCellRange;
    }

    /**
     * Gets the value of the tableDataPilotField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the tableDataPilotField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableDataPilotField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableDataPilotField }
     * 
     * 
     */
    public List<TableDataPilotField> getTableDataPilotField() {
        if (this.tableDataPilotField == null) {
            this.tableDataPilotField = new ArrayList<TableDataPilotField>();
        }
        return this.tableDataPilotField;
    }

    /**
     * Gets the value of the tableGrandTotal property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableGrandTotal() {
        if (this.tableGrandTotal == null) {
            return "both";
        } else {
            return this.tableGrandTotal;
        }
    }

    /**
     * Gets the value of the tableIdentifyCategories property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableIdentifyCategories() {
        if (this.tableIdentifyCategories == null) {
            return "false";
        } else {
            return this.tableIdentifyCategories;
        }
    }

    /**
     * Gets the value of the tableIgnoreEmptyRows property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableIgnoreEmptyRows() {
        if (this.tableIgnoreEmptyRows == null) {
            return "false";
        } else {
            return this.tableIgnoreEmptyRows;
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
     * Gets the value of the tableTargetRangeAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableTargetRangeAddress() {
        return this.tableTargetRangeAddress;
    }

    /**
     * Sets the value of the tableApplicationData property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableApplicationData(final String value) {
        this.tableApplicationData = value;
    }

    /**
     * Sets the value of the tableButtons property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableButtons(final String value) {
        this.tableButtons = value;
    }

    /**
     * Sets the value of the tableGrandTotal property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableGrandTotal(final String value) {
        this.tableGrandTotal = value;
    }

    /**
     * Sets the value of the tableIdentifyCategories property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableIdentifyCategories(final String value) {
        this.tableIdentifyCategories = value;
    }

    /**
     * Sets the value of the tableIgnoreEmptyRows property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableIgnoreEmptyRows(final String value) {
        this.tableIgnoreEmptyRows = value;
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
     * Sets the value of the tableTargetRangeAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableTargetRangeAddress(final String value) {
        this.tableTargetRangeAddress = value;
    }

}
