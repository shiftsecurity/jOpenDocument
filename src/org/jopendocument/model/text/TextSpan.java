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

import org.jopendocument.model.style.StyleStyle;

public class TextSpan {

    private String[] cutedValues;

    private StyleStyle textStyle;

    private String value;

    private boolean completed;

    public void concatValue(final String string) {
        if (completed) {
            throw new IllegalArgumentException("already completed");
        }
        if (string == null) {
            throw new IllegalArgumentException("Style null");
        }

        if (this.value == null) {
            this.value = string;
        } else {
            this.value += string;
        }
    }

    public String[] getCutedValues() {
        if (this.cutedValues == null) {
            final ArrayList<String> list = new ArrayList<String>(5);
            String s = "";
            final String v = this.getValue();
            for (int i = 0; i < v.length(); i++) {
                final char c = v.charAt(i);
                if (c == ' ') {
                    list.add(s);
                    s = "";
                }

                s += c;

            }
            if (s.trim().length() > 0) {
                list.add(s);
            }
            this.cutedValues = list.toArray(new String[] {});
        }
        return this.cutedValues;
    }

    /**
     * Gets the value of the textStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public StyleStyle getTextStyle() {
        return this.textStyle;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value of the textStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStyle(final StyleStyle value) {
        if (value == null) {
            throw new IllegalArgumentException("Style null");
        }
        this.textStyle = value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setValue(final String value) {
        if (completed) {
            throw new IllegalArgumentException("already completed");
        }
        if (value == null) {
            throw new IllegalArgumentException("null argument");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public void addTextSpan(TextSpan textspan) {
        // TODO Auto-generated method stub
        // FIXME TextSpan in TextSpan...
    }

    public void addTextElement(Object item) {
        // TODO Auto-generated method stub
        // FIXME LineBreak... in TextSpan...

    }

    public void completed() {
        this.completed = true;

    }

    public boolean isCompleted() {
        return completed;
    }
}
