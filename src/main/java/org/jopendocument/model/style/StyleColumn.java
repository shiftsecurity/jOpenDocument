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
public class StyleColumn {

    protected String foMarginLeft;
    protected String foMarginRight;
    protected String styleRelWidth;

    /**
     * Gets the value of the foMarginLeft property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFoMarginLeft() {
        return this.foMarginLeft;
    }

    /**
     * Gets the value of the foMarginRight property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFoMarginRight() {
        return this.foMarginRight;
    }

    /**
     * Gets the value of the styleRelWidth property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleRelWidth() {
        return this.styleRelWidth;
    }

    /**
     * Sets the value of the foMarginLeft property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFoMarginLeft(final String value) {
        this.foMarginLeft = value;
    }

    /**
     * Sets the value of the foMarginRight property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFoMarginRight(final String value) {
        this.foMarginRight = value;
    }

    /**
     * Sets the value of the styleRelWidth property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleRelWidth(final String value) {
        this.styleRelWidth = value;
    }

}
