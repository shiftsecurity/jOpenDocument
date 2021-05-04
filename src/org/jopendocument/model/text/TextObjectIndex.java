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
public class TextObjectIndex {

    protected TextIndexBody textIndexBody;
    protected String textName;
    protected TextObjectIndexSource textObjectIndexSource;
    protected String textProtected;
    protected String textStyleName;

    /**
     * Gets the value of the textIndexBody property.
     * 
     * @return possible object is {@link TextIndexBody }
     * 
     */
    public TextIndexBody getTextIndexBody() {
        return this.textIndexBody;
    }

    /**
     * Gets the value of the textName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextName() {
        return this.textName;
    }

    /**
     * Gets the value of the textObjectIndexSource property.
     * 
     * @return possible object is {@link TextObjectIndexSource }
     * 
     */
    public TextObjectIndexSource getTextObjectIndexSource() {
        return this.textObjectIndexSource;
    }

    /**
     * Gets the value of the textProtected property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextProtected() {
        if (this.textProtected == null) {
            return "false";
        } else {
            return this.textProtected;
        }
    }

    /**
     * Gets the value of the textStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStyleName() {
        return this.textStyleName;
    }

    /**
     * Sets the value of the textIndexBody property.
     * 
     * @param value allowed object is {@link TextIndexBody }
     * 
     */
    public void setTextIndexBody(final TextIndexBody value) {
        this.textIndexBody = value;
    }

    /**
     * Sets the value of the textName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextName(final String value) {
        this.textName = value;
    }

    /**
     * Sets the value of the textObjectIndexSource property.
     * 
     * @param value allowed object is {@link TextObjectIndexSource }
     * 
     */
    public void setTextObjectIndexSource(final TextObjectIndexSource value) {
        this.textObjectIndexSource = value;
    }

    /**
     * Sets the value of the textProtected property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextProtected(final String value) {
        this.textProtected = value;
    }

    /**
     * Sets the value of the textStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStyleName(final String value) {
        this.textStyleName = value;
    }

}
