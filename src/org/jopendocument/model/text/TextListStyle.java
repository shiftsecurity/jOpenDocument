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

import java.util.ArrayList;
import java.util.List;

import org.jopendocument.model.style.StyleStyle;

/**
 * 
 */
public class TextListStyle extends StyleStyle {

    protected String textConsecutiveNumbering;
    private List<TextListLevelStyleBullet> textListLevelStyleBullets = new ArrayList<TextListLevelStyleBullet>();
    private List<TextListLevelStyleNumber> textListLevelStyleNumbers = new ArrayList<TextListLevelStyleNumber>();

    /**
     * Gets the value of the textConsecutiveNumbering property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextConsecutiveNumbering() {
        if (this.textConsecutiveNumbering == null) {
            return "false";
        } else {
            return this.textConsecutiveNumbering;
        }
    }

    /**
     * Sets the value of the textConsecutiveNumbering property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextConsecutiveNumbering(final String value) {
        this.textConsecutiveNumbering = value;
    }

    @Override
    public String toString() {
        return "TextListStyle:" + super.toString();
    }

    public void addListLevelStyleBullet(TextListLevelStyleBullet tls) {
        textListLevelStyleBullets.add(tls);

    }

    public void addListLevelStyleNumber(TextListLevelStyleNumber tls) {
        textListLevelStyleNumbers.add(tls);

    }
}
