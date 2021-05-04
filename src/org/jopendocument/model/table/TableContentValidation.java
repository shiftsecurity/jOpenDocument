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

import org.jopendocument.model.office.OfficeEvents;

/**
 * 
 */
public class TableContentValidation {

    protected String tableAllowEmptyCell;
    protected String tableBaseCellAddress;
    protected String tableCondition;
    protected List<Object> tableErrorMessageOrTableErrorMacroOrOfficeEvents;
    protected TableHelpMessage tableHelpMessage;
    protected String tableName;
    protected String tableShowList;

    /**
     * Gets the value of the tableAllowEmptyCell property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableAllowEmptyCell() {
        return this.tableAllowEmptyCell;
    }

    /**
     * Gets the value of the tableBaseCellAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableBaseCellAddress() {
        return this.tableBaseCellAddress;
    }

    /**
     * Gets the value of the tableCondition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCondition() {
        return this.tableCondition;
    }

    /**
     * Gets the value of the tableErrorMessageOrTableErrorMacroOrOfficeEvents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * tableErrorMessageOrTableErrorMacroOrOfficeEvents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableErrorMessageOrTableErrorMacroOrOfficeEvents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableErrorMessage }
     * {@link TableErrorMacro } {@link OfficeEvents }
     * 
     * 
     */
    public List<Object> getTableErrorMessageOrTableErrorMacroOrOfficeEvents() {
        if (this.tableErrorMessageOrTableErrorMacroOrOfficeEvents == null) {
            this.tableErrorMessageOrTableErrorMacroOrOfficeEvents = new ArrayList<Object>();
        }
        return this.tableErrorMessageOrTableErrorMacroOrOfficeEvents;
    }

    /**
     * Gets the value of the tableHelpMessage property.
     * 
     * @return possible object is {@link TableHelpMessage }
     * 
     */
    public TableHelpMessage getTableHelpMessage() {
        return this.tableHelpMessage;
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
     * Gets the value of the tableShowList property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableShowList() {
        return this.tableShowList;
    }

    /**
     * Sets the value of the tableAllowEmptyCell property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableAllowEmptyCell(final String value) {
        this.tableAllowEmptyCell = value;
    }

    /**
     * Sets the value of the tableBaseCellAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableBaseCellAddress(final String value) {
        this.tableBaseCellAddress = value;
    }

    /**
     * Sets the value of the tableCondition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCondition(final String value) {
        this.tableCondition = value;
    }

    /**
     * Sets the value of the tableHelpMessage property.
     * 
     * @param value allowed object is {@link TableHelpMessage }
     * 
     */
    public void setTableHelpMessage(final TableHelpMessage value) {
        this.tableHelpMessage = value;
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
     * Sets the value of the tableShowList property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableShowList(final String value) {
        this.tableShowList = value;
    }

}
