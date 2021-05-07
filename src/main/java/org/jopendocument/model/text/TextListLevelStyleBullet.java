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

import org.jopendocument.model.style.StyleListLevelProperties;
import org.jopendocument.model.style.StyleProperties;
import org.jopendocument.model.style.StyleStyle;
import org.jopendocument.model.style.StyleTextProperties;

/**
 * 
 */
public class TextListLevelStyleBullet extends StyleStyle {

    protected String styleNumPrefix;
    protected String styleNumSuffix;

    protected String textBulletChar;
    protected String textLevel;
    protected String textStyleName;
    private StyleListLevelProperties styleListLevelProperties;
    private StyleTextProperties styleTextProperties;

    /**
     * Gets the value of the styleNumPrefix property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleNumPrefix() {
        return this.styleNumPrefix;
    }

    /**
     * Gets the value of the styleNumSuffix property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleNumSuffix() {
        return this.styleNumSuffix;
    }

    /**
     * Gets the value of the textBulletChar property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextBulletChar() {
        return this.textBulletChar;
    }

    /**
     * Gets the value of the textLevel property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextLevel() {
        return this.textLevel;
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
     * Sets the value of the styleNumPrefix property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleNumPrefix(final String value) {
        this.styleNumPrefix = value;
    }

    /**
     * Sets the value of the styleNumSuffix property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleNumSuffix(final String value) {
        this.styleNumSuffix = value;
    }

    /**
     * Sets the value of the styleProperties property.
     * 
     * @param value allowed object is {@link StyleProperties }
     * 
     */
    public void setStyleProperties(final StyleProperties value) {
        this.styleProperties = value;
    }

    /**
     * Sets the value of the textBulletChar property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextBulletChar(final String value) {
        this.textBulletChar = value;
    }

    /**
     * Sets the value of the textLevel property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextLevel(final String value) {
        this.textLevel = value;
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

    public void setStyleListLevelProperties(StyleListLevelProperties props) {
        styleListLevelProperties = props;

    }

    public void setTextProperties(StyleTextProperties props) {
        styleTextProperties = props;

    }

}
