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
public class TableDetective {

    protected List<TableHighlightedRange> tableHighlightedRange;
    protected List<TableOperation> tableOperation;

    /**
     * Gets the value of the tableHighlightedRange property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the tableHighlightedRange property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableHighlightedRange().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableHighlightedRange }
     * 
     * 
     */
    public List<TableHighlightedRange> getTableHighlightedRange() {
        if (this.tableHighlightedRange == null) {
            this.tableHighlightedRange = new ArrayList<TableHighlightedRange>();
        }
        return this.tableHighlightedRange;
    }

    /**
     * Gets the value of the tableOperation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the tableOperation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableOperation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableOperation }
     * 
     * 
     */
    public List<TableOperation> getTableOperation() {
        if (this.tableOperation == null) {
            this.tableOperation = new ArrayList<TableOperation>();
        }
        return this.tableOperation;
    }

}
