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
public class TableCalculationSettings {

    protected String tableAutomaticFindLabels;
    protected String tableCaseSensitive;
    protected TableIteration tableIteration;
    protected TableNullDate tableNullDate;
    protected String tableNullYear;
    protected String tablePrecisionAsShown;
    protected String tableSearchCriteriaMustApplyToWholeCell;
    protected String tableUseRegularExpressions;

    /**
     * Gets the value of the tableAutomaticFindLabels property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableAutomaticFindLabels() {
        if (this.tableAutomaticFindLabels == null) {
            return "true";
        } else {
            return this.tableAutomaticFindLabels;
        }
    }

    /**
     * Gets the value of the tableCaseSensitive property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCaseSensitive() {
        if (this.tableCaseSensitive == null) {
            return "true";
        } else {
            return this.tableCaseSensitive;
        }
    }

    /**
     * Gets the value of the tableIteration property.
     * 
     * @return possible object is {@link TableIteration }
     * 
     */
    public TableIteration getTableIteration() {
        return this.tableIteration;
    }

    /**
     * Gets the value of the tableNullDate property.
     * 
     * @return possible object is {@link TableNullDate }
     * 
     */
    public TableNullDate getTableNullDate() {
        return this.tableNullDate;
    }

    /**
     * Gets the value of the tableNullYear property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableNullYear() {
        if (this.tableNullYear == null) {
            return "1930";
        } else {
            return this.tableNullYear;
        }
    }

    /**
     * Gets the value of the tablePrecisionAsShown property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTablePrecisionAsShown() {
        if (this.tablePrecisionAsShown == null) {
            return "false";
        } else {
            return this.tablePrecisionAsShown;
        }
    }

    /**
     * Gets the value of the tableSearchCriteriaMustApplyToWholeCell property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableSearchCriteriaMustApplyToWholeCell() {
        if (this.tableSearchCriteriaMustApplyToWholeCell == null) {
            return "true";
        } else {
            return this.tableSearchCriteriaMustApplyToWholeCell;
        }
    }

    /**
     * Gets the value of the tableUseRegularExpressions property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableUseRegularExpressions() {
        if (this.tableUseRegularExpressions == null) {
            return "true";
        } else {
            return this.tableUseRegularExpressions;
        }
    }

    /**
     * Sets the value of the tableAutomaticFindLabels property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableAutomaticFindLabels(final String value) {
        this.tableAutomaticFindLabels = value;
    }

    /**
     * Sets the value of the tableCaseSensitive property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCaseSensitive(final String value) {
        this.tableCaseSensitive = value;
    }

    /**
     * Sets the value of the tableIteration property.
     * 
     * @param value allowed object is {@link TableIteration }
     * 
     */
    public void setTableIteration(final TableIteration value) {
        this.tableIteration = value;
    }

    /**
     * Sets the value of the tableNullDate property.
     * 
     * @param value allowed object is {@link TableNullDate }
     * 
     */
    public void setTableNullDate(final TableNullDate value) {
        this.tableNullDate = value;
    }

    /**
     * Sets the value of the tableNullYear property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableNullYear(final String value) {
        this.tableNullYear = value;
    }

    /**
     * Sets the value of the tablePrecisionAsShown property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTablePrecisionAsShown(final String value) {
        this.tablePrecisionAsShown = value;
    }

    /**
     * Sets the value of the tableSearchCriteriaMustApplyToWholeCell property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableSearchCriteriaMustApplyToWholeCell(final String value) {
        this.tableSearchCriteriaMustApplyToWholeCell = value;
    }

    /**
     * Sets the value of the tableUseRegularExpressions property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableUseRegularExpressions(final String value) {
        this.tableUseRegularExpressions = value;
    }

}
