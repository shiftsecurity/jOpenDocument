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

public class StyleFontFace {

    private String fontFamily;
    private String fontFamilyGeneric;
    private String fontPitch;
    private String styleName;

    public String getFontFamily() {
        return this.fontFamily;
    }

    public String getFontFamilyGeneric() {
        return this.fontFamilyGeneric;
    }

    public String getFontPitch() {
        return this.fontPitch;
    }

    public String getStyleName() {
        return this.styleName;
    }

    public void setFontFamily(final String value) {
        this.fontFamily = value;

    }

    public void setFontFamilyGeneric(final String value) {
        this.fontFamilyGeneric = value;

    }

    public void setFontPitch(final String value) {
        this.fontPitch = value;
    }

    public void setStyleName(final String value) {
        this.styleName = value;

    }
}
