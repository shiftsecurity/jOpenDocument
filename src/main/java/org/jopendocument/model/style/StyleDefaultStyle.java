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

import org.jopendocument.io.StyleTableProperties;
import org.jopendocument.model.script.StyleGraphicProperties;

/**
 * 
 */
public class StyleDefaultStyle {

    protected String styleFamily;

    protected StyleProperties styleProperties;

    private StyleTextProperties styleTextProperties;
    private StyleParagraphProperties styleParagraphProperties;

    private StyleGraphicProperties styleGraphicsProperties;

    private StyleTableProperties styleTableProperties;

    /**
     * Gets the value of the styleFamily property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleFamily() {
        return this.styleFamily;
    }

    /**
     * Gets the value of the styleProperties property.
     * 
     * @return possible object is {@link StyleProperties }
     * 
     */
    public StyleProperties getStyleProperties() {
        return this.styleProperties;
    }

    public StyleTextProperties getStyleTextProperties() {
        return this.styleTextProperties;
    }

    /**
     * Sets the value of the styleFamily property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleFamily(final String value) {
        this.styleFamily = value;
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

    public void setStyleTextProperties(final StyleTextProperties value) {
        this.styleTextProperties = value;

    }

    public void setParagraphProperties(StyleParagraphProperties props) {
        this.styleParagraphProperties = props;

    }

    public void setGraphicProperties(StyleGraphicProperties props) {
        this.styleGraphicsProperties = props;

    }

    public void setTableProperties(StyleTableProperties props) {
        this.styleTableProperties = props;

    }
}
