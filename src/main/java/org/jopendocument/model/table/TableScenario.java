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
public class TableScenario {

    protected String tableBorderColor;
    protected String tableComment;
    protected String tableCopyBack;
    protected String tableCopyFormulas;
    protected String tableCopyStyles;
    protected String tableDisplayBorder;
    protected String tableIsActive;
    protected String tableScenarioRanges;

    /**
     * Gets the value of the tableBorderColor property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableBorderColor() {
        return this.tableBorderColor;
    }

    /**
     * Gets the value of the tableComment property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableComment() {
        return this.tableComment;
    }

    /**
     * Gets the value of the tableCopyBack property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCopyBack() {
        if (this.tableCopyBack == null) {
            return "true";
        } else {
            return this.tableCopyBack;
        }
    }

    /**
     * Gets the value of the tableCopyFormulas property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCopyFormulas() {
        if (this.tableCopyFormulas == null) {
            return "true";
        } else {
            return this.tableCopyFormulas;
        }
    }

    /**
     * Gets the value of the tableCopyStyles property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCopyStyles() {
        if (this.tableCopyStyles == null) {
            return "true";
        } else {
            return this.tableCopyStyles;
        }
    }

    /**
     * Gets the value of the tableDisplayBorder property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDisplayBorder() {
        if (this.tableDisplayBorder == null) {
            return "true";
        } else {
            return this.tableDisplayBorder;
        }
    }

    /**
     * Gets the value of the tableIsActive property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableIsActive() {
        return this.tableIsActive;
    }

    /**
     * Gets the value of the tableScenarioRanges property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableScenarioRanges() {
        return this.tableScenarioRanges;
    }

    /**
     * Sets the value of the tableBorderColor property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableBorderColor(final String value) {
        this.tableBorderColor = value;
    }

    /**
     * Sets the value of the tableComment property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableComment(final String value) {
        this.tableComment = value;
    }

    /**
     * Sets the value of the tableCopyBack property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCopyBack(final String value) {
        this.tableCopyBack = value;
    }

    /**
     * Sets the value of the tableCopyFormulas property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCopyFormulas(final String value) {
        this.tableCopyFormulas = value;
    }

    /**
     * Sets the value of the tableCopyStyles property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCopyStyles(final String value) {
        this.tableCopyStyles = value;
    }

    /**
     * Sets the value of the tableDisplayBorder property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDisplayBorder(final String value) {
        this.tableDisplayBorder = value;
    }

    /**
     * Sets the value of the tableIsActive property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableIsActive(final String value) {
        this.tableIsActive = value;
    }

    /**
     * Sets the value of the tableScenarioRanges property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableScenarioRanges(final String value) {
        this.tableScenarioRanges = value;
    }

}
