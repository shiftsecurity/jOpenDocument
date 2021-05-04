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
public class TableIteration {

    protected String tableMaximumDifference;
    protected String tableStatus;
    protected String tableSteps;

    /**
     * Gets the value of the tableMaximumDifference property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableMaximumDifference() {
        if (this.tableMaximumDifference == null) {
            return "0.001";
        } else {
            return this.tableMaximumDifference;
        }
    }

    /**
     * Gets the value of the tableStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableStatus() {
        if (this.tableStatus == null) {
            return "disable";
        } else {
            return this.tableStatus;
        }
    }

    /**
     * Gets the value of the tableSteps property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableSteps() {
        if (this.tableSteps == null) {
            return "100";
        } else {
            return this.tableSteps;
        }
    }

    /**
     * Sets the value of the tableMaximumDifference property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableMaximumDifference(final String value) {
        this.tableMaximumDifference = value;
    }

    /**
     * Sets the value of the tableStatus property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableStatus(final String value) {
        this.tableStatus = value;
    }

    /**
     * Sets the value of the tableSteps property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableSteps(final String value) {
        this.tableSteps = value;
    }

}
