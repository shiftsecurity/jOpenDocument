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
public class TableSubtotalRule {

    protected String tableGroupByFieldNumber;
    protected List<TableSubtotalField> tableSubtotalField;

    /**
     * Gets the value of the tableGroupByFieldNumber property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableGroupByFieldNumber() {
        return this.tableGroupByFieldNumber;
    }

    /**
     * Gets the value of the tableSubtotalField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the tableSubtotalField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableSubtotalField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableSubtotalField }
     * 
     * 
     */
    public List<TableSubtotalField> getTableSubtotalField() {
        if (this.tableSubtotalField == null) {
            this.tableSubtotalField = new ArrayList<TableSubtotalField>();
        }
        return this.tableSubtotalField;
    }

    /**
     * Sets the value of the tableGroupByFieldNumber property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableGroupByFieldNumber(final String value) {
        this.tableGroupByFieldNumber = value;
    }

}
