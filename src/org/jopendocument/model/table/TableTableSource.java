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
public class TableTableSource {

    protected String tableFilterName;
    protected String tableFilterOptions;
    protected String tableMode;
    protected String tableRefreshDelay;
    protected String tableTableName;
    protected String xlinkActuate;
    protected String xlinkHref;
    protected String xlinkType;

    /**
     * Gets the value of the tableFilterName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableFilterName() {
        return this.tableFilterName;
    }

    /**
     * Gets the value of the tableFilterOptions property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableFilterOptions() {
        return this.tableFilterOptions;
    }

    /**
     * Gets the value of the tableMode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableMode() {
        if (this.tableMode == null) {
            return "copy-all";
        } else {
            return this.tableMode;
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
     * Gets the value of the tableTableName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableTableName() {
        return this.tableTableName;
    }

    /**
     * Gets the value of the xlinkActuate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkActuate() {
        if (this.xlinkActuate == null) {
            return "onRequest";
        } else {
            return this.xlinkActuate;
        }
    }

    /**
     * Gets the value of the xlinkHref property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkHref() {
        return this.xlinkHref;
    }

    /**
     * Gets the value of the xlinkType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkType() {
        if (this.xlinkType == null) {
            return "simple";
        } else {
            return this.xlinkType;
        }
    }

    /**
     * Sets the value of the tableFilterName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableFilterName(final String value) {
        this.tableFilterName = value;
    }

    /**
     * Sets the value of the tableFilterOptions property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableFilterOptions(final String value) {
        this.tableFilterOptions = value;
    }

    /**
     * Sets the value of the tableMode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableMode(final String value) {
        this.tableMode = value;
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
     * Sets the value of the tableTableName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableTableName(final String value) {
        this.tableTableName = value;
    }

    /**
     * Sets the value of the xlinkActuate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkActuate(final String value) {
        this.xlinkActuate = value;
    }

    /**
     * Sets the value of the xlinkHref property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkHref(final String value) {
        this.xlinkHref = value;
    }

    /**
     * Sets the value of the xlinkType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkType(final String value) {
        this.xlinkType = value;
    }

}
