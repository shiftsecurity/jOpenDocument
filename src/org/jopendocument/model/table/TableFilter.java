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
public class TableFilter {

    protected String tableConditionSource;
    protected String tableConditionSourceRangeAddress;
    protected String tableDisplayDuplicates;
    protected List<Object> tableFilterConditionOrTableFilterAndOrTableFilterOr;
    protected String tableTargetRangeAddress;

    /**
     * Gets the value of the tableConditionSource property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableConditionSource() {
        if (this.tableConditionSource == null) {
            return "self";
        } else {
            return this.tableConditionSource;
        }
    }

    /**
     * Gets the value of the tableConditionSourceRangeAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableConditionSourceRangeAddress() {
        return this.tableConditionSourceRangeAddress;
    }

    /**
     * Gets the value of the tableDisplayDuplicates property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDisplayDuplicates() {
        if (this.tableDisplayDuplicates == null) {
            return "true";
        } else {
            return this.tableDisplayDuplicates;
        }
    }

    /**
     * Gets the value of the tableFilterConditionOrTableFilterAndOrTableFilterOr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * tableFilterConditionOrTableFilterAndOrTableFilterOr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableFilterConditionOrTableFilterAndOrTableFilterOr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableFilterCondition }
     * {@link TableFilterAnd } {@link TableFilterOr }
     * 
     * 
     */
    public List<Object> getTableFilterConditionOrTableFilterAndOrTableFilterOr() {
        if (this.tableFilterConditionOrTableFilterAndOrTableFilterOr == null) {
            this.tableFilterConditionOrTableFilterAndOrTableFilterOr = new ArrayList<Object>();
        }
        return this.tableFilterConditionOrTableFilterAndOrTableFilterOr;
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
     * Sets the value of the tableConditionSource property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableConditionSource(final String value) {
        this.tableConditionSource = value;
    }

    /**
     * Sets the value of the tableConditionSourceRangeAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableConditionSourceRangeAddress(final String value) {
        this.tableConditionSourceRangeAddress = value;
    }

    /**
     * Sets the value of the tableDisplayDuplicates property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDisplayDuplicates(final String value) {
        this.tableDisplayDuplicates = value;
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
