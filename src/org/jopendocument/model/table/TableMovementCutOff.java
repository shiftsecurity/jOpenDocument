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
public class TableMovementCutOff {

    protected String tableEndPosition;
    protected String tableId;
    protected String tablePosition;
    protected String tableStartPosition;

    /**
     * Gets the value of the tableEndPosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableEndPosition() {
        return this.tableEndPosition;
    }

    /**
     * Gets the value of the tableId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableId() {
        return this.tableId;
    }

    /**
     * Gets the value of the tablePosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTablePosition() {
        return this.tablePosition;
    }

    /**
     * Gets the value of the tableStartPosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableStartPosition() {
        return this.tableStartPosition;
    }

    /**
     * Sets the value of the tableEndPosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableEndPosition(final String value) {
        this.tableEndPosition = value;
    }

    /**
     * Sets the value of the tableId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableId(final String value) {
        this.tableId = value;
    }

    /**
     * Sets the value of the tablePosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTablePosition(final String value) {
        this.tablePosition = value;
    }

    /**
     * Sets the value of the tableStartPosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableStartPosition(final String value) {
        this.tableStartPosition = value;
    }

}
