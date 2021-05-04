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

import org.jopendocument.model.office.OfficeDdeSource;

/**
 * 
 */
public class TableDdeLink {

    protected OfficeDdeSource officeDdeSource;
    protected TableTable tableTable;

    /**
     * Gets the value of the officeDdeSource property.
     * 
     * @return possible object is {@link OfficeDdeSource }
     * 
     */
    public OfficeDdeSource getOfficeDdeSource() {
        return this.officeDdeSource;
    }

    /**
     * Gets the value of the tableTable property.
     * 
     * @return possible object is {@link TableTable }
     * 
     */
    public TableTable getTableTable() {
        return this.tableTable;
    }

    /**
     * Sets the value of the officeDdeSource property.
     * 
     * @param value allowed object is {@link OfficeDdeSource }
     * 
     */
    public void setOfficeDdeSource(final OfficeDdeSource value) {
        this.officeDdeSource = value;
    }

    /**
     * Sets the value of the tableTable property.
     * 
     * @param value allowed object is {@link TableTable }
     * 
     */
    public void setTableTable(final TableTable value) {
        this.tableTable = value;
    }

}
