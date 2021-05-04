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

package org.jopendocument.model.text;

/**
 * 
 */
public class TextDatabaseSelect {

    protected String textCondition;
    protected String textDatabaseName;
    protected String textRowNumber;
    protected String textTableName;
    protected String textTableType;
    protected String value;

    /**
     * Gets the value of the textCondition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCondition() {
        return this.textCondition;
    }

    /**
     * Gets the value of the textDatabaseName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextDatabaseName() {
        return this.textDatabaseName;
    }

    /**
     * Gets the value of the textRowNumber property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextRowNumber() {
        return this.textRowNumber;
    }

    /**
     * Gets the value of the textTableName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextTableName() {
        return this.textTableName;
    }

    /**
     * Gets the value of the textTableType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextTableType() {
        return this.textTableType;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getvalue() {
        return this.value;
    }

    /**
     * Sets the value of the textCondition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCondition(final String value) {
        this.textCondition = value;
    }

    /**
     * Sets the value of the textDatabaseName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextDatabaseName(final String value) {
        this.textDatabaseName = value;
    }

    /**
     * Sets the value of the textRowNumber property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextRowNumber(final String value) {
        this.textRowNumber = value;
    }

    /**
     * Sets the value of the textTableName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextTableName(final String value) {
        this.textTableName = value;
    }

    /**
     * Sets the value of the textTableType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextTableType(final String value) {
        this.textTableType = value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setvalue(final String value) {
        this.value = value;
    }

}
