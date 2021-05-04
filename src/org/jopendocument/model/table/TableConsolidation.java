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
public class TableConsolidation {

    protected String tableFunction;
    protected String tableLinkToSourceData;
    protected String tableSourceCellRangeAddresses;
    protected String tableTargetCellAddress;
    protected String tableUseLabel;

    /**
     * Gets the value of the tableFunction property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableFunction() {
        return this.tableFunction;
    }

    /**
     * Gets the value of the tableLinkToSourceData property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableLinkToSourceData() {
        if (this.tableLinkToSourceData == null) {
            return "false";
        } else {
            return this.tableLinkToSourceData;
        }
    }

    /**
     * Gets the value of the tableSourceCellRangeAddresses property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableSourceCellRangeAddresses() {
        return this.tableSourceCellRangeAddresses;
    }

    /**
     * Gets the value of the tableTargetCellAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableTargetCellAddress() {
        return this.tableTargetCellAddress;
    }

    /**
     * Gets the value of the tableUseLabel property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableUseLabel() {
        if (this.tableUseLabel == null) {
            return "none";
        } else {
            return this.tableUseLabel;
        }
    }

    /**
     * Sets the value of the tableFunction property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableFunction(final String value) {
        this.tableFunction = value;
    }

    /**
     * Sets the value of the tableLinkToSourceData property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableLinkToSourceData(final String value) {
        this.tableLinkToSourceData = value;
    }

    /**
     * Sets the value of the tableSourceCellRangeAddresses property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableSourceCellRangeAddresses(final String value) {
        this.tableSourceCellRangeAddresses = value;
    }

    /**
     * Sets the value of the tableTargetCellAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableTargetCellAddress(final String value) {
        this.tableTargetCellAddress = value;
    }

    /**
     * Sets the value of the tableUseLabel property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableUseLabel(final String value) {
        this.tableUseLabel = value;
    }

}
