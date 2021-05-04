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

package org.jopendocument.model.style;

/**
 * 
 */
public class StyleMap {

    protected String styleApplyStyleName;
    protected String styleBaseCellAddress;
    protected String styleCondition;

    /**
     * Gets the value of the styleApplyStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleApplyStyleName() {
        return this.styleApplyStyleName;
    }

    /**
     * Gets the value of the styleBaseCellAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleBaseCellAddress() {
        return this.styleBaseCellAddress;
    }

    /**
     * Gets the value of the styleCondition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleCondition() {
        return this.styleCondition;
    }

    /**
     * Sets the value of the styleApplyStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleApplyStyleName(final String value) {
        this.styleApplyStyleName = value;
    }

    /**
     * Sets the value of the styleBaseCellAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleBaseCellAddress(final String value) {
        this.styleBaseCellAddress = value;
    }

    /**
     * Sets the value of the styleCondition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleCondition(final String value) {
        this.styleCondition = value;
    }

}
